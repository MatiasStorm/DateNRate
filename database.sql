CREATE DATABASE  IF NOT EXISTS dateNrate /*!40100 DEFAULT CHARACTER SET latin1 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dateNrate`;
-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: db-mysql-ams3-47046-do-user-8264467-0.b.db.ondigitalocean.com    Database: dateNrate
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'ed6ade46-1f59-11eb-b354-3e6e72c17aca:1-299';

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorites` (
  `user_id` int NOT NULL,
  `favorite_user_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`favorite_user_id`),
  KEY `favorite_user_id_idx` (`favorite_user_id`),
  CONSTRAINT `favorite_user_id` FOREIGN KEY (`favorite_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `message_id` int NOT NULL AUTO_INCREMENT,
  `sender_id` int NOT NULL,
  `reciever_id` int NOT NULL,
  `message_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `message` varchar(255) DEFAULT NULL,
  `is_read` tinyint DEFAULT '0',
  PRIMARY KEY (`message_id`),
  KEY `sender_id_idx` (`sender_id`),
  KEY `reciever_id_idx` (`reciever_id`),
  CONSTRAINT `reciever_id` FOREIGN KEY (`reciever_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `sender_id` FOREIGN KEY (`sender_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ratings`
--

DROP TABLE IF EXISTS `ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ratings` (
  `rating_id` int NOT NULL AUTO_INCREMENT,
  `rating_name` varchar(100) NOT NULL,
  PRIMARY KEY (`rating_id`),
  UNIQUE KEY `rating_name_UNIQUE` (`rating_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tags`
--

DROP TABLE IF EXISTS `tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tags` (
  `tag_id` int NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(45) NOT NULL,
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `tag_name_UNIQUE` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `towns`
--

DROP TABLE IF EXISTS `towns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `towns` (
  `town_id` int NOT NULL AUTO_INCREMENT,
  `postal_code` int NOT NULL,
  `town_name` varchar(100) NOT NULL,
  PRIMARY KEY (`town_id`),
  UNIQUE KEY `postal_code_UNIQUE` (`postal_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1104 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_ratings`
--

DROP TABLE IF EXISTS `user_ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_ratings` (
  `user_rating_id` int NOT NULL AUTO_INCREMENT,
  `target_user_id` int NOT NULL,
  `rating` int NOT NULL,
  `rating_id` int NOT NULL,
  `creator_user_id` int NOT NULL,
  PRIMARY KEY (`user_rating_id`),
  KEY `rating_id_idx` (`rating_id`),
  KEY `target_user_id_idx` (`target_user_id`),
  KEY `creator_user_id_idx` (`creator_user_id`),
  CONSTRAINT `creator_user_id` FOREIGN KEY (`creator_user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `rating_id` FOREIGN KEY (`rating_id`) REFERENCES `ratings` (`rating_id`),
  CONSTRAINT `target_user_id` FOREIGN KEY (`target_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_tags`
--

DROP TABLE IF EXISTS `user_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tags` (
  `tag_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`tag_id`,`user_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_tags_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`tag_id`),
  CONSTRAINT `user_tags_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `town_id` int DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `username` varchar(14) NOT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `date_of_birth` date NOT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_description` varchar(500) DEFAULT NULL,
  `is_male` tinyint NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `town_id_idx` (`town_id`),
  CONSTRAINT `town_id` FOREIGN KEY (`town_id`) REFERENCES `towns` (`town_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES `towns` WRITE;
/*!40000 ALTER TABLE `towns` DISABLE KEYS */;
INSERT INTO `towns` VALUES (1,800,'Høje Taastrup'),(2,900,'København C'),(3,917,'Københavns Pakkecent'),(4,960,'Udland'),(5,999,'København C'),(6,1000,'København K'),(7,1050,'København K'),(8,1051,'København K'),(9,1052,'København K'),(10,1053,'København K'),(11,1054,'København K'),(12,1055,'København K'),(13,1056,'København K'),(14,1057,'København K'),(15,1058,'København K'),(16,1059,'København K'),(17,1060,'København K'),(18,1061,'København K'),(19,1062,'København K'),(20,1063,'København K'),(21,1064,'København K'),(22,1065,'København K'),(23,1066,'København K'),(24,1067,'København K'),(25,1068,'København K'),(26,1069,'København K'),(27,1070,'København K'),(28,1071,'København K'),(29,1072,'København K'),(30,1073,'København K'),(31,1074,'København K'),(32,1092,'København K'),(33,1093,'København K'),(34,1095,'København K'),(35,1098,'København K'),(36,1100,'København K'),(37,1101,'København K'),(38,1102,'København K'),(39,1103,'København K'),(40,1104,'København K'),(41,1105,'København K'),(42,1106,'København K'),(43,1107,'København K'),(44,1110,'København K'),(45,1111,'København K'),(46,1112,'København K'),(47,1113,'København K'),(48,1114,'København K'),(49,1115,'København K'),(50,1116,'København K'),(51,1117,'København K'),(52,1118,'København K'),(53,1119,'København K'),(54,1120,'København K'),(55,1121,'København K'),(56,1122,'København K'),(57,1123,'København K'),(58,1124,'København K'),(59,1125,'København K'),(60,1126,'København K'),(61,1127,'København K'),(62,1128,'København K'),(63,1129,'København K'),(64,1130,'København K'),(65,1131,'København K'),(66,1140,'København K'),(67,1147,'København K'),(68,1148,'København K'),(69,1150,'København K'),(70,1151,'København K'),(71,1152,'København K'),(72,1153,'København K'),(73,1154,'København K'),(74,1155,'København K'),(75,1156,'København K'),(76,1157,'København K'),(77,1158,'København K'),(78,1159,'København K'),(79,1160,'København K'),(80,1161,'København K'),(81,1162,'København K'),(82,1164,'København K'),(83,1165,'København K'),(84,1166,'København K'),(85,1167,'København K'),(86,1168,'København K'),(87,1169,'København K'),(88,1170,'København K'),(89,1171,'København K'),(90,1172,'København K'),(91,1173,'København K'),(92,1174,'København K'),(93,1175,'København K'),(94,1200,'København K'),(95,1201,'København K'),(96,1202,'København K'),(97,1203,'København K'),(98,1204,'København K'),(99,1205,'København K'),(100,1206,'København K'),(101,1207,'København K'),(102,1208,'København K'),(103,1209,'København K'),(104,1210,'København K'),(105,1211,'København K'),(106,1213,'København K'),(107,1214,'København K'),(108,1215,'København K'),(109,1216,'København K'),(110,1217,'København K'),(111,1218,'København K'),(112,1219,'København K'),(113,1220,'København K'),(114,1221,'København K'),(115,1240,'København K'),(116,1250,'København K'),(117,1251,'København K'),(118,1253,'København K'),(119,1254,'København K'),(120,1255,'København K'),(121,1256,'København K'),(122,1257,'København K'),(123,1259,'København K'),(124,1260,'København K'),(125,1261,'København K'),(126,1263,'København K'),(127,1264,'København K'),(128,1265,'København K'),(129,1266,'København K'),(130,1267,'København K'),(131,1268,'København K'),(132,1270,'København K'),(133,1271,'København K'),(134,1291,'København K'),(135,1300,'København K'),(136,1301,'København K'),(137,1302,'København K'),(138,1303,'København K'),(139,1304,'København K'),(140,1306,'København K'),(141,1307,'København K'),(142,1308,'København K'),(143,1309,'København K'),(144,1310,'København K'),(145,1311,'København K'),(146,1312,'København K'),(147,1313,'København K'),(148,1314,'København K'),(149,1315,'København K'),(150,1316,'København K'),(151,1317,'København K'),(152,1318,'København K'),(153,1319,'København K'),(154,1320,'København K'),(155,1321,'København K'),(156,1322,'København K'),(157,1323,'København K'),(158,1324,'København K'),(159,1325,'København K'),(160,1326,'København K'),(161,1327,'København K'),(162,1328,'København K'),(163,1329,'København K'),(164,1350,'København K'),(165,1352,'København K'),(166,1353,'København K'),(167,1354,'København K'),(168,1355,'København K'),(169,1356,'København K'),(170,1357,'København K'),(171,1358,'København K'),(172,1359,'København K'),(173,1360,'København K'),(174,1361,'København K'),(175,1362,'København K'),(176,1363,'København K'),(177,1364,'København K'),(178,1365,'København K'),(179,1366,'København K'),(180,1367,'København K'),(181,1368,'København K'),(182,1369,'København K'),(183,1370,'København K'),(184,1371,'København K'),(185,1400,'København K'),(186,1401,'København K'),(187,1402,'København K'),(188,1403,'København K'),(189,1406,'København K'),(190,1407,'København K'),(191,1408,'København K'),(192,1409,'København K'),(193,1410,'København K'),(194,1411,'København K'),(195,1412,'København K'),(196,1413,'København K'),(197,1414,'København K'),(198,1415,'København K'),(199,1416,'København K'),(200,1417,'København K'),(201,1418,'København K'),(202,1419,'København K'),(203,1420,'København K'),(204,1421,'København K'),(205,1422,'København K'),(206,1423,'København K'),(207,1424,'København K'),(208,1425,'København K'),(209,1426,'København K'),(210,1427,'København K'),(211,1428,'København K'),(212,1429,'København K'),(213,1430,'København K'),(214,1431,'København K'),(215,1432,'København K'),(216,1433,'København K'),(217,1434,'København K'),(218,1435,'København K'),(219,1436,'København K'),(220,1437,'København K'),(221,1438,'København K'),(222,1439,'København K'),(223,1440,'København K'),(224,1441,'København K'),(225,1448,'København K'),(226,1450,'København K'),(227,1451,'København K'),(228,1452,'København K'),(229,1453,'København K'),(230,1454,'København K'),(231,1455,'København K'),(232,1456,'København K'),(233,1457,'København K'),(234,1458,'København K'),(235,1459,'København K'),(236,1460,'København K'),(237,1462,'København K'),(238,1463,'København K'),(239,1464,'København K'),(240,1466,'København K'),(241,1467,'København K'),(242,1468,'København K'),(243,1470,'København K'),(244,1471,'København K'),(245,1472,'København K'),(246,1500,'København V'),(247,1513,'Centraltastning'),(248,1532,'København V'),(249,1533,'København V'),(250,1550,'København V'),(251,1551,'København V'),(252,1552,'København V'),(253,1553,'København V'),(254,1554,'København V'),(255,1555,'København V'),(256,1556,'København V'),(257,1557,'København V'),(258,1558,'København V'),(259,1559,'København V'),(260,1560,'København V'),(261,1561,'København V'),(262,1562,'København V'),(263,1563,'København V'),(264,1564,'København V'),(265,1566,'København V'),(266,1567,'København V'),(267,1568,'København V'),(268,1569,'København V'),(269,1570,'København V'),(270,1571,'København V'),(271,1572,'København V'),(272,1573,'København V'),(273,1574,'København V'),(274,1575,'København V'),(275,1576,'København V'),(276,1577,'København V'),(277,1592,'København V'),(278,1599,'København V'),(279,1600,'København V'),(280,1601,'København V'),(281,1602,'København V'),(282,1603,'København V'),(283,1604,'København V'),(284,1606,'København V'),(285,1607,'København V'),(286,1608,'København V'),(287,1609,'København V'),(288,1610,'København V'),(289,1611,'København V'),(290,1612,'København V'),(291,1613,'København V'),(292,1614,'København V'),(293,1615,'København V'),(294,1616,'København V'),(295,1617,'København V'),(296,1618,'København V'),(297,1619,'København V'),(298,1620,'København V'),(299,1621,'København V'),(300,1622,'København V'),(301,1623,'København V'),(302,1624,'København V'),(303,1630,'København V'),(304,1631,'København V'),(305,1632,'København V'),(306,1633,'København V'),(307,1634,'København V'),(308,1635,'København V'),(309,1650,'København V'),(310,1651,'København V'),(311,1652,'København V'),(312,1653,'København V'),(313,1654,'København V'),(314,1655,'København V'),(315,1656,'København V'),(316,1657,'København V'),(317,1658,'København V'),(318,1659,'København V'),(319,1660,'København V'),(320,1661,'København V'),(321,1662,'København V'),(322,1663,'København V'),(323,1664,'København V'),(324,1665,'København V'),(325,1666,'København V'),(326,1667,'København V'),(327,1668,'København V'),(328,1669,'København V'),(329,1670,'København V'),(330,1671,'København V'),(331,1672,'København V'),(332,1673,'København V'),(333,1674,'København V'),(334,1675,'København V'),(335,1676,'København V'),(336,1677,'København V'),(337,1699,'København V'),(338,1700,'København V'),(339,1701,'København V'),(340,1702,'København V'),(341,1703,'København V'),(342,1704,'København V'),(343,1705,'København V'),(344,1706,'København V'),(345,1707,'København V'),(346,1708,'København V'),(347,1709,'København V'),(348,1710,'København V'),(349,1711,'København V'),(350,1712,'København V'),(351,1714,'København V'),(352,1715,'København V'),(353,1716,'København V'),(354,1717,'København V'),(355,1718,'København V'),(356,1719,'København V'),(357,1720,'København V'),(358,1721,'København V'),(359,1722,'København V'),(360,1723,'København V'),(361,1724,'København V'),(362,1725,'København V'),(363,1726,'København V'),(364,1727,'København V'),(365,1728,'København V'),(366,1729,'København V'),(367,1730,'København V'),(368,1731,'København V'),(369,1732,'København V'),(370,1733,'København V'),(371,1734,'København V'),(372,1735,'København V'),(373,1736,'København V'),(374,1737,'København V'),(375,1738,'København V'),(376,1739,'København V'),(377,1749,'København V'),(378,1750,'København V'),(379,1751,'København V'),(380,1752,'København V'),(381,1753,'København V'),(382,1754,'København V'),(383,1755,'København V'),(384,1756,'København V'),(385,1757,'København V'),(386,1758,'København V'),(387,1759,'København V'),(388,1760,'København V'),(389,1761,'København V'),(390,1762,'København V'),(391,1763,'København V'),(392,1764,'København V'),(393,1765,'København V'),(394,1766,'København V'),(395,1770,'København V'),(396,1771,'København V'),(397,1772,'København V'),(398,1773,'København V'),(399,1774,'København V'),(400,1775,'København V'),(401,1777,'København V'),(402,1780,'København V'),(403,1785,'København V'),(404,1786,'København V'),(405,1787,'København V'),(406,1790,'København V'),(407,1799,'København V'),(408,1800,'Frederiksberg C'),(409,1801,'Frederiksberg C'),(410,1802,'Frederiksberg C'),(411,1803,'Frederiksberg C'),(412,1804,'Frederiksberg C'),(413,1805,'Frederiksberg C'),(414,1806,'Frederiksberg C'),(415,1807,'Frederiksberg C'),(416,1808,'Frederiksberg C'),(417,1809,'Frederiksberg C'),(418,1810,'Frederiksberg C'),(419,1811,'Frederiksberg C'),(420,1812,'Frederiksberg C'),(421,1813,'Frederiksberg C'),(422,1814,'Frederiksberg C'),(423,1815,'Frederiksberg C'),(424,1816,'Frederiksberg C'),(425,1817,'Frederiksberg C'),(426,1818,'Frederiksberg C'),(427,1819,'Frederiksberg C'),(428,1820,'Frederiksberg C'),(429,1822,'Frederiksberg C'),(430,1823,'Frederiksberg C'),(431,1824,'Frederiksberg C'),(432,1825,'Frederiksberg C'),(433,1826,'Frederiksberg C'),(434,1827,'Frederiksberg C'),(435,1828,'Frederiksberg C'),(436,1829,'Frederiksberg C'),(437,1850,'Frederiksberg C'),(438,1851,'Frederiksberg C'),(439,1852,'Frederiksberg C'),(440,1853,'Frederiksberg C'),(441,1854,'Frederiksberg C'),(442,1855,'Frederiksberg C'),(443,1856,'Frederiksberg C'),(444,1857,'Frederiksberg C'),(445,1860,'Frederiksberg C'),(446,1861,'Frederiksberg C'),(447,1862,'Frederiksberg C'),(448,1863,'Frederiksberg C'),(449,1864,'Frederiksberg C'),(450,1865,'Frederiksberg C'),(451,1866,'Frederiksberg C'),(452,1867,'Frederiksberg C'),(453,1868,'Frederiksberg C'),(454,1870,'Frederiksberg C'),(455,1871,'Frederiksberg C'),(456,1872,'Frederiksberg C'),(457,1873,'Frederiksberg C'),(458,1874,'Frederiksberg C'),(459,1875,'Frederiksberg C'),(460,1876,'Frederiksberg C'),(461,1877,'Frederiksberg C'),(462,1878,'Frederiksberg C'),(463,1879,'Frederiksberg C'),(464,1900,'Frederiksberg C'),(465,1901,'Frederiksberg C'),(466,1902,'Frederiksberg C'),(467,1903,'Frederiksberg C'),(468,1904,'Frederiksberg C'),(469,1905,'Frederiksberg C'),(470,1906,'Frederiksberg C'),(471,1908,'Frederiksberg C'),(472,1909,'Frederiksberg C'),(473,1910,'Frederiksberg C'),(474,1911,'Frederiksberg C'),(475,1912,'Frederiksberg C'),(476,1913,'Frederiksberg C'),(477,1914,'Frederiksberg C'),(478,1915,'Frederiksberg C'),(479,1916,'Frederiksberg C'),(480,1917,'Frederiksberg C'),(481,1920,'Frederiksberg C'),(482,1921,'Frederiksberg C'),(483,1922,'Frederiksberg C'),(484,1923,'Frederiksberg C'),(485,1924,'Frederiksberg C'),(486,1925,'Frederiksberg C'),(487,1926,'Frederiksberg C'),(488,1927,'Frederiksberg C'),(489,1928,'Frederiksberg C'),(490,1950,'Frederiksberg C'),(491,1951,'Frederiksberg C'),(492,1952,'Frederiksberg C'),(493,1953,'Frederiksberg C'),(494,1954,'Frederiksberg C'),(495,1955,'Frederiksberg C'),(496,1956,'Frederiksberg C'),(497,1957,'Frederiksberg C'),(498,1958,'Frederiksberg C'),(499,1959,'Frederiksberg C'),(500,1960,'Frederiksberg C'),(501,1961,'Frederiksberg C'),(502,1962,'Frederiksberg C'),(503,1963,'Frederiksberg C'),(504,1964,'Frederiksberg C'),(505,1965,'Frederiksberg C'),(506,1966,'Frederiksberg C'),(507,1967,'Frederiksberg C'),(508,1970,'Frederiksberg C'),(509,1971,'Frederiksberg C'),(510,1972,'Frederiksberg C'),(511,1973,'Frederiksberg C'),(512,1974,'Frederiksberg C'),(513,2000,'Frederiksberg'),(514,2100,'København Ø'),(515,2150,'Nordhavn'),(516,2200,'København N'),(517,2300,'København S'),(518,2400,'København NV'),(519,2450,'København SV'),(520,2500,'Valby'),(521,2600,'Glostrup'),(522,2605,'Brøndby'),(523,2610,'Rødovre'),(524,2620,'Albertslund'),(525,2625,'Vallensbæk'),(526,2630,'Taastrup'),(527,2635,'Ishøj'),(528,2640,'Hedehusene'),(529,2650,'Hvidovre'),(530,2660,'Brøndby Strand'),(531,2665,'Vallensbæk Strand'),(532,2670,'Greve'),(533,2680,'Solrød Strand'),(534,2690,'Karlslunde'),(535,2700,'Brønshøj'),(536,2720,'Vanløse'),(537,2730,'Herlev'),(538,2740,'Skovlunde'),(539,2750,'Ballerup'),(540,2760,'Måløv'),(541,2765,'Smørum'),(542,2770,'Kastrup'),(543,2791,'Dragør'),(544,2800,'Kongens Lyngby'),(545,2820,'Gentofte'),(546,2830,'Virum'),(547,2840,'Holte'),(548,2850,'Nærum'),(549,2860,'Søborg'),(550,2870,'Dyssegård'),(551,2880,'Bagsværd'),(552,2900,'Hellerup'),(553,2920,'Charlottenlund'),(554,2930,'Klampenborg'),(555,2942,'Skodsborg'),(556,2950,'Vedbæk'),(557,2960,'Rungsted Kyst'),(558,2970,'Hørsholm'),(559,2980,'Kokkedal'),(560,2990,'Nivå'),(561,3000,'Helsingør'),(562,3050,'Humlebæk'),(563,3060,'Espergærde'),(564,3070,'Snekkersten'),(565,3080,'Tikøb'),(566,3100,'Hornbæk'),(567,3120,'Dronningmølle'),(568,3140,'Ålsgårde'),(569,3150,'Hellebæk'),(570,3200,'Helsinge'),(571,3210,'Vejby'),(572,3220,'Tisvildeleje'),(573,3230,'Græsted'),(574,3250,'Gilleleje'),(575,3300,'Frederiksværk'),(576,3310,'Ølsted'),(577,3320,'Skævinge'),(578,3330,'Gørløse'),(579,3360,'Liseleje'),(580,3370,'Melby'),(581,3390,'Hundested'),(582,3400,'Hillerød'),(583,3450,'Allerød'),(584,3460,'Birkerød'),(585,3480,'Fredensborg'),(586,3490,'Kvistgård'),(587,3500,'Værløse'),(588,3520,'Farum'),(589,3540,'Lynge'),(590,3550,'Slangerup'),(591,3600,'Frederikssund'),(592,3630,'Jægerspris'),(593,3650,'Ølstykke'),(594,3660,'Stenløse'),(595,3670,'Veksø Sjælland'),(596,3700,'Rønne'),(597,3720,'Aakirkeby'),(598,3730,'Nexø'),(599,3740,'Svaneke'),(600,3751,'Østermarie'),(601,3760,'Gudhjem'),(602,3770,'Allinge'),(603,3782,'Klemensker'),(604,3790,'Hasle'),(605,4000,'Roskilde'),(606,4030,'Tune'),(607,4040,'Jyllinge'),(608,4050,'Skibby'),(609,4060,'Kirke Såby'),(610,4070,'Kirke Hyllinge'),(611,4100,'Ringsted'),(612,4130,'Viby Sjælland'),(613,4140,'Borup'),(614,4160,'Herlufmagle'),(615,4171,'Glumsø'),(616,4173,'Fjenneslev'),(617,4174,'Jystrup Midtsj'),(618,4180,'Sorø'),(619,4190,'Munke Bjergby'),(620,4200,'Slagelse'),(621,4220,'Korsør'),(622,4230,'Skælskør'),(623,4241,'Vemmelev'),(624,4242,'Boeslunde'),(625,4243,'Rude'),(626,4250,'Fuglebjerg'),(627,4261,'Dalmose'),(628,4262,'Sandved'),(629,4270,'Høng'),(630,4281,'Gørlev'),(631,4291,'Ruds Vedby'),(632,4293,'Dianalund'),(633,4295,'Stenlille'),(634,4296,'Nyrup'),(635,4300,'Holbæk'),(636,4320,'Lejre'),(637,4330,'Hvalsø'),(638,4340,'Tølløse'),(639,4350,'Ugerløse'),(640,4360,'Kirke Eskilstrup'),(641,4370,'Store Merløse'),(642,4390,'Vipperød'),(643,4400,'Kalundborg'),(644,4420,'Regstrup'),(645,4440,'Mørkøv'),(646,4450,'Jyderup'),(647,4460,'Snertinge'),(648,4470,'Svebølle'),(649,4480,'Store Fuglede'),(650,4490,'Jerslev Sjælland'),(651,4500,'Nykøbing Sj'),(652,4520,'Svinninge'),(653,4532,'Gislinge'),(654,4534,'Hørve'),(655,4540,'Fårevejle'),(656,4550,'Asnæs'),(657,4560,'Vig'),(658,4571,'Grevinge'),(659,4572,'Nørre Asmindrup'),(660,4573,'Højby'),(661,4581,'Rørvig'),(662,4583,'Sjællands Odde'),(663,4591,'Føllenslev'),(664,4592,'Sejerø'),(665,4593,'Eskebjerg'),(666,4600,'Køge'),(667,4621,'Gadstrup'),(668,4622,'Havdrup'),(669,4623,'Lille Skensved'),(670,4632,'Bjæverskov'),(671,4640,'Faxe'),(672,4652,'Hårlev'),(673,4653,'Karise'),(674,4654,'Faxe Ladeplads'),(675,4660,'Store Heddinge'),(676,4671,'Strøby'),(677,4672,'Klippinge'),(678,4673,'Rødvig Stevns'),(679,4681,'Herfølge'),(680,4682,'Tureby'),(681,4683,'Rønnede'),(682,4684,'Holmegaard'),(683,4690,'Haslev'),(684,4700,'Næstved'),(685,4720,'Præstø'),(686,4733,'Tappernøje'),(687,4735,'Mern'),(688,4736,'Karrebæksminde'),(689,4750,'Lundby'),(690,4760,'Vordingborg'),(691,4771,'Kalvehave'),(692,4772,'Langebæk'),(693,4773,'Stensved'),(694,4780,'Stege'),(695,4791,'Borre'),(696,4792,'Askeby'),(697,4793,'Bogø By'),(698,4800,'Nykøbing F'),(699,4840,'Nørre Alslev'),(700,4850,'Stubbekøbing'),(701,4862,'Guldborg'),(702,4863,'Eskilstrup'),(703,4871,'Horbelev'),(704,4872,'Idestrup'),(705,4873,'Væggerløse'),(706,4874,'Gedser'),(707,4880,'Nysted'),(708,4891,'Toreby L'),(709,4892,'Kettinge'),(710,4894,'Øster Ulslev'),(711,4895,'Errindlev'),(712,4900,'Nakskov'),(713,4912,'Harpelunde'),(714,4913,'Horslunde'),(715,4920,'Søllested'),(716,4930,'Maribo'),(717,4941,'Bandholm'),(718,4943,'Torrig L'),(719,4944,'Fejø'),(720,4951,'Nørreballe'),(721,4952,'Stokkemarke'),(722,4953,'Vesterborg'),(723,4960,'Holeby'),(724,4970,'Rødby'),(725,4983,'Dannemare'),(726,4990,'Sakskøbing'),(727,5000,'Odense C'),(728,5200,'Odense V'),(729,5210,'Odense NV'),(730,5220,'Odense SØ'),(731,5230,'Odense M'),(732,5240,'Odense NØ'),(733,5250,'Odense SV'),(734,5260,'Odense S'),(735,5270,'Odense N'),(736,5290,'Marslev'),(737,5300,'Kerteminde'),(738,5320,'Agedrup'),(739,5330,'Munkebo'),(740,5350,'Rynkeby'),(741,5370,'Mesinge'),(742,5380,'Dalby'),(743,5390,'Martofte'),(744,5400,'Bogense'),(745,5450,'Otterup'),(746,5462,'Morud'),(747,5463,'Harndrup'),(748,5464,'Brenderup Fyn'),(749,5466,'Asperup'),(750,5471,'Søndersø'),(751,5474,'Veflinge'),(752,5485,'Skamby'),(753,5491,'Blommenslyst'),(754,5492,'Vissenbjerg'),(755,5500,'Middelfart'),(756,5540,'Ullerslev'),(757,5550,'Langeskov'),(758,5560,'Aarup'),(759,5580,'Nørre Aaby'),(760,5591,'Gelsted'),(761,5592,'Ejby'),(762,5600,'Faaborg'),(763,5610,'Assens'),(764,5620,'Glamsbjerg'),(765,5631,'Ebberup'),(766,5642,'Millinge'),(767,5672,'Broby'),(768,5683,'Haarby'),(769,5690,'Tommerup'),(770,5700,'Svendborg'),(771,5750,'Ringe'),(772,5762,'Vester Skerninge'),(773,5771,'Stenstrup'),(774,5772,'Kværndrup'),(775,5792,'Årslev'),(776,5800,'Nyborg'),(777,5853,'Ørbæk'),(778,5854,'Gislev'),(779,5856,'Ryslinge'),(780,5863,'Ferritslev Fyn'),(781,5871,'Frørup'),(782,5874,'Hesselager'),(783,5881,'Skårup Fyn'),(784,5882,'Vejstrup'),(785,5883,'Oure'),(786,5884,'Gudme'),(787,5892,'Gudbjerg Sydfyn'),(788,5900,'Rudkøbing'),(789,5932,'Humble'),(790,5935,'Bagenkop'),(791,5953,'Tranekær'),(792,5960,'Marstal'),(793,5970,'Ærøskøbing'),(794,5985,'Søby Ærø'),(795,6000,'Kolding'),(796,6040,'Egtved'),(797,6051,'Almind'),(798,6052,'Viuf'),(799,6064,'Jordrup'),(800,6070,'Christiansfeld'),(801,6091,'Bjert'),(802,6092,'Sønder Stenderup'),(803,6093,'Sjølund'),(804,6094,'Hejls'),(805,6100,'Haderslev'),(806,6200,'Aabenraa'),(807,6230,'Rødekro'),(808,6240,'Løgumkloster'),(809,6261,'Bredebro'),(810,6270,'Tønder'),(811,6280,'Højer'),(812,6300,'Gråsten'),(813,6310,'Broager'),(814,6320,'Egernsund'),(815,6330,'Padborg'),(816,6340,'Kruså'),(817,6360,'Tinglev'),(818,6372,'Bylderup-Bov'),(819,6392,'Bolderslev'),(820,6400,'Sønderborg'),(821,6430,'Nordborg'),(822,6440,'Augustenborg'),(823,6470,'Sydals'),(824,6500,'Vojens'),(825,6510,'Gram'),(826,6520,'Toftlund'),(827,6534,'Agerskov'),(828,6535,'Branderup J'),(829,6541,'Bevtoft'),(830,6560,'Sommersted'),(831,6580,'Vamdrup'),(832,6600,'Vejen'),(833,6621,'Gesten'),(834,6622,'Bække'),(835,6623,'Vorbasse'),(836,6630,'Rødding'),(837,6640,'Lunderskov'),(838,6650,'Brørup'),(839,6660,'Lintrup'),(840,6670,'Holsted'),(841,6682,'Hovborg'),(842,6683,'Føvling'),(843,6690,'Gørding'),(844,6700,'Esbjerg'),(845,6705,'Esbjerg Ø'),(846,6710,'Esbjerg V'),(847,6715,'Esbjerg N'),(848,6720,'Fanø'),(849,6731,'Tjæreborg'),(850,6740,'Bramming'),(851,6752,'Glejbjerg'),(852,6753,'Agerbæk'),(853,6760,'Ribe'),(854,6771,'Gredstedbro'),(855,6780,'Skærbæk'),(856,6792,'Rømø'),(857,6800,'Varde'),(858,6818,'Årre'),(859,6823,'Ansager'),(860,6830,'Nørre Nebel'),(861,6840,'Oksbøl'),(862,6851,'Janderup Vestj'),(863,6852,'Billum'),(864,6853,'Vejers Strand'),(865,6854,'Henne'),(866,6855,'Outrup'),(867,6857,'Blåvand'),(868,6862,'Tistrup'),(869,6870,'Ølgod'),(870,6880,'Tarm'),(871,6893,'Hemmet'),(872,6900,'Skjern'),(873,6920,'Videbæk'),(874,6933,'Kibæk'),(875,6940,'Lem St'),(876,6950,'Ringkøbing'),(877,6960,'Hvide Sande'),(878,6971,'Spjald'),(879,6973,'Ørnhøj'),(880,6980,'Tim'),(881,6990,'Ulfborg'),(882,7000,'Fredericia'),(883,7007,'Fredericia'),(884,7080,'Børkop'),(885,7100,'Vejle'),(886,7120,'Vejle Øst'),(887,7130,'Juelsminde'),(888,7140,'Stouby'),(889,7150,'Barrit'),(890,7160,'Tørring'),(891,7171,'Uldum'),(892,7173,'Vonge'),(893,7182,'Bredsten'),(894,7183,'Randbøl'),(895,7184,'Vandel'),(896,7190,'Billund'),(897,7200,'Grindsted'),(898,7250,'Hejnsvig'),(899,7260,'Sønder Omme'),(900,7270,'Stakroge'),(901,7280,'Sønder Felding'),(902,7300,'Jelling'),(903,7321,'Gadbjerg'),(904,7323,'Give'),(905,7330,'Brande'),(906,7361,'Ejstrupholm'),(907,7362,'Hampen'),(908,7400,'Herning'),(909,7430,'Ikast'),(910,7441,'Bording'),(911,7442,'Engesvang'),(912,7451,'Sunds'),(913,7470,'Karup J'),(914,7480,'Vildbjerg'),(915,7490,'Aulum'),(916,7500,'Holstebro'),(917,7540,'Haderup'),(918,7550,'Sørvad'),(919,7560,'Hjerm'),(920,7570,'Vemb'),(921,7600,'Struer'),(922,7620,'Lemvig'),(923,7650,'Bøvlingbjerg'),(924,7660,'Bækmarksbro'),(925,7673,'Harboøre'),(926,7680,'Thyborøn'),(927,7700,'Thisted'),(928,7730,'Hanstholm'),(929,7741,'Frøstrup'),(930,7742,'Vesløs'),(931,7752,'Snedsted'),(932,7755,'Bedsted Thy'),(933,7760,'Hurup Thy'),(934,7770,'Vestervig'),(935,7790,'Thyholm'),(936,7800,'Skive'),(937,7830,'Vinderup'),(938,7840,'Højslev'),(939,7850,'Stoholm Jyll'),(940,7860,'Spøttrup'),(941,7870,'Roslev'),(942,7884,'Fur'),(943,7900,'Nykøbing M'),(944,7950,'Erslev'),(945,7960,'Karby'),(946,7970,'Redsted M'),(947,7980,'Vils'),(948,7990,'Øster Assels'),(949,8000,'Aarhus C'),(950,8200,'Aarhus N'),(951,8210,'Aarhus V'),(952,8220,'Brabrand'),(953,8230,'Åbyhøj'),(954,8240,'Risskov'),(955,8245,'Risskov Ø'),(956,8250,'Egå'),(957,8260,'Viby J'),(958,8270,'Højbjerg'),(959,8300,'Odder'),(960,8305,'Samsø'),(961,8310,'Tranbjerg J'),(962,8320,'Mårslet'),(963,8330,'Beder'),(964,8340,'Malling'),(965,8350,'Hundslund'),(966,8355,'Solbjerg'),(967,8361,'Hasselager'),(968,8362,'Hørning'),(969,8370,'Hadsten'),(970,8380,'Trige'),(971,8381,'Tilst'),(972,8382,'Hinnerup'),(973,8400,'Ebeltoft'),(974,8410,'Rønde'),(975,8420,'Knebel'),(976,8444,'Balle'),(977,8450,'Hammel'),(978,8462,'Harlev J'),(979,8464,'Galten'),(980,8471,'Sabro'),(981,8472,'Sporup'),(982,8500,'Grenaa'),(983,8520,'Lystrup'),(984,8530,'Hjortshøj'),(985,8541,'Skødstrup'),(986,8543,'Hornslet'),(987,8544,'Mørke'),(988,8550,'Ryomgård'),(989,8560,'Kolind'),(990,8570,'Trustrup'),(991,8581,'Nimtofte'),(992,8585,'Glesborg'),(993,8586,'Ørum Djurs'),(994,8592,'Anholt'),(995,8600,'Silkeborg'),(996,8620,'Kjellerup'),(997,8632,'Lemming'),(998,8641,'Sorring'),(999,8643,'Ans By'),(1000,8653,'Them'),(1001,8654,'Bryrup'),(1002,8660,'Skanderborg'),(1003,8670,'Låsby'),(1004,8680,'Ry'),(1005,8700,'Horsens'),(1006,8721,'Daugård'),(1007,8722,'Hedensted'),(1008,8723,'Løsning'),(1009,8732,'Hovedgård'),(1010,8740,'Brædstrup'),(1011,8751,'Gedved'),(1012,8752,'Østbirk'),(1013,8762,'Flemming'),(1014,8763,'Rask Mølle'),(1015,8765,'Klovborg'),(1016,8766,'Nørre Snede'),(1017,8781,'Stenderup'),(1018,8783,'Hornsyld'),(1019,8800,'Viborg'),(1020,8830,'Tjele'),(1021,8831,'Løgstrup'),(1022,8832,'Skals'),(1023,8840,'Rødkærsbro'),(1024,8850,'Bjerringbro'),(1025,8860,'Ulstrup'),(1026,8870,'Langå'),(1027,8881,'Thorsø'),(1028,8882,'Fårvang'),(1029,8883,'Gjern'),(1030,8900,'Randers C'),(1031,8920,'Randers NV'),(1032,8930,'Randers NØ'),(1033,8940,'Randers SV'),(1034,8950,'Ørsted'),(1035,8960,'Randers SØ'),(1036,8961,'Allingåbro'),(1037,8963,'Auning'),(1038,8970,'Havndal'),(1039,8981,'Spentrup'),(1040,8983,'Gjerlev J'),(1041,8990,'Fårup'),(1042,9000,'Aalborg'),(1043,9200,'Aalborg SV'),(1044,9210,'Aalborg SØ'),(1045,9220,'Aalborg Øst'),(1046,9230,'Svenstrup J'),(1047,9240,'Nibe'),(1048,9260,'Gistrup'),(1049,9270,'Klarup'),(1050,9280,'Storvorde'),(1051,9293,'Kongerslev'),(1052,9300,'Sæby'),(1053,9310,'Vodskov'),(1054,9320,'Hjallerup'),(1055,9330,'Dronninglund'),(1056,9340,'Asaa'),(1057,9352,'Dybvad'),(1058,9362,'Gandrup'),(1059,9370,'Hals'),(1060,9380,'Vestbjerg'),(1061,9381,'Sulsted'),(1062,9382,'Tylstrup'),(1063,9400,'Nørresundby'),(1064,9430,'Vadum'),(1065,9440,'Aabybro'),(1066,9460,'Brovst'),(1067,9480,'Løkken'),(1068,9490,'Pandrup'),(1069,9492,'Blokhus'),(1070,9493,'Saltum'),(1071,9500,'Hobro'),(1072,9510,'Arden'),(1073,9520,'Skørping'),(1074,9530,'Støvring'),(1075,9541,'Suldrup'),(1076,9550,'Mariager'),(1077,9560,'Hadsund'),(1078,9574,'Bælum'),(1079,9575,'Terndrup'),(1080,9600,'Aars'),(1081,9610,'Nørager'),(1082,9620,'Aalestrup'),(1083,9631,'Gedsted'),(1084,9632,'Møldrup'),(1085,9640,'Farsø'),(1086,9670,'Løgstør'),(1087,9681,'Ranum'),(1088,9690,'Fjerritslev'),(1089,9700,'Brønderslev'),(1090,9740,'Jerslev J'),(1091,9750,'Østervrå'),(1092,9760,'Vrå'),(1093,9800,'Hjørring'),(1094,9830,'Tårs'),(1095,9850,'Hirtshals'),(1096,9870,'Sindal'),(1097,9881,'Bindslev'),(1098,9900,'Frederikshavn'),(1099,9940,'Læsø'),(1100,9970,'Strandby'),(1101,9981,'Jerup'),(1102,9982,'Ålbæk'),(1103,9990,'Skagen');
/*!40000 ALTER TABLE `towns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ratings`
--

LOCK TABLES `ratings` WRITE;
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
INSERT INTO `ratings` VALUES (7,'Chance for sex'),(2,'Humor'),(4,'Romance'),(6,'Rundhåndet'),(1,'Udseende'),(3,'VenligHed'),(5,'Åbenhed');
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tags`
--

LOCK TABLES `tags` WRITE;
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` VALUES (1,'Blond'),(2,'Brunette'),(13,'Emo'),(3,'Ginger'),(12,'God Røv'),(14,'Goth'),(17,'Grissebasse'),(7,'Høj'),(8,'Lav'),(16,'Lummergøj'),(18,'Narkoman'),(6,'Ok Tyk'),(11,'Piercing'),(15,'Punk'),(5,'Slank'),(9,'Solbrun'),(4,'Store Bryster'),(10,'Tattoo');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;
UNLOCK TABLES;

SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-11 18:37:28
