-- MySQL dump 10.13  Distrib 9.0.1, for macos14.7 (x86_64)
--
-- Host: 127.0.0.1    Database: J_Library
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booktbl`
--

DROP TABLE IF EXISTS `booktbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booktbl` (
  `bookId` bigint NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `availableCount` int DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bookId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booktbl`
--

LOCK TABLES `booktbl` WRITE;
/*!40000 ALTER TABLE `booktbl` DISABLE KEYS */;
INSERT INTO `booktbl` VALUES (1,'히가시노 게이고',15,'소설','고객의 고민을 해결하는 신비한 잡화점 이야기.','https://i.namu.wiki/i/CLKfuKNO-G1STM9S3fzU9gyclBAl2NOlS8K9WSNGAZ_RwiQOnSt2DzGEN30XIzdA3EV6yzDKyq9WXDEL4qqSVw.webp','현대문학','나미야 잡화점의 기적'),(2,'기시미 이치로',20,'자기계발','용기를 갖고 행복한 삶을 추구하는 방법에 대한 철학적 대화.','https://image.yes24.com/goods/15058512/XL','인플루엔셜','미움받을 용기'),(3,'손원평',12,'소설','감정을 느끼지 못하는 소년이 사랑과 상처를 경험하는 성장 이야기.','https://contents.kyobobook.co.kr/sih/fit-in/400x0/pdt/9788936456788.jpg','창비','아몬드'),(4,'빅터 프랭클',10,'자기계발','홀로코스트 생존자가 전하는 삶의 의미에 대한 이야기.','https://image.yes24.com/goods/1775518/XL','청아출판사','죽음의 수용소에서'),(6,'헤르만 헤세',18,'소설','자아와 성장의 본질을 탐구하는 고전 소설.','https://minumsa.minumsa.com/wp-content/uploads/bookcover/044_%EB%8D%B0%EB%AF%B8%EC%95%88-300x504.jpg','민음사','데미안'),(7,'박경리',8,'한국문학','한국 역사의 파란만장한 이야기를 담은 대하소설.','https://i.namu.wiki/i/d5Y8XXzIOdFQHLca0yKLvQL_gC7b2xDWHAwNIgK7ESYP1pI7m3YP6n_RZKCeJCC38CrI3yModNoiYLbjy5ULPQ.webp','마로니에북스','토지'),(8,'조정래',10,'한국문학','한국 근대사의 아픔과 민족적 갈등을 그린 소설.','https://upload.wikimedia.org/wikipedia/ko/5/5e/%ED%83%9C%EB%B0%B1%EC%82%B0%EB%A7%A5_%ED%91%9C%EC%A7%80.jpg','해냄출판사','태백산맥'),(9,'나관중',30,'역사','중국 삼국시대를 배경으로 한 대하 역사 소설.','https://image.yes24.com/momo/TopCate88/MidCate06/8754765.jpg','민음사','삼국지'),(10,'존 스튜어트 밀',15,'철학','자유와 민주주의에 대한 철학적 고찰.','https://image.yes24.com/goods/61158900/XL','을유문화사','자유론'),(11,'로버트 기요사키',22,'자기계발','부의 법칙과 투자 마인드를 설명하는 자기계발서.','https://minumin.minumsa.com/wp-content/uploads/bookcover/9421072-medium.jpg','흐름출판','부자 아빠 가난한 아빠'),(12,'밀란 쿤데라',18,'철학','사랑과 자유를 철학적으로 탐구한 소설.','https://image.yes24.com/goods/3657268/XL','민음사','참을 수 없는 존재의 가벼움'),(13,'김영하',20,'에세이','여행의 본질과 인간의 갈망에 대한 이야기.','https://image.yes24.com/goods/71923011/XL','문학동네','여행의 이유'),(14,'혜민 스님',25,'에세이','현대인의 마음 치유를 위한 힐링 에세이.','https://image.yes24.com/usedshop/2023/0827/cc84ff0a-ec88-4de2-bb7c-678eb57e9631_XL.jpg','쌤앤파커스','멈추면 비로소 보이는 것들'),(15,'조남주',12,'소설','한국 여성의 삶을 진솔하게 그린 소설.','https://cdn.mc-plus.net/news/photo/202105/10735_31348_5020.jpg','민음사','82년생 김지영'),(16,'Robert C. Martin',10,'IT/프로그래밍','깨끗하고 유지보수하기 쉬운 코드를 작성하는 방법.','https://image.yes24.com/goods/11681152/XL','Prentice Hall','Clean Code'),(18,'David Flanagan',8,'IT/프로그래밍','자바스크립트 언어의 모든 것을 다룬 가이드북.','https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/9788966263462.jpg','한빛미디어','자바스크립트 완벽 가이드'),(20,'이도훈',5,'IT/프로그래밍','파이썬으로 알고리즘 트레이딩을 구현하는 방법.','https://contents.kyobobook.co.kr/sih/fit-in/400x0/pdt/9791158391461.jpg','한빛미디어','파이썬으로 배우는 알고리즘 트레이딩');
/*!40000 ALTER TABLE `booktbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rentalTBL`
--

DROP TABLE IF EXISTS `rentalTBL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rentalTBL` (
  `rentalId` bigint NOT NULL AUTO_INCREMENT,
  `dueDate` datetime(6) DEFAULT NULL,
  `overdue` int DEFAULT NULL,
  `rentalDate` datetime(6) DEFAULT NULL,
  `returnDate` datetime(6) DEFAULT NULL,
  `bookId` bigint DEFAULT NULL,
  `userId` bigint DEFAULT NULL,
  PRIMARY KEY (`rentalId`),
  KEY `FK9uo8enkl3qalpnp2n61srpgts` (`bookId`),
  KEY `FKgn840vfgxwwjt1bojlg7gdiq4` (`userId`),
  CONSTRAINT `FK9uo8enkl3qalpnp2n61srpgts` FOREIGN KEY (`bookId`) REFERENCES `bookTBL` (`bookId`),
  CONSTRAINT `FKgn840vfgxwwjt1bojlg7gdiq4` FOREIGN KEY (`userId`) REFERENCES `userTBL` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rentalTBL`
--

LOCK TABLES `rentalTBL` WRITE;
/*!40000 ALTER TABLE `rentalTBL` DISABLE KEYS */;
/*!40000 ALTER TABLE `rentalTBL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userTBL`
--

DROP TABLE IF EXISTS `userTBL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userTBL` (
  `userId` bigint NOT NULL AUTO_INCREMENT,
  `currentRentalCount` int DEFAULT NULL,
  `loginId` varchar(255) NOT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `overdueCount` int DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `UK3lldw5c14hshedc7fo0rccqwe` (`loginId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userTBL`
--

LOCK TABLES `userTBL` WRITE;
/*!40000 ALTER TABLE `userTBL` DISABLE KEYS */;
INSERT INTO `userTBL` VALUES (2,0,'admin','010-5187-3217',0,'$2a$10$FBipI7c7LcTkz3CBwh0JyOumoEwc719u0.XRYgGiuF4WpSFF4i4Di','ROLE_ADMIN',NULL),(3,0,'bbb','010-3210-3123',0,'$2a$10$kfLcoo2xyXRIFCTJ9kVgYu/HESKfZ/cYWwOUWgUDmprcI/J4./UxK','ROLE_USER',NULL),(4,0,'ccc','010-1231-1246',0,'$2a$10$LNeNMRmaiwOFJPEK8OY3OOpGN.Gs7D0i3FAOwUchI.KDT4xQF3VIy','ROLE_USER',NULL);
/*!40000 ALTER TABLE `userTBL` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-24 18:21:44
