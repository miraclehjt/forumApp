/*
Navicat MySQL Data Transfer

Source Server         : chenzl
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : studentforumapp

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2017-06-05 15:23:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_area`
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `area_id` varchar(5) NOT NULL,
  `area_name` varchar(20) NOT NULL,
  `status` char(255) NOT NULL,
  `remark` varchar(100) default NULL,
  `boss_id` varchar(6) default NULL,
  `area_prov` varchar(6) default NULL,
  `cps_id` varchar(4) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES ('11111', '成都', '1', null, '1', '四川', '1');

-- ----------------------------
-- Table structure for `sys_cscapp_keyword`
-- ----------------------------
DROP TABLE IF EXISTS `sys_cscapp_keyword`;
CREATE TABLE `sys_cscapp_keyword` (
  `keyword_id` int(10) NOT NULL auto_increment,
  `keyword` varchar(20) default NULL,
  `creat_time` date default NULL,
  `create_user` varchar(10) default NULL,
  `update_time` date default NULL,
  `update_user` varchar(10) default NULL,
  `keyword_level` int(1) default NULL,
  PRIMARY KEY  (`keyword_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_cscapp_keyword
-- ----------------------------
INSERT INTO `sys_cscapp_keyword` VALUES ('1', 'java', '2017-04-23', '陈造龙', '2017-05-04', 'STU001', '1');
INSERT INTO `sys_cscapp_keyword` VALUES ('3', 'css', '2017-04-26', '1', '2017-04-26', '1', null);
INSERT INTO `sys_cscapp_keyword` VALUES ('4', 'ssh', '2017-04-26', '1', '2017-05-04', 'STU001', null);
INSERT INTO `sys_cscapp_keyword` VALUES ('8', '斗战胜佛', '2017-04-26', '1', '2017-05-04', 'STU001', null);

-- ----------------------------
-- Table structure for `sys_cscapp_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `sys_cscapp_user_info`;
CREATE TABLE `sys_cscapp_user_info` (
  `user_id` varchar(10) NOT NULL,
  `portrait` varchar(100) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_cscapp_user_info
-- ----------------------------
INSERT INTO `sys_cscapp_user_info` VALUES ('STU001', '/cscapp/jpg/20170414/20170414100641KHS92892OKN6_1000x500.jpg');

-- ----------------------------
-- Table structure for `sys_forum_audit_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_forum_audit_user`;
CREATE TABLE `sys_forum_audit_user` (
  `user_id` varchar(10) NOT NULL,
  `post-type` int(1) default NULL,
  `area_id` varchar(10) default NULL,
  `state` int(1) default NULL,
  `category_id` int(10) default NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提问审核人员';

-- ----------------------------
-- Records of sys_forum_audit_user
-- ----------------------------
INSERT INTO `sys_forum_audit_user` VALUES ('STU001', '1', '11111', '1', '1');
INSERT INTO `sys_forum_audit_user` VALUES ('STU002', '1', '11111', '1', '4');

-- ----------------------------
-- Table structure for `sys_forum_category`
-- ----------------------------
DROP TABLE IF EXISTS `sys_forum_category`;
CREATE TABLE `sys_forum_category` (
  `category_id` int(12) NOT NULL auto_increment,
  `category_name` varchar(100) default NULL,
  `category_type` int(1) default NULL,
  `state` int(1) default NULL,
  `create_user` varchar(20) default NULL,
  `update_user` varchar(20) default NULL,
  `create_date` date default NULL,
  `update_date` date default NULL,
  PRIMARY KEY  (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛版块';

-- ----------------------------
-- Records of sys_forum_category
-- ----------------------------
INSERT INTO `sys_forum_category` VALUES ('1', 'JAVA', '1', '1', '陈造龙', '陈造龙', '2017-04-13', '2017-04-13');
INSERT INTO `sys_forum_category` VALUES ('3', 'C语言', '1', '1', 'null', 'null', '2017-04-14', '2017-04-14');
INSERT INTO `sys_forum_category` VALUES ('4', 'JAVASCRIPT2', '1', '1', 'null', 'null', '2017-04-14', '2017-04-17');

-- ----------------------------
-- Table structure for `sys_forum_post`
-- ----------------------------
DROP TABLE IF EXISTS `sys_forum_post`;
CREATE TABLE `sys_forum_post` (
  `post_id` int(12) NOT NULL auto_increment,
  `category_id` int(12) default NULL,
  `post_title` varchar(100) default NULL,
  `post_content` varchar(500) default NULL,
  `post_type` char(1) default NULL,
  `kb_id` int(12) default NULL,
  `kb_title` varchar(100) default NULL,
  `area_id` varchar(20) default NULL,
  `state` int(1) default NULL,
  `reply_num` int(20) default NULL,
  `new_reply_num` int(20) default NULL,
  `star_num` int(20) default NULL,
  `create_user` varchar(20) default NULL,
  `update_user` varchar(20) default NULL,
  `create_date` date default NULL,
  `update_date` date default NULL,
  `audit_state` int(1) default NULL,
  `audit_user` varchar(20) default NULL,
  `audit_comment` varchar(100) default NULL,
  `audit_date` date default NULL,
  `new_debate_num` int(20) default NULL,
  PRIMARY KEY  (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='论坛社区提问\r\n';

-- ----------------------------
-- Records of sys_forum_post
-- ----------------------------
INSERT INTO `sys_forum_post` VALUES ('1', '1', 'javasssssssssssss', 'java学习ssssssssssssssss', '1', '1', '学习知识', '11111', '1', '23', '0', '2', 'STU001', 'STU001', '2017-04-15', '2017-04-15', '1', '陈造龙', '审核通过', '2017-04-15', null);
INSERT INTO `sys_forum_post` VALUES ('2', '4', 'javascript', '哪位大神有关于javascpt的文档教程，在线等', '1', null, null, '11111', '1', '3', '1', '3', 'STU001', 'STU001', '2017-05-09', '2017-05-09', '1', 'STU001', '审核通过', '2017-05-09', null);
INSERT INTO `sys_forum_post` VALUES ('3', '4', 'js', '学习', '1', null, null, '11111', '1', '1', '0', '1', 'STU001', 'STU001', '2017-05-09', '2017-05-09', '1', 'STU001', '审核通过', '2017-05-09', null);
INSERT INTO `sys_forum_post` VALUES ('4', '3', 'html页面实现和jsp的不同', '了解一下学校的召唤', '1', null, null, '11111', '1', '2', '0', '2', 'STU001', 'STU001', '2017-05-09', '2017-05-09', '1', 'STU001', '审核通过', '2017-05-10', null);
INSERT INTO `sys_forum_post` VALUES ('5', '3', '呵呵呵呵', 'c需要去别你都', '1', null, null, '11111', '1', '0', '0', '0', 'STU001', 'STU001', '2017-05-09', '2017-05-09', '1', 'STU001', '审核通过', '2017-05-31', null);
INSERT INTO `sys_forum_post` VALUES ('6', '1', '哈哈哈', '安全网络科技有限公司', '1', null, null, '11111', '1', '10', '4', '4', 'STU001', 'STU001', '2017-05-10', '2017-05-10', '1', 'STU001', '审核通过', '2017-05-10', null);
INSERT INTO `sys_forum_post` VALUES ('7', '1', '哈哈哈', '大早上的你笑啥这么开心', '1', null, null, '11111', '1', '1', '0', '1', 'STU001', 'STU001', '2017-05-10', '2017-05-10', '2', 'STU001', '不通过', '2017-05-10', null);
INSERT INTO `sys_forum_post` VALUES ('8', '1', 'high哈', '什么原因呢', '1', null, null, '11111', '1', '2', '2', '2', 'STU001', 'STU001', '2017-05-10', '2017-05-10', '1', 'STU001', '审核通过', '2017-05-10', null);
INSERT INTO `sys_forum_post` VALUES ('9', '1', '哈哈哈哈哈', '哈哈哈哈哈哈', '1', null, null, '11111', '1', '0', '0', '1', 'STU001', 'STU001', '2017-05-31', '2017-05-31', '1', 'STU001', '审核通过', '2017-05-31', null);
INSERT INTO `sys_forum_post` VALUES ('10', '3', '这是什么也会', 'c不会sss', '1', null, null, '11111', '1', '1', '1', '1', 'STU001', 'STU001', '2017-05-31', '2017-05-31', '1', 'STU001', '审核通过', '2017-05-31', null);
INSERT INTO `sys_forum_post` VALUES ('11', '3', '我是009', '你是谁？', '1', null, null, '11111', '1', '0', '0', '1', 'STU009', 'STU009', '2017-05-31', '2017-05-31', '1', 'STU001', '审核通过', '2017-05-31', null);

-- ----------------------------
-- Table structure for `sys_forum_post_click`
-- ----------------------------
DROP TABLE IF EXISTS `sys_forum_post_click`;
CREATE TABLE `sys_forum_post_click` (
  `post_click_id` int(4) NOT NULL auto_increment,
  `post_id` int(4) NOT NULL,
  `create_user` varchar(10) default NULL,
  `create_date` date default NULL,
  PRIMARY KEY  (`post_click_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_forum_post_click
-- ----------------------------
INSERT INTO `sys_forum_post_click` VALUES ('1', '1', '陈造龙', '2017-04-17');
INSERT INTO `sys_forum_post_click` VALUES ('2', '1', 'STU001', '2017-05-07');
INSERT INTO `sys_forum_post_click` VALUES ('3', '1', 'STU001', '2017-05-07');
INSERT INTO `sys_forum_post_click` VALUES ('4', '1', 'STU001', '2017-05-07');
INSERT INTO `sys_forum_post_click` VALUES ('5', '1', 'STU001', '2017-05-07');
INSERT INTO `sys_forum_post_click` VALUES ('6', '1', 'STU001', '2017-05-07');
INSERT INTO `sys_forum_post_click` VALUES ('7', '1', 'STU001', '2017-05-07');
INSERT INTO `sys_forum_post_click` VALUES ('8', '1', 'STU001', '2017-05-07');
INSERT INTO `sys_forum_post_click` VALUES ('9', '1', 'STU001', '2017-05-07');
INSERT INTO `sys_forum_post_click` VALUES ('10', '1', 'STU001', '2017-05-07');
INSERT INTO `sys_forum_post_click` VALUES ('11', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('12', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('13', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('14', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('15', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('16', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('17', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('18', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('19', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('20', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('21', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('22', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('23', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('24', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('25', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('26', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('27', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('28', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('29', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('30', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('31', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('32', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('33', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('34', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('35', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('36', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('37', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('38', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('39', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('40', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('41', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('42', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('43', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('44', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('45', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('46', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('47', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('48', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('49', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('50', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('51', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('52', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('53', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('54', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('55', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('56', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('57', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('58', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('59', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('60', '1', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_click` VALUES ('61', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('62', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('63', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('64', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('65', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('66', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('67', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('68', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('69', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('70', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('71', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('72', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('73', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('74', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('75', '1', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('76', '2', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('77', '2', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('78', '2', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('79', '3', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('80', '3', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_click` VALUES ('81', '4', 'STU001', '2017-05-10');
INSERT INTO `sys_forum_post_click` VALUES ('82', '7', 'STU001', '2017-05-10');
INSERT INTO `sys_forum_post_click` VALUES ('83', '7', 'STU001', '2017-05-10');
INSERT INTO `sys_forum_post_click` VALUES ('84', '1', 'STU001', '2017-05-10');
INSERT INTO `sys_forum_post_click` VALUES ('85', '1', 'STU001', '2017-05-10');
INSERT INTO `sys_forum_post_click` VALUES ('86', '7', 'STU001', '2017-05-10');
INSERT INTO `sys_forum_post_click` VALUES ('87', '1', 'STU001', '2017-05-10');
INSERT INTO `sys_forum_post_click` VALUES ('88', '1', 'STU001', '2017-05-10');
INSERT INTO `sys_forum_post_click` VALUES ('89', '6', 'STU001', '2017-05-11');
INSERT INTO `sys_forum_post_click` VALUES ('90', '6', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('91', '3', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('92', '3', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('93', '4', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('94', '4', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('95', '4', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('96', '6', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('97', '6', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('98', '6', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('99', '4', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('100', '4', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('101', '1', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('102', '1', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('103', '1', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('104', '1', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('105', '1', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('106', '1', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_click` VALUES ('107', '7', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('108', '7', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('109', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('110', '8', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('111', '8', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('112', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('113', '8', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('114', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('115', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('116', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('117', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('118', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('119', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('120', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('121', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('122', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('123', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('124', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('125', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('126', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('127', '1', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('128', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('129', '6', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('130', '2', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('131', '2', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('132', '2', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('133', '4', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('134', '4', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_click` VALUES ('135', '4', 'STU001', '2017-05-17');
INSERT INTO `sys_forum_post_click` VALUES ('136', '4', 'STU001', '2017-05-17');
INSERT INTO `sys_forum_post_click` VALUES ('137', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('138', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('139', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('140', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('141', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('142', '4', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('143', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('144', '6', 'STU002', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('145', '6', 'STU002', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('146', '6', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('147', '6', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('148', '6', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('149', '6', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('150', '6', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('151', '4', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('152', '4', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('153', '2', 'STU004', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('154', '2', 'STU004', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('155', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('156', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('157', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('158', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('159', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('160', '6', 'STU001', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('161', '6', 'STU004', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('162', '6', 'STU004', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('163', '6', 'STU004', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('164', '6', 'STU004', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('165', '6', 'STU004', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('166', '6', 'STU004', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('167', '6', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('168', '6', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('169', '2', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('170', '4', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('171', '6', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('172', '2', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('173', '2', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('174', '6', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('175', '6', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('176', '6', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('177', '6', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('178', '6', 'STU009', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('179', '6', 'STU009', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('180', '6', 'STU009', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('181', '6', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('182', '8', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('183', '8', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('184', '8', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('185', '8', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('186', '8', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('187', '6', 'STU007', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('188', '6', 'STU007', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('189', '10', 'STU007', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('190', '10', 'STU007', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('191', '2', 'STU009', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('192', '9', 'STU009', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('193', '9', 'STU009', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('194', '11', 'STU009', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('195', '11', 'STU009', '2017-05-31');
INSERT INTO `sys_forum_post_click` VALUES ('196', '6', 'STU009', '2017-05-31');

-- ----------------------------
-- Table structure for `sys_forum_post_reply`
-- ----------------------------
DROP TABLE IF EXISTS `sys_forum_post_reply`;
CREATE TABLE `sys_forum_post_reply` (
  `post_reply_id` int(20) NOT NULL auto_increment,
  `post_id` int(20) default NULL,
  `post_reply_content` varchar(255) default NULL,
  `debate_num` int(12) default NULL,
  `reply_is_new` int(1) default NULL,
  `new_debate_num` int(12) default NULL,
  `state` int(1) default NULL,
  `sticky` int(1) default NULL,
  `create_user` varchar(20) default NULL,
  `update_user` varchar(20) default NULL,
  `create_date` date default NULL,
  `update_date` date default NULL,
  `new_reply_num` int(20) default NULL,
  PRIMARY KEY  (`post_reply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提问回复表';

-- ----------------------------
-- Records of sys_forum_post_reply
-- ----------------------------
INSERT INTO `sys_forum_post_reply` VALUES ('1', '1', '测试sssssssss', '3', '0', '0', '1', '0', 'STU001', '1', '2017-04-21', '2017-04-21', null);
INSERT INTO `sys_forum_post_reply` VALUES ('11', '1', '海伦凯勒', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('12', '1', '海伦凯勒', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('13', '1', '海伦凯勒', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('14', '1', '海伦凯勒', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('15', '1', '海伦凯勒', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('16', '1', '海伦凯勒', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('17', '1', '', '0', '0', '0', '0', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('18', '1', '哈哈', '1', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('19', '1', '你好棒哦(´-ω-`)', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('20', '1', '你好棒哦(´-ω-`)', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('21', '1', '什么情况，为什么发送失败，保存失败什么原因god，方法没什么问题啊:scream:', '1', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('22', '1', '什么情况，为什么发送失败，保存失败什么原因god，方法没什么问题啊:scream:', '1', '0', '0', '1', '1', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('23', '1', '！:blush:11', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-08', '2017-05-08', null);
INSERT INTO `sys_forum_post_reply` VALUES ('24', '1', '早上', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-09', '2017-05-09', null);
INSERT INTO `sys_forum_post_reply` VALUES ('25', '1', '早上', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-09', '2017-05-09', null);
INSERT INTO `sys_forum_post_reply` VALUES ('26', '1', '早上', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-09', '2017-05-09', null);
INSERT INTO `sys_forum_post_reply` VALUES ('27', '1', '呵呵呵呵', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-09', '2017-05-09', null);
INSERT INTO `sys_forum_post_reply` VALUES ('29', '1', '呵呵呵呵', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-09', '2017-05-09', null);
INSERT INTO `sys_forum_post_reply` VALUES ('30', '1', '你好请问一下就好了', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-09', '2017-05-09', null);
INSERT INTO `sys_forum_post_reply` VALUES ('31', '1', '哈咯', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-09', '2017-05-09', null);
INSERT INTO `sys_forum_post_reply` VALUES ('32', '1', 'hi哈哈哈哈哈哈哈', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-09', '2017-05-09', null);
INSERT INTO `sys_forum_post_reply` VALUES ('33', '1', '卢以辉', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-09', '2017-05-09', null);
INSERT INTO `sys_forum_post_reply` VALUES ('34', '2', '可以百度查一下', '2', '0', '1', '1', null, 'STU001', 'STU001', '2017-05-09', '2017-05-09', null);
INSERT INTO `sys_forum_post_reply` VALUES ('35', '3', '不问你问谁', '0', '0', '0', '1', null, 'STU001', 'STU001', '2017-05-09', '2017-05-09', null);
INSERT INTO `sys_forum_post_reply` VALUES ('36', '4', '百度查一下就知道了', '2', '0', '1', '1', null, 'STU001', 'STU001', '2017-05-10', '2017-05-10', null);
INSERT INTO `sys_forum_post_reply` VALUES ('37', '7', '因为开心，所以就开心咯', '0', '0', '0', '1', null, 'STU001', 'STU001', '2017-05-10', '2017-05-10', null);
INSERT INTO `sys_forum_post_reply` VALUES ('38', '1', '你好，个错误', '0', '0', '0', '1', '0', 'STU001', 'STU001', '2017-05-10', '2017-05-10', null);
INSERT INTO `sys_forum_post_reply` VALUES ('42', '6', '哈哈哈哈哈哈', '2', '0', '0', '1', null, 'STU001', 'STU001', '2017-05-14', '2017-05-14', null);
INSERT INTO `sys_forum_post_reply` VALUES ('43', '6', '陈造龙回复', '5', '0', '0', '1', null, 'STU001', 'STU001', '2017-05-14', '2017-05-14', null);
INSERT INTO `sys_forum_post_reply` VALUES ('44', '6', '再来一条亲眷', '0', '0', '0', '1', null, 'STU001', 'STU001', '2017-05-14', '2017-05-14', null);
INSERT INTO `sys_forum_post_reply` VALUES ('46', '6', '再来一条', '3', '0', '0', '1', null, 'STU001', 'STU001', '2017-05-14', '2017-05-14', null);
INSERT INTO `sys_forum_post_reply` VALUES ('47', '2', '你好棒', '0', '0', '0', '1', null, 'STU001', 'STU001', '2017-05-14', '2017-05-14', null);
INSERT INTO `sys_forum_post_reply` VALUES ('48', '4', '哈哈', '0', '0', '0', '1', null, 'STU001', 'STU001', '2017-05-14', '2017-05-14', null);
INSERT INTO `sys_forum_post_reply` VALUES ('51', '6', '你好', '0', '0', '0', '1', null, 'STU001', 'STU001', '2017-05-31', '2017-05-31', null);
INSERT INTO `sys_forum_post_reply` VALUES ('52', '6', '哈哈哈哈哈', '1', '0', '0', '1', null, 'STU001', 'STU001', '2017-05-31', '2017-05-31', null);
INSERT INTO `sys_forum_post_reply` VALUES ('56', '6', '你好，我是陈造龙', '0', '0', '0', '1', null, 'STU004', 'STU004', '2017-05-31', '2017-05-31', null);
INSERT INTO `sys_forum_post_reply` VALUES ('57', '6', '我是羊维忠', '1', '0', '1', '1', null, 'STU004', 'STU004', '2017-05-31', '2017-05-31', null);
INSERT INTO `sys_forum_post_reply` VALUES ('58', '6', '嗨咯', '0', '0', '0', '1', null, 'STU005', 'STU005', '2017-05-31', '2017-05-31', null);
INSERT INTO `sys_forum_post_reply` VALUES ('59', '2', '哈哈哈哈哈', '0', '0', '0', '1', null, 'STU005', 'STU005', '2017-05-31', '2017-05-31', null);
INSERT INTO `sys_forum_post_reply` VALUES ('60', '6', '有前途', '2', '0', '0', '1', null, 'STU009', 'STU009', '2017-05-31', '2017-05-31', null);
INSERT INTO `sys_forum_post_reply` VALUES ('61', '8', '百度一下就好了', '2', '0', '0', '1', null, 'STU008', 'STU008', '2017-05-31', '2017-05-31', null);
INSERT INTO `sys_forum_post_reply` VALUES ('62', '8', '呵呵呵呵', '0', '1', '0', '1', null, 'STU008', 'STU008', '2017-05-31', '2017-05-31', null);
INSERT INTO `sys_forum_post_reply` VALUES ('63', '10', '哈哈哈', '0', '0', '0', '1', null, 'STU007', 'STU007', '2017-05-31', '2017-05-31', null);

-- ----------------------------
-- Table structure for `sys_forum_post_star`
-- ----------------------------
DROP TABLE IF EXISTS `sys_forum_post_star`;
CREATE TABLE `sys_forum_post_star` (
  `post_star_id` int(20) NOT NULL auto_increment,
  `post_id` int(20) default NULL,
  `user_id` varchar(20) default NULL,
  `create_user` varchar(20) default NULL,
  `create_date` date default NULL,
  PRIMARY KEY  (`post_star_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='点赞明细表';

-- ----------------------------
-- Records of sys_forum_post_star
-- ----------------------------
INSERT INTO `sys_forum_post_star` VALUES ('1', '1', '1', 'STU001', '2017-04-21');
INSERT INTO `sys_forum_post_star` VALUES ('33', '1', 'STU001', 'STU001', '2017-05-08');
INSERT INTO `sys_forum_post_star` VALUES ('34', '2', 'STU001', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_star` VALUES ('35', '3', 'STU001', 'STU001', '2017-05-09');
INSERT INTO `sys_forum_post_star` VALUES ('36', '4', 'STU001', 'STU001', '2017-05-10');
INSERT INTO `sys_forum_post_star` VALUES ('37', '7', 'STU001', 'STU001', '2017-05-10');
INSERT INTO `sys_forum_post_star` VALUES ('38', '6', 'STU001', 'STU001', '2017-05-12');
INSERT INTO `sys_forum_post_star` VALUES ('39', '8', 'STU001', 'STU001', '2017-05-14');
INSERT INTO `sys_forum_post_star` VALUES ('40', '6', 'STU002', 'STU002', '2017-05-31');
INSERT INTO `sys_forum_post_star` VALUES ('41', '6', 'STU008', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_star` VALUES ('42', '4', 'STU008', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_star` VALUES ('43', '2', 'STU004', 'STU004', '2017-05-31');
INSERT INTO `sys_forum_post_star` VALUES ('44', '6', 'STU005', 'STU005', '2017-05-31');
INSERT INTO `sys_forum_post_star` VALUES ('45', '8', 'STU008', 'STU008', '2017-05-31');
INSERT INTO `sys_forum_post_star` VALUES ('46', '10', 'STU007', 'STU007', '2017-05-31');
INSERT INTO `sys_forum_post_star` VALUES ('47', '2', 'STU009', 'STU009', '2017-05-31');
INSERT INTO `sys_forum_post_star` VALUES ('48', '9', 'STU009', 'STU009', '2017-05-31');
INSERT INTO `sys_forum_post_star` VALUES ('49', '11', 'STU009', 'STU009', '2017-05-31');

-- ----------------------------
-- Table structure for `sys_forum_recommend_post`
-- ----------------------------
DROP TABLE IF EXISTS `sys_forum_recommend_post`;
CREATE TABLE `sys_forum_recommend_post` (
  `recommend_post_id` int(4) NOT NULL auto_increment,
  `post_id` int(4) NOT NULL,
  `area_id` varchar(20) default NULL,
  `state` int(1) default NULL,
  `create_user` varchar(20) default NULL,
  `update_user` varchar(20) default NULL,
  `create_date` date default NULL,
  `update_date` date default NULL,
  PRIMARY KEY  (`recommend_post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推荐提问表';

-- ----------------------------
-- Records of sys_forum_recommend_post
-- ----------------------------
INSERT INTO `sys_forum_recommend_post` VALUES ('2', '2', '11111', '1', 'STU001', 'STU001', '2017-05-10', '2017-05-10');
INSERT INTO `sys_forum_recommend_post` VALUES ('4', '6', '11111', '1', 'STU001', 'STU001', '2017-05-10', '2017-05-10');
INSERT INTO `sys_forum_recommend_post` VALUES ('5', '4', '11111', '1', 'STU001', 'STU001', '2017-05-10', '2017-05-10');
INSERT INTO `sys_forum_recommend_post` VALUES ('6', '1', '11111', '1', 'STU001', 'STU001', '2017-05-10', '2017-05-10');

-- ----------------------------
-- Table structure for `sys_forum_reply_debate`
-- ----------------------------
DROP TABLE IF EXISTS `sys_forum_reply_debate`;
CREATE TABLE `sys_forum_reply_debate` (
  `post_reply_debate_id` int(4) NOT NULL auto_increment,
  `post_reply_id` int(4) default NULL,
  `post_reply_debate_content` varchar(1000) default NULL,
  `debate_is_new` int(1) default NULL,
  `state` int(1) default NULL,
  `create_user` varchar(20) default NULL,
  `update_user` varchar(20) default NULL,
  `create_date` date default NULL,
  `update_date` date default NULL,
  PRIMARY KEY  (`post_reply_debate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提问讨论回复';

-- ----------------------------
-- Records of sys_forum_reply_debate
-- ----------------------------
INSERT INTO `sys_forum_reply_debate` VALUES ('1', '1', '1阿萨', '0', '1', '1', '1', '2017-04-21', '2017-04-21');
INSERT INTO `sys_forum_reply_debate` VALUES ('2', '1', '哈哈', '0', '1', 'STU001', 'STU001', '2017-05-08', '2017-05-08');
INSERT INTO `sys_forum_reply_debate` VALUES ('3', '1', '这是什么问题', '0', '1', 'STU001', 'STU001', '2017-05-08', '2017-05-08');
INSERT INTO `sys_forum_reply_debate` VALUES ('4', '18', '这是什么情况', '0', '1', 'STU001', 'STU001', '2017-05-08', '2017-05-08');
INSERT INTO `sys_forum_reply_debate` VALUES ('5', '21', '你百度啊，是不是啥', '0', '1', 'STU001', 'STU001', '2017-05-08', '2017-05-08');
INSERT INTO `sys_forum_reply_debate` VALUES ('6', '22', '百度一下你就知道', '0', '1', 'STU001', 'STU001', '2017-05-09', '2017-05-09');
INSERT INTO `sys_forum_reply_debate` VALUES ('7', '34', '不知道', '0', '1', 'STU001', 'STU001', '2017-05-09', '2017-05-09');
INSERT INTO `sys_forum_reply_debate` VALUES ('8', '42', '哈哈哈哈哈', '0', '1', 'STU001', 'STU001', '2017-05-14', '2017-05-14');
INSERT INTO `sys_forum_reply_debate` VALUES ('9', '42', '再来一条', '0', '1', 'STU001', 'STU001', '2017-05-14', '2017-05-14');
INSERT INTO `sys_forum_reply_debate` VALUES ('10', '36', '哈哈哈哈', '0', '1', 'STU001', 'STU001', '2017-05-14', '2017-05-14');
INSERT INTO `sys_forum_reply_debate` VALUES ('11', '46', 'zsss', '0', '1', 'STU001', 'STU001', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('12', '43', '什么鬼', '0', '1', 'STU001', 'STU001', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('13', '43', '哈咯你好', '0', '1', 'STU001', 'STU001', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('14', '43', '测试', '0', '1', 'STU001', 'STU001', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('15', '46', '你好我是STU008', '0', '1', 'STU008', 'STU008', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('16', '43', 'mssss', '0', '1', 'STU008', 'STU008', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('17', '36', '百度有用吗', '0', '1', 'STU008', 'STU008', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('18', '34', '呵呵呵呵', '0', '1', 'STU004', 'STU004', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('19', '46', '呵呵呵呵', '0', '1', 'STU001', 'STU001', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('20', '52', '我是陈造龙', '0', '1', 'STU001', 'STU001', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('21', '57', '呵呵呵呵', '0', '1', 'STU001', 'STU001', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('22', '43', '上岗', '0', '1', 'STU001', 'STU001', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('23', '60', '你几个南山', '0', '1', 'STU009', 'STU009', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('24', '61', '你好', '0', '1', 'STU008', 'STU008', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('25', '61', '健健康康', '0', '1', 'STU008', 'STU008', '2017-05-31', '2017-05-31');
INSERT INTO `sys_forum_reply_debate` VALUES ('26', '60', '你好', '0', '1', 'STU007', 'STU007', '2017-05-31', '2017-05-31');

-- ----------------------------
-- Table structure for `sys_kb`
-- ----------------------------
DROP TABLE IF EXISTS `sys_kb`;
CREATE TABLE `sys_kb` (
  `kb_id` int(10) NOT NULL auto_increment,
  `kb_title` varchar(100) default NULL,
  `kb_type` int(1) default NULL,
  `state` int(1) default NULL,
  `create_date` date default NULL,
  `update_date` date default NULL,
  `content` varchar(1000) default NULL,
  `kb_link` varchar(100) default NULL,
  `create_user` varchar(20) default NULL,
  `update_user` varchar(20) default NULL,
  PRIMARY KEY  (`kb_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_kb
-- ----------------------------
INSERT INTO `sys_kb` VALUES ('1', 'java', '1', '1', '2017-04-25', '2017-04-25', 'java Study kb content', 'http://baidu.com', '陈造龙', '陈造龙');
INSERT INTO `sys_kb` VALUES ('2', 'aaaaaaa', null, '0', '2017-04-25', '2017-04-25', 'sss', 'http://baidu.com', '1', '1');
INSERT INTO `sys_kb` VALUES ('3', 'AO', '1', '0', '2017-04-26', '2017-04-26', '测试', 'http//github.com', '1', '1');
INSERT INTO `sys_kb` VALUES ('4', 'as', '1', '0', '2017-04-26', '2017-04-26', 'ssssssssssss', 'baidu.com', '1', '1');
INSERT INTO `sys_kb` VALUES ('5', 'as', '1', '0', '2017-04-26', '2017-04-26', 'sssssss', 's', '1', '1');
INSERT INTO `sys_kb` VALUES ('6', 'as', '1', '0', '2017-04-26', '2017-04-26', 's', 'baidu.com', '1', '1');
INSERT INTO `sys_kb` VALUES ('7', 'JAVASCRIPT', '1', '1', '2017-05-06', '2017-05-06', '关于js的学习，加油', 'http://blog.csdn.net/cgs1999/article/details/8642748', 'null', 'STU001');
INSERT INTO `sys_kb` VALUES ('8', 'MUI', '1', '1', '2017-05-06', '2017-05-06', 'H5开发webAPP的先关前端工具', 'http://dev.dcloud.net.cn/mui/', 'STU001', 'STU001');
INSERT INTO `sys_kb` VALUES ('9', 'PHP', '1', '1', '2017-05-06', '2017-05-06', '学习pHP相关资料', 'http://www.php.cn/php/php-tutorial.html', 'STU001', 'STU001');
INSERT INTO `sys_kb` VALUES ('10', 'ExtJS', '1', '1', '2017-05-06', '2017-05-06', 'Extjs表格控件功能丰富、界面美观、轻量级的实现，都是人们对其趋之若鹜的理由，究竟Extjs能实现多少不可思议的功能，就让我们一起来学习一下教程里面的内容。', 'http://www.w3cschool.cn/extjs/', 'STU001', 'STU001');
INSERT INTO `sys_kb` VALUES ('11', 'MyBatis', '1', '1', '2017-05-06', '2017-05-06', 'MyBatis 是支持定制化 SQL、存储过程以及高级映射的优秀的持久层框架。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以对配置和原生Map使用简单的 XML 或注解，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。', 'http://www.mybatis.org/mybatis-3/zh/index.html', 'STU001', 'STU001');

-- ----------------------------
-- Table structure for `sys_news`
-- ----------------------------
DROP TABLE IF EXISTS `sys_news`;
CREATE TABLE `sys_news` (
  `news_id` int(10) NOT NULL auto_increment,
  `news_title` varchar(100) default NULL,
  `news_link` varchar(100) default NULL,
  `news_type` int(1) default NULL,
  `description` varchar(500) default NULL,
  `state` int(1) default NULL,
  `create_date` date default NULL,
  `create_user` varchar(20) default NULL,
  `end_date` date default NULL,
  `update_user` varchar(20) default NULL,
  PRIMARY KEY  (`news_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻头条';

-- ----------------------------
-- Records of sys_news
-- ----------------------------
INSERT INTO `sys_news` VALUES ('1', null, 'http://baidu.com', '1', 'CESHI', '0', '2017-05-11', 'STU001', '2017-05-11', 'STU001');
INSERT INTO `sys_news` VALUES ('2', 'ddddddddddd', 'http://baidu.com', '1', 'asdjalsdkjal', '1', '2017-05-11', 'STU001', '2017-05-11', 'STU001');
INSERT INTO `sys_news` VALUES ('3', 'sadas', 'http://baidu.com', '1', 'asdasda', '1', '2017-05-11', 'STU001', '2017-05-11', 'STU001');
INSERT INTO `sys_news` VALUES ('4', 'ds', 'http://baidu.com', '1', 'dfffff', '0', '2017-05-11', 'STU001', '2017-05-11', 'STU001');
INSERT INTO `sys_news` VALUES ('5', '国家大事', 'http://news.163.com/17/0511/07/CK4TTR7L000187VI.html', '1', '国家大事', '1', '2017-05-11', 'STU001', '2017-05-11', 'STU001');
INSERT INTO `sys_news` VALUES ('6', '想槛车', 'http://baidu.com', '1', '滴答滴答滴答滴答滴答滴答滴答滴答滴答', '1', '2017-05-11', 'STU001', '2017-05-11', 'STU001');
INSERT INTO `sys_news` VALUES ('7', '飒爽的', 'http://baidu.com', '1', '得到大幅度反弹复苏的第三方斯蒂芬斯蒂芬斯蒂芬森德法撒旦发射点发生的环境规划结构和法国工会经费规划局法规环境条件kg还没好呢', '1', '2017-05-11', 'STU001', '2017-05-11', 'STU001');

-- ----------------------------
-- Table structure for `sys_school`
-- ----------------------------
DROP TABLE IF EXISTS `sys_school`;
CREATE TABLE `sys_school` (
  `school_id` varchar(10) NOT NULL,
  `school_name` varchar(50) NOT NULL,
  `area_id` varchar(50) default NULL,
  PRIMARY KEY  (`school_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_school
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_slider`
-- ----------------------------
DROP TABLE IF EXISTS `sys_slider`;
CREATE TABLE `sys_slider` (
  `slider_id` int(12) NOT NULL auto_increment,
  `slider_img` varchar(100) default NULL,
  `slider_link` varchar(100) default NULL,
  `create_user` varchar(20) default NULL,
  `create_date` date default NULL,
  `update_user` varchar(20) default NULL,
  `update_date` date default NULL,
  `slider_name` varchar(100) default NULL,
  `state` char(1) default NULL,
  `sort_id` varchar(12) default NULL,
  PRIMARY KEY  (`slider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='滚动轮播图\r\nSLIDER_ID主键\r\nSLIDER_IMG图片\r\nSLIDER_L';

-- ----------------------------
-- Records of sys_slider
-- ----------------------------
INSERT INTO `sys_slider` VALUES ('1', '/cscapp/jpg/20170414/20170414100641KHS92892OKN6_1000x500.jpg', 'https://www.baidu.com', '陈造龙', '2017-04-12', 'STU001', '2017-05-04', '测试修改', '0', '1');
INSERT INTO `sys_slider` VALUES ('14', '/cscapp/jpg/20170413/20170413192614015871899e510fb30738ae86db33c895d1430c17.jpg', 'https://www.baidu.com', '陈造龙', '2017-04-13', '陈造龙', '2017-04-13', '验证根据ID修改', '1', '3');
INSERT INTO `sys_slider` VALUES ('15', '/cscapp/jpg/20170503/20170503221219828W55A0G6SY_1000x500.jpg', 'http://www.tuicool.com/articles/Fru26n', 'null', '2017-05-03', 'STU001', '2017-05-04', '陈造龙', '0', '4');
INSERT INTO `sys_slider` VALUES ('16', '/cscapp/jpg/20170504/20170504210816springmvc.png', 'http://m.blog.csdn.net/article/details?id=50323077', 'STU001', '2017-05-04', 'STU001', '2017-05-04', '关于SpringMVC的学习', '1', '5');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(20) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `school` varchar(100) default NULL,
  `area_id` varchar(20) default NULL,
  `user_sex` varchar(2) default NULL,
  `phone` varchar(50) default NULL,
  `create_date` datetime default NULL,
  `update_date` datetime default NULL,
  `user_type` char(1) default NULL,
  `user_portrait` varchar(1000) default NULL,
  `user_email` varchar(50) default NULL,
  `status` char(255) default NULL,
  `access` varchar(20) NOT NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '哈哈哈哈哈', '12345', null, '11111', '男', '123456', '2017-05-31 14:34:02', null, '1', null, '122', '1', '2');
INSERT INTO `sys_user` VALUES ('1234564', '1234567', '123456', null, '11111', '男', '12311111111', '2017-05-31 16:07:19', null, '1', null, '123@qq.com', '1', '2');
INSERT INTO `sys_user` VALUES ('15528496698', '123456', '123456', null, '11111', '男', '15528496698', '2017-05-31 14:31:49', null, '1', null, '849380131@qq.com', '1', '2');
INSERT INTO `sys_user` VALUES ('STU001', '陈造龙', '123456', 'SWPU', '11111', '男', '15528496698', '2017-04-07 23:32:33', '2017-04-07 23:32:47', '1', '/cscapp/jpg/20170414/20170414100641KHS92892OKN6_1000x500.jpg', '849380131@qq.com', '1', '1');
INSERT INTO `sys_user` VALUES ('STU002', '阿萨德', '123456', '塞维尔', '11111', '男', '110', '2017-05-11 11:17:43', '2017-05-11 11:17:47', '1', null, '11111111@qq.com', '1', '1');
INSERT INTO `sys_user` VALUES ('STU003', '123456', '123456', null, '11111', '男', '15528496698', '2017-05-31 14:01:18', null, '1', null, '849380131@qq.com', '1', '2');
INSERT INTO `sys_user` VALUES ('STU004', '羊维忠', '123456', null, '11111', '男', '12345678911', '2017-05-31 16:14:09', null, '1', null, '123@qq.com', '1', '2');
INSERT INTO `sys_user` VALUES ('STU005', '卢以辉', '123456', null, '11111', '男', '12345678911', '2017-05-31 16:34:49', null, '1', null, '111@qq.com', '1', '2');
INSERT INTO `sys_user` VALUES ('STU007', '羊维忠', '123456', null, '11111', '男', '15528496698', '2017-05-31 16:28:24', null, '1', null, '849380131@qq.com', '1', '2');
INSERT INTO `sys_user` VALUES ('STU008', '123456', '123456', null, '11111', '男', '15528496698', '2017-05-31 16:33:21', null, '1', null, '123456@qq.com', '1', '2');
INSERT INTO `sys_user` VALUES ('STU009', '123456', '123456', null, '11111', '男', '15528496698', '2017-05-31 13:59:29', null, '1', null, '849380131@qq.com', '1', '2');

-- ----------------------------
-- Table structure for `sys_user_login`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_login`;
CREATE TABLE `sys_user_login` (
  `user_id` varchar(20) NOT NULL,
  `login_time` date default NULL,
  `loginout_time` date default NULL,
  `login_ip` varchar(15) default NULL,
  `session_id` varchar(20) NOT NULL default '',
  `login_flag` varchar(1) default NULL,
  `login_faild_result` varchar(20) default NULL,
  PRIMARY KEY  (`session_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_login
-- ----------------------------
INSERT INTO `sys_user_login` VALUES ('STU002', null, null, null, 'STU002', null, null);

-- ----------------------------
-- Table structure for `sys_user_tel`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_tel`;
CREATE TABLE `sys_user_tel` (
  `user_id` varchar(20) NOT NULL default '',
  `phone` varchar(11) default NULL,
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_tel
-- ----------------------------
INSERT INTO `sys_user_tel` VALUES ('STU001', '15528496698');

-- ----------------------------
-- Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL auto_increment,
  `user_name` varchar(40) NOT NULL,
  `password` varchar(255) NOT NULL,
  `age` int(4) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES ('1', '测试', 'sfasgfaf', '24');

-- ----------------------------
-- Procedure structure for `usp_getno`
-- ----------------------------
DROP PROCEDURE IF EXISTS `usp_getno`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `usp_getno`()
select 1
                                                     
--      call sp()
;;
DELIMITER ;
