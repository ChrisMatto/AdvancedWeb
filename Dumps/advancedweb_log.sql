-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: advancedweb
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
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `IDLog` int(11) NOT NULL AUTO_INCREMENT,
  `Utente` int(11) NOT NULL,
  `Data` datetime NOT NULL,
  `Descrizione` text NOT NULL,
  PRIMARY KEY (`IDLog`),
  KEY `Utente` (`Utente`),
  CONSTRAINT `log_ibfk_1` FOREIGN KEY (`Utente`) REFERENCES `utente` (`IDUtente`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES (1,1,'2018-02-21 08:42:53','Ha creato il cdl Informatica'),(2,1,'2018-02-21 08:45:23','Ha registrato il docenteGiuseppeDella Penna'),(3,1,'2018-02-21 09:08:04','Ha creato il corso Reti neurali'),(4,2,'2018-02-21 09:45:42','Ha modificato il corso diReti neurali'),(5,1,'2018-02-21 10:10:28','Ha creato il corso Laboratorio basi di dati'),(6,1,'2018-02-21 10:40:26','Ha creato il cdl Magistrale Informatica'),(7,1,'2018-02-21 10:44:53','Ha registrato il docenteFilippoMignosi'),(8,1,'2018-02-21 10:50:31','Ha creato il corso Teoria Dell\'Informazione'),(9,1,'2018-02-21 10:53:04','Ha aggiunto il materialeBase di daticorso2'),(10,1,'2018-02-21 10:55:46','Ha modificato il corso diTeoria Dell\'Informazione'),(11,1,'2018-02-21 11:55:51','Ha creato il corso Algoritmi'),(12,1,'2018-02-21 15:06:07','Ha creato il cdl /'),(13,2,'2018-02-25 15:18:27','Ha modificato il corso diTeoria Dell\'Informazione');
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-04 11:26:17
