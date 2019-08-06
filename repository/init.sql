/*
 Navicat MySQL Data Transfer

 Source Server         : mac-dev
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : yugh

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 06/08/2019 11:48:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `stock` int(11) unsigned NOT NULL COMMENT '商品库存',
  `version` int(2) DEFAULT NULL COMMENT '版本号',
  `token_time` datetime NOT NULL COMMENT '乐观锁时间戳',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods
-- ----------------------------
BEGIN;
INSERT INTO `goods` VALUES (1, 'product', 1090137, 1, '2019-07-02 00:13:07');
COMMIT;

-- ----------------------------
-- Table structure for goods_dict
-- ----------------------------
DROP TABLE IF EXISTS `goods_dict`;
CREATE TABLE `goods_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` bigint(20) NOT NULL,
  `updated_at` bigint(20) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `bar_code` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `product_specification_id` bigint(20) DEFAULT NULL,
  `valid_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5197 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of goods_dict
-- ----------------------------
BEGIN;
INSERT INTO `goods_dict` VALUES (1, 1553653288815, 1553653347677, 1, 'Box00001', 4, 10, 1);
COMMIT;

-- ----------------------------
-- Table structure for pay_order
-- ----------------------------
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(32) COLLATE utf8_bin NOT NULL,
  `pay_money` decimal(7,2) NOT NULL,
  `created_At` bigint(20) NOT NULL,
  `updated_At` bigint(20) NOT NULL,
  `version` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name_Zh` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称：管理员；用户',
  `name` varchar(11) DEFAULT NULL COMMENT '角色：ROLE_ADMIN;ROLE_USER',
  `created_At` bigint(20) NOT NULL,
  `updated_At` bigint(20) NOT NULL,
  `version` int(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '用户', 'ROLE_USER', 1554717666, 1554717666, 0);
INSERT INTO `sys_role` VALUES (2, '管理员', 'ROLE_ADMIN', 1554717666, 1554717666, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(11) NOT NULL DEFAULT '',
  `password` varchar(64) NOT NULL DEFAULT '',
  `phone` varchar(11) NOT NULL,
  `created_At` bigint(20) NOT NULL,
  `updated_At` bigint(20) NOT NULL,
  `version` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (16, 'yugh', '$2a$10$7Mb.hgXnr3vBaegliALHveI33pkSP0UrfzpfrKSKRIhThRrsvu52e', '17611255755', 1554794918133, 1554794918133, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `created_At` bigint(20) NOT NULL,
  `updated_At` bigint(20) NOT NULL,
  `version` int(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (5, 16, 1, 1554794918173, 1554794918174, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
