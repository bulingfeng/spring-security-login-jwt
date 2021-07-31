package com.bulingfeng.login.config;


import com.bulingfeng.login.filter.JwtFilter;
import com.bulingfeng.login.service.UserServie;
import com.bulingfeng.login.service.impl.CustomUserService;
import com.bulingfeng.login.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;



@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private PasswordEncoder myPasswordEncoder;

	private CustomUserService myCustomUserService;

	private ObjectMapper objectMapper;

	private JwtFilter jwtFilter;

	private JwtUtil jwtUtil;

	private UserServie userServie;

	public SecurityConfig(PasswordEncoder myPasswordEncoder,
						  CustomUserService myCustomUserService,
						  ObjectMapper objectMapper,
						  JwtFilter jwtFilter,
						  JwtUtil jwtUtil,
						  UserServie userServie) {
		this.myPasswordEncoder=myPasswordEncoder;
		this.myCustomUserService = myCustomUserService;
		this.objectMapper = objectMapper;
		this.jwtFilter = jwtFilter;
		this.jwtUtil=jwtUtil;
		this.userServie=userServie;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.authenticationProvider(authenticationProvider())
				// 加上以下这一句代表：禁止使用session进行登录，只能用token来实现访问。
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.httpBasic()
				//未登录时，进行json格式的提示，很喜欢这种写法，不用单独写一个又一个的类
				.authenticationEntryPoint((request,response,authException) -> {
					response.setContentType("application/json;charset=utf-8");
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					PrintWriter out = response.getWriter();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("code",403);
					map.put("message","未登录");
					out.write(objectMapper.writeValueAsString(map));
					out.flush();
					out.close();
				})

				.and()
				.authorizeRequests()
				.antMatchers("/user/**").hasRole("ADMIN")
				.anyRequest().authenticated() //必须授权才能范围

				.and()
				.formLogin() //使用自带的登录
				.permitAll()
				//登录失败，返回json
				.failureHandler((request,response,ex) -> {
					response.setContentType("application/json;charset=utf-8");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					PrintWriter out = response.getWriter();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("code",401);
					if (ex instanceof UsernameNotFoundException || ex instanceof BadCredentialsException) {
						map.put("message","用户名或密码错误");
					} else if (ex instanceof DisabledException) {
						map.put("message","账户被禁用");
					} else {
						map.put("message","登录失败!");
					}
					out.write(objectMapper.writeValueAsString(map));
					out.flush();
					out.close();
				})
				//登录成功，返回json
				.successHandler((request,response,authentication) -> {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("code",200);
					map.put("message","登录成功");
					map.put("data",authentication);
					response.setContentType("application/json;charset=utf-8");
					String username=request.getParameter("username");
					String password=request.getParameter("password");
					response.setHeader(JwtFilter.tokenHeader,JwtFilter.tokenHead+" " + jwtUtil.createJwtToken(myCustomUserService.loadUserByUsername(username)));
					PrintWriter out = response.getWriter();
					out.write(objectMapper.writeValueAsString(map));
					out.flush();
					out.close();
				})
				.and()
				.exceptionHandling()
				//没有权限，返回json
				.accessDeniedHandler((request,response,ex) -> {
					response.setContentType("application/json;charset=utf-8");
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					PrintWriter out = response.getWriter();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("code",403);
					map.put("message", "权限不足");
					out.write(objectMapper.writeValueAsString(map));
					out.flush();
					out.close();
				})
				.and()
				.logout()
				//退出成功，返回json
				.logoutSuccessHandler((request,response,authentication) -> {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("code",200);
					map.put("message","退出成功");
					map.put("data",authentication);
					response.setContentType("application/json;charset=utf-8");
					PrintWriter out = response.getWriter();
					out.write(objectMapper.writeValueAsString(map));
					out.flush();
					out.close();
				})
				.permitAll();
		//开启跨域访问
		http.cors().disable();
		//开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
		http.csrf().disable();
		//添加token的过滤器，并且在username和password认证之前
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) {
		//对于在header里面增加token等类似情况，放行所有OPTIONS请求。
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
//		web.ignoring().antMatchers("/user/token");
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		//对默认的UserDetailsService进行覆盖
		authenticationProvider.setUserDetailsService(myCustomUserService);
		authenticationProvider.setPasswordEncoder(myPasswordEncoder);
		return authenticationProvider;
	}

}