/*
 Navicat MySQL Data Transfer

 Source Server         : seke
 Source Server Version : 50626
 Source Host           : localhost
 Source Database       : delta

 Target Server Version : 50626
 File Encoding         : utf-8

 Date: 06/06/2016 00:54:04 AM
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
--  Records of `course_info`
-- ----------------------------
BEGIN;
INSERT INTO `course_info` VALUES ('CRM系統使用', '10', '9', null, null, null, null), ('PLM系統實作', '4', '9', null, null, null, null), ('SAP系統實作', '3', '7', null, null, null, null), ('全球通路規劃與管理', '1', '7', null, null, null, null), ('台達銷售流程', '8.5', '10', null, null, null, null), ('合約分析與談判', '8', '1', null, null, null, null), ('品質七大手法', '5', '2', null, null, null, null), ('售後服務', '9', '9', null, null, null, null), ('售後服務規劃與管理', '9.5', '2', null, null, null, null), ('商業分析與評估', '9.5', '4', '不限', '陳明全', '針對8D方法及各步驟使用的相關常用工具進行重點說明，找出製程或客訴的問題原因並與解決對策，提昇客戶滿意度。', '无'), ('商業趨勢與分析', '3', '5', '不限', '陳明全', '針對8D方法及各步驟使用的相關常用工具進行重點說明，找出製程或客訴的問題原因並與解決對策，提昇客戶滿意度。', '无'), ('問題分析與解決', '3', '5', '不限', '陳明全', '針對8D方法及各步驟使用的相關常用工具進行重點說明，找出製程或客訴的問題原因並與解決對策，提昇客戶滿意度。', '无'), ('存貨與呆料管理', '4', '1', null, null, null, null), ('專案管理', '7.5', '7', '業務、採購、工程及專案管理人員', '李健銘', '一、 透過個案探討，找出台達專案管理的問題原因與解決方法，以提昇專案管理工作績效。二、 以 PMP 系統為骨幹，將 PMP 工具、表單及流程實際運用於台達專案管理中，以提升專案規劃、執行及控管效率與成功比率。三、 對 PMP 的全貌有更精進的架構，確保專案的成功。', '需上過專案管理實用版'), ('專業銷售技巧', '8.5', '9', null, null, null, null), ('專業銷售談判', '10', '2', null, null, null, null), ('市場/業務策略規劃', '8', '4', null, null, null, null), ('市場策略規劃', '9.5', '7', null, null, null, null), ('撰寫行銷企劃書', '2.5', '4', null, null, null, null), ('新產品市場業務開發', '0.5', '1', null, null, null, null), ('溝通技巧', '10', '4', null, null, null, null), ('產品發展規劃', '1.5', '9', null, null, null, null), ('產品開發流程', '7.5', '2', null, null, null, null), ('策略思維', '8.5', '5', null, null, null, null), ('策略聯盟', '6.5', '2', null, null, null, null), ('簡報技巧', '3', '10', null, null, null, null), ('經銷商管理', '5', '8', null, null, null, null), ('行銷策略與實務', '8', '3', null, null, null, null), ('訂單管理', '3', '4', null, null, null, null), ('跨文化/部門溝通', '8.5', '3', null, null, null, null), ('通路管理', '3.5', '9', null, null, null, null), ('通路規劃', '6', '3', null, null, null, null), ('進階專案管理', '7.5', '10', null, null, null, null), ('重點客戶計畫與管理', '10', '6', null, null, null, null), ('銷售輔導與激勵', '3.5', '9', null, null, null, null), ('銷售預測與管理', '2', '10', null, null, null, null), ('高階簡報技巧', '4.5', '6', null, null, null, null);
COMMIT;

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
--  Records of `course_project`
-- ----------------------------
BEGIN;
INSERT INTO `course_project` VALUES ('商業分析與評估', '8'), ('商業分析與評估', '9'), ('商業分析與評估', '10'), ('商業分析與評估', '11'), ('問題分析與解決', '8'), ('問題分析與解決', '9'), ('問題分析與解決', '10'), ('問題分析與解決', '11'), ('問題分析與解決', '12'), ('專案管理', '1'), ('專案管理', '2'), ('專案管理', '3'), ('專案管理', '4'), ('專案管理', '5'), ('專案管理', '6'), ('專案管理', '7'), ('產品開發流程', '7'), ('產品開發流程', '8');
COMMIT;

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
--  Records of `course_relation`
-- ----------------------------
BEGIN;
INSERT INTO `course_relation` VALUES ('全球通路規劃與管理', '合約分析與談判'), ('合約分析與談判', '通路管理'), ('合約分析與談判', '通路規劃'), ('售後服務規劃與管理', '售後服務'), ('商業趨勢與分析', '商業分析與評估'), ('市場/業務策略規劃', '台達銷售流程'), ('市場/業務策略規劃', '問題分析與解決'), ('市場策略規劃', '市場/業務策略規劃'), ('市場策略規劃', '產品開發流程'), ('產品發展規劃', '商業趨勢與分析'), ('產品發展規劃', '高階簡報技巧'), ('產品開發流程', '商業分析與評估'), ('產品開發流程', '問題分析與解決'), ('策略思維', '跨文化/部門溝通'), ('策略聯盟', '市場策略規劃'), ('策略聯盟', '策略思維'), ('跨文化/部門溝通', '溝通技巧'), ('進階專案管理', '專案管理'), ('銷售預測與管理', '市場策略規劃'), ('高階簡報技巧', '簡報技巧');
COMMIT;

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
--  Records of `grade`
-- ----------------------------
BEGIN;
INSERT INTO `grade` VALUES ('A7', 'S1/S2/S3'), ('M1+', null), ('P1/P2', 'P3/P4'), ('P3/P4', 'M1+'), ('S1/S2/S3', 'S4/S5'), ('S4/S5', 'P1/P2');
COMMIT;

-- ----------------------------
--  Table structure for `job`
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `job` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`job`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `job`
-- ----------------------------
BEGIN;
INSERT INTO `job` VALUES ('OEM/ODM'), ('PM'), ('品牌与解决方案(TBC)'), ('通讯');
COMMIT;

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
--  Records of `job_grade_course`
-- ----------------------------
BEGIN;
INSERT INTO `job_grade_course` VALUES ('OEM/ODM', 'A7', '存貨與呆料管理'), ('OEM/ODM', 'A7', '產品開發流程'), ('OEM/ODM', 'A7', '訂單管理'), ('OEM/ODM', 'M1+', '策略聯盟'), ('OEM/ODM', 'M1+', '銷售預測與管理'), ('OEM/ODM', 'P1/P2', '台達銷售流程'), ('OEM/ODM', 'P1/P2', '市場策略規劃'), ('OEM/ODM', 'P3/P4', '台達銷售流程'), ('OEM/ODM', 'P3/P4', '市場策略規劃'), ('OEM/ODM', 'S1/S2/S3', '存貨與呆料管理'), ('OEM/ODM', 'S1/S2/S3', '產品開發流程'), ('OEM/ODM', 'S1/S2/S3', '訂單管理'), ('OEM/ODM', 'S4/S5', '存貨與呆料管理'), ('OEM/ODM', 'S4/S5', '產品開發流程'), ('OEM/ODM', 'S4/S5', '訂單管理'), ('PM', 'A7', 'PLM系統實作'), ('PM', 'A7', 'SAP系統實作'), ('PM', 'A7', '進階專案管理'), ('PM', 'M1+', ''), ('PM', 'P1/P2', '品質七大手法'), ('PM', 'P1/P2', '進階專案管理'), ('PM', 'P3/P4', '品質七大手法'), ('PM', 'P3/P4', '進階專案管理'), ('PM', 'S1/S2/S3', 'PLM系統實作'), ('PM', 'S1/S2/S3', 'SAP系統實作'), ('PM', 'S1/S2/S3', '進階專案管理'), ('PM', 'S4/S5', 'PLM系統實作'), ('PM', 'S4/S5', 'SAP系統實作'), ('PM', 'S4/S5', '進階專案管理'), ('品牌与解决方案(TBC)', 'A7', '售後服務'), ('品牌与解决方案(TBC)', 'A7', '撰寫行銷企劃書'), ('品牌与解决方案(TBC)', 'A7', '經銷商管理'), ('品牌与解决方案(TBC)', 'A7', '通路管理'), ('品牌与解决方案(TBC)', 'A7', '通路規劃'), ('品牌与解决方案(TBC)', 'A7', '重點客戶計畫與管理'), ('品牌与解决方案(TBC)', 'M1+', '全球通路規劃與管理'), ('品牌与解决方案(TBC)', 'P1/P2', '合約分析與談判'), ('品牌与解决方案(TBC)', 'P1/P2', '售後服務規劃與管理'), ('品牌与解决方案(TBC)', 'P1/P2', '新產品市場業務開發'), ('品牌与解决方案(TBC)', 'P1/P2', '銷售輔導與激勵'), ('品牌与解决方案(TBC)', 'P3/P4', '合約分析與談判'), ('品牌与解决方案(TBC)', 'P3/P4', '售後服務規劃與管理'), ('品牌与解决方案(TBC)', 'P3/P4', '新產品市場業務開發'), ('品牌与解决方案(TBC)', 'P3/P4', '銷售輔導與激勵'), ('品牌与解决方案(TBC)', 'S1/S2/S3', '售後服務'), ('品牌与解决方案(TBC)', 'S1/S2/S3', '撰寫行銷企劃書'), ('品牌与解决方案(TBC)', 'S1/S2/S3', '經銷商管理'), ('品牌与解决方案(TBC)', 'S1/S2/S3', '通路管理'), ('品牌与解决方案(TBC)', 'S1/S2/S3', '通路規劃'), ('品牌与解决方案(TBC)', 'S1/S2/S3', '重點客戶計畫與管理'), ('品牌与解决方案(TBC)', 'S4/S5', '售後服務'), ('品牌与解决方案(TBC)', 'S4/S5', '撰寫行銷企劃書'), ('品牌与解决方案(TBC)', 'S4/S5', '經銷商管理'), ('品牌与解决方案(TBC)', 'S4/S5', '通路管理'), ('品牌与解决方案(TBC)', 'S4/S5', '通路規劃'), ('品牌与解决方案(TBC)', 'S4/S5', '重點客戶計畫與管理'), ('通讯', 'A7', 'CRM系統使用'), ('通讯', 'A7', '台達銷售流程'), ('通讯', 'A7', '商業分析與評估'), ('通讯', 'A7', '問題分析與解決'), ('通讯', 'A7', '專案管理'), ('通讯', 'A7', '專業銷售技巧'), ('通讯', 'A7', '專業銷售談判'), ('通讯', 'A7', '溝通技巧'), ('通讯', 'A7', '簡報技巧'), ('通讯', 'M1+', '市場策略規劃'), ('通讯', 'M1+', '產品發展規劃'), ('通讯', 'M1+', '策略思維'), ('通讯', 'P1/P2', '商業趨勢與分析'), ('通讯', 'P1/P2', '市場/業務策略規劃'), ('通讯', 'P1/P2', '行銷策略與實務'), ('通讯', 'P1/P2', '跨文化/部門溝通'), ('通讯', 'P1/P2', '重點客戶計畫與管理'), ('通讯', 'P1/P2', '高階簡報技巧'), ('通讯', 'P3/P4', '商業趨勢與分析'), ('通讯', 'P3/P4', '市場/業務策略規劃'), ('通讯', 'P3/P4', '行銷策略與實務'), ('通讯', 'P3/P4', '跨文化/部門溝通'), ('通讯', 'P3/P4', '重點客戶計畫與管理'), ('通讯', 'P3/P4', '高階簡報技巧'), ('通讯', 'S1/S2/S3', 'CRM系統使用'), ('通讯', 'S1/S2/S3', '台達銷售流程'), ('通讯', 'S1/S2/S3', '商業分析與評估'), ('通讯', 'S1/S2/S3', '問題分析與解決'), ('通讯', 'S1/S2/S3', '專案管理'), ('通讯', 'S1/S2/S3', '專業銷售技巧'), ('通讯', 'S1/S2/S3', '專業銷售談判'), ('通讯', 'S1/S2/S3', '溝通技巧'), ('通讯', 'S1/S2/S3', '簡報技巧'), ('通讯', 'S4/S5', 'CRM系統使用'), ('通讯', 'S4/S5', '台達銷售流程'), ('通讯', 'S4/S5', '商業分析與評估'), ('通讯', 'S4/S5', '問題分析與解決'), ('通讯', 'S4/S5', '專案管理'), ('通讯', 'S4/S5', '專業銷售技巧'), ('通讯', 'S4/S5', '專業銷售談判'), ('通讯', 'S4/S5', '溝通技巧'), ('通讯', 'S4/S5', '簡報技巧');
COMMIT;

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

-- ----------------------------
--  Records of `project_info`
-- ----------------------------
BEGIN;
INSERT INTO `project_info` VALUES ('1', '瞭解專案管理的定義及方法論。 ', '專案基本概念 專案組織', '講授、問答、分組討論', '簡報、海報、色筆', '40', '1. 分組討論 (各大組討論，討論目前實際執行專案所遇到的困難)2. 組長指派組員寫海報及報告。'), ('2', '能使用表單定義及架構專案工作內容', '專案起始', '講授、分組討論、演練、問答', '簡報、表單、分組討論', '60', '1. 2-3 一小組，並討論選一個案做為今天演練用的專案。2. 完成專案章程。'), ('3', '能建立工作分解結構 (WBS) 及使用權責指派矩陣 (RAM)', '專案範疇管理', '講授、分組討論、演練、問答', '簡報、軟體工具、表單', '80', '1. 各小組依所進行專案，利用 Openproj 軟體寫出 WBS (2項 WP、每個 WP 各兩項 Activity)2. 各小組依 WBS，寫出表單的 RAM - RACI，找出相關利害關係人的 RACI。'), ('4', '能分析並改善時間管理的問題', '時間管理與個案探討', '講授、分組討論、演練、問答', '簡報、軟體工具', '90', '1. 與學員互動，針對課本練習題找出關鍵路徑 (CP)2. 依範例，利用Openproj 軟體找出關鍵路徑 (CP)3. 找出自己目前演練的專案的關鍵路徑 (CP)'), ('5', '能選擇估計成本方式、闡述品質管理的精隨、並使用風險管理表單規劃風險', '成本規劃、品質規劃與風險規劃', '講授、分組討論、演練、問答', '簡報、表單、分組討論', '70', '1. 透過講述，與學員互動說明成本規劃和品質規劃方式2. 舉例說明風險回應策略，並請學員利用表單完成自己專案的風險回應策略'), ('6', '能分析並解決人力資源規畫問題所在、能使用技巧來執行與監控專案、能闡述並檢視專案結案需做的事', '人力資源規劃、執行、監控專案、結束專案', '講授、問答', '簡報', '50', '1. 講述並與學員互動說明'), ('7', '觀摩各組專案規劃及比較各組規劃差異，作為自己學習的參考', '分組報告', '分組報告', '簡報', '60', '請各組報告 5min, 講師說明優缺點、詢問學員有無看到問題'), ('8', '瞭解問題的基本慨念與定義', '何謂問題?', '講授、問答', '簡報、討論', '20', null), ('9', '能說明並畫出問題分析的過程', '問題分析成過程', '講授、問答', '簡報、討論', '20', null), ('10', '能選擇合適的QC手法來分析問題', '問題分析與常用的QC手法之關係', '講授、問答', '簡報、討論', '20', null), ('11', '能寫出問題分析與解決的八大步驟', '問題分析與解決的八大步驟', '講授、分組討論、問答', '簡報、分組討論', '70', null), ('12', '觀摩各組問題分析與解決的8D演練及比較各組模式差異，作為自己學習的參考', '分組報告', '分組討論與報告', '簡報、海報、色筆、分組討論', '50', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
