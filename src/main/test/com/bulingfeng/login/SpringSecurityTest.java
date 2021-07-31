package com.bulingfeng.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


/**
 * @Author:bulingfeng
 * @Date: 2021/7/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSecurityTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp(){
        mockMvc= MockMvcBuilders
                .webAppContextSetup(context)
                // 添加spring-security的验证
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(roles="ADMIN")
    @Test
    public void giveAuth_with200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/get"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
