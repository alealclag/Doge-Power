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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actuator`
--

LOCK TABLES `actuator` WRITE;
/*!40000 ALTER TABLE `actuator` DISABLE KEYS */;
INSERT INTO `actuator` VALUES (13,'basic',17,'led'),(14,'basic',17,'vibration'),(15,'basic',18,'led'),(16,'basic',18,'vibration'),(17,'basic',19,'led'),(18,'basic',19,'vibration'),(19,'basic',20,'led'),(20,'basic',20,'vibration'),(21,'basic',21,'led'),(22,'basic',21,'vibration');
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actuator_value_basic`
--

LOCK TABLES `actuator_value_basic` WRITE;
/*!40000 ALTER TABLE `actuator_value_basic` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES (17,'Lala','alalla'),(18,'Lenny','alalla'),(19,'Tina','enrchagon'),(20,'Trufa','enrchagon'),(21,'Ipa','enrchagon');
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
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor`
--

LOCK TABLES `sensor` WRITE;
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
INSERT INTO `sensor` VALUES (48,'basic',17,'Sound'),(49,'basic',17,'Pressure'),(50,'location',17,'Location'),(51,'distance',17,'Distance'),(52,'basic',18,'Sound'),(53,'basic',18,'Pressure'),(54,'location',18,'Location'),(55,'distance',18,'Distance'),(56,'basic',19,'Sound'),(57,'basic',19,'Pressure'),(58,'location',19,'Location'),(59,'distance',19,'Distance'),(60,'basic',20,'Sound'),(61,'basic',20,'Pressure'),(62,'location',20,'Location'),(63,'distance',20,'Distance'),(64,'basic',21,'Sound'),(65,'basic',21,'Pressure'),(66,'location',21,'Location'),(67,'distance',21,'Distance');
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_value_basic`
--

LOCK TABLES `sensor_value_basic` WRITE;
/*!40000 ALTER TABLE `sensor_value_basic` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_value_distance`
--

LOCK TABLES `sensor_value_distance` WRITE;
/*!40000 ALTER TABLE `sensor_value_distance` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_value_location`
--

LOCK TABLES `sensor_value_location` WRITE;
/*!40000 ALTER TABLE `sensor_value_location` DISABLE KEYS */;
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
  `city` varchar(45) NOT NULL,
  PRIMARY KEY (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('alalla','Alejandro Alcántara','12345','1999-03-17','Écija'),('enrchagon','Enrique Chamber','54321','1998-03-14','Sevilla'),('hol','que tal','2345','1997-05-05','no'),('hola','Alejandro Alcántaraaaaa','54321','1703-10-10','ncaca'),('lamama','Isabel González de Quevedo','pipito','1962-08-05','Sevilla');
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

-- Dump completed on 2020-04-24 18:21:30
