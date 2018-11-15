CREATE DATABASE  IF NOT EXISTS `newadvancedweb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `newadvancedweb`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: newadvancedweb
-- ------------------------------------------------------
-- Server version	5.7.23

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
-- Table structure for table `cdl`
--

DROP TABLE IF EXISTS `cdl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdl` (
  `IDCDL` int(11) NOT NULL AUTO_INCREMENT,
  `Nome_it` varchar(50) NOT NULL,
  `Nome_en` varchar(50) NOT NULL,
  `Anno` int(11) NOT NULL,
  `CFU` int(11) NOT NULL,
  `Magistrale` tinyint(4) NOT NULL DEFAULT '0',
  `Immagine` varchar(100) DEFAULT NULL,
  `Descrizione_it` longtext,
  `Descrizione_en` longtext,
  `Abbr_it` varchar(5) NOT NULL,
  `Abbr_en` varchar(5) NOT NULL,
  PRIMARY KEY (`IDCDL`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdl`
--

LOCK TABLES `cdl` WRITE;
/*!40000 ALTER TABLE `cdl` DISABLE KEYS */;
INSERT INTO `cdl` VALUES (1,'Informatica','Computer Science',2018,180,0,'imgCDL\\Informatica.jpg','Corso informatica triennale','Computer science course','Inf','inf'),(2,'Magistrale Informatica','Master Computer Science',2018,120,1,'imgCDL\\Magistrale Informatica.jpg','Corso magistrale in Informatica','Master Degree in Computer Science','L.M.I','M.C.S'),(3,'/','/',2018,0,0,'','','','/','/');
/*!40000 ALTER TABLE `cdl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colleg_corsi`
--

DROP TABLE IF EXISTS `colleg_corsi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `colleg_corsi` (
  `This_Corso` int(11) NOT NULL,
  `Other_Corso` int(11) NOT NULL,
  `Anno_This_Corso` int(11) NOT NULL,
  `Anno_Other_Corso` int(11) NOT NULL,
  `Tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`This_Corso`,`Other_Corso`,`Anno_This_Corso`,`Anno_Other_Corso`),
  KEY `This_Corso` (`This_Corso`,`Anno_This_Corso`),
  KEY `Other_Corso` (`Other_Corso`,`Anno_Other_Corso`),
  CONSTRAINT `colleg_corsi_ibfk_1` FOREIGN KEY (`This_Corso`, `Anno_This_Corso`) REFERENCES `corso` (`IDCorso`, `Anno`),
  CONSTRAINT `colleg_corsi_ibfk_2` FOREIGN KEY (`Other_Corso`, `Anno_Other_Corso`) REFERENCES `corso` (`IDCorso`, `Anno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colleg_corsi`
--

LOCK TABLES `colleg_corsi` WRITE;
/*!40000 ALTER TABLE `colleg_corsi` DISABLE KEYS */;
INSERT INTO `colleg_corsi` VALUES (1,4,2017,2017,'mutuato');
/*!40000 ALTER TABLE `colleg_corsi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `corsi_cdl`
--

DROP TABLE IF EXISTS `corsi_cdl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `corsi_cdl` (
  `Corso` int(11) NOT NULL,
  `AnnoCorso` int(11) NOT NULL,
  `CDL` int(11) NOT NULL,
  PRIMARY KEY (`Corso`,`AnnoCorso`,`CDL`),
  KEY `CDL` (`CDL`),
  CONSTRAINT `corsi_cdl_ibfk_1` FOREIGN KEY (`Corso`, `AnnoCorso`) REFERENCES `corso` (`IDCorso`, `Anno`),
  CONSTRAINT `corsi_cdl_ibfk_2` FOREIGN KEY (`CDL`) REFERENCES `cdl` (`IDCDL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corsi_cdl`
--

LOCK TABLES `corsi_cdl` WRITE;
/*!40000 ALTER TABLE `corsi_cdl` DISABLE KEYS */;
INSERT INTO `corsi_cdl` VALUES (1,2017,1),(1,2018,1),(2,2018,1),(4,2017,1),(3,2018,2);
/*!40000 ALTER TABLE `corsi_cdl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `corso`
--

DROP TABLE IF EXISTS `corso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `corso` (
  `IDCorso` int(11) NOT NULL,
  `Anno` int(11) NOT NULL,
  `Nome_it` varchar(50) DEFAULT NULL,
  `Nome_en` varchar(50) DEFAULT NULL,
  `SSD` varchar(10) DEFAULT NULL,
  `Lingua` varchar(20) DEFAULT NULL,
  `Semestre` int(11) DEFAULT NULL,
  `CFU` int(11) DEFAULT NULL,
  `Tipologia` char(1) DEFAULT NULL,
  PRIMARY KEY (`IDCorso`,`Anno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corso`
--

LOCK TABLES `corso` WRITE;
/*!40000 ALTER TABLE `corso` DISABLE KEYS */;
INSERT INTO `corso` VALUES (1,2017,'Reti neurali','Neural network','007','ita',1,12,'A'),(1,2018,'Reti neurali','Neural network','007','ita',1,12,'A'),(2,2018,'Laboratorio basi di dati','Lab Database','INF','Ita',1,12,'A'),(3,2018,'Teoria Dell\'Informazione','Information Theory','INF','Ita',1,6,'A'),(4,2017,'Algoritmi','Algoritmi','inf','ita',1,12,'A');
/*!40000 ALTER TABLE `corso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descrizione_en`
--

DROP TABLE IF EXISTS `descrizione_en`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `descrizione_en` (
  `Corso` int(11) NOT NULL,
  `AnnoCorso` int(11) NOT NULL,
  `Prerequisiti` text NOT NULL,
  `Obiettivi` text NOT NULL,
  `Mod_Esame` text NOT NULL,
  `Mod_Insegnamento` text NOT NULL,
  `Sillabo` text NOT NULL,
  `Note` text,
  PRIMARY KEY (`Corso`,`AnnoCorso`),
  CONSTRAINT `descrizione_en_ibfk_1` FOREIGN KEY (`Corso`, `AnnoCorso`) REFERENCES `corso` (`IDCorso`, `Anno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descrizione_en`
--

LOCK TABLES `descrizione_en` WRITE;
/*!40000 ALTER TABLE `descrizione_en` DISABLE KEYS */;
INSERT INTO `descrizione_en` VALUES (1,2017,'','','','','',''),(1,2018,'','','','','',''),(2,2018,'','','','','',''),(3,2018,'<p>Things</p>','<p>Things</p>','','','<p>Syllabus</p>','<p>Notes</p>'),(4,2017,'','','','','','');
/*!40000 ALTER TABLE `descrizione_en` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `descrizione_it`
--

DROP TABLE IF EXISTS `descrizione_it`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `descrizione_it` (
  `Corso` int(11) NOT NULL,
  `AnnoCorso` int(11) NOT NULL,
  `Prerequisiti` text NOT NULL,
  `Obiettivi` text NOT NULL,
  `Mod_Esame` text NOT NULL,
  `Mod_Insegnamento` text NOT NULL,
  `Sillabo` text NOT NULL,
  `Note` text,
  PRIMARY KEY (`Corso`,`AnnoCorso`),
  CONSTRAINT `descrizione_it_ibfk_1` FOREIGN KEY (`Corso`, `AnnoCorso`) REFERENCES `corso` (`IDCorso`, `Anno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `descrizione_it`
--

LOCK TABLES `descrizione_it` WRITE;
/*!40000 ALTER TABLE `descrizione_it` DISABLE KEYS */;
INSERT INTO `descrizione_it` VALUES (1,2017,'<p>aaaaaaaaa</p>','<p>aaaaaaaaaaaaaa</p>','<p>aaaaaaaaaaaaaaaa</p>','<p>aaaaaaaaaaaaa</p>','',''),(1,2018,'<p>aaaaaaaaa</p>','<p>aaaaaaaaaaaaaa</p>','<p>aaaaaaaaaaaaaaaa</p>','<p>aaaaaaaaaaaaa</p>','',''),(2,2018,'<p>Algebra</p>','<p>Imparare le basi di dati</p>','<p>scritto</p>','<p>lezioni frontali</p>','<ul>\r\n<li>1</li>\r\n<li>2</li>\r\n<li>3</li>\r\n</ul>','<p>nessuna</p>'),(3,2018,'<ul>\r\n<li>Prerequisiti vari</li>\r\n<li>Prerequisiti vari</li>\r\n</ul>','<ul>\r\n<li>Obiettivi vari</li>\r\n<li>Obiettivi vari</li>\r\n</ul>','<p>Modalit&agrave; d\'esame</p>','<p>Modalit&agrave; Insegnamento</p>','<ul>\r\n<li>Sillabo</li>\r\n<li>Sillabo</li>\r\n</ul>','<p>Note varie</p>'),(4,2017,'<p>sdgjfhdsdh&agrave;kjdfh&agrave;d</p>','','','','','');
/*!40000 ALTER TABLE `descrizione_it` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docente`
--

DROP TABLE IF EXISTS `docente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `docente` (
  `IDDocente` int(11) NOT NULL AUTO_INCREMENT,
  `Immagine` text,
  `Nome` varchar(20) NOT NULL,
  `Cognome` varchar(20) NOT NULL,
  `Email` varchar(50) DEFAULT NULL,
  `Ufficio` varchar(50) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Specializzazione` varchar(50) DEFAULT NULL,
  `Ricerche` text,
  `Pubblicazioni` text,
  `Curriculum` text,
  `Ricevimento` text,
  PRIMARY KEY (`IDDocente`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docente`
--

LOCK TABLES `docente` WRITE;
/*!40000 ALTER TABLE `docente` DISABLE KEYS */;
INSERT INTO `docente` VALUES (1,'imgDocenti\\GiuseppeDella Penna.jpg','Giuseppe','Della Penna','gdellapenna@univaq.it','12','3462738476','Web','','','curriculum\\GiuseppeDella Penna.pdf','Lun 8:00'),(2,'imgDocenti\\FilippoMignosi.jpg','Filippo','Mignosi','filippo@learn.it','Ufficio n.12','7743443233','Teoria Dell\'Informazione','<p>Ricerche varie</p>\r\n<p>Ricerche varie</p>','<p>Pubblicazioni varie</p>\r\n<p>Pubblicazioni varie</p>','curriculum\\FilippoMignosi.txt','Lun 8:00');
/*!40000 ALTER TABLE `docente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docenti_corso`
--

DROP TABLE IF EXISTS `docenti_corso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `docenti_corso` (
  `Corso` int(11) NOT NULL,
  `AnnoCorso` int(11) NOT NULL,
  `Docente` int(11) NOT NULL,
  PRIMARY KEY (`Corso`,`AnnoCorso`,`Docente`),
  KEY `Docente` (`Docente`),
  CONSTRAINT `docenti_corso_ibfk_1` FOREIGN KEY (`Corso`, `AnnoCorso`) REFERENCES `corso` (`IDCorso`, `Anno`),
  CONSTRAINT `docenti_corso_ibfk_2` FOREIGN KEY (`Docente`) REFERENCES `docente` (`IDDocente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docenti_corso`
--

LOCK TABLES `docenti_corso` WRITE;
/*!40000 ALTER TABLE `docenti_corso` DISABLE KEYS */;
INSERT INTO `docenti_corso` VALUES (1,2017,1),(1,2018,1),(2,2018,1),(3,2018,1),(3,2018,2),(4,2017,2);
/*!40000 ALTER TABLE `docenti_corso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dublino_en`
--

DROP TABLE IF EXISTS `dublino_en`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dublino_en` (
  `Corso` int(11) NOT NULL,
  `AnnoCorso` int(11) NOT NULL,
  `Knowledge` text,
  `Application` text,
  `Evaluation` text,
  `Communication` text,
  `Lifelong` text,
  PRIMARY KEY (`Corso`,`AnnoCorso`),
  CONSTRAINT `dublino_en_ibfk_1` FOREIGN KEY (`Corso`, `AnnoCorso`) REFERENCES `corso` (`IDCorso`, `Anno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dublino_en`
--

LOCK TABLES `dublino_en` WRITE;
/*!40000 ALTER TABLE `dublino_en` DISABLE KEYS */;
INSERT INTO `dublino_en` VALUES (1,2017,'','','','',''),(1,2018,'','','','',''),(2,2018,'','','','',''),(3,2018,'','','','',''),(4,2017,'','','','','');
/*!40000 ALTER TABLE `dublino_en` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dublino_it`
--

DROP TABLE IF EXISTS `dublino_it`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dublino_it` (
  `Corso` int(11) NOT NULL,
  `AnnoCorso` int(11) NOT NULL,
  `Knowledge` text,
  `Application` text,
  `Evaluation` text,
  `Communication` text,
  `Lifelong` text,
  PRIMARY KEY (`Corso`,`AnnoCorso`),
  CONSTRAINT `dublino_it_ibfk_1` FOREIGN KEY (`Corso`, `AnnoCorso`) REFERENCES `corso` (`IDCorso`, `Anno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dublino_it`
--

LOCK TABLES `dublino_it` WRITE;
/*!40000 ALTER TABLE `dublino_it` DISABLE KEYS */;
INSERT INTO `dublino_it` VALUES (1,2017,'','','','',''),(1,2018,'','','','',''),(2,2018,'<p>prova</p>','<p>prova</p>','<p>prova</p>','<p>prova</p>','<p>prova</p>'),(3,2018,'<p>Knowledge</p>','<p>Application</p>','<p>Evaluation</p>','<p>Communication</p>','<p>Lifelong</p>'),(4,2017,'','','','','');
/*!40000 ALTER TABLE `dublino_it` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_services`
--

DROP TABLE IF EXISTS `group_services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_services` (
  `Gruppo` int(11) NOT NULL,
  `Servizio` int(11) NOT NULL,
  PRIMARY KEY (`Gruppo`,`Servizio`),
  KEY `Servizio` (`Servizio`),
  CONSTRAINT `group_services_ibfk_1` FOREIGN KEY (`Gruppo`) REFERENCES `gruppo` (`IDGruppo`),
  CONSTRAINT `group_services_ibfk_2` FOREIGN KEY (`Servizio`) REFERENCES `servizio` (`IDServizio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_services`
--

LOCK TABLES `group_services` WRITE;
/*!40000 ALTER TABLE `group_services` DISABLE KEYS */;
INSERT INTO `group_services` VALUES (1,1),(2,2),(1,3),(1,4),(1,5),(1,6),(2,7),(1,8),(2,9),(1,10),(2,11),(1,12),(1,13),(2,14),(1,15),(2,16),(1,17),(1,18),(1,19),(1,20),(2,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(2,27),(1,28),(1,29);
/*!40000 ALTER TABLE `group_services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gruppo`
--

DROP TABLE IF EXISTS `gruppo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gruppo` (
  `IDGruppo` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`IDGruppo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gruppo`
--

LOCK TABLES `gruppo` WRITE;
/*!40000 ALTER TABLE `gruppo` DISABLE KEYS */;
INSERT INTO `gruppo` VALUES (1,'Admin'),(2,'Docenti');
/*!40000 ALTER TABLE `gruppo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libri_corso`
--

DROP TABLE IF EXISTS `libri_corso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libri_corso` (
  `Corso` int(11) NOT NULL,
  `AnnoCorso` int(11) NOT NULL,
  `Libro` int(11) NOT NULL,
  PRIMARY KEY (`Corso`,`AnnoCorso`,`Libro`),
  KEY `Libro` (`Libro`),
  CONSTRAINT `libri_corso_ibfk_1` FOREIGN KEY (`Corso`, `AnnoCorso`) REFERENCES `corso` (`IDCorso`, `Anno`),
  CONSTRAINT `libri_corso_ibfk_2` FOREIGN KEY (`Libro`) REFERENCES `libro` (`IDLibro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libri_corso`
--

LOCK TABLES `libri_corso` WRITE;
/*!40000 ALTER TABLE `libri_corso` DISABLE KEYS */;
/*!40000 ALTER TABLE `libri_corso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro`
--

DROP TABLE IF EXISTS `libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libro` (
  `IDLibro` int(11) NOT NULL AUTO_INCREMENT,
  `Autore` varchar(20) NOT NULL,
  `Titolo` varchar(50) NOT NULL,
  `Volume` int(11) DEFAULT NULL,
  `Anno` int(11) NOT NULL,
  `Editore` varchar(50) DEFAULT NULL,
  `Link` text,
  PRIMARY KEY (`IDLibro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libro`
--

LOCK TABLES `libro` WRITE;
/*!40000 ALTER TABLE `libro` DISABLE KEYS */;
/*!40000 ALTER TABLE `libro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `links`
--

DROP TABLE IF EXISTS `links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `links` (
  `Corso` int(11) NOT NULL,
  `AnnoCorso` int(11) NOT NULL,
  `Homepage` text,
  `Forum` text,
  `Elearning` text,
  `Risorse_ext` text,
  PRIMARY KEY (`Corso`,`AnnoCorso`),
  CONSTRAINT `links_ibfk_1` FOREIGN KEY (`Corso`, `AnnoCorso`) REFERENCES `corso` (`IDCorso`, `Anno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `links`
--

LOCK TABLES `links` WRITE;
/*!40000 ALTER TABLE `links` DISABLE KEYS */;
/*!40000 ALTER TABLE `links` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `materiale`
--

DROP TABLE IF EXISTS `materiale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `materiale` (
  `IDMateriale` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(20) NOT NULL,
  `Link` text NOT NULL,
  `Descrizione_it` varchar(50) DEFAULT NULL,
  `Descrizione_en` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IDMateriale`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materiale`
--

LOCK TABLES `materiale` WRITE;
/*!40000 ALTER TABLE `materiale` DISABLE KEYS */;
INSERT INTO `materiale` VALUES (1,'Base di dati','Materiale\\Base di dati.pdf','<p>Una base di dati</p>','<p>A Database</p>');
/*!40000 ALTER TABLE `materiale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materiale_corso`
--

DROP TABLE IF EXISTS `materiale_corso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `materiale_corso` (
  `Corso` int(11) NOT NULL,
  `AnnoCorso` int(11) NOT NULL,
  `Materiale` int(11) NOT NULL,
  PRIMARY KEY (`Corso`,`AnnoCorso`,`Materiale`),
  KEY `Materiale` (`Materiale`),
  CONSTRAINT `materiale_corso_ibfk_1` FOREIGN KEY (`Corso`, `AnnoCorso`) REFERENCES `corso` (`IDCorso`, `Anno`),
  CONSTRAINT `materiale_corso_ibfk_2` FOREIGN KEY (`Materiale`) REFERENCES `materiale` (`IDMateriale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materiale_corso`
--

LOCK TABLES `materiale_corso` WRITE;
/*!40000 ALTER TABLE `materiale_corso` DISABLE KEYS */;
INSERT INTO `materiale_corso` VALUES (2,2018,1);
/*!40000 ALTER TABLE `materiale_corso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servizio`
--

DROP TABLE IF EXISTS `servizio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servizio` (
  `IDServizio` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) NOT NULL,
  `Metodo` varchar(6) NOT NULL,
  PRIMARY KEY (`IDServizio`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servizio`
--

LOCK TABLES `servizio` WRITE;
/*!40000 ALTER TABLE `servizio` DISABLE KEYS */;
INSERT INTO `servizio` VALUES (1,'Backoffice',''),(2,'BackofficeD',''),(3,'Corsianno',''),(4,'CreateAdmin',''),(5,'CreateCDL',''),(6,'CreateCorso',''),(7,'CreateCorsoD',''),(8,'LibroNew',''),(9,'LibroNewD',''),(10,'LibroUp',''),(11,'LibroUpD',''),(12,'Log',''),(13,'MaterialeNew',''),(14,'MaterialeNewD',''),(15,'MaterialeUp',''),(16,'MaterialeUpD',''),(17,'ModificaCDL',''),(18,'ModificaCorso',''),(19,'ModificaDocente',''),(20,'Profile',''),(21,'ProfileD',''),(22,'RegisterDocente',''),(23,'users','GET'),(24,'users','POST'),(25,'users','PUT'),(26,'users','DELETE'),(27,'courses','PUT'),(28,'courses','POST'),(29,'courses','DELETE');
/*!40000 ALTER TABLE `servizio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessione`
--

DROP TABLE IF EXISTS `sessione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessione` (
  `Token` varchar(32) NOT NULL,
  `Data` datetime DEFAULT NULL,
  `Utente` int(11) DEFAULT NULL,
  PRIMARY KEY (`Token`),
  UNIQUE KEY `Utente_UNIQUE` (`Utente`),
  CONSTRAINT `sessione_ibfk_1` FOREIGN KEY (`Utente`) REFERENCES `utente` (`IDUtente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessione`
--

LOCK TABLES `sessione` WRITE;
/*!40000 ALTER TABLE `sessione` DISABLE KEYS */;
INSERT INTO `sessione` VALUES ('hS3BDrdkJ0DIlfKTQvZliFT17BIJzCkd','2018-11-01 19:15:00',1);
/*!40000 ALTER TABLE `sessione` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `utente` (
  `IDUtente` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Docente` int(11) DEFAULT NULL,
  `Gruppo` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDUtente`),
  UNIQUE KEY `Username_UNIQUE` (`Username`),
  UNIQUE KEY `Docente_UNIQUE` (`Docente`),
  KEY `utente_ibfk_2` (`Docente`),
  KEY `Gruppo` (`Gruppo`),
  CONSTRAINT `utente_ibfk_1` FOREIGN KEY (`Gruppo`) REFERENCES `gruppo` (`IDGruppo`),
  CONSTRAINT `utente_ibfk_2` FOREIGN KEY (`Docente`) REFERENCES `docente` (`IDDocente`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES (1,'admin','password',NULL,1),(2,'gide','gide',1,2),(3,'fimi','ewvf7Nf1jC',2,2),(4,'adminnazz','password',NULL,1);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `versioni`
--

DROP TABLE IF EXISTS `versioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `versioni` (
  `Tabella` varchar(20) NOT NULL,
  `Versione` int(11) NOT NULL,
  PRIMARY KEY (`Tabella`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `versioni`
--

LOCK TABLES `versioni` WRITE;
/*!40000 ALTER TABLE `versioni` DISABLE KEYS */;
INSERT INTO `versioni` VALUES ('cdl',0),('corso',1667708116),('docente',0),('libro',0),('materiale',0);
/*!40000 ALTER TABLE `versioni` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'newadvancedweb'
--

--
-- Dumping routines for database 'newadvancedweb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-15 11:15:46
