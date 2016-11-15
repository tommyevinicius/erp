CREATE DATABASE  IF NOT EXISTS `erp` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `erp`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: erp
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `IDCLIENTE` bigint(20) NOT NULL AUTO_INCREMENT,
  `CNPJ` varchar(20) DEFAULT NULL,
  `CPF` varchar(20) DEFAULT NULL,
  `NOME` varchar(100) DEFAULT NULL,
  `ENDERECO` varchar(255) DEFAULT NULL,
  `RAZAOSOCIAL` varchar(100) DEFAULT NULL,
  `SITUACAO` varchar(255) NOT NULL,
  `TELEFONE` varchar(20) DEFAULT NULL,
  `TTPOPESSOA` varchar(255) NOT NULL,
  PRIMARY KEY (`IDCLIENTE`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,NULL,'69410291100','Tommye Vinicius','R Julio Frederico Muller Qd 19 Bl 01',NULL,'ATIVO','65999545067','FISICA'),(2,'542141231254',NULL,'Subway','Univag Bloco D','Lanches Naturais','ATIVO','544213512','JURIDICA');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornecedor`
--

DROP TABLE IF EXISTS `fornecedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fornecedor` (
  `IDFORNECEDOR` bigint(20) NOT NULL AUTO_INCREMENT,
  `CNPJ` varchar(20) DEFAULT NULL,
  `DESCRICAO` varchar(100) DEFAULT NULL,
  `ENDERECO` varchar(255) DEFAULT NULL,
  `RAZAOSOCIAL` varchar(100) DEFAULT NULL,
  `SITUACAO` varchar(255) NOT NULL,
  `TELEFONE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`IDFORNECEDOR`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornecedor`
--

LOCK TABLES `fornecedor` WRITE;
/*!40000 ALTER TABLE `fornecedor` DISABLE KEYS */;
INSERT INTO `fornecedor` VALUES (1,'123456789321','TOTEM','R Sebastiana','CONSULTORIA TOTEM TI','ATIVO','3027-1353'),(2,'123456789789','Univag','Não faço a minima ideia','Centro Universitário de Várzea Grande','ATIVO','321654987'),(3,'562143123121','Modelo','R. Alem Aquino','Supermercador Modelo','ATIVO','123541234');
/*!40000 ALTER TABLE `fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfil` (
  `IDPERFIL` bigint(20) NOT NULL AUTO_INCREMENT,
  `SITUACAO` varchar(255) DEFAULT NULL,
  `DESCRICAO` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`IDPERFIL`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES (1,'ATIVO','Administrador'),(2,'ATIVO','Tester'),(3,'INATIVO','Beta');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produto`
--

DROP TABLE IF EXISTS `produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produto` (
  `IDPRODUTO` bigint(20) NOT NULL AUTO_INCREMENT,
  `DESCRICAO` varchar(100) DEFAULT NULL,
  `QUANTIDADE` bigint(20) DEFAULT NULL,
  `SITUACAO` varchar(255) NOT NULL,
  `VALOR` double DEFAULT NULL,
  PRIMARY KEY (`IDPRODUTO`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (1,'Caderno 100 Folhas',10,'ATIVO',21),(2,'Borracha',5,'ATIVO',3.99);
/*!40000 ALTER TABLE `produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `IDUSUARIO` bigint(20) NOT NULL AUTO_INCREMENT,
  `CPF` varchar(15) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `LOGIN` varchar(20) DEFAULT NULL,
  `NOME` varchar(80) DEFAULT NULL,
  `SENHA` varchar(50) DEFAULT NULL,
  `SITUACAO` varchar(255) NOT NULL,
  `PERFIL_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`IDUSUARIO`),
  UNIQUE KEY `LOGIN` (`LOGIN`),
  KEY `FK22E07F0EEDE82E8E` (`PERFIL_ID`),
  CONSTRAINT `FK22E07F0EEDE82E8E` FOREIGN KEY (`PERFIL_ID`) REFERENCES `perfil` (`IDPERFIL`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'69410291100','tommy_vinicius@hotmail.com','root','Tommye Vinícius','b4b8daf4b8ea9d39568719e1e320076f','ATIVO',1),(2,'14612314497','samuel@totemti.com.br','samu','Samuel Figueiredo','b868cdfc8beae943c2386331dc56bd6b','ATIVO',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-15 15:07:20
