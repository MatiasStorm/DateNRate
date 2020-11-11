CREATE DATABASE  IF NOT EXISTS "dateNrate" /*!40100 DEFAULT CHARACTER SET latin1 */ /*!80016 DEFAULT ENCRYPTION='N' */;
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
