-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
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
-- Table structure for table `deletedprojects`
--

DROP TABLE IF EXISTS `deletedprojects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deletedprojects` (
  `IdProject` int(11) NOT NULL COMMENT 'ID of the project',
  `ProjectName` varchar(60) NOT NULL COMMENT 'Name of the project',
  `ProjectDescription` varchar(150) DEFAULT NULL COMMENT 'Description of the project',
  `ONGFile` blob NOT NULL COMMENT 'Binary file containing the project',
  `UID` int(11) NOT NULL COMMENT 'UID of the user that created the project',
  `IdProjectStatus` tinyint(4) NOT NULL COMMENT 'Status of the project',
  `DateCreation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Date at which the project was created',
  `DateDeleted` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'Date at which the project was deleted',
  PRIMARY KEY (`IdProject`),
  KEY `FK_DUSERS_idx` (`UID`),
  KEY `FK_DSTATUS_idx` (`IdProjectStatus`),
  CONSTRAINT `FK_DSTATUS` FOREIGN KEY (`IdProjectStatus`) REFERENCES `statuscategories` (`IdProjectStatus`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_DUSERS` FOREIGN KEY (`UID`) REFERENCES `users` (`UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deletedprojects`
--

LOCK TABLES `deletedprojects` WRITE;
/*!40000 ALTER TABLE `deletedprojects` DISABLE KEYS */;
/*!40000 ALTER TABLE `deletedprojects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `openjpa_sequence_table`
--

DROP TABLE IF EXISTS `openjpa_sequence_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `openjpa_sequence_table` (
  `ID` tinyint(4) NOT NULL,
  `SEQUENCE_VALUE` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `openjpa_sequence_table`
--

LOCK TABLES `openjpa_sequence_table` WRITE;
/*!40000 ALTER TABLE `openjpa_sequence_table` DISABLE KEYS */;
INSERT INTO `openjpa_sequence_table` VALUES (0,151);
/*!40000 ALTER TABLE `openjpa_sequence_table` ENABLE KEYS */;
UNLOCK TABLES;

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
  `IdProjectStatus` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'Status of the project',
  `DateCreation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Date at which the project was created',
  `DateModified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Date at which the project was modified',
  PRIMARY KEY (`IdProject`),
  KEY `FK_USER_idx` (`UID`),
  KEY `FK_STATUS_idx` (`IdProjectStatus`),
  CONSTRAINT `FK_STATUS` FOREIGN KEY (`IdProjectStatus`) REFERENCES `statuscategories` (`IdProjectStatus`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_USER` FOREIGN KEY (`UID`) REFERENCES `users` (`UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Tabla con la información de los poryectos';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sharings`
--

DROP TABLE IF EXISTS `sharings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sharings` (
  `IdSharing` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID of the link between a user and a project',
  `IdProject` int(11) NOT NULL COMMENT 'ID of the project which is shared',
  `UID` int(11) NOT NULL COMMENT 'User ID of the user which has been granted rights in this project',
  `UIDsharer` int(11) NOT NULL COMMENT 'User ID of the user which gives rights (which may not be the owner)',
  `DateShared` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Date at which the project was shared',
  `DateChanged` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Date at which the rights granted were modified the last time',
  `AllowRecalculate` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'True if the user can launch a recalculation of the project',
  `AllowDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'True if the user can delete the project',
  `AllowDownload` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'True if the user can download the project file',
  `AllowShare` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'True if the user can share the project with other users',
  PRIMARY KEY (`IdSharing`),
  KEY `FK_USERSH_idx` (`UIDsharer`),
  KEY `FK_PROJECTSH_idx` (`IdProject`),
  KEY `FK_USERGIV_idx` (`UID`),
  CONSTRAINT `FK_PROJECTSH` FOREIGN KEY (`IdProject`) REFERENCES `projects` (`IdProject`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_USERGIV` FOREIGN KEY (`UID`) REFERENCES `users` (`UID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_USERSH` FOREIGN KEY (`UIDsharer`) REFERENCES `users` (`UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sharings`
--

LOCK TABLES `sharings` WRITE;
/*!40000 ALTER TABLE `sharings` DISABLE KEYS */;
/*!40000 ALTER TABLE `sharings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `statuscategories`
--

DROP TABLE IF EXISTS `statuscategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `statuscategories` (
  `IdProjectStatus` tinyint(4) NOT NULL COMMENT 'ID of the status (0 - pending, 1- calculating, 2- simulated, 3- errors.)',
  `StatusName` varchar(45) NOT NULL COMMENT 'Name of this status',
  `StatusDescription` varchar(150) NOT NULL COMMENT 'Text describing the meaning of this status',
  PRIMARY KEY (`IdProjectStatus`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `statuscategories`
--

LOCK TABLES `statuscategories` WRITE;
/*!40000 ALTER TABLE `statuscategories` DISABLE KEYS */;
INSERT INTO `statuscategories` VALUES (0,'Pendiente','El caso está subido pero todavía no hubo simulación'),(1,'Calculando...','Se ha lanzado la simulación, pero todavía no concluyó'),(2,'Simulado','Simulación concluida con éxito'),(3,'Errores','Error durante la simulación');
/*!40000 ALTER TABLE `statuscategories` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='Tabla de usuarios';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Pepe','Martínez Lopetegui','javierdavid93@gemail.com',1,'2015-03-12 11:16:08','7eaebeeccb821546a4a82efb14c7d896'),(3,'Andrés','Román Tobijo','antail@a.com',1,'2015-03-12 11:16:08','7eaebeeccb821546a4a82efb14c7d896'),(4,'Facundo','Romero Pérez','facundo@gmail.com',1,'2015-03-12 11:16:08','7eaebeeccb821546a4a82efb14c7d896'),(5,'Hola','Hasta Luego','hola@gmail.com',1,'2015-03-12 11:16:08','7eaebeeccb821546a4a82efb14c7d896'),(6,'Javier','García Pérez','javierdavid93@gmail.com',1,'2015-04-03 17:29:58','7eaebeeccb821546a4a82efb14c7d896');
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

-- Dump completed on 2015-04-05 20:11:30
