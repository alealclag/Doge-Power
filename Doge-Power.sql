-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: doge power
-- ------------------------------------------------------
-- Server version	8.0.19

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

--
-- Table structure for table `actuator`
--

DROP TABLE IF EXISTS `actuator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actuator` (
  `idactuator` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `iddevice` int NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idactuator`),
  KEY `device_actuator_device_idx` (`iddevice`),
  CONSTRAINT `device_actuator_device` FOREIGN KEY (`iddevice`) REFERENCES `device` (`iddevice`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actuator`
--

LOCK TABLES `actuator` WRITE;
/*!40000 ALTER TABLE `actuator` DISABLE KEYS */;
INSERT INTO `actuator` VALUES (13,'basic',17,'led'),(14,'basic',17,'vibration'),(15,'basic',18,'led'),(16,'basic',18,'vibration'),(23,'basic',27,'led'),(24,'basic',27,'vibration'),(25,'basic',28,'led'),(26,'basic',28,'vibration'),(27,'basic',29,'led'),(28,'basic',29,'vibration'),(31,'basic',31,'led'),(32,'basic',31,'vibration'),(45,'basic',38,'led'),(46,'basic',38,'vibration'),(49,'basic',40,'led'),(50,'basic',40,'vibration');
/*!40000 ALTER TABLE `actuator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actuator_value_basic`
--

DROP TABLE IF EXISTS `actuator_value_basic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actuator_value_basic` (
  `idactuator_value_basic` int NOT NULL AUTO_INCREMENT,
  `value` float NOT NULL,
  `timestamp` bigint NOT NULL,
  `idactuator` int NOT NULL,
  `length` float NOT NULL,
  `mode` int NOT NULL,
  PRIMARY KEY (`idactuator_value_basic`),
  KEY `actuator_actuator_value_basic_actuator_idx` (`idactuator`),
  CONSTRAINT `actuator_actuator_value_basic_actuator` FOREIGN KEY (`idactuator`) REFERENCES `actuator` (`idactuator`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actuator_value_basic`
--

LOCK TABLES `actuator_value_basic` WRITE;
/*!40000 ALTER TABLE `actuator_value_basic` DISABLE KEYS */;
INSERT INTO `actuator_value_basic` VALUES (14,89.43,1,25,34.5,12),(15,453.45,2,25,62.3,34),(16,45.38,3,25,63.4,345),(17,67.35,4,25,75.4,83),(18,97.24,5,25,84.5,87),(19,37.65,1,26,73.4,78),(20,67.34,2,26,53.2,94),(21,34.35,3,26,26.4,31),(22,94.38,4,26,63.4,12),(23,98.34,5,26,67.8,32),(35,89.43,1589245734711,26,34.5,12),(36,22,1589245754473,49,22,2),(37,17,1589245770446,50,11,11),(38,1,1589245892014,49,0,1),(39,10,1589245898776,49,10,10),(40,11,1589245905025,49,11,11),(41,12,1589245910295,49,12,12),(42,13,1589245916717,49,13,13),(43,14,1589245923457,49,14,14),(44,15,1589245941650,49,15,15),(45,16,1589245946895,49,16,16),(46,0,1589245947892,49,0,0),(47,0,1589245948911,49,0,0),(48,18,1589245975321,49,0,18),(49,0,1589245977074,49,0,0),(50,0,1589245979661,49,0,0),(51,18,1589246212425,49,18,18),(52,17,1589246256354,26,19,18);
/*!40000 ALTER TABLE `actuator_value_basic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device` (
  `iddevice` int NOT NULL AUTO_INCREMENT,
  `dog` varchar(45) NOT NULL,
  `iduser` varchar(20) NOT NULL,
  PRIMARY KEY (`iddevice`),
  KEY `user_device_user_idx` (`iduser`),
  CONSTRAINT `user_device_user` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES (17,'Lala','alalla'),(18,'Lenny','alalla'),(27,'Kike perro','alalla'),(28,'perra','enrchagon'),(29,'perrittto','enrchagon'),(31,'Kike perro','alalla'),(38,'sandlcan','perico'),(40,'kk','kike');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor`
--

DROP TABLE IF EXISTS `sensor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensor` (
  `idsensor` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `iddevice` int NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idsensor`),
  KEY `device_sensor_device_idx` (`iddevice`),
  CONSTRAINT `device_sensor_device` FOREIGN KEY (`iddevice`) REFERENCES `device` (`iddevice`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor`
--

LOCK TABLES `sensor` WRITE;
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
INSERT INTO `sensor` VALUES (48,'basic',17,'Sound'),(49,'basic',17,'Pressure'),(50,'location',17,'Location'),(51,'distance',17,'Distance'),(52,'basic',18,'Sound'),(53,'basic',18,'Pressure'),(54,'location',18,'Location'),(55,'distance',18,'Distance'),(68,'Location',27,'Location'),(69,'Basic',27,'Pressure'),(70,'Basic',27,'Sound'),(71,'Distance',27,'Distance'),(72,'Location',28,'Location'),(73,'Basic',28,'Pressure'),(74,'Basic',28,'Sound'),(75,'Distance',28,'Distance'),(76,'Location',29,'Location'),(77,'Basic',29,'Pressure'),(78,'Basic',29,'Sound'),(79,'Distance',29,'Distance'),(84,'Location',31,'Location'),(85,'Basic',31,'Pressure'),(86,'Basic',31,'Sound'),(87,'Distance',31,'Distance'),(112,'Location',38,'Location'),(113,'Basic',38,'Pressure'),(114,'Basic',38,'Sound'),(115,'Distance',38,'Distance'),(120,'Location',40,'Location'),(121,'Basic',40,'Pressure'),(122,'Basic',40,'Sound'),(123,'Distance',40,'Distance');
/*!40000 ALTER TABLE `sensor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor_value_basic`
--

DROP TABLE IF EXISTS `sensor_value_basic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensor_value_basic` (
  `idsensor_value_basic` int NOT NULL AUTO_INCREMENT,
  `value` float NOT NULL,
  `timestamp` bigint NOT NULL,
  `idsensor` int NOT NULL,
  PRIMARY KEY (`idsensor_value_basic`),
  KEY `sensor_value_basic_sensor_idx` (`idsensor`),
  CONSTRAINT `sensor_value_basic_sensor` FOREIGN KEY (`idsensor`) REFERENCES `sensor` (`idsensor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_value_basic`
--

LOCK TABLES `sensor_value_basic` WRITE;
/*!40000 ALTER TABLE `sensor_value_basic` DISABLE KEYS */;
INSERT INTO `sensor_value_basic` VALUES (14,15,1,74),(16,17,1234,114),(17,32,2,74),(18,65,3,74),(19,75,4,74),(20,34,5,74),(21,345,1,73),(22,654,2,73),(23,765,3,73),(24,34,4,73),(25,65,5,73);
/*!40000 ALTER TABLE `sensor_value_basic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor_value_distance`
--

DROP TABLE IF EXISTS `sensor_value_distance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensor_value_distance` (
  `idsensor_value_distance` int NOT NULL AUTO_INCREMENT,
  `distance_to_door` float NOT NULL,
  `is_inside` tinyint NOT NULL,
  `idsensor` int NOT NULL,
  `timestamp` bigint NOT NULL,
  PRIMARY KEY (`idsensor_value_distance`),
  KEY `sensor_distance_sensor_idx` (`idsensor`),
  CONSTRAINT `sensor_distance_sensor` FOREIGN KEY (`idsensor`) REFERENCES `sensor` (`idsensor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_value_distance`
--

LOCK TABLES `sensor_value_distance` WRITE;
/*!40000 ALTER TABLE `sensor_value_distance` DISABLE KEYS */;
INSERT INTO `sensor_value_distance` VALUES (13,523,1,75,1),(14,26,0,75,2),(15,74,1,75,3),(16,84,0,75,4),(17,25,0,75,5);
/*!40000 ALTER TABLE `sensor_value_distance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sensor_value_location`
--

DROP TABLE IF EXISTS `sensor_value_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sensor_value_location` (
  `idsensor_value_location` int NOT NULL AUTO_INCREMENT,
  `value_x` float NOT NULL,
  `value_y` float NOT NULL,
  `timestamp` bigint NOT NULL,
  `idsensor` int NOT NULL,
  PRIMARY KEY (`idsensor_value_location`),
  KEY `sensor_sensor_value_location_sensor_idx` (`idsensor`),
  CONSTRAINT `sensor_sensor_value_location_sensor` FOREIGN KEY (`idsensor`) REFERENCES `sensor` (`idsensor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_value_location`
--

LOCK TABLES `sensor_value_location` WRITE;
/*!40000 ALTER TABLE `sensor_value_location` DISABLE KEYS */;
INSERT INTO `sensor_value_location` VALUES (17,657,456,1,72),(18,876,5678,2,72),(19,987,678,3,72),(20,897,345,4,72),(21,908,8453,5,72);
/*!40000 ALTER TABLE `sensor_value_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `iduser` varchar(20) NOT NULL,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `birthdate` date NOT NULL,
  `City` varchar(45) NOT NULL,
  PRIMARY KEY (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('alalla','Alejandro Alcántara','12345','1999-03-17','Écija'),('enrcha','Kike Wapo','54321','1997-03-17','Álava'),('enrchagon','Enrique Chamber','54321','1998-03-14','Sevilla'),('hol','que tal','2345','1997-05-05','no'),('hola','Alejandro Alcántaraaaaa','54321','1703-10-10','ncaca'),('k karaho?','no se k pasa','kakita','1977-05-09','Cádiz'),('kike','hola','hola','2020-05-31','Álava'),('lamama','Isabel González de Quevedo','pipito','1962-08-05','Sevilla'),('perico','perico de los palotes','pepe','1905-09-14','Palencia');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'doge power'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-12 14:02:16
