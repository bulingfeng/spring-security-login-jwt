/*
 Navicat Premium Data Transfer

 Source Server         : 本地mac
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : localhost:3306
 Source Schema         : spring-boot-login

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 21/08/2020 13:29:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 'ROLE_ADMIN');
INSERT INTO `sys_role` VALUES (2, 'ROLE_USER');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(2000) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'root', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ROLE_ADMIN');
INSERT INTO `sys_user` VALUES (2, 'sang', 'sang', 'ROLE_USER');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_roles`;
CREATE TABLE `sys_user_roles` (
  `sys_user_id` int(11) NOT NULL,
  `sys_roles_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_roles
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_roles` VALUES (1, 1);
INSERT INTO `sys_user_roles` VALUES (2, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
