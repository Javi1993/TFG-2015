CREATE DATABASE  IF NOT EXISTS `eprail` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `eprail`;
-- MySQL dump 10.13  Distrib 5.6.19, for Win32 (x86)
--
-- Host: localhost    Database: eprail
-- ------------------------------------------------------
-- Server version	5.6.20-enterprise-commercial-advanced

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
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `IdProject` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID of the project',
  `ProjectName` varchar(60) NOT NULL COMMENT 'Name of the project',
  `ProjectDescription` varchar(150) DEFAULT NULL COMMENT 'Description of the project',
  `ONGFile` blob NOT NULL COMMENT 'Binary file containing the project',
  `UID` int(11) NOT NULL COMMENT 'UID of the user that created the project',
  `IdProjectStatus` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Status of the project: 0 - pending, 1- calculated, 2- simulated, 3- errors.',
  `DateCreation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Date at which the project was created',
  `DateModified` timestamp NULL DEFAULT NULL COMMENT 'Date at which the project was modified',
  PRIMARY KEY (`IdProject`),
  KEY `FK_USER_idx` (`UID`),
  CONSTRAINT `FK_USER` FOREIGN KEY (`UID`) REFERENCES `users` (`UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Tabla con la información de los poryectos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES (1,'a.ongf',NULL,'C:\\Users\\Javier\\Desktop\\TFG\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\eprail\\ONGFiles\\18',18,0,'2015-02-18 09:00:05',NULL);
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `UID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'User ID',
  `FirstName` varchar(60) NOT NULL COMMENT 'First name of user',
  `FamilyName` varchar(80) NOT NULL COMMENT 'Family names of the user',
  `email` varchar(60) NOT NULL COMMENT 'e-mail of the user',
  `IsValidate` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'True if the user has been validated',
  `DateRegistration` timestamp NULL DEFAULT NULL COMMENT 'Date in which the user has completed the registration (validated account)',
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`UID`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='Tabla de usuarios';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (18,'Javier','García Pérez','javierdavid93@gmail.com',1,'2015-02-17 10:10:57','7eaebeeccb821546a4a82efb14c7d896');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-18 10:01:39
