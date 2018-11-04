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
-- Table structure for table `dublino_it`
--

DROP TABLE IF EXISTS `dublino_it`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dublino_it` (
  `Corso` int(11) NOT NULL,
  AnnoCorso int(11) not null,
  `Knowledge` text,
  `Application` text,
  `Evaluation` text,
  `Communication` text,
  `Lifelong` text,
  PRIMARY KEY (`Corso`,AnnoCorso),
  CONSTRAINT `dublino_it_ibfk_1` FOREIGN KEY (`Corso`,AnnoCorso) REFERENCES `corso` (`IDCorso`,Anno)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dublino_it`
--

LOCK TABLES `dublino_it` WRITE;
/*!40000 ALTER TABLE `dublino_it` DISABLE KEYS */;
INSERT INTO `dublino_it` VALUES (1,2018,'','','','',''),(1,2017,'','','','',''),(2,2018,'<p>prova</p>','<p>prova</p>','<p>prova</p>','<p>prova</p>','<p>prova</p>'),(3,2018,'<p>Knowledge</p>','<p>Application</p>','<p>Evaluation</p>','<p>Communication</p>','<p>Lifelong</p>'),(4,2017,'','','','','');
/*!40000 ALTER TABLE `dublino_it` ENABLE KEYS */;
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
