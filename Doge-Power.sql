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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actuator`
--

LOCK TABLES `actuator` WRITE;
/*!40000 ALTER TABLE `actuator` DISABLE KEYS */;
INSERT INTO `actuator` VALUES (1,'basic',1,'led'),(2,'basic',1,'vibration'),(3,'basic',2,'led'),(4,'basic',2,'vibration'),(5,'basic',3,'led'),(6,'basic',3,'vibration'),(7,'basic',4,'led'),(8,'basic',4,'vibration'),(9,'basic',5,'led'),(10,'basic',5,'vibration');
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actuator_value_basic`
--

LOCK TABLES `actuator_value_basic` WRITE;
/*!40000 ALTER TABLE `actuator_value_basic` DISABLE KEYS */;
INSERT INTO `actuator_value_basic` VALUES (1,0,123456789012,1,1,0),(2,50,123456789012,2,1,1),(3,100,123456789012,3,2,1),(4,75,123456789012,4,5,1),(5,25,123456789012,5,10,2),(6,100,123456789012,6,0.5,2),(7,0,123456789012,7,0.5,0),(8,100,123456789012,8,1,0),(9,50,123456789012,9,0.75,1),(10,50,123456789012,10,0.25,2);
/*!40000 ALTER TABLE `actuator_value_basic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device` (
  `iddevice` int NOT NULL,
  `dog` varchar(45) NOT NULL,
  `iduser` int NOT NULL,
  PRIMARY KEY (`iddevice`),
  KEY `user_device_user_idx` (`iduser`),
  CONSTRAINT `user_device_user` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES (1,'Tina',2),(2,'Trufa',2),(3,'Ipa',4),(4,'Lenny',3),(5,'Lala',3);
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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor`
--

LOCK TABLES `sensor` WRITE;
/*!40000 ALTER TABLE `sensor` DISABLE KEYS */;
INSERT INTO `sensor` VALUES (1,'Location',1,'Location'),(2,'Basic',1,'Pressure'),(3,'Basic',1,'Sound'),(4,'Location',2,'Location'),(5,'Basic',2,'Pressure'),(6,'Basic',2,'Sound'),(7,'Location',3,'Location'),(8,'Basic',3,'Pressure'),(9,'Basic',3,'Sound'),(10,'Location',4,'Location'),(11,'Basic',4,'Pressure'),(12,'Basic',4,'Sound'),(13,'Location',5,'Location'),(14,'Basic',5,'Pressure'),(15,'Basic',5,'Sound'),(39,'Distance',1,'Distance'),(40,'Distance',2,'Distance'),(41,'Distance',3,'Distance'),(42,'Distance',4,'Distance'),(43,'Distance',5,'Distance');
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_value_basic`
--

LOCK TABLES `sensor_value_basic` WRITE;
/*!40000 ALTER TABLE `sensor_value_basic` DISABLE KEYS */;
INSERT INTO `sensor_value_basic` VALUES (1,50,123456789012,2),(2,40,123456789012,3),(3,60,123456789012,5),(4,30,123456789012,6),(5,70,123456789012,8),(6,35,123456789012,9),(7,60,123456789012,11),(8,50,123456789012,12),(9,30,123456789012,14),(10,45,123456789012,15),(11,3245,654534654361,2),(12,3245,654534654361,3);
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_value_distance`
--

LOCK TABLES `sensor_value_distance` WRITE;
/*!40000 ALTER TABLE `sensor_value_distance` DISABLE KEYS */;
INSERT INTO `sensor_value_distance` VALUES (1,1,1,1,123456789012),(2,0.5,1,2,123456789012),(3,0.2,0,3,123456789012),(4,3,1,4,123456789012),(5,3.5,1,5,123456789012),(6,32.45,1,39,654534654361);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sensor_value_location`
--

LOCK TABLES `sensor_value_location` WRITE;
/*!40000 ALTER TABLE `sensor_value_location` DISABLE KEYS */;
INSERT INTO `sensor_value_location` VALUES (1,38,-5,123456789012,1),(2,50,-10,123456789012,4),(3,60,30,123456789012,7),(4,0,0,123456789012,10),(5,25,25,123456789012,13),(6,6,6,4365346,4),(14,32,62,17,1),(15,3245,6254,654534654361,1);
/*!40000 ALTER TABLE `sensor_value_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `iduser` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `birthdate` bigint NOT NULL,
  `City` varchar(45) NOT NULL,
  PRIMARY KEY (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'Enrique Chamber','12345',14031998,'Sevilla'),(3,'Alejandro Alcántara','54321',17031998,'Écija'),(4,'Jorge Luis','12345',130071999,'Huelva');
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

-- Dump completed on 2020-04-12 20:18:26
