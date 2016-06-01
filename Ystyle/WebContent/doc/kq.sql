/*
Navicat MySQL Data Transfer

Source Server         : afei
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : kq

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2016-06-01 15:25:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `toid` varchar(40) NOT NULL default '',
  `username` varchar(20) default NULL,
  `starttime` varchar(40) default NULL,
  `endtime` varchar(40) default NULL,
  PRIMARY KEY  (`toid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
