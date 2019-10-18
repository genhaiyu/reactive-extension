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

 Date: 30/11/2018 23:42:13
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
INSERT INTO `goods` VALUES (1, 'product', 1101110, 1, '2018-11-30 22:06:20');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
