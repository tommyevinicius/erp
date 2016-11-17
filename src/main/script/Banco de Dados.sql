-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: erp
-- ------------------------------------------------------
-- Server version	5.7.14-log

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,NULL,'69410291100','Tommye Vinicius','R Julio Frederico Muller Qd 19 Bl 01',NULL,'ATIVO','65999545067','FISICA'),(2,'542141231254',NULL,'Subway','Univag Bloco D','Lanches Naturais','ATIVO','544213512','JURIDICA'),(3,NULL,'05794620013','Joicyellen Pereira','R Escondido na Folha',NULL,'ATIVO','456489745','FISICA'),(4,NULL,'5212412412','Aislan Honorato','R Unidos da Tijuca',NULL,'ATIVO','54231241234','FISICA'),(5,NULL,'62342134423','Alessandra Paz','R Coordena tudo',NULL,'ATIVO','412353532','FISICA'),(6,NULL,'5324124124','Giwberto Gill Pereira','R Dom Pedro',NULL,'ATIVO','412341341','FISICA'),(7,NULL,'5233123412','Projeto Integrador','R Sala 1408',NULL,'ATIVO','2423415415','FISICA'),(8,NULL,'5899381293','Janilson Cruz','Bloco C',NULL,'ATIVO','89782374387','FISICA'),(9,NULL,'4312312514','Alisson Silva','R Me perdi',NULL,'ATIVO','87381273823','FISICA'),(10,'43718237481974',NULL,'Carlos Emilio','R to fazendo o que aqui','Carlinhos de Jesus','INATIVO','53289274239','JURIDICA'),(11,'578293447283',NULL,'Ivete Sangalo','R To no bloco','Iveteiros LTDA','INATIVO','782738297','JURIDICA');
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
INSERT INTO `fornecedor` VALUES (1,'123456789321','TOTEM','R Sebastiana','CONSULTORIA TOTEM TI','ATIVO','3027-1353'),(2,'123456789789','Univag','Não faço a minima ideia','Centro Universitário de Várzea Grande','ATIVO','321654987'),(3,'562143123121','Modelo','R. Alem Aquino','Supermercador Modelo','INATIVO','123541234');
/*!40000 ALTER TABLE `fornecedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lancamento`
--

DROP TABLE IF EXISTS `lancamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lancamento` (
  `IDLANCAMENTO` bigint(20) NOT NULL AUTO_INCREMENT,
  `TIPOLANCAMENTO` varchar(255) DEFAULT NULL,
  `CLIENTE_ID` bigint(20) DEFAULT NULL,
  `FORNECEDOR_ID` bigint(20) DEFAULT NULL,
  `COMENTARIO` varchar(255) DEFAULT NULL,
  `USUARIO_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`IDLANCAMENTO`),
  KEY `FK1D53917AFE2E61A6` (`CLIENTE_ID`),
  KEY `FK1D53917A59E11EE` (`FORNECEDOR_ID`),
  KEY `FK_LANCAMENTO_USUARIO_ID` (`USUARIO_ID`),
  CONSTRAINT `FK1D53917A59E11EE` FOREIGN KEY (`FORNECEDOR_ID`) REFERENCES `fornecedor` (`IDFORNECEDOR`),
  CONSTRAINT `FK1D53917AFE2E61A6` FOREIGN KEY (`CLIENTE_ID`) REFERENCES `cliente` (`IDCLIENTE`),
  CONSTRAINT `FK_LANCAMENTO_USUARIO_ID` FOREIGN KEY (`USUARIO_ID`) REFERENCES `usuario` (`IDUSUARIO`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lancamento`
--

LOCK TABLES `lancamento` WRITE;
/*!40000 ALTER TABLE `lancamento` DISABLE KEYS */;
INSERT INTO `lancamento` VALUES (1,'ENTRADA',1,1,'TESTE',1),(5,'ENTRADA',NULL,3,'teste',1),(6,'SAIDA',4,NULL,'saida',1),(7,'SAIDA',5,NULL,'teste',2);
/*!40000 ALTER TABLE `lancamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lancamentoproduto`
--

DROP TABLE IF EXISTS `lancamentoproduto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lancamentoproduto` (
  `IDLANCAMENTOPRODUTO` bigint(20) NOT NULL AUTO_INCREMENT,
  `QUANTIDADE` bigint(20) DEFAULT NULL,
  `VALOR` double DEFAULT NULL,
  `LANCAMENTO_ID` bigint(20) NOT NULL,
  `PRODUTO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`IDLANCAMENTOPRODUTO`),
  KEY `FKF3600C1FDF991DCE` (`LANCAMENTO_ID`),
  KEY `FKF3600C1F6441081E` (`PRODUTO`),
  CONSTRAINT `FKF3600C1F6441081E` FOREIGN KEY (`PRODUTO`) REFERENCES `produto` (`IDPRODUTO`),
  CONSTRAINT `FKF3600C1FDF991DCE` FOREIGN KEY (`LANCAMENTO_ID`) REFERENCES `lancamento` (`IDLANCAMENTO`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lancamentoproduto`
--

LOCK TABLES `lancamentoproduto` WRITE;
/*!40000 ALTER TABLE `lancamentoproduto` DISABLE KEYS */;
INSERT INTO `lancamentoproduto` VALUES (1,3,3.99,1,2),(2,2,4.99,1,1),(3,51,2.99,5,2),(4,3,1.99,5,1),(5,10,2.99,6,2),(6,3,2.99,7,2);
/*!40000 ALTER TABLE `lancamentoproduto` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (1,'Caderno 100 Folhas',10,'ATIVO',21),(2,'Borracha',43,'ATIVO',3.99),(3,'Erva Tereré',0,'ATIVO',3.99);
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
INSERT INTO `usuario` VALUES (1,'69410291100','tommy_vinicius@hotmail.com','root','Tommye Vinícius','b4b8daf4b8ea9d39568719e1e320076f','ATIVO',1),(2,'14612314497','samuel@totemti.com.br','samu','Samuel Figueiredo','a7023330e5297446be99a86787434e4f','ATIVO',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'erp'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-17 16:46:16
