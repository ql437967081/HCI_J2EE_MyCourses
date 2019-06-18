-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: localhost    Database: j2ee_my_courses
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `uid` varchar(45) NOT NULL,
  `status` enum('SUBMIT','APPROVED','REJECTED','REJECTED_READ') NOT NULL DEFAULT 'SUBMIT',
  PRIMARY KEY (`id`),
  KEY `course_user_idx` (`uid`),
  CONSTRAINT `course_user` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'','wwhaor','REJECTED_READ'),(2,'微积分I','wwhaor','REJECTED_READ'),(3,'微积分II','wwhaor','REJECTED_READ'),(4,'线性代数','wwhaor','APPROVED'),(5,'数据结构与算法','wwhaor','APPROVED'),(6,'计算机组织结构','wwhaor','APPROVED'),(7,'计算机网络','wwhaor','APPROVED'),(8,'微积分I','wwhaor','REJECTED_READ'),(9,'微积分I','wwhaor','REJECTED_READ'),(10,'软件需求工程','khy','APPROVED'),(11,'软件工程与计算Ⅲ','liujia','APPROVED'),(12,'数据库开发技术','liujia','APPROVED'),(13,'软件工程与计算Ⅰ','qinliu','APPROVED'),(14,'软件工程与计算Ⅱ','qinliu','APPROVED'),(15,'数据库系统','wwhaor','SUBMIT'),(16,'数据库系统','st','APPROVED');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_class`
--

DROP TABLE IF EXISTS `course_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_id` int(11) NOT NULL,
  `limit_num` int(11) NOT NULL,
  `pid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `course_class_publish_course_idx` (`pid`),
  CONSTRAINT `course_class_publish_course` FOREIGN KEY (`pid`) REFERENCES `publish_course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_class`
--

LOCK TABLES `course_class` WRITE;
/*!40000 ALTER TABLE `course_class` DISABLE KEYS */;
INSERT INTO `course_class` VALUES (1,2,90,1),(2,1,100,1),(3,1,50,2),(4,2,119,3),(5,1,120,3),(6,3,12,4),(7,1,24,4),(8,2,22,4),(9,4,34,4),(10,1,110,5),(11,2,110,5),(12,4,3,6),(13,1,2,6),(14,2,1,6),(15,3,1,6),(16,1,2,7),(17,2,1,7),(18,2,1,8),(19,1,3,8),(20,1,200,9),(21,1,3,10),(22,2,4,11),(23,1,5,11),(24,1,2,12),(25,2,3,12),(26,1,5,13),(27,2,3,13);
/*!40000 ALTER TABLE `course_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_grade`
--

DROP TABLE IF EXISTS `course_grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_grade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL,
  `hid` bigint(20) DEFAULT NULL,
  `remark` varchar(45) NOT NULL,
  `location` varchar(100) NOT NULL,
  `open` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `course_grade_publish_course_idx` (`pid`),
  KEY `course_grade_homework_request_idx` (`hid`),
  CONSTRAINT `course_grade_homework_request` FOREIGN KEY (`hid`) REFERENCES `homework_request` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `course_grade_publish_course` FOREIGN KEY (`pid`) REFERENCES `publish_course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_grade`
--

LOCK TABLES `course_grade` WRITE;
/*!40000 ALTER TABLE `course_grade` DISABLE KEYS */;
INSERT INTO `course_grade` VALUES (1,3,1,'作业1部分成绩','wwhaor_32c054ae-15da-46e8-8a54-e8c97141becf成绩.xlsx',1),(2,3,1,'前一次成绩发布失败','wwhaor_b7f04dac-857d-401c-84c4-2434fc67edac成绩.xlsx',1),(3,3,1,'前一次成绩发布失败	','wwhaor_ec181702-6502-4d4f-a8b8-959a189f3b0c成绩.xlsx',1),(4,3,NULL,'半期考试成绩公布','wwhaor_b4187984-a1c3-467c-8499-dfd755fc73ef成绩.xlsx',0),(5,11,14,'部分同学的成绩','qinliu_c4e0dd0f-4520-4712-803f-3bb7ff127408成绩.xlsx',0),(6,11,14,'ceshi','qinliu_f8589fb2-7442-4ba5-b5ae-83798472a042成绩.xlsx',1);
/*!40000 ALTER TABLE `course_grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courseware`
--

DROP TABLE IF EXISTS `courseware`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courseware` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `location` varchar(100) NOT NULL,
  `cid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `courseware_course_idx` (`cid`),
  CONSTRAINT `courseware_course` FOREIGN KEY (`cid`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courseware`
--

LOCK TABLES `courseware` WRITE;
/*!40000 ALTER TABLE `courseware` DISABLE KEYS */;
INSERT INTO `courseware` VALUES (1,'CEACAA00846Q04.PDF','wwhaor_50df5851-787d-4ea5-ac01-54e5eae46227CEACAA00846Q04.PDF',2),(2,'IMG_6974.jpg','wwhaor_935009e6-3d03-41a5-a334-7008e53579f5IMG_6974.jpg',2),(3,'CEACAA00846Q04.PDF','wwhaor_5cb50919-ccc0-4cf3-870d-295d18c85a7bCEACAA00846Q04.PDF',3),(4,'logo.png','wwhaor_2f69bcf8-5fb0-4a1d-a25b-b7057eb7a6a1logo.png',3),(7,'CEACAA00846Q04.PDF','wwhaor_91378c15-4827-4e23-be43-b0f3e113f7ccCEACAA00846Q04.PDF',4),(8,'chapter1.PPT','wwhaor_37dc9e4d-2cfd-4e0e-b1df-b48e5feb26ffchapter1.PPT',5),(9,'chapter3.1.ppt','wwhaor_52fd73f7-9369-4e71-baeb-b1d3cc2a1af5chapter3.1.ppt',5),(10,'chapter3.PPT','wwhaor_d8ce742d-7411-4001-aff0-e3fc5fec2f2achapter3.PPT',5),(12,'chapter4.1.ppt','wwhaor_cadb45d6-9ed5-4631-8e47-0c7afb323c37chapter4.1.ppt',5),(13,'chapter4.ppt','wwhaor_55488c11-6821-486e-8a90-d44e4e052f34chapter4.ppt',5),(14,'chapter3-2.pptx','wwhaor_95e6b9d6-0e1d-4817-b677-3f4ac3f8e035chapter3-2.pptx',6),(15,'chapter2-1.pptx','wwhaor_0c44cd92-9938-486f-890a-0d202de7171fchapter2-1.pptx',6),(16,'chapter2-2.pptx','wwhaor_a88c61ea-15d0-4bf2-8e19-cfd4fd69899cchapter2-2.pptx',6),(17,'chapter2-3.pptx','wwhaor_8b21c8b3-807a-4516-930b-c7fa9964ead0chapter2-3.pptx',6),(18,'chapter3-1.pptx','wwhaor_f2915359-4e2b-4fa5-b491-34ff7396d447chapter3-1.pptx',6),(20,'第4章：网络层.ppt','wwhaor_4cd1ad15-540a-444f-afee-5760cd10aa0a第4章：网络层.ppt',7),(22,'第3章：OSI层次：数据链路层.pptx','wwhaor_640fe9fe-831c-460d-be85-f831495e0b48第3章：OSI层次：数据链路层.pptx',7),(23,'第1章：计算机网络及其参考模型.ppt','wwhaor_df2f5b58-deee-4a54-866b-30597223c088第1章：计算机网络及其参考模型.ppt',7),(25,'第2章：OSI层次：物理层.pptx','wwhaor_249da941-c182-4b50-bb44-9ba20da5ba9a第2章：OSI层次：物理层.pptx',7),(26,'第5章：传输层.pptx','wwhaor_c958eae8-5bf7-4e78-96b5-db2faa672530第5章：传输层.pptx',7),(27,'IMG_6974.jpg','wwhaor_b7cd8585-e334-4abe-94ed-e199bfd09de3IMG_6974.jpg',8),(28,'logo.png','wwhaor_789006ec-75a4-4f30-a864-d73a7aad3943logo.png',8),(29,'IMG_6974.jpg','wwhaor_cdb63943-3448-46d3-826d-e7d4d531cbe0IMG_6974.jpg',9),(30,'logo.png','wwhaor_68afea7a-4dd6-43f9-aaaa-57f65fdd2827logo.png',9),(31,'7.第6周 周三 第8章.面谈.ppt','khy_77ecbe60-89a9-46ed-8178-105b389c199c7.第6周 周三 第8章.面谈.ppt',10),(32,'2.第2周 周一 第2章.需求基础.ppt','khy_f8089b4c-247f-4f7a-9500-6a384d8801882.第2周 周一 第2章.需求基础.ppt',10),(33,'6.第6周 周一 第7章.基于用例场景模型展开用户需求获取.ppt','khy_77e99100-311a-4866-a01e-6188236f5ecd6.第6周 周一 第7章.基于用例场景模型展开用户需求获取.ppt',10),(34,'9.第6周 周五 第10章.观察和文档审查.ppt','khy_71cc54af-d640-45bd-90b8-0f68ac40fcd29.第6周 周五 第10章.观察和文档审查.ppt',10),(35,'3.第2周 周三 第3章.需求工程过程.ppt','khy_6b0a72a2-f8a8-4256-95a8-b5fae02de4f73.第2周 周三 第3章.需求工程过程.ppt',10),(36,'5.第4周 周三 第6章.涉众分析与硬数据采样.ppt','khy_132dd13a-129f-48a1-acf5-8be275d470915.第4周 周三 第6章.涉众分析与硬数据采样.ppt',10),(37,'第1周 周三 第1章 需求工程导论.ppt','khy_401255c7-79b0-471c-8c36-8d4ca662baf9第1周 周三 第1章 需求工程导论.ppt',10),(38,'4.第3周 周三 第5章.确定项目的前景和范围.ppt','khy_50551fe2-b9e2-40ac-8131-4aced75fa09f4.第3周 周三 第5章.确定项目的前景和范围.ppt',10),(39,'8.第6周 周五 第9章.原型.ppt','khy_a8d92d7d-9119-41e7-a5c6-8344f3fb96438.第6周 周五 第9章.原型.ppt',10),(40,'CSEIII03-2018张.pdf','liujia_e8ff7aee-14d7-49c8-8e75-b75ceb273c63CSEIII03-2018张.pdf',11),(41,'JUnit Recipes .pdf','liujia_aa3b82a0-c120-4ea9-a0a8-a2143d847b02JUnit Recipes .pdf',11),(42,'众包数据标注及其问题.pdf','liujia_998d4cac-df73-449e-b81c-31486ad0b898众包数据标注及其问题.pdf',11),(43,'CSEIII-第二次迭代.pdf','liujia_de4d043a-785c-4455-a236-8405674b9be6CSEIII-第二次迭代.pdf',11),(44,'JUnit介绍.pdf','liujia_135f57cc-9a5c-4e4b-8319-05b1189b0b6cJUnit介绍.pdf',11),(45,'CSEIII-第一次迭代.pdf','liujia_8b776215-827c-4895-9c54-d8a5912fc384CSEIII-第一次迭代.pdf',11),(46,'CSEIII 01 -2018.pdf','liujia_84b6cf0c-e266-4899-ba6e-13136d29dcf1CSEIII 01 -2018.pdf',11),(47,'软工三课程实践要求.pdf','liujia_cfa43af1-0381-43c1-bd46-6e80362d5150软工三课程实践要求.pdf',11),(48,'Part 1 高性能数据库开发原则.pdf','liujia_8bf7aed6-1b93-421b-bbc1-a97c51d9b084Part 1 高性能数据库开发原则.pdf',12),(49,'Part 2 索引结构及使用.pdf','liujia_35e0b7bf-7227-4081-8301-0d08a33e5871Part 2 索引结构及使用.pdf',12),(50,'Part 3 查询优化器及SQL优化.pdf','liujia_3e8e3225-3024-435d-bfae-045b6a4840c0Part 3 查询优化器及SQL优化.pdf',12),(51,'Part 4 物理结构与实现.pdf','liujia_efff2bcf-9d8b-4ba0-903b-e7e39b696e46Part 4 物理结构与实现.pdf',12),(52,'Part 5 数据库模式设计之层次结构.pdf','liujia_22695a5b-c0b2-4723-9a62-33c24308fc8dPart 5 数据库模式设计之层次结构.pdf',12),(53,'Part 6 基于数据的数据库方法学.pdf','liujia_7dbda76e-f6fe-4428-854b-c186db7b60a7Part 6 基于数据的数据库方法学.pdf',12),(54,'Part 7 反范式模式及数据库设计示例.pdf','liujia_7cca3eeb-f569-4e96-b4ef-64bf95fc9f2dPart 7 反范式模式及数据库设计示例.pdf',12),(55,'Part 8 处理并发和大规模数据量.pdf','liujia_75504e7c-6e0c-47bd-b322-8e267a87ef7dPart 8 处理并发和大规模数据量.pdf',12),(56,'Part 9 大数据产品链条.pdf','liujia_97e1a438-7e4f-4719-af36-194996975a36Part 9 大数据产品链条.pdf',12),(57,'00 - Overview.pdf','qinliu_e691cc45-c32e-4734-ba18-fd137a112e1300 - Overview.pdf',13),(58,'01 - Java概述.pdf','qinliu_cd1654f3-2791-4b79-a1ee-49de4cd94b7901 - Java概述.pdf',13),(59,'1软件开发概述.pdf','qinliu_ec9e45a0-12c9-429a-a2aa-16dc5585042d1软件开发概述.pdf',13),(60,'02 - 编程基础 I.pdf','qinliu_bc6f9bbc-0cce-40ea-913f-66659694c3c902 - 编程基础 I.pdf',13),(61,'03 - 可计算性.pdf','qinliu_b16e03be-c7bb-4ec0-8ccb-7365b219db8e03 - 可计算性.pdf',13),(62,'04 - Lambda演算 .pdf','qinliu_597f12b3-4ca1-481f-ba49-5676ac44414b04 - Lambda演算 .pdf',13),(63,'05 - 编程基础 IV-函数式编程范式.pdf','qinliu_c1a52545-a201-4107-8007-2a8858813b0e05 - 编程基础 IV-函数式编程范式.pdf',13),(64,'06 - 编程基础 V-编程语言.pdf','qinliu_8a458514-c9f8-4aa0-b686-baf0141198d306 - 编程基础 V-编程语言.pdf',13),(65,'07 - 编程基础 VI-习题课.pdf','qinliu_d909d4c2-7301-4ec8-848c-a7a93cab506f07 - 编程基础 VI-习题课.pdf',13),(66,'08 - 结构化编程 I - 思想.pdf','qinliu_a980d326-13e7-49a0-a57a-52f74ea0671c08 - 结构化编程 I - 思想.pdf',13),(67,'09 - 结构化编程 II - 变量.pdf','qinliu_f29ddf18-5041-4233-8539-a85a71b1896509 - 结构化编程 II - 变量.pdf',13),(68,'10 - 结构化编程 III - 方法.pdf','qinliu_4ad4b91d-ff51-4b47-8ede-4a2cb0449dee10 - 结构化编程 III - 方法.pdf',13),(69,'01-软件工程基础.pdf','qinliu_166a256b-895b-48e5-b9b1-9f2c0bde2ea201-软件工程基础.pdf',14),(70,'ch02_RelationalModel_20180315.pptx','wwhaor_3b4a4a33-f513-473a-8784-a774f463ecd4ch02_RelationalModel_20180315.pptx',15),(71,'ch03_BasicSQL_20180408.pptx','wwhaor_7c6a28f3-1db6-415b-8fcb-559b7818ca1dch03_BasicSQL_20180408.pptx',15),(72,'项目提案.pdf','qinliu_27f82362-36f5-4c4a-8492-a8c3c745ff0a项目提案.pdf',13);
/*!40000 ALTER TABLE `courseware` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grade`
--

DROP TABLE IF EXISTS `grade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cid` bigint(20) NOT NULL,
  `sid` bigint(20) NOT NULL,
  `score` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `grade_course_grade_idx` (`cid`),
  KEY `grade_select_course_idx` (`sid`),
  CONSTRAINT `grade_course_grade` FOREIGN KEY (`cid`) REFERENCES `course_grade` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `grade_select_course` FOREIGN KEY (`sid`) REFERENCES `select_course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grade`
--

LOCK TABLES `grade` WRITE;
/*!40000 ALTER TABLE `grade` DISABLE KEYS */;
INSERT INTO `grade` VALUES (1,1,2,0),(2,2,2,0),(3,3,2,90),(4,4,2,95),(5,5,11,100),(6,5,34,0),(7,5,19,0),(8,5,42,0),(9,5,27,0),(10,5,7,100),(11,5,49,0),(12,6,42,0),(13,6,27,0),(14,6,19,0),(15,6,34,0),(16,6,49,0),(17,6,7,100),(18,6,11,100);
/*!40000 ALTER TABLE `grade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `homework_request`
--

DROP TABLE IF EXISTS `homework_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `homework_request` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NOT NULL,
  `title` varchar(45) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `ddl` datetime NOT NULL,
  `file_max_size` int(11) NOT NULL DEFAULT '10',
  `file_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `homework_request_publish_course_idx` (`pid`),
  CONSTRAINT `homework_request_publish_course` FOREIGN KEY (`pid`) REFERENCES `publish_course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `homework_request`
--

LOCK TABLES `homework_request` WRITE;
/*!40000 ALTER TABLE `homework_request` DISABLE KEYS */;
INSERT INTO `homework_request` VALUES (1,3,'作业1','1.你叫什么名字\r\n2.你的性别\r\n3.你的母亲的生日','2019-03-07 14:30:00',1,'pdf'),(2,5,'小组组队与问题提出','自行组队，3-4人一队。每个队伍选出一个PM，以后的作业都是由PM提交。\r\n\r\nPM主要职责是确保整个项目按照项目计划完成。PM在项目开发过程中，要组织项目例会，分配任务，以及作业的按时提交。\r\n\r\n在本阶段，每个小组构想一个需要解决的实际问题，并随机分配给其他小组提供解决方案（提交问题时请顺便注明本组人员构成情况）。\r\n\r\n所反映的系统规模要适中，大概有3~5个问题需要解决。（每个问题的难度也要适中，其解决方案涉及的输入/输出数据流在4~8个之间，解决方案需要的系统特性在3~5个。）\r\n\r\n问题请站在客户的角度用自然语言描述，不要过于冷门，描述要充分，规模要适中，想法要可行。如果出现提出的问题质量较差，则经判定后将提出问题组的一部分分数补偿给解决问题的小组','2019-09-23 20:00:00',1,'txt'),(3,5,'完成项目前景与范围文档','请小组组长前往如下链接登记小组联系方式：\r\n\r\nhttps://docs.qq.com/form/fill/DTFdZQ0Jsa3dNV09N\r\n\r\n提出问题的小组扮演用户方，负责解决问题的小组扮演需求工程团队。以需求工程团队为主，完成项目的业务需求分析，建立有效的高层解决方案及系统特性，完成项目前景和范围文档。（推荐与项目提出者进行交流）\r\n\r\n结果文档：问题（目标）分析过程文档，前景和范围文档。\r\n\r\n要求度量：问题数量，每个问题解决方案的平均输入/输出数量和平均特性数量。\r\n\r\n文件提交格式：将所有文件添加到一个压缩包中，压缩文件命名格式“学号+小组编号”','2019-10-09 20:00:00',2,'zip'),(4,5,'展开需求获取并完成用例文档','1) 需求工程团队以提交的前景和范围文档为依据安排计划，展开需求获取活动。 2) 利用需求获取方法，通过多次结合获取与分析的迭代过程，获取用户需求，建立用户需求列表。 3) 完成用例文档（用户需求文档）。 4) 结果文档：需求获取安排计划书；用例文档（用户需求文档）；用户需求列表；使用的面谈报告和原型物件。 5) 要求度量：需求获取的次数；面谈方法获取的用例数量、原型方法获取的用例数量；用户需求数量、非功能需求的数量和种类比率、用例数量、平均用例的场景数量、平均用例的字数和最大用例的字数。 ','2019-10-23 20:00:00',3,'zip'),(5,5,'利用面向对象模型对项目定型','    需求工程团队根据用户需求，通过面向对象建模与分析手段，为问题设计解决方案，完成软件需求规格说明文档。\r\n\r\n    开发方建立分析模型，细化系统需求，完成软件需求列表。\r\n\r\n    结果文档：分析模型；软件需求列表；软件需求规格说明文档。\r\n\r\n    要求度量：软件需求的数量、非功能需求的数量；类图的类数量、关联数量，每个类的平均属性数量；行为图（包括交互图、状态图和活动图）的数量，交互图内平均参与对象数量和最大参与对象数量、交互图内平均消息数量和最大消息数量，状态图内平均状态数量和最大状态数量、状态图内平均转移数量和最大转移数量，活动图内平均的甬道数量、活动数量和数据对象数量、活动图内最大的甬道数量、活动数量和数据对象数量、方法契约说明的数量、方法契约说明的平均行数；\r\n\r\n    注意事项：交互图、状态图、活动图和方法契约的使用可以根据项目情况安排，不要求必须使用。但是对于没有使用的团队，必须要有足够的理由，助教会对其进行检查。','2019-11-13 20:00:00',3,'zip'),(6,7,'迭代一','参照课上助教讲解和ppt完成','2019-04-06 23:59:00',10,'zip'),(7,7,'迭代二','参照课上助教讲解和ppt完成','2019-04-29 23:59:00',10,'zip'),(8,7,'迭代三','参照课上助教讲解和ppt完成','2019-06-20 23:59:00',20,'zip'),(9,8,'迭代一','参照课上助教讲解和ppt完成','2020-04-01 23:59:00',10,'zip'),(10,6,'问题提出','提出一个问题并完成小组组队','2020-09-24 20:00:00',1,'doc/docx'),(11,9,'作业一','请参照上传的课件 作业01.pdf','2019-10-30 00:00:00',2,'zip'),(12,9,'作业二','请参照上传的课件：作业02.pdf','2019-10-30 00:00:00',10,'zip'),(13,11,'Homework01-HelloWorld','Deadline: March 8 23:59:59\r\nTasks:\r\n\r\n    用命令行模式实现HelloWorld\r\n    用IDE实现HelloWorld\r\n\r\nSubmit:\r\n\r\n    运行截图(截图大小小于2M)\r\n','2019-03-15 00:00:00',2,'zip'),(14,11,'Homework02-Sum','Deadline: March 13 23:59:59\r\nTasks:\r\n\r\n    求1到2的和\r\n    求1到100的和\r\n    求1到100的平方和。\r\n    求1到n的平方和。（输入值1《n《100）\r\n\r\nSubmit:\r\n\r\n    源代码\r\n','2019-03-20 00:00:00',1,'java'),(15,11,'Homework03-CurrencyExchange','    Deadline: March 15 23:59:59\r\n    Tasks:\r\n        编写一个货币兑换程序，将欧亚兑换成美元。体术输入手中的欧元数，以及欧元的当前汇率。打印可以兑换的美元数。\r\n        示例输出：\r\n        How many eruos are you exchanging? 81\r\n        What is the exchange rate? 137.51\r\n        81 euros at an exchange rate of 137.51 is\r\n        111.38 U.S. dollars.\r\n    Submit:\r\n        源代码\r\n','2019-03-22 00:00:00',1,'java'),(16,11,'Homework04-原始递归函数','Deadline: March 15, 23:59:59\r\n利用原始递归函数的定义乘法。\r\n利用原始递归函数的定义阶乘。\r\n提交word文件。','2019-03-25 00:00:00',1,'doc/docx'),(17,11,'Homework05-LargestNumber','Deadline: March 15, 23:59:59\r\n编写一个程序，让用户输入3个数。首先确认所有数字各不相同，如果存在相同的书，退出程序，否则显示其中最大的。\r\n示例输出\r\nEnter the first number: 1\r\nEnter the second number: 51\r\nEnter the third number: 2\r\nThe largest number is 51.\r\nSubmit:\r\n\r\n    源代码\r\n','2019-03-28 00:00:00',1,'java'),(18,11,'Homework06-Lambda验算','    Deadline: March 27, 23:59:59\r\n    Lambda验算\r\n    Submit:\r\n        Word，Txt或者手写草稿拍照\r\n\r\n    给出完整的具体推导步骤 \r\n    1. \r\n    I = \\x.x \r\n    S = \\x.\\y.\\z.x z(y z) \r\n    l = S I \r\n    求 l m n \r\n    2. \r\n    ZERO = \\f.\\x.x \r\n    SUCC = \\n.\\f.\\x.f (n f x) \r\n    求 SUCC (SUCC ZERO) \r\n    3. \r\n    POW = \\b.\\e.e b \r\n    求POW TWO THREE \r\n    4. \r\n    TRUE = \\x.\\y.x \r\n    FALSE = \\x.\\y.y \r\n    AND = \\p.\\q.p q p \r\n    OR = \\p.\\q.p p q \r\n    NOT = \\p.\\a.\\b.p b a \r\n    IF = \\p.\\a.\\b.p a b \r\n    求NOT (NOT TRUE) \r\n    求IF (OR FALSE FALSE) a b \r\n    5 LEQ = \\m.\\n.ISZERO (SUB m n) \r\n    求大于等于GEQ\r\n\r\n    写出每一步推导过程\r\n    6.Lambda> FACT1 = \\f.\\n.IF (ISZERO n) ONE (MULT n (f f (PRED n)))\r\n    Lambda> FACT = FACT1 FACT1\r\n    求 FACT THREE\r\n    7.Lambda> ADD = W (\\f.\\n.\\m.IF (ISZERO m) n (f f (SUCC n) (PRED m)))\r\n    求 ADD TWO FOUR\r\n    8. Lambda> FACT2 = \\f.\\n.IF (ISZERO n) ONE (MULT n (f (PRED n)))\r\n    Lambda> FACTY = Y FACT2\r\n    求FACTY THREE\r\n    9. Lambda> CONS a (CONS b (CONS c NIL))\r\n    求CAR (CDR (CONS a (CONS b (CONS c NIL))))\r\n    10.  求  有序对的 LENGTH\r\n','2019-03-31 00:00:00',1,'doc/docx/txt/png/jpg/jpeg'),(19,11,'Homework07-TargetHeartRate','Deadline: March 22, 23:59:59\r\n卡蒙内心率\r\n\r\n    TargetHeartRate =(((220-age)-RestingHR)*intensity)+RestingHR\r\n\r\n示例输出\r\n\r\n    Resting Pulse: 65 Age: 22 //提示输入\r\n    Intensity |   Rate\r\n    —————————|—————-\r\n    55%          |138bpm\r\n    60%          |145bpm\r\n    。。。\r\n    95%          |191bpm\r\n\r\nSubmit:\r\n\r\n    源码\r\n','2019-03-31 00:00:00',1,''),(20,11,'Homework08-AvarageNumber','Deadline: March 22, 23:59:59\r\n编写一个程序，提示输入某个网站的响应时间，以毫秒表示，不断让用户输入值，直到用户输入“done”。改程序应打印平均时间（mean），最小时间（min），最大时间（max）和标准差（standard deviation）\r\n示例输出\r\n\r\n    Enter a number: 100\r\n    Enter a number: 200\r\n    Enter a number: 1000\r\n    Enter a number: 300\r\n    Enter a number: done\r\n    Numbers: 100,200,1000,300\r\n    The average is 400.\r\n    The minimum is 100.\r\n    The maximum is 1000.\r\n    The standard deviation is 400.25.\r\n\r\nSubmit:\r\n\r\n    源码\r\n','2019-03-31 00:00:00',1,'java'),(21,11,'Homework09-Stream','Deadline: March 29, 23:59:59\r\n假如给定一个名称列表，其中一些名称包含一个字符。系统会要求您在一个逗号分隔的字符串中返回名称，该字符串中不包含单字母的名称，每个名称的首字母都大写。\r\n\r\n    输入List(\"neal\", \"s\", \"stu\", \"j\", \"rich\", “bob\")\r\n    输出”Neal,Stu,Rich,Bob”\r\n\r\n分别用命令式范式和函数式范式实现。\r\n提交源代码。','2019-03-31 00:00:00',1,'java'),(22,11,'Homework10-CSVFile','    Deadline: March 29, 23:59:59\r\n    编写一个程序，读入以下数据文件\r\n    Ling,Mai,55900\r\n    Johnson,Jim,56500\r\n    …\r\n    Zarnecki,Sabrina,51500\r\n    处理改记录，并以格式化的表格形式显示结果，间隔均匀（4个空格），如示例输出。\r\n        Last    Fisrt    Salary\r\n        Ling    Mai    55900\r\n        Johnson    Jim    56500\r\n        …\r\n        Zarnecki    Sabrina    51500\r\n    提交源代码。\r\n','2019-03-31 00:00:00',1,'java'),(23,11,'Homework11-WeatherData','Homework11-WeatherData\r\n\r\nDeadline: March 29, 23:59:59\r\n题目要求：\r\n\r\n利用已有API抓取显示当地的天气数据。\r\n1. 获取当前城市名称\r\n2. 根据城市名称获取天气数据\r\n\r\n数据格式如下：\r\n\r\n    {\"city\":\"南京\",\"update_time\":\"2017-03-27 22:51\",\"weather\":\"多云\",\"temperature\":13,\"pressure\":1020,\"humidity\":53}\r\n\r\n说明：\r\nupdate_time：天气更新时间\r\ntemperature：温度，单位摄氏度（℃）\r\npressure：气压，单位百帕（mb）\r\nhumidity：相对湿度，单位百分比（%）\r\n3. 解析2中获取的天气数据，并按示例格式输出\r\n示例输出\r\n\r\n城市：南京\r\n更新时间：2017-03-27 22:51\r\n天气：多云\r\n温度：13℃\r\n气压：1020mb\r\n相对湿度：53%','2019-04-06 00:00:00',1,'java'),(24,11,'Homework12-CurriculumSchedule','Deadline: April 10, 23:59:59  通过gitlab提交\r\n题目要求\r\n1. 编写一个课程表程序, 通过命令行方式完成对课程的增、删、改、查、显示。\r\n2. 数据保存在文件中，文件名称：CurriculumSchedule.txt。\r\n输入示例\r\n\r\nAdd 星期四，三、四节，计算与软件工程，仙2-407//如果成功 显示“已添加到文件中”\r\nRemove 星期四，三、四节，计算与软件工程，仙2-407//如果成功 显示“已从文件删除”\r\nUpdate 星期四，三、四节，计算与软件工程，仙2-408//如果成功 显示“已更新文件”\r\nFind 星期四，三、四节 //如存在课程 显示 “课程名，上课地点”\r\nShow //排序显示所有课程\r\n\r\n命令格式\r\n\r\n操作（英文）+空格+信息（全部中文表示） 一行包含所有内容。\r\n\r\nAdd 时间，课时，课程，地点\r\nRemove 时间，课时，课程，地点\r\nUpdate 时间，课时，课程，地点\r\nFind 时间，课时\r\nShow\r\n\r\n数据的范围和格式\r\n\r\n时间：星期一 -- 星期日  全部中文表示\r\n课时：一 -- 八          全部中文表示，多个课时之间用汉字的“、”号分隔\r\n课程：任意              全部中文表示\r\n地点：任意              全部中文表示\r\n\r\n注意\r\n\r\n    Add和Update时不能出现重复值，否则提示“课程已存在”。 判断课程重复的依据是：时间、课时、地点全部相同，容易理解，同一天同一课时，在同一教室不能有多门课程在进行。\r\n    Find命令如果出现多条符合结果，都需要输出，输出格式见输出示例。\r\n    Show命令需要显示已排序的完整课程信息，输出格式见输出示例。 排序要求先按时间升序即周一至周日的顺序，再按课时部分第一位所表示的数字大小升序进行排序。\r\n\r\n输出示例\r\n\r\nFind结果输出:\r\n\r\n    \"计算与软件工程，仙2-407\"\r\n\r\n    \"离散数学，仙1-407\"\r\n\r\n每条记录输出一行，格式为 （课程，地点）。\r\n\r\n课程与地点之间用中文“，”分隔，多条记录之间用换行符分隔。\r\n\r\nShow结果输出：\r\n\r\n    \"星期一，一节，政治，仙2-408\"\r\n\r\n    \"星期一，二、三节，计算与软件工程，仙2-408\"\r\n\r\n    \"星期四，三、四节，计算与软件工程，仙2-407\"\r\n\r\n每条记录输出一行，格式为（时间，课时，课程，地点）。\r\n\r\n每项信息之间用中文“，”分隔，多条记录之间用换行符分隔。\r\n\r\n请注意：输出信息的格式和分隔符请使用示例中定义的，不要随意修改，否则会导致测试用例无法通过。','2019-04-06 00:00:00',1,'java'),(25,12,'需求阶段作业（1）','以小组为单位上交： \r\n\r\n1. 用例文档（用例图、所有用例的描述） \r\n\r\n2. 需求分析模型 （包括每个用例的系统顺序图和概念类图、全局的概念类图、状态图）如果来不及用工具，可以手画。  ','2019-09-23 00:00:00',10,'zip'),(26,12,'需求阶段作业（2）','小组为单位提交： \r\n1. 软件需求规格说明文档（重点包括高优先级的功能需求（每个人至少写2个），低优先级的可以放到作业4中提交，但必须包含其他文档模版中的其他要素）\r\n\r\n2、功能测试用例集\r\n\r\n3、度量数据（包括但不限于：用例数量、场景数量、用例/场景大小、功能需求数量、非功能需求数量、功能点）','2019-10-01 00:00:00',10,'zip'),(27,11,'作业十一','问题shiyi','2019-04-03 22:00:00',10,'zip');
/*!40000 ALTER TABLE `homework_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `homework_submit`
--

DROP TABLE IF EXISTS `homework_submit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `homework_submit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `hid` bigint(20) NOT NULL,
  `sid` bigint(20) NOT NULL,
  `name` varchar(45) NOT NULL,
  `location` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `homework_submit_homework_request_idx` (`hid`),
  KEY `homework_submit_select_course_idx` (`sid`),
  CONSTRAINT `homework_submit_homework_request` FOREIGN KEY (`hid`) REFERENCES `homework_request` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `homework_submit_select_course` FOREIGN KEY (`sid`) REFERENCES `select_course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `homework_submit`
--

LOCK TABLES `homework_submit` WRITE;
/*!40000 ALTER TABLE `homework_submit` DISABLE KEYS */;
INSERT INTO `homework_submit` VALUES (1,1,2,'Entitlement_Details_-_LING_QIN.pdf','161250106_c04b5df6-c28d-49fb-b800-82e37dd602fdEntitlement_Details_-_LING_QIN.pdf'),(2,14,7,'Goods.java','161250106_be662498-016c-4134-b349-e5f072535b98Goods.java'),(3,15,7,'Goods.java','161250106_35f4e439-ef03-494c-84a5-84bdb57e02e7Goods.java'),(4,17,11,'Goods.java','161250105_6403e1fa-d95a-4c01-bffe-16af875ec54cGoods.java'),(5,14,11,'HelloWorld.java','161250105_2e0d358c-9c34-439a-804f-6e05c8b2ac8dHelloWorld.java'),(6,27,7,'Homework02-Sum.zip','161250106_85377b7d-b0f8-42ec-9a82-afe1e3fa0326Homework02-Sum.zip');
/*!40000 ALTER TABLE `homework_submit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tid` bigint(20) NOT NULL,
  `uid` varchar(45) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `post_topic_idx` (`tid`),
  KEY `post_user_idx` (`uid`),
  CONSTRAINT `post_topic` FOREIGN KEY (`tid`) REFERENCES `topic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `post_user` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1,1,'161250106','请老师给点提示','2019-03-10 20:36:58'),(2,1,'161250106','老师快点回话啊\r\n急急急\r\n！！！','2019-03-11 01:38:48'),(3,1,'161250106','快点啊老师','2019-03-11 02:16:21'),(4,1,'wwhaor','用解方程的方法完成','2019-03-11 11:53:53'),(5,2,'wwhaor','作业一成绩老师发布的几个都是测试\r\n不作数的','2019-03-11 11:55:15'),(6,2,'161250106','老师你耍我呢？？？','2019-03-11 17:10:49'),(7,3,'qinliu','飒飒阿萨是','2019-03-18 15:25:52'),(8,3,'161250106','挖到阿萨大大','2019-03-18 15:30:08');
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publish_course`
--

DROP TABLE IF EXISTS `publish_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `publish_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `season` enum('SPRING','SUMMER','FALL','WINTER') NOT NULL,
  `cid` bigint(20) NOT NULL,
  `status` enum('SUBMIT','APPROVED','REJECTED','REJECTED_READ') NOT NULL DEFAULT 'SUBMIT',
  PRIMARY KEY (`id`),
  KEY `publish_course_course_idx` (`cid`),
  CONSTRAINT `publish_course_course` FOREIGN KEY (`cid`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publish_course`
--

LOCK TABLES `publish_course` WRITE;
/*!40000 ALTER TABLE `publish_course` DISABLE KEYS */;
INSERT INTO `publish_course` VALUES (1,2019,'SPRING',4,'APPROVED'),(2,2019,'SPRING',2,'REJECTED'),(3,2019,'SPRING',5,'APPROVED'),(4,2019,'SUMMER',7,'APPROVED'),(5,2019,'FALL',10,'APPROVED'),(6,2020,'FALL',10,'APPROVED'),(7,2019,'SPRING',11,'APPROVED'),(8,2020,'SPRING',11,'APPROVED'),(9,2019,'FALL',12,'APPROVED'),(10,2020,'FALL',11,'REJECTED_READ'),(11,2019,'SPRING',13,'APPROVED'),(12,2019,'FALL',14,'APPROVED'),(13,2019,'SPRING',16,'APPROVED');
/*!40000 ALTER TABLE `publish_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `select_course`
--

DROP TABLE IF EXISTS `select_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `select_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cid` bigint(20) NOT NULL,
  `uid` varchar(45) NOT NULL,
  `select_time` datetime NOT NULL,
  `quit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `select_course_course_class_idx` (`cid`),
  KEY `select_course_user_idx` (`uid`),
  CONSTRAINT `select_course_course_class` FOREIGN KEY (`cid`) REFERENCES `course_class` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `select_course_user` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `select_course`
--

LOCK TABLES `select_course` WRITE;
/*!40000 ALTER TABLE `select_course` DISABLE KEYS */;
INSERT INTO `select_course` VALUES (1,5,'161250106','2019-03-04 21:23:30','2019-03-04 21:53:13'),(2,5,'161250106','2019-03-04 21:53:42',NULL),(3,7,'161250106','2019-03-13 14:41:40',NULL),(4,2,'161250106','2019-03-13 14:42:12',NULL),(6,13,'161250106','2019-03-14 16:49:31',NULL),(7,23,'161250106','2019-03-14 16:50:10',NULL),(8,19,'161250106','2019-03-14 16:50:23','2019-03-18 15:28:02'),(9,20,'161250106','2019-03-14 16:51:17',NULL),(10,24,'161250105','2019-03-14 16:51:37',NULL),(11,22,'161250105','2019-03-14 16:51:48',NULL),(12,20,'161250105','2019-03-14 16:51:58',NULL),(13,18,'161250105','2019-03-14 16:52:09',NULL),(14,14,'161250105','2019-03-14 16:52:25',NULL),(15,10,'161250105','2019-03-14 16:52:43',NULL),(16,8,'161250105','2019-03-14 16:53:00',NULL),(17,1,'161250105','2019-03-14 16:53:46',NULL),(18,25,'161250160','2019-03-14 16:54:40',NULL),(19,23,'161250160','2019-03-14 16:54:48',NULL),(20,20,'161250160','2019-03-14 16:54:58',NULL),(21,19,'161250160','2019-03-14 16:55:08',NULL),(22,16,'161250160','2019-03-14 16:55:24',NULL),(23,15,'161250160','2019-03-14 16:55:34',NULL),(24,11,'161250160','2019-03-14 16:55:45',NULL),(25,6,'161250160','2019-03-14 16:55:53',NULL),(26,25,'MF1832225','2019-03-14 16:56:51',NULL),(27,22,'MF1832225','2019-03-14 16:57:00',NULL),(28,19,'MF1832225','2019-03-14 16:57:19',NULL),(29,17,'MF1832225','2019-03-14 16:57:28',NULL),(30,12,'MF1832225','2019-03-14 16:57:37',NULL),(31,2,'MF1832225','2019-03-14 16:57:52',NULL),(32,9,'MF1832225','2019-03-14 16:58:03',NULL),(33,24,'MF1832270','2019-03-14 16:58:21',NULL),(34,23,'MF1832270','2019-03-14 16:58:31',NULL),(35,16,'MF1832270','2019-03-14 16:59:03',NULL),(36,10,'MF1832270','2019-03-14 16:59:12',NULL),(37,9,'MF1832270','2019-03-14 16:59:31',NULL),(38,1,'MF1832270','2019-03-14 16:59:42',NULL),(39,4,'MF1832270','2019-03-14 16:59:51',NULL),(40,20,'MF1832270','2019-03-14 17:00:06',NULL),(41,25,'DG1708001','2019-03-14 17:00:41',NULL),(42,22,'DG1708001','2019-03-14 17:00:50',NULL),(43,20,'DG1708001','2019-03-14 17:00:58',NULL),(44,12,'DG1708001','2019-03-14 17:01:15',NULL),(45,11,'DG1708001','2019-03-14 17:01:56',NULL),(46,7,'DG1708001','2019-03-14 17:02:04',NULL),(47,5,'DG1708001','2019-03-14 17:02:13',NULL),(48,2,'DG1708001','2019-03-14 17:02:22',NULL),(49,23,'DZ1508027','2019-03-14 17:02:54',NULL),(50,20,'DZ1508027','2019-03-14 17:03:05',NULL),(51,13,'DZ1508027','2019-03-14 17:03:28',NULL),(52,10,'DZ1508027','2019-03-14 17:03:39',NULL),(53,8,'DZ1508027','2019-03-14 17:03:49',NULL),(54,4,'DZ1508027','2019-03-14 17:03:59',NULL),(55,1,'DZ1508027','2019-03-14 17:04:07',NULL),(56,26,'161250106','2019-03-18 15:27:27',NULL);
/*!40000 ALTER TABLE `select_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cid` bigint(20) NOT NULL,
  `title` varchar(45) NOT NULL,
  `fpid` bigint(20) DEFAULT NULL,
  `lpid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_course_idx` (`cid`),
  KEY `topic_first_post_idx` (`fpid`),
  KEY `topic_last_post_idx` (`lpid`),
  CONSTRAINT `topic_course` FOREIGN KEY (`cid`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `topic_first_post` FOREIGN KEY (`fpid`) REFERENCES `post` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `topic_last_post` FOREIGN KEY (`lpid`) REFERENCES `post` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES (1,5,'作业一帮助',1,4),(2,5,'作业一成绩问题',5,6),(3,13,'作业一',7,8);
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `type` enum('TEACHER','STUDENT','ADMINISTRATOR') NOT NULL,
  `email` varchar(56) NOT NULL,
  `portrait` varchar(100) NOT NULL DEFAULT 'default portrait.png',
  `valid` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('161250105','123','乔鑫','STUDENT','161250105@smail.nju.edu.cn','default portrait.png',1),('161250106','123','秦岭','STUDENT','161250106@smail.nju.edu.cn','161250106_b6931b6c-8f91-4dad-a75f-2f83b5fe7f48IMG_6974.jpg',1),('161250107','123','天一','STUDENT','161250107@smail.nju.edu.cn','default portrait.png',1),('161250160','123','肖宇豪','STUDENT','161250160@smail.nju.edu.cn','default portrait.png',1),('admin','123','MyCourses主管','ADMINISTRATOR','437967081@qq.com','default portrait.png',1),('DG1708001','123','安生','STUDENT','DG1708001@smail.nju.edu.cn','default portrait.png',1),('DZ1508027','123','刘泰廷','STUDENT','DZ1508027@smail.nju.edu.cn','default portrait.png',1),('khy','123','匡宏宇','TEACHER','khy@nju.edu.cn','default portrait.png',1),('liujia','123','刘嘉','TEACHER','liujia@nju.edu.cn','default portrait.png',1),('MF1832225','123','尹子越','STUDENT','MF1832225@smail.nju.edu.cn','default portrait.png',1),('MF1832270','123','周赛','STUDENT','MF1832270@smail.nju.edu.cn','default portrait.png',1),('qinliu','123','刘钦','TEACHER','qinliu@nju.edu.cn','default portrait.png',1),('st','123','宋抟','TEACHER','st@nju.edu.cn','default portrait.png',1),('wwhaor','123','王浩然','TEACHER','wwhaor@nju.edu.cn','default portrait.png',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-04 23:44:17
