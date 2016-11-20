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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,NULL,'611.278.645-13','Tommye Vinicius','R Julio Muller',NULL,'ATIVO','6599-1234','FISICA'),(2,'59.408.938/0001-98',NULL,'Subway','Univag Bloco D','Lanches Naturais','ATIVO','5442-3512','JURIDICA'),(3,NULL,'057.946.201-37','Joicyellen Pereira','R Escondido na Folha',NULL,'ATIVO','4564-9745','FISICA'),(4,NULL,'224.695.719-28','Aislan Honorato','R Unidos da Tijuca',NULL,'ATIVO','5423-1241','FISICA'),(5,NULL,'437.456.506-03','Alessandra Paz','R Coordena tudo',NULL,'ATIVO','4123-5353','FISICA'),(6,NULL,'232.234.145-25','Giwberto Gill Pereira','R Dom Pedro',NULL,'ATIVO','4123-1341','FISICA'),(7,NULL,'858.714.561-46','Projeto Integrador','R Sala 1408',NULL,'ATIVO','2423-5415','FISICA'),(8,NULL,'858.714.561-46','Janilson Cruz','Bloco C',NULL,'ATIVO','8978-4387','FISICA'),(9,NULL,'557.760.742-66','Alisson Silva','R Me perdi',NULL,'ATIVO','8738-1273','FISICA'),(10,'12.888.492/0001-82',NULL,'Carlos Emilio','R...','Carlinhos','INATIVO','5328-9274','JURIDICA'),(11,'73.116.053/0001-46',NULL,'Ivete Sangalo','R To no bloco','Iveteiros LTDA','INATIVO','7827-8297','JURIDICA'),(12,'43.987.579/0001-30',NULL,'João Benedito','R. zero','Super Jazz','ATIVO','7543-4235','JURIDICA');
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
INSERT INTO `fornecedor` VALUES (1,'43.504.652/0001-76','Totem','R Sebastiana','Consultoria Totem TI','ATIVO','3027-1353'),(2,'87.581.221/0001-89','Univag','R cedros','Centro Universitário de Várzea Grande','ATIVO','3216-4987'),(3,'92.405.484/0001-77','Modelo','R. Alem Aquino','Supermercador Modelo','INATIVO','1235-1234');
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
  `DATA` datetime NOT NULL,
  PRIMARY KEY (`IDLANCAMENTO`),
  KEY `FK1D53917AFE2E61A6` (`CLIENTE_ID`),
  KEY `FK1D53917A59E11EE` (`FORNECEDOR_ID`),
  KEY `FK_LANCAMENTO_USUARIO_ID` (`USUARIO_ID`),
  CONSTRAINT `FK1D53917A59E11EE` FOREIGN KEY (`FORNECEDOR_ID`) REFERENCES `fornecedor` (`IDFORNECEDOR`),
  CONSTRAINT `FK1D53917AFE2E61A6` FOREIGN KEY (`CLIENTE_ID`) REFERENCES `cliente` (`IDCLIENTE`),
  CONSTRAINT `FK_LANCAMENTO_USUARIO_ID` FOREIGN KEY (`USUARIO_ID`) REFERENCES `usuario` (`IDUSUARIO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lancamento`
--

LOCK TABLES `lancamento` WRITE;
/*!40000 ALTER TABLE `lancamento` DISABLE KEYS */;
INSERT INTO `lancamento` VALUES (1,'ENTRADA',NULL,2,'Entrada do estoque atual',1,'2016-11-20 00:00:00'),(2,'SAIDA',9,NULL,'Venda para aluno',2,'2016-11-20 00:00:19'),(3,'ENTRADA',NULL,1,'Inclusão teste',3,'2016-11-20 13:54:13');
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lancamentoproduto`
--

LOCK TABLES `lancamentoproduto` WRITE;
/*!40000 ALTER TABLE `lancamentoproduto` DISABLE KEYS */;
INSERT INTO `lancamentoproduto` VALUES (1,1,5.88,1,4),(2,1,3.88,1,2),(3,1,3.33,1,1),(4,1,2.99,1,3),(5,1,1.22,1,6),(6,1,2.99,1,7),(7,1,1.67,1,8),(8,1,56.7,1,9),(9,1,6.89,1,10),(10,1,5.87,1,11),(11,1,5,1,13),(12,1,7.54,1,12),(13,1,6.99,2,4),(14,1,3.65,2,2),(15,1,52,3,4),(16,1,3.9,3,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produto`
--

LOCK TABLES `produto` WRITE;
/*!40000 ALTER TABLE `produto` DISABLE KEYS */;
INSERT INTO `produto` VALUES (1,'Caderno 100 Folhas',1,'ATIVO',21),(2,'Borracha',1,'ATIVO',3.99),(3,'Erva Tereré',1,'ATIVO',3.99),(4,'Anel Cristal',1,'ATIVO',0.99),(5,'Apito 3 Furos',0,'INATIVO',1.99),(6,'Fita de Papelão Branca 100X50CM',1,'ATIVO',2.88),(7,'Garfo de Madeira Pacote 50UN',1,'ATIVO',3.65),(8,'Guardanapo Amarelo 23X22CM',1,'ATIVO',10.98),(9,'Palito Espetinho 18cm',1,'ATIVO',13.99),(10,'Palito Espetinho 25cm Bambu Pacote 50UN',1,'ATIVO',13.89),(11,'Pazinha para sorvete',1,'ATIVO',5.19),(12,'Saco de Papel Hamburger Branco 10X11CM',1,'ATIVO',1.8),(13,'Saco Plástico Mini Lanche Branco 14X10CM',1,'ATIVO',18);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'057.946.201-37','joicyellen.pereira@hotmail.com','root','Joicyellen Pereira','b4b8daf4b8ea9d39568719e1e320076f','ATIVO',1),(2,'924.691.287-06','samuel@totemti.com.br','samu','Samuel Figueiredo','b868cdfc8beae943c2386331dc56bd6b','ATIVO',1),(3,'999.999.999-99','tester@tester.com','tester','Tester Base','5e2dcbe453690e9ffa0ac75cb2e39e8b','INATIVO',2),(4,'439.358.843-62','','admin','Administrador','f6fdffe48c908deb0f4c3bd36c032e72','ATIVO',1);
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

-- Dump completed on 2016-11-20 14:18:45
