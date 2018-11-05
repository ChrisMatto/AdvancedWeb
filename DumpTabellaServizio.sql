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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servizio`
--

LOCK TABLES `servizio` WRITE;
/*!40000 ALTER TABLE `servizio` DISABLE KEYS */;
INSERT INTO `servizio` VALUES (1,'Backoffice',''),(2,'BackofficeD',''),(3,'Corsianno',''),(4,'CreateAdmin',''),(5,'CreateCDL',''),(6,'CreateCorso',''),(7,'CreateCorsoD',''),(8,'LibroNew',''),(9,'LibroNewD',''),(10,'LibroUp',''),(11,'LibroUpD',''),(12,'Log',''),(13,'MaterialeNew',''),(14,'MaterialeNewD',''),(15,'MaterialeUp',''),(16,'MaterialeUpD',''),(17,'ModificaCDL',''),(18,'ModificaCorso',''),(19,'ModificaDocente',''),(20,'Profile',''),(21,'ProfileD',''),(22,'RegisterDocente',''),(23,'users','GET'),(24,'users','POST');
/*!40000 ALTER TABLE `servizio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'advancedweb'
--
/*!50106 SET @save_time_zone= @@TIME_ZONE */ ;
/*!50106 DROP EVENT IF EXISTS `new_corsi_year` */;
DELIMITER ;;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;;
/*!50003 SET character_set_client  = utf8 */ ;;
/*!50003 SET character_set_results = utf8 */ ;;
/*!50003 SET collation_connection  = utf8_general_ci */ ;;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;;
/*!50003 SET @saved_time_zone      = @@time_zone */ ;;
/*!50003 SET time_zone             = 'SYSTEM' */ ;;
/*!50106 CREATE*/ /*!50117 DEFINER=`root`@`localhost`*/ /*!50106 EVENT `new_corsi_year` ON SCHEDULE EVERY 1 YEAR STARTS '2018-08-01 03:00:00' ON COMPLETION PRESERVE ENABLE DO BEGIN
			CREATE TEMPORARY TABLE tmpCorso
				SELECT * FROM Corso WHERE Anno=YEAR(NOW())-1;
				UPDATE tmpCorso SET OldID=IDCorso;
				ALTER TABLE tmpCorso DROP IDCorso; # Drop autoincrement field
				UPDATE tmpCorso SET Anno=YEAR(NOW()) WHERE Anno=YEAR(NOW())-1;
				INSERT INTO Corso SELECT 0,tmpCorso.* FROM tmpCorso;
				DROP TEMPORARY TABLE tmpCorso;

			CREATE TEMPORARY TABLE tmpCorsi_CDL
				SELECT corsi_cdl.Corso,CDL FROM Corsi_CDL,Corso WHERE Corso.IDCorso=corsi_cdl.Corso AND Anno=YEAR(NOW())-1;
				UPDATE tmpCorsi_CDL SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=tmpCorsi_CDL.Corso);
				INSERT INTO Corsi_CDL SELECT tmpCorsi_CDL.* FROM tmpCorsi_CDL;
				DROP TEMPORARY TABLE tmpCorsi_CDL;
				
			CREATE TEMPORARY TABLE tmpColleg_Corsi
				SELECT colleg_corsi.* FROM Corso,Colleg_Corsi WHERE IDCorso=This_Corso AND Anno=YEAR(NOW())-1;
				UPDATE tmpColleg_Corsi SET This_Corso=(SELECT IDCorso FROM Corso WHERE OldID=This_Corso), Other_Corso=(SELECT IDCorso FROM Corso WHERE OldID=Other_Corso);
				INSERT INTO Colleg_Corsi SELECT tmpColleg_Corsi.* FROM tmpColleg_Corsi;
				DROP TEMPORARY TABLE tmpColleg_Corsi;
				
			CREATE TEMPORARY TABLE tmpDocenti_Corso
				SELECT Corso,Docente FROM Docenti_Corso,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpDocenti_Corso SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Docenti_Corso SELECT tmpDocenti_Corso.* FROM tmpDocenti_Corso;
				DROP TEMPORARY TABLE tmpDocenti_Corso;
				
			CREATE TEMPORARY TABLE tmpDescrizione_it
				SELECT Corso,Prerequisiti,Obiettivi,Mod_Esame,Mod_Insegnamento,Sillabo,Note,Homepage,Forum,Risorse_ext FROM Descrizione_it,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpDescrizione_it SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Descrizione_it SELECT tmpDescrizione_it.* FROM tmpDescrizione_it;
				DROP TEMPORARY TABLE tmpDescrizione_it;
				
			CREATE TEMPORARY TABLE tmpDescrizione_en
				SELECT Corso,Prerequisiti,Obiettivi,Mod_Esame,Mod_Insegnamento,Sillabo,Note,Homepage,Forum,Risorse_ext FROM Descrizione_en,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpDescrizione_en SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Descrizione_en SELECT tmpDescrizione_en.* FROM tmpDescrizione_en;
				DROP TEMPORARY TABLE tmpDescrizione_en;
				
			CREATE TEMPORARY TABLE tmpDublino_it
				SELECT Corso,Knowledge,Application,Evaluation,Communication,Lifelong FROM Dublino_it,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpDublino_it SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Dublino_it SELECT tmpDublino_it.* FROM tmpDublino_it;
				DROP TEMPORARY TABLE tmpDublino_it;
				
			CREATE TEMPORARY TABLE tmpDublino_en
				SELECT Corso,Knowledge,Application,Evaluation,Communication,Lifelong FROM Dublino_en,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpDublino_en SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Dublino_en SELECT tmpDublino_en.* FROM tmpDublino_en;
				DROP TEMPORARY TABLE tmpDublino_en;
				
			CREATE TEMPORARY TABLE tmpMateriale
				SELECT IDMateriale,Corso,Nome,Link,Descrizione_it,Descrizione_en FROM Materiale,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpMateriale SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				ALTER TABLE tmpMateriale DROP IDMateriale;
				INSERT INTO Materiale SELECT 0,tmpMateriale.* FROM tmpMateriale;
				DROP TEMPORARY TABLE tmpMateriale;
				
			CREATE TEMPORARY TABLE tmpLibri_Corso
				SELECT Corso,Libro FROM Libri_Corso,Corso WHERE Corso=IDCorso AND Anno=YEAR(NOW())-1;
				UPDATE tmpLibri_Corso SET Corso=(SELECT IDCorso FROM Corso WHERE OldID=Corso);
				INSERT INTO Libri_Corso SELECT tmpLibri_Corso.* FROM tmpLibri_Corso;
				DROP TEMPORARY TABLE tmpLibri_Corso;
		END */ ;;
/*!50003 SET time_zone             = @saved_time_zone */ ;;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;;
/*!50003 SET character_set_client  = @saved_cs_client */ ;;
/*!50003 SET character_set_results = @saved_cs_results */ ;;
/*!50003 SET collation_connection  = @saved_col_connection */ ;;
DELIMITER ;
/*!50106 SET TIME_ZONE= @save_time_zone */ ;

--
-- Dumping routines for database 'advancedweb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-27 18:53:57
