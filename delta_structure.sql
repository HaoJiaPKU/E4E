/*
 Navicat MySQL Data Transfer

 Source Server         : seke
 Source Server Version : 50626
 Source Host           : localhost
 Source Database       : delta

 Target Server Version : 50626
 File Encoding         : utf-8

 Date: 06/06/2016 14:12:21 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `course_info`
-- ----------------------------
DROP TABLE IF EXISTS `course_info`;
CREATE TABLE `course_info` (
  `name` varchar(255) NOT NULL DEFAULT '',
  `hour` float DEFAULT NULL,
  `project_num` int(11) DEFAULT NULL,
  `population` text,
  `teacher` text,
  `goal` text,
  `limitation` text,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `course_project`
-- ----------------------------
DROP TABLE IF EXISTS `course_project`;
CREATE TABLE `course_project` (
  `course_name` varchar(255) NOT NULL DEFAULT '',
  `project_id` decimal(10,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`course_name`,`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `course_relation`
-- ----------------------------
DROP TABLE IF EXISTS `course_relation`;
CREATE TABLE `course_relation` (
  `course_name` varchar(255) NOT NULL DEFAULT '',
  `pre_course_name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`course_name`,`pre_course_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `grade`
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade` (
  `grade` varchar(255) NOT NULL DEFAULT '',
  `next_grade` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`grade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `job`
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `job` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`job`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `job_grade_course`
-- ----------------------------
DROP TABLE IF EXISTS `job_grade_course`;
CREATE TABLE `job_grade_course` (
  `job` varchar(255) NOT NULL DEFAULT '',
  `grade` varchar(255) NOT NULL DEFAULT '',
  `course_name` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`job`,`grade`,`course_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `project_info`
-- ----------------------------
DROP TABLE IF EXISTS `project_info`;
CREATE TABLE `project_info` (
  `id` decimal(10,0) NOT NULL DEFAULT '0',
  `goal` text,
  `outline` text,
  `method` varchar(255) DEFAULT NULL,
  `media` varchar(255) DEFAULT NULL,
  `minute` int(11) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
