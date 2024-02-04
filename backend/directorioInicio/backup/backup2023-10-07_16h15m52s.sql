-- MySQL dump 10.13  Distrib 8.0.34, for Linux (x86_64)
--
-- Host: localhost    Database: bdtalk2gether
-- ------------------------------------------------------
-- Server version	8.0.34-0ubuntu0.22.04.1

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
-- Current Database: `bdtalk2gether`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `bdtalk2gether` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `bdtalk2gether`;

--
-- Table structure for table `bloqueos`
--

DROP TABLE IF EXISTS `bloqueos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bloqueos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_bloqueo_realizado` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_bloqueo_realizado` datetime(6) DEFAULT NULL,
  `fk_usuario_bloqueado` bigint DEFAULT NULL,
  `fk_usuario_que_bloquea` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKapvg13h3hy7dn28ec87f8sfy6` (`fk_usuario_bloqueado`),
  KEY `FKf93j28g05mpug4rko9hd3wedj` (`fk_usuario_que_bloquea`),
  CONSTRAINT `FKapvg13h3hy7dn28ec87f8sfy6` FOREIGN KEY (`fk_usuario_bloqueado`) REFERENCES `cuenta` (`id`),
  CONSTRAINT `FKf93j28g05mpug4rko9hd3wedj` FOREIGN KEY (`fk_usuario_que_bloquea`) REFERENCES `cuenta` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bloqueos`
--

LOCK TABLES `bloqueos` WRITE;
/*!40000 ALTER TABLE `bloqueos` DISABLE KEYS */;
INSERT INTO `bloqueos` VALUES (1,'2023-09-15 09:16:02.551000','2023-09-20 10:37:12.726000',1,4),(2,'2023-09-20 11:01:12.578000','2023-09-20 10:37:12.726000',2,3),(3,'2023-09-20 11:10:28.274000','2023-09-20 10:37:12.726000',5,4),(4,'2023-09-20 12:14:59.885000','2023-09-20 12:56:48.124000',4,3),(5,'2023-09-20 12:17:36.790000','2023-09-20 12:50:16.096000',3,4),(6,'2023-09-22 18:07:30.501000','2023-09-20 10:37:12.726000',4,2),(7,'2023-09-30 11:35:35.950000','2023-09-30 11:36:49.636000',2,1);
/*!40000 ALTER TABLE `bloqueos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calificacion`
--

DROP TABLE IF EXISTS `calificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calificacion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad_estrellas` int DEFAULT NULL,
  `fecha_hora_alta_calificacion` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_calificacion` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificacion`
--

LOCK TABLES `calificacion` WRITE;
/*!40000 ALTER TABLE `calificacion` DISABLE KEYS */;
INSERT INTO `calificacion` VALUES (1,1,'2023-08-08 21:06:54.840000',NULL),(2,2,'2023-08-08 21:06:54.840000',NULL),(3,3,'2023-08-08 21:06:54.840000',NULL),(4,4,'2023-08-08 21:06:54.840000',NULL),(5,5,'2023-08-08 21:06:54.840000',NULL);
/*!40000 ALTER TABLE `calificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `confirmation_token`
--

DROP TABLE IF EXISTS `confirmation_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `confirmation_token` (
  `id` bigint NOT NULL,
  `confirmed_at` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `expires_at` datetime(6) NOT NULL,
  `token` varchar(255) NOT NULL,
  `idcuenta` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg3nhqw6wfubpfxnjvc88bc3qj` (`idcuenta`),
  CONSTRAINT `FKg3nhqw6wfubpfxnjvc88bc3qj` FOREIGN KEY (`idcuenta`) REFERENCES `cuenta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmation_token`
--

LOCK TABLES `confirmation_token` WRITE;
/*!40000 ALTER TABLE `confirmation_token` DISABLE KEYS */;
INSERT INTO `confirmation_token` VALUES (1,'2023-08-22 19:42:50.741102','2023-08-22 19:38:19.324592','2023-08-22 19:53:19.324592','abd0dedb-7aa6-4ea0-9f00-15fbb8dfafe4',1),(2,'2023-08-22 19:51:08.747668','2023-08-22 19:50:58.936395','2023-08-22 20:05:58.936395','b30acee4-936b-43f1-ae36-85da77af7064',2),(3,'2023-08-22 19:59:19.133877','2023-08-22 19:55:59.339854','2023-08-22 20:10:59.339854','435e955b-1603-40d9-8f8f-2954183404d3',3),(4,'2023-08-22 20:06:55.452155','2023-08-22 20:06:14.107835','2023-08-22 20:21:14.107835','79fdd1c5-876c-4437-a8d2-1f066fadef02',4),(5,'2023-08-22 20:09:55.269949','2023-08-22 20:09:22.046984','2023-08-22 20:24:22.046984','89711976-eae1-4654-83a1-a9d813248f3d',5),(6,'2023-09-29 20:40:04.263441','2023-09-29 20:38:50.995539','2023-09-29 20:53:50.995539','db150f6c-fcf1-4994-97e0-9f7e7a83e196',6),(7,'2023-09-29 20:44:18.410404','2023-09-29 20:44:08.540607','2023-09-29 20:59:08.540607','3df5d293-2fb4-40c9-9774-f28b727b207a',7),(8,'2023-09-29 20:49:08.051644','2023-09-29 20:47:27.476383','2023-09-29 21:02:27.476383','5580eba1-e0b1-4a00-8308-493e63850133',8),(9,'2023-09-29 20:50:43.372210','2023-09-29 20:50:19.085623','2023-09-29 21:05:19.085623','1973052c-086c-461e-adce-0daa200266ce',9),(10,'2023-09-29 20:52:54.531775','2023-09-29 20:52:43.993943','2023-09-29 21:07:43.994936','c466fb59-c82c-496d-962a-97f2d9a5dad3',10),(11,'2023-09-29 20:55:12.800162','2023-09-29 20:55:04.443829','2023-09-29 21:10:04.443829','b5ea4974-5f44-4ed7-b8ca-4f262fa165cb',11),(12,'2023-09-29 20:56:35.222663','2023-09-29 20:56:24.177761','2023-09-29 21:11:24.177761','cb33e3ab-5b8c-4267-b21b-ad4e2ba1e331',12),(13,'2023-09-29 20:59:07.076616','2023-09-29 20:58:41.388109','2023-09-29 21:13:41.388109','ac5126e0-0fe7-460e-bd8b-bc62d2b287bc',13),(14,'2023-09-29 21:00:46.960241','2023-09-29 21:00:17.404966','2023-09-29 21:15:17.404966','4cfd325d-67a0-4415-839d-34fac281f385',14),(15,'2023-09-29 21:02:28.239887','2023-09-29 21:02:15.966036','2023-09-29 21:17:15.966036','141d1337-7a85-4026-bf50-a62ab4a9ee6a',15),(16,'2023-09-29 21:06:50.453759','2023-09-29 21:06:32.393639','2023-09-29 21:21:32.393639','c60bfd08-4947-4938-a81a-1fd9b6357076',16),(17,'2023-09-29 21:08:03.628576','2023-09-29 21:07:53.133212','2023-09-29 21:22:53.133212','9670a5ed-3210-475e-947e-b098cc536ca7',17),(18,'2023-09-29 21:09:30.927117','2023-09-29 21:09:09.802999','2023-09-29 21:24:09.802999','a5514caa-7c66-4c75-9161-a4af1502e71f',18),(19,'2023-09-29 21:11:20.823783','2023-09-29 21:11:11.319894','2023-09-29 21:26:11.319894','bd00e373-909f-4667-aff4-0df07ac45271',19);
/*!40000 ALTER TABLE `confirmation_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `confirmation_token_sequence`
--

DROP TABLE IF EXISTS `confirmation_token_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `confirmation_token_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `confirmation_token_sequence`
--

LOCK TABLES `confirmation_token_sequence` WRITE;
/*!40000 ALTER TABLE `confirmation_token_sequence` DISABLE KEYS */;
INSERT INTO `confirmation_token_sequence` VALUES (20);
/*!40000 ALTER TABLE `confirmation_token_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta`
--

DROP TABLE IF EXISTS `cuenta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad_referidos` int DEFAULT NULL,
  `contrasenia` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `cuenta_creada` datetime(6) DEFAULT NULL,
  `cuenta_verificada` datetime(6) DEFAULT NULL,
  `ultima_conexion` datetime(6) DEFAULT NULL,
  `url_foto` varchar(255) NOT NULL,
  `fk_cuenta_eliminada` bigint DEFAULT NULL,
  `fk_rol` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t9w6gd0wpuyis6hn8acaqkw9y` (`correo`),
  KEY `FKcb4kep6rwhrbt6u3aj46bxi9s` (`fk_cuenta_eliminada`),
  KEY `FKhpxwc088b1o64v583dyl8b1ov` (`fk_rol`),
  CONSTRAINT `FKcb4kep6rwhrbt6u3aj46bxi9s` FOREIGN KEY (`fk_cuenta_eliminada`) REFERENCES `cuentaeliminada` (`id`),
  CONSTRAINT `FKhpxwc088b1o64v583dyl8b1ov` FOREIGN KEY (`fk_rol`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta`
--

LOCK TABLES `cuenta` WRITE;
/*!40000 ALTER TABLE `cuenta` DISABLE KEYS */;
INSERT INTO `cuenta` VALUES (1,0,'$2a$10$wdmX8IPMCHf5GAqfclD8ueHHupXqsa6knGZcfIagACSAy7a6OIOFC','lealivan009@gmail.com','2023-08-22 19:38:18.653000','2023-08-22 19:42:50.967000','2023-10-06 13:24:50.465000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2FIMG_20230417_132957504.jpg?alt=media&token=f1e8fc58-4df6-42f4-89b1-02793199ff37',NULL,2),(2,0,'$2a$10$4F8d909F6G.5iXuvNnIc0eoRw8G7XBWh3KvyyLHdKJ1zvpduuAU5u','nataliacortes@talk2gether.com','2023-08-22 19:50:58.514000','2023-08-22 19:51:08.773000','2023-10-04 20:42:11.083000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fretrato-hermoso-mujer-joven-posicion-pared-gris_231208-10760.webp?alt=media&token=4b985f00-4439-4a26-abe8-05e41875ba27',NULL,1),(3,0,'$2a$10$7PkxIzgiZ4sSCDLxG23BxuatUTMnWQEW609PO6cnkeBgsSzBqGiQS','pablomusaber2000@gmail.com','2023-08-22 19:55:59.283000','2023-08-22 19:59:19.141000','2023-10-03 16:02:22.263000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Ffoto_perfil.jpeg?alt=media&token=878fa228-a67e-4b3a-969f-7a6198e6cfc4',NULL,2),(4,0,'$2a$10$qHmgk4q2L77Lw3ZFxsUkPu2gmTv2/P4ehelsEEF1uFhs5z.QEbyzK','franncoherrera2011@gmail.com','2023-08-22 20:06:13.750000','2023-08-22 20:06:55.667000','2023-10-07 11:12:16.632000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Ffranco.png?alt=media&token=fb768ff7-7828-4975-8a78-7801c64d042d',NULL,2),(5,0,'$2a$10$H1dUr0YIxC1QespiIRIecORbiV77ArQmEiwFvziWKPBQRSnYTSj6C','sebastianriverosdev@gmail.com','2023-08-22 20:09:21.945000','2023-08-22 20:09:55.275000','2023-10-03 15:38:22.024000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Funnamed.jpg?alt=media&token=ba9732c2-8e9d-4fd2-9ce7-dda85825a18d',NULL,2),(6,0,'$2a$10$ueZIuqbFcAE7Fv7YtDRLmuLsgRuRO5Gn2MOhEfOlTA3YLssJqXkKq','juanlopez@talk2gether.com','2023-09-29 20:38:47.344000','2023-09-29 20:40:04.459000','2023-10-02 23:32:54.893000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fchico-guapo-seguro-posando-contra-pared-blanca_176420-32936.jpeg?alt=media&token=0f078409-cc16-4214-b45c-a587b14c4580',NULL,1),(7,0,'$2a$10$6G7yTbNDSshsIDf6G4iRjuXiMfWILp3aSZHHDJ6ou19mhOOIzIPGi','jhondoe@talk2gether.com','2023-09-29 20:44:04.785000','2023-09-29 20:44:18.599000','2023-10-02 23:38:46.664000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fjoven-confiado_1098-20868.jpeg?alt=media&token=13f2e71a-ab90-489f-8486-d9e9e28a962e',NULL,1),(8,0,'$2a$10$7qa9jS0u5fumFwcKzOQ4lOjikpYuO0kuuLt4VlZFCb/6I2NQQ.O1a','luisrodriguez@talk2gether.com','2023-09-29 20:47:23.846000','2023-09-29 20:49:08.234000','2023-10-02 23:39:48.957000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fman-539993_640.jpg?alt=media&token=e8ebaa73-70c0-4b0e-8e3e-cd67979f3b78',NULL,1),(9,0,'$2a$10$vQSjidEa91PhNL8TrJN68.bDjgUfcthJpbIcgLp6UBkYNptvr9LA2','mariagomez@talk2gether.com','2023-09-29 20:50:15.507000','2023-09-29 20:50:43.554000','2023-10-02 23:40:39.563000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fwoman-1853939_640.jpg?alt=media&token=d656fb94-7282-4a1b-86fb-4cedba3b795d',NULL,1),(10,0,'$2a$10$DPfcdLxTsK.qXctTW/aVou6Zmn7JYpLxNG/BeTCo8ndhaaJxb7Qj6','lauramartinez@talk2gether.com','2023-09-29 20:52:40.324000','2023-09-29 20:52:54.709000','2023-10-02 23:42:11.072000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fsmile-2072907_640%20(1).jpg?alt=media&token=0b92dac2-29cc-45df-a267-fd240a4a31ff',NULL,1),(11,0,'$2a$10$/XnFE3IJbms4hxayWhPof.Bg72EENGPJjOXtHRVczBGp8/Gm9Wx8.','andrealmeida@talk2gether.com','2023-09-29 20:55:00.845000','2023-09-29 20:55:12.982000','2023-10-02 23:42:52.889000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fbeautiful-2405131_640.jpg?alt=media&token=23c6d99a-bd7c-43d3-af4b-fcf7a7fd9d45',NULL,1),(12,0,'$2a$10$1gGdOCfv/4.iseSeyfhqyeznHObAyZOSlmHZFMFGFMDhG.LfDiaWe','emilyanderson@talk2gether.com','2023-09-29 20:56:21.047000','2023-09-29 20:56:35.408000','2023-10-02 23:43:31.337000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fsad-2042536_640.jpg?alt=media&token=10ab93f9-7932-468d-8c23-047aad94300e',NULL,1),(13,0,'$2a$10$3aaI3/VORBFQ9Uzw5GnLKefwmHHMt7KOkrIhTkSFitYUxOxGH9doO','joaosilva@talk2gether.com','2023-09-29 20:58:37.753000','2023-09-29 20:59:07.249000','2023-10-02 23:44:22.432000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fportrait-2194457_640.jpg?alt=media&token=3dab7ab4-ede0-4907-9b55-fc28f91c9a84',NULL,1),(14,0,'$2a$10$f9/DaMU4M47I8KdDGaTYgexS8Y6qavm7gywtbPeDu/1hywZ8qqCCW','paulamendoza@talk2gether.com','2023-09-29 21:00:13.772000','2023-09-29 21:00:47.139000','2023-10-02 23:45:01.446000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fmodel-2387582_640.jpg?alt=media&token=ae2bbb48-4402-4d85-9835-ca5f0fc70ac4',NULL,1),(15,0,'$2a$10$yqpxzZueFVjpOEdmDXD7iOVuR2oxpQta6fwKmIWZLpJ.hxjph.FsK','ricardopena@talk2gether.com','2023-09-29 21:02:11.919000','2023-09-29 21:02:28.419000','2023-10-02 23:45:36.628000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fman-597178_640.jpg?alt=media&token=26c05d1d-5df9-47c5-b7a3-ea1e1d647aea',NULL,1),(16,0,'$2a$10$ItuG.jRBrAomUAfInSXh9OeI2h1NBc4FDW2SSwOY7O40M/TYxxsGu','valentinarios@talk2gether.com','2023-09-29 21:06:27.964000','2023-09-29 21:06:50.637000','2023-10-02 23:56:57.852000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fwoman-1958723_640.jpg?alt=media&token=6ff27476-df12-4807-b9b4-32b35ea3d4ce',NULL,1),(17,0,'$2a$10$kgq0OGHWbiBHwkTzAzwoDeArD7z2MGnJYjh1IoeA44shXLCZGP.6O','carolinamora@talk2gether.com','2023-09-29 21:07:49.456000','2023-09-29 21:08:03.842000','2023-10-02 23:46:50.579000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fsuitcase-1488516_640.jpg?alt=media&token=ff460931-5b8c-4639-adaa-df1a61af541c',NULL,1),(18,0,'$2a$10$PcpKBLKTqdY.iEg8sgH8r.MPi1ivfOYD6j0QxeQdmvMk.lWAU9/Ku','lisawilson@talk2gether.com','2023-09-29 21:09:06.658000','2023-09-29 21:09:31.107000','2023-10-02 23:47:35.847000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Flaptop-3087585_640.jpg?alt=media&token=3b443f1c-61c2-4918-9965-ca2cfbea18f7',NULL,1),(19,0,'$2a$10$5BkAlRLRT9ipHEFNdi.yzO76bzJG6Wddor4yejErQQpnlWp6Hr8py','samanthaclark@talk2gether.com','2023-09-29 21:11:06.985000','2023-09-29 21:11:20.999000','2023-10-02 23:48:17.898000','https://firebasestorage.googleapis.com/v0/b/talk2gether-a8b61.appspot.com/o/images%2Fcomputer-1185626_640.jpg?alt=media&token=1fe199d4-e744-4eb0-ba23-9b083f4b8303',NULL,1);
/*!40000 ALTER TABLE `cuenta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta_lista_seguidores`
--

DROP TABLE IF EXISTS `cuenta_lista_seguidores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta_lista_seguidores` (
  `cuenta_id` bigint NOT NULL,
  `lista_seguidores_id` bigint NOT NULL,
  UNIQUE KEY `UK_hceqxdy9x39anuvetsh4u95t4` (`lista_seguidores_id`),
  KEY `FKeilf60jc4wbl7dtv23d7teu35` (`cuenta_id`),
  CONSTRAINT `FK6aluwqii3br9fmjfd2dlxp50y` FOREIGN KEY (`lista_seguidores_id`) REFERENCES `seguidores` (`id`),
  CONSTRAINT `FKeilf60jc4wbl7dtv23d7teu35` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta_lista_seguidores`
--

LOCK TABLES `cuenta_lista_seguidores` WRITE;
/*!40000 ALTER TABLE `cuenta_lista_seguidores` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuenta_lista_seguidores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuenta_lista_seguidos`
--

DROP TABLE IF EXISTS `cuenta_lista_seguidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuenta_lista_seguidos` (
  `cuenta_id` bigint NOT NULL,
  `lista_seguidos_id` bigint NOT NULL,
  UNIQUE KEY `UK_ebtb7l39axywt3aflgdd5sr8d` (`lista_seguidos_id`),
  KEY `FK96xtkos1mn5kfbw65s5vf57ua` (`cuenta_id`),
  CONSTRAINT `FK96xtkos1mn5kfbw65s5vf57ua` FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`),
  CONSTRAINT `FKav8q7argib8oventcsm4umo7k` FOREIGN KEY (`lista_seguidos_id`) REFERENCES `seguidos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuenta_lista_seguidos`
--

LOCK TABLES `cuenta_lista_seguidos` WRITE;
/*!40000 ALTER TABLE `cuenta_lista_seguidos` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuenta_lista_seguidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuentaeliminada`
--

DROP TABLE IF EXISTS `cuentaeliminada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuentaeliminada` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_cuenta_eliminada` datetime(6) DEFAULT NULL,
  `id_administrador_reponsable` bigint DEFAULT NULL,
  `descripcion_cuenta_eliminada` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentaeliminada`
--

LOCK TABLES `cuentaeliminada` WRITE;
/*!40000 ALTER TABLE `cuentaeliminada` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuentaeliminada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuentaeliminada_lista_cuenta_eliminada_motivo`
--

DROP TABLE IF EXISTS `cuentaeliminada_lista_cuenta_eliminada_motivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuentaeliminada_lista_cuenta_eliminada_motivo` (
  `cuenta_eliminada_id` bigint NOT NULL,
  `lista_cuenta_eliminada_motivo_id` bigint NOT NULL,
  UNIQUE KEY `UK_og6vtkjt5r00cbwgki50721hy` (`lista_cuenta_eliminada_motivo_id`),
  KEY `FKdxk8r8mgudhjlu3ae9efgrgue` (`cuenta_eliminada_id`),
  CONSTRAINT `FK7hcoc3jadegcf9fubcyuox9nj` FOREIGN KEY (`lista_cuenta_eliminada_motivo_id`) REFERENCES `cuentaeliminadamotivo` (`id`),
  CONSTRAINT `FKdxk8r8mgudhjlu3ae9efgrgue` FOREIGN KEY (`cuenta_eliminada_id`) REFERENCES `cuentaeliminada` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentaeliminada_lista_cuenta_eliminada_motivo`
--

LOCK TABLES `cuentaeliminada_lista_cuenta_eliminada_motivo` WRITE;
/*!40000 ALTER TABLE `cuentaeliminada_lista_cuenta_eliminada_motivo` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuentaeliminada_lista_cuenta_eliminada_motivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuentaeliminadamotivo`
--

DROP TABLE IF EXISTS `cuentaeliminadamotivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuentaeliminadamotivo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_cuenta_eliminada_motivo` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_cuenta_eliminada_motivo` datetime(6) DEFAULT NULL,
  `fk_motivo` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2aourj85pyxq7yl45mev6j6hr` (`fk_motivo`),
  CONSTRAINT `FK2aourj85pyxq7yl45mev6j6hr` FOREIGN KEY (`fk_motivo`) REFERENCES `motivo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentaeliminadamotivo`
--

LOCK TABLES `cuentaeliminadamotivo` WRITE;
/*!40000 ALTER TABLE `cuentaeliminadamotivo` DISABLE KEYS */;
INSERT INTO `cuentaeliminadamotivo` VALUES (1,'2023-09-16 10:42:31.869000',NULL,4);
/*!40000 ALTER TABLE `cuentaeliminadamotivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_participante`
--

DROP TABLE IF EXISTS `detalle_participante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_participante` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_fin_detalle_participante` datetime(6) DEFAULT NULL,
  `fecha_hora_inicio_detalle_participante` datetime(6) DEFAULT NULL,
  `fk_participante` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs9dtwuv7jq30mapkonu3r8sr8` (`fk_participante`),
  CONSTRAINT `FKs9dtwuv7jq30mapkonu3r8sr8` FOREIGN KEY (`fk_participante`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_participante`
--

LOCK TABLES `detalle_participante` WRITE;
/*!40000 ALTER TABLE `detalle_participante` DISABLE KEYS */;
INSERT INTO `detalle_participante` VALUES (1,'2023-09-16 18:49:35.134000','2023-09-16 18:49:00.042000',4),(2,'2023-09-16 18:49:35.134000','2023-09-16 18:49:21.178000',1),(3,'2023-09-16 18:51:49.045000','2023-09-16 18:51:00.754000',4),(4,'2023-09-16 18:51:49.045000','2023-09-16 18:51:19.873000',1),(5,'2023-09-20 09:54:33.409000','2023-09-20 09:53:02.276000',5),(6,'2023-09-20 09:54:33.409000','2023-09-20 09:53:31.691000',1),(7,'2023-09-20 10:16:42.896000','2023-09-20 10:15:02.419000',1),(8,'2023-09-20 10:16:42.896000','2023-09-20 10:15:19.671000',5),(9,'2023-09-20 10:19:12.080000','2023-09-20 10:18:15.414000',1),(10,'2023-09-20 10:19:12.080000','2023-09-20 10:18:46.905000',5),(11,'2023-09-20 10:20:58.731000','2023-09-20 10:19:54.391000',1),(12,'2023-09-20 10:20:58.731000','2023-09-20 10:20:21.279000',5),(13,'2023-09-29 20:25:02.794000','2023-09-29 20:21:45.235000',4),(14,'2023-09-29 20:25:02.794000','2023-09-29 20:24:15.262000',5),(15,'2023-09-29 20:25:02.794000','2023-09-30 19:32:25.067000',1),(16,'2023-09-29 20:25:02.794000','2023-09-30 19:32:36.772000',3),(17,'2023-09-29 20:25:02.794000','2023-09-30 19:32:49.279000',4),(18,'2023-09-29 20:25:02.794000','2023-09-30 19:32:59.645000',5),(19,'2023-09-29 20:25:02.794000','2023-09-30 19:33:36.089000',8),(20,'2023-09-29 20:25:02.794000','2023-09-30 19:33:44.407000',10),(21,'2023-09-29 20:25:02.794000','2023-09-30 19:35:36.107000',12),(22,'2023-09-29 20:25:02.794000','2023-09-30 19:35:45.867000',15),(23,'2023-09-29 20:25:02.794000','2023-09-30 19:40:29.504000',14),(24,'2023-09-29 20:25:02.794000','2023-09-30 19:40:55.564000',11),(25,NULL,'2023-09-30 19:50:30.047000',6),(26,NULL,'2023-09-30 19:50:39.596000',8),(27,NULL,'2023-09-30 19:50:48.363000',9),(28,NULL,'2023-09-30 19:51:00.809000',10),(29,NULL,'2023-09-30 19:51:09.175000',11),(30,NULL,'2023-09-30 19:51:17.710000',12),(31,NULL,'2023-09-30 19:51:25.141000',13),(32,NULL,'2023-09-30 19:51:32.817000',14),(33,NULL,'2023-09-30 19:51:41.216000',15);
/*!40000 ALTER TABLE `detalle_participante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `error`
--

DROP TABLE IF EXISTS `error`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `error` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_error` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_error` datetime(6) DEFAULT NULL,
  `nombre_error` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `error`
--

LOCK TABLES `error` WRITE;
/*!40000 ALTER TABLE `error` DISABLE KEYS */;
/*!40000 ALTER TABLE `error` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_reporte_error`
--

DROP TABLE IF EXISTS `estado_reporte_error`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_reporte_error` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_estado_reporte_error` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_estado_reporte_error` datetime(6) DEFAULT NULL,
  `nombre_estado_reporte_error` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_reporte_error`
--

LOCK TABLES `estado_reporte_error` WRITE;
/*!40000 ALTER TABLE `estado_reporte_error` DISABLE KEYS */;
/*!40000 ALTER TABLE `estado_reporte_error` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `idioma`
--

DROP TABLE IF EXISTS `idioma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `idioma` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_idioma` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_idioma` datetime(6) DEFAULT NULL,
  `nombre_idioma` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `idioma`
--

LOCK TABLES `idioma` WRITE;
/*!40000 ALTER TABLE `idioma` DISABLE KEYS */;
INSERT INTO `idioma` VALUES (1,'2023-06-23 21:51:05.000000',NULL,'Español'),(2,'2023-06-23 21:51:05.000000',NULL,'Inglés'),(3,'2023-06-23 21:51:05.000000',NULL,'Frances'),(4,'2023-06-23 21:51:05.000000',NULL,'Portugués'),(5,'2023-06-23 21:51:05.000000',NULL,'Ruso'),(6,'2023-06-23 21:51:05.000000',NULL,'Alemán'),(7,'2023-06-23 21:51:05.000000',NULL,'Japonés');
/*!40000 ALTER TABLE `idioma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interes`
--

DROP TABLE IF EXISTS `interes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_interes` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_interes` datetime(6) DEFAULT NULL,
  `nombre_interes` varchar(50) DEFAULT NULL,
  `url_interes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interes`
--

LOCK TABLES `interes` WRITE;
/*!40000 ALTER TABLE `interes` DISABLE KEYS */;
INSERT INTO `interes` VALUES (1,'2023-06-23 21:52:28.750000','2023-09-12 16:49:02.354000','Tecnología','fa-solid fa-microchip'),(2,'2023-06-23 21:52:38.524000',NULL,'Cine','fa-solid fa-film'),(3,'2023-06-23 21:52:44.841000',NULL,'Viajes','fa-solid fa-plane'),(4,'2023-06-23 21:52:49.392000',NULL,'Ciencias','fa-solid fa-flask'),(5,'2023-06-23 21:53:01.797000',NULL,'Deporte','fa-solid fa-futbol'),(6,'2023-06-23 21:53:05.701000',NULL,'Libros','fa-solid fa-book'),(8,'2023-08-10 10:54:26.326000',NULL,'Música','fa-solid fa-music'),(9,'2023-08-10 10:58:46.219000',NULL,'Arte','fa-solid fa-palette'),(10,'2023-09-08 19:11:05.117000','2023-09-08 19:15:00.828000','Autos','fa-solid fa-car'),(11,'2023-09-08 19:12:56.612000','2023-09-10 16:10:36.460000','Cultura','fa-solid fa-masks-theater'),(12,'2023-09-08 19:16:17.573000',NULL,'Programación','fa-solid fa-keyboard');
/*!40000 ALTER TABLE `interes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liga`
--

DROP TABLE IF EXISTS `liga`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `liga` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `color_liga` varchar(255) DEFAULT NULL,
  `fecha_hora_alta_liga` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_liga` datetime(6) DEFAULT NULL,
  `nombre_liga` varchar(255) DEFAULT NULL,
  `puntos_desde` int DEFAULT NULL,
  `puntos_hasta` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liga`
--

LOCK TABLES `liga` WRITE;
/*!40000 ALTER TABLE `liga` DISABLE KEYS */;
/*!40000 ALTER TABLE `liga` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logro`
--

DROP TABLE IF EXISTS `logro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logro` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_logro` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_logro` datetime(6) DEFAULT NULL,
  `nombre_logro` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logro`
--

LOCK TABLES `logro` WRITE;
/*!40000 ALTER TABLE `logro` DISABLE KEYS */;
/*!40000 ALTER TABLE `logro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `motivo`
--

DROP TABLE IF EXISTS `motivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `motivo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_motivo` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_motivo` datetime(6) DEFAULT NULL,
  `nombre_motivo` varchar(255) DEFAULT NULL,
  `fk_tipo_motivo` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn5y7oanc78istr7hly3r4fred` (`fk_tipo_motivo`),
  CONSTRAINT `FKn5y7oanc78istr7hly3r4fred` FOREIGN KEY (`fk_tipo_motivo`) REFERENCES `tipomotivo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `motivo`
--

LOCK TABLES `motivo` WRITE;
/*!40000 ALTER TABLE `motivo` DISABLE KEYS */;
INSERT INTO `motivo` VALUES (1,'2023-06-23 21:51:42.081000',NULL,'No cumplir con las normas de la plataforma',2),(2,'2023-06-23 21:51:50.206000',NULL,'Insultar',2),(3,'2023-06-23 21:52:00.157000','2023-09-16 11:38:53.056000','Suplantación de identidad',2),(4,'2023-06-23 21:51:42.081000',NULL,'La plataforma me parece aburrida',1),(5,'2023-06-23 21:51:42.081000',NULL,'Los usuarios no se unen a mis reuniones',1),(6,'2023-06-23 21:51:42.081000',NULL,'Los usuarios entran y al instante se salen',1),(7,'2023-09-16 11:24:06.381000',NULL,'Mala conexión',1);
/*!40000 ALTER TABLE `motivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nivel_idioma`
--

DROP TABLE IF EXISTS `nivel_idioma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nivel_idioma` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_nivel_idioma` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_nivel_idioma` datetime(6) DEFAULT NULL,
  `nombre_nivel_idioma` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nivel_idioma`
--

LOCK TABLES `nivel_idioma` WRITE;
/*!40000 ALTER TABLE `nivel_idioma` DISABLE KEYS */;
INSERT INTO `nivel_idioma` VALUES (1,'2023-06-23 21:53:48.993000',NULL,'Principiante'),(2,'2023-06-23 21:53:48.993000',NULL,'Nativo'),(3,'2023-06-23 21:53:48.993000',NULL,'Intermedio'),(4,'2023-06-23 21:53:58.132000',NULL,'Avanzado');
/*!40000 ALTER TABLE `nivel_idioma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pais`
--

DROP TABLE IF EXISTS `pais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pais` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_pais` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_pais` datetime(6) DEFAULT NULL,
  `nombre_pais` varchar(50) DEFAULT NULL,
  `url_bandera` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=195 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pais`
--

LOCK TABLES `pais` WRITE;
/*!40000 ALTER TABLE `pais` DISABLE KEYS */;
INSERT INTO `pais` VALUES (1,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Afganistán','af'),(2,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Albania','av'),(3,'2023-06-23 21:49:45.795000',NULL,'Alemania','de'),(4,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Andorra',NULL),(5,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Angola',NULL),(6,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Antigua y Barbuda',NULL),(7,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Arabia Saudita',NULL),(8,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Argelia',NULL),(9,'2023-06-23 21:49:45.795000',NULL,'Argentina','ar'),(10,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Armenia',NULL),(11,'2023-06-23 21:49:45.795000',NULL,'Australia','au'),(12,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Austria',NULL),(13,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Azerbaiyán',NULL),(14,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Bahamas',NULL),(15,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Bangladés',NULL),(16,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Barbados',NULL),(17,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Baréin',NULL),(18,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Bélgica',NULL),(19,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Belice',NULL),(20,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Benín',NULL),(21,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Bielorrusia',NULL),(22,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Birmania',NULL),(23,'2023-06-23 21:49:45.795000',NULL,'Bolivia','bo'),(24,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Bosnia y Herzegovina',NULL),(25,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Botsuana',NULL),(26,'2023-06-23 21:49:45.795000',NULL,'Brasil','br'),(27,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Brunéi',NULL),(28,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Bulgaria',NULL),(29,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Burkina Faso',NULL),(30,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Burundi',NULL),(31,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Bután',NULL),(32,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Cabo Verde',NULL),(33,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Camboya',NULL),(34,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Camerún',NULL),(35,'2023-06-23 21:49:45.795000',NULL,'Canadá','ca'),(36,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Catar',NULL),(37,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Chad',NULL),(38,'2023-06-23 21:49:45.795000',NULL,'Chile','cl'),(39,'2023-06-23 21:49:45.795000',NULL,'China','cn'),(40,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Chipre',NULL),(41,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Ciudad del Vaticano',NULL),(42,'2023-06-23 21:49:45.795000',NULL,'Colombia','co'),(43,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Comoras',NULL),(44,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Corea del Norte',NULL),(45,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Corea del Sur',NULL),(46,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Costa de Marfil',NULL),(47,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Costa Rica','cr'),(48,'2023-06-23 21:49:45.795000',NULL,'Croacia','hr'),(49,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Cuba',NULL),(50,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Dinamarca',NULL),(51,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Dominica',NULL),(52,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Ecuador',NULL),(53,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Egipto',NULL),(54,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','El Salvador',NULL),(55,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Emiratos Árabes Unidos',NULL),(56,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Eritrea',NULL),(57,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Eslovaquia',NULL),(58,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Eslovenia',NULL),(59,'2023-06-23 21:49:45.795000',NULL,'España','es'),(60,'2023-06-23 21:49:45.795000',NULL,'Estados Unidos','us'),(61,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Estonia',NULL),(62,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Etiopía',NULL),(63,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Filipinas',NULL),(64,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Finlandia',NULL),(65,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Fiyi',NULL),(66,'2023-06-23 21:49:45.795000',NULL,'Francia','fr'),(67,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Gabón',NULL),(68,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Gambia',NULL),(69,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Georgia',NULL),(70,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Ghana',NULL),(71,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Granada',NULL),(72,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Grecia',NULL),(73,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Guatemala',NULL),(74,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Guyana',NULL),(75,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Guinea',NULL),(76,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Guinea-Bisáu',NULL),(77,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Guinea Ecuatorial',NULL),(78,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Haití',NULL),(79,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Honduras',NULL),(80,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Hungría',NULL),(81,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','India',NULL),(82,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Indonesia',NULL),(83,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Irak',NULL),(84,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Irán',NULL),(85,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Irlanda',NULL),(86,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Islandia',NULL),(87,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Islas Marshall',NULL),(88,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Islas Salomón',NULL),(89,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Israel',NULL),(90,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Italia',NULL),(91,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Jamaica',NULL),(92,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Japón',NULL),(93,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Jordania',NULL),(94,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Kazajistán',NULL),(95,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Kenia',NULL),(96,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Kirguistán',NULL),(97,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Kiribati',NULL),(98,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Kuwait',NULL),(99,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Laos',NULL),(100,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Lesoto',NULL),(101,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Letonia',NULL),(102,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Líbano',NULL),(103,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Liberia',NULL),(104,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Libia',NULL),(105,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Liechtenstein',NULL),(106,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Lituania',NULL),(107,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Luxemburgo',NULL),(108,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Madagascar',NULL),(109,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Malasia',NULL),(110,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Malaui',NULL),(111,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Maldivas',NULL),(112,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Malí',NULL),(113,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Malta',NULL),(114,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Marruecos',NULL),(115,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Mauricio',NULL),(116,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Mauritania',NULL),(117,'2023-06-23 21:49:45.795000',NULL,'México','mx'),(118,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Micronesia',NULL),(119,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Moldavia',NULL),(120,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Mónaco',NULL),(121,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Mongolia',NULL),(122,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Montenegro',NULL),(123,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Mozambique',NULL),(124,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Namibia',NULL),(125,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Nauru',NULL),(126,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Nepal',NULL),(127,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Nicaragua',NULL),(128,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Níger',NULL),(129,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Nigeria',NULL),(130,'2023-06-23 21:49:45.795000',NULL,'Noruega','no'),(131,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Nueva Zelanda',NULL),(132,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Omán',NULL),(133,'2023-06-23 21:49:45.795000',NULL,'Países Bajos','nl'),(134,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Pakistán',NULL),(135,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Palaos',NULL),(136,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Panamá',NULL),(137,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Papúa Nueva Guinea',NULL),(138,'2023-06-23 21:49:45.795000',NULL,'Paraguay','py'),(139,'2023-06-23 21:49:45.795000',NULL,'Perú','pe'),(140,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Polonia',NULL),(141,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Portugal',NULL),(142,'2023-06-23 21:49:45.795000',NULL,'Reino Unido','gb'),(143,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','República Centroafricana',NULL),(144,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','República Checa',NULL),(145,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','República de Macedonia del Norte',NULL),(146,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','República del Congo',NULL),(147,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','República Democrática del Congo',NULL),(148,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','República Dominicana',NULL),(149,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','República Sudafricana',NULL),(150,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Ruanda',NULL),(151,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Rumania',NULL),(152,'2023-06-23 21:49:45.795000',NULL,'Rusia','ru'),(153,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Samoa',NULL),(154,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','San Cristóbal y Nieves',NULL),(155,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','San Marino',NULL),(156,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','San Vicente y las Granadinas',NULL),(157,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Santa Lucía',NULL),(158,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Santo Tomé y Príncipe',NULL),(159,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Senegal',NULL),(160,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Serbia',NULL),(161,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Seychelles',NULL),(162,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Sierra Leona',NULL),(163,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Singapur',NULL),(164,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Siria',NULL),(165,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Somalia',NULL),(166,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Sri Lanka',NULL),(167,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Suazilandia',NULL),(168,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Sudán',NULL),(169,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Sudán del Sur',NULL),(170,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Suecia',NULL),(171,'2023-06-23 21:49:45.795000',NULL,'Suiza','ch'),(172,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Surinam',NULL),(173,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Tailandia',NULL),(174,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Tanzania',NULL),(175,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Tayikistán',NULL),(176,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Timor Oriental',NULL),(177,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Togo',NULL),(178,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Tonga',NULL),(179,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Trinidad y Tobago',NULL),(180,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Túnez',NULL),(181,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Turkmenistán',NULL),(182,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Turquía',NULL),(183,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Tuvalu',NULL),(184,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Ucrania',NULL),(185,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Uganda',NULL),(186,'2023-06-23 21:49:45.795000',NULL,'Uruguay','ur'),(187,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Uzbekistán',NULL),(188,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Vanuatu',NULL),(189,'2023-06-23 21:49:45.795000',NULL,'Venezuela','ve'),(190,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Vietnam',NULL),(191,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Yemen',NULL),(192,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Yibuti',NULL),(193,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Zambia',NULL),(194,'2023-06-23 21:49:45.795000','2023-06-23 21:49:45.795000','Zimbabue',NULL);
/*!40000 ALTER TABLE `pais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permiso`
--

DROP TABLE IF EXISTS `permiso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permiso` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_permiso` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_permiso` datetime(6) DEFAULT NULL,
  `nombre_permiso` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permiso`
--

LOCK TABLES `permiso` WRITE;
/*!40000 ALTER TABLE `permiso` DISABLE KEYS */;
INSERT INTO `permiso` VALUES (1,'2023-08-08 17:23:28.602000',NULL,'registrarse','/registro'),(2,'2023-08-08 17:23:28.602000',NULL,'inicio','/inicio'),(3,'2023-08-08 17:23:28.602000',NULL,'reunion virtual','/reunion-virtual'),(4,'2023-08-08 17:23:28.602000',NULL,'configuracion','/configuracion'),(6,'2023-08-08 17:23:28.602000',NULL,'cuenta eliminada','/cuenta-eliminada'),(7,'2023-08-08 17:23:28.602000',NULL,'ranking','/ranking'),(8,'2023-08-08 17:23:28.602000',NULL,'terminos y condiciones','/terminos-y-condiciones'),(9,'2023-08-08 17:23:28.602000',NULL,'verificar cuenta','/verificar-cuenta'),(10,'2023-08-08 17:23:28.602000',NULL,'cuenta verificada','/cuenta-verificada'),(11,'2023-08-08 17:23:28.602000',NULL,'pantalla bienvenida','/'),(12,'2023-08-08 17:23:28.602000',NULL,'proximamente','/proximamente'),(13,'2023-08-08 17:23:28.602000',NULL,'login','/login'),(14,'2023-08-08 17:23:28.602000',NULL,'recuperar contrasenia','/recuperarContrasenia'),(15,'2023-08-08 17:23:28.602000',NULL,'nueva contrasenia','/nuevaContrasenia'),(16,'2023-08-08 17:23:28.602000',NULL,'confirmacion','/confirmacion'),(17,'2023-08-08 17:23:28.602000',NULL,'bloqueado','/bloqueado'),(18,'2023-08-08 17:23:28.602000',NULL,'perfil de usuario','/perfilUsuario'),(19,'2023-08-08 17:23:28.602000',NULL,'usuario bloqueado','/usuarioBloqueado'),(20,'2023-08-08 17:23:28.602000',NULL,'chat','/chat'),(21,'2023-08-08 17:23:28.602000',NULL,'ayuda','/ayuda'),(22,'2023-08-08 17:23:28.602000',NULL,'adminRoles','/administrador/administrarRoles'),(25,'2023-08-11 16:29:18.532000',NULL,'adminPermisos','/administrador/administrarPermisos'),(26,'2023-08-22 17:24:48.384000',NULL,NULL,'/administrador'),(27,'2023-08-22 17:28:23.384000',NULL,NULL,'/administrador/administrarPais'),(28,'2023-08-22 17:28:28.436000',NULL,NULL,'/administrador/administrarInteres'),(29,'2023-08-22 17:28:33.871000',NULL,NULL,'/administrador/administrarIdioma'),(30,'2023-08-22 17:28:38.775000',NULL,NULL,'/administrador/administrarNivelIdioma'),(31,'2023-08-22 17:28:44.374000',NULL,NULL,'/administrador/administrarUsuario'),(32,'2023-08-22 17:28:49.347000',NULL,NULL,'/administrador/recuperacion'),(33,'2023-08-22 17:28:56.612000',NULL,NULL,'/administrador/crearUsuario'),(34,'2023-08-22 17:29:00.536000',NULL,NULL,'/administrador/administrarMotivos'),(35,'2023-08-22 17:29:07.769000',NULL,NULL,'/administrador/reportesUsuarios'),(36,'2023-08-22 17:29:11.945000',NULL,NULL,'/administrador/reportesChatsUsuarios'),(37,'2023-08-22 17:29:17.475000',NULL,NULL,'/administrador/administrarDashboard'),(38,'2023-08-22 18:12:33.239000',NULL,NULL,'/perfilUsuario/logros'),(39,'2023-08-22 18:12:45.550000',NULL,NULL,'/perfilUsuario/estadisticas'),(40,'2023-08-22 18:12:45.550000',NULL,NULL,'/administrador/puntosPorActividad'),(41,NULL,NULL,NULL,'/administrador/reportesUsuarios');
/*!40000 ALTER TABLE `permiso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permiso_roles`
--

DROP TABLE IF EXISTS `permiso_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permiso_roles` (
  `permiso_id` bigint NOT NULL,
  `rol_id` bigint NOT NULL,
  PRIMARY KEY (`permiso_id`,`rol_id`),
  KEY `FKjqavfvuox27y09kohkldhpn51` (`rol_id`),
  CONSTRAINT `FK1fxmsrew7lreuv4vkpphpb7f` FOREIGN KEY (`permiso_id`) REFERENCES `permiso` (`id`),
  CONSTRAINT `FKjqavfvuox27y09kohkldhpn51` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permiso_roles`
--

LOCK TABLES `permiso_roles` WRITE;
/*!40000 ALTER TABLE `permiso_roles` DISABLE KEYS */;
INSERT INTO `permiso_roles` VALUES (2,1),(3,1),(4,1),(7,1),(9,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(26,1),(38,1),(39,1),(2,2),(3,2),(4,2),(7,2),(9,2),(14,2),(15,2),(16,2),(17,2),(18,2),(19,2),(20,2),(22,2),(25,2),(26,2),(27,2),(28,2),(29,2),(30,2),(31,2),(32,2),(33,2),(34,2),(35,2),(37,2),(38,2),(39,2),(40,2);
/*!40000 ALTER TABLE `permiso_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `puntos_por_actividad`
--

DROP TABLE IF EXISTS `puntos_por_actividad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `puntos_por_actividad` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_puntos_por_actividad` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_puntos_por_actividad` datetime(6) DEFAULT NULL,
  `nombre_puntos_por_actividad` varchar(255) DEFAULT NULL,
  `puntos_por_actividad` int DEFAULT NULL,
  `descripcion_puntos_por_actividad` varchar(255) DEFAULT NULL,
  `puntos_maximos` varchar(255) DEFAULT NULL,
  `tipo_puntos_por_actividad` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puntos_por_actividad`
--

LOCK TABLES `puntos_por_actividad` WRITE;
/*!40000 ALTER TABLE `puntos_por_actividad` DISABLE KEYS */;
INSERT INTO `puntos_por_actividad` VALUES (1,'2023-08-07 04:46:52.376000',NULL,'videollamada',10,NULL,NULL,NULL),(2,'2023-08-07 04:47:30.985000',NULL,'registrarse a partir de un referido',600,NULL,NULL,NULL),(3,'2023-08-07 04:52:29.389000',NULL,'el comunicador',700,NULL,NULL,NULL),(4,'2023-08-07 04:52:44.786000',NULL,'aprendiz ejemplar',800,NULL,NULL,NULL),(5,'2023-08-07 04:53:04.955000',NULL,'mente multilingue',900,NULL,NULL,NULL),(6,'2023-08-07 04:53:26.247000',NULL,'el filosofo',700,NULL,NULL,NULL),(7,'2023-08-07 04:53:47.060000',NULL,'el popular',300,NULL,NULL,NULL),(8,'2023-08-07 04:54:02.215000',NULL,'el viajero',400,NULL,NULL,NULL);
/*!40000 ALTER TABLE `puntos_por_actividad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `puntuacion`
--

DROP TABLE IF EXISTS `puntuacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `puntuacion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad_puntos` int DEFAULT NULL,
  `fecha_puntuacion` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puntuacion`
--

LOCK TABLES `puntuacion` WRITE;
/*!40000 ALTER TABLE `puntuacion` DISABLE KEYS */;
INSERT INTO `puntuacion` VALUES (1,2,'2023-09-16 18:49:36.328000'),(2,2,'2023-09-16 18:49:36.328000'),(3,5,'2023-09-16 18:51:50.306000'),(4,5,'2023-09-16 18:51:50.306000'),(5,10,'2023-09-20 09:54:34.500000'),(6,10,'2023-09-20 09:54:34.500000'),(7,14,'2023-09-20 10:16:43.950000'),(8,14,'2023-09-20 10:16:43.950000'),(9,4,'2023-09-20 10:19:13.140000'),(10,4,'2023-09-20 10:19:13.140000'),(11,6,'2023-09-20 10:20:59.907000'),(12,6,'2023-09-20 10:20:59.907000'),(13,8,'2023-09-29 20:25:04.127000'),(14,8,'2023-09-29 20:25:04.127000');
/*!40000 ALTER TABLE `puntuacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporte_error`
--

DROP TABLE IF EXISTS `reporte_error`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte_error` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion_reporte_error` varchar(255) DEFAULT NULL,
  `fecha_hora_alta_reporte_error` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_reporte_error` datetime(6) DEFAULT NULL,
  `id_cuenta_administrador_error` int DEFAULT NULL,
  `id_cuenta_informante_error` int DEFAULT NULL,
  `fk_error` bigint DEFAULT NULL,
  `fk_estado_reporte_error` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeroslap44vkgu47x84o5fhnbk` (`fk_error`),
  KEY `FK6m4puj40gycji1nrhfs13lpw9` (`fk_estado_reporte_error`),
  CONSTRAINT `FK6m4puj40gycji1nrhfs13lpw9` FOREIGN KEY (`fk_estado_reporte_error`) REFERENCES `estado_reporte_error` (`id`),
  CONSTRAINT `FKeroslap44vkgu47x84o5fhnbk` FOREIGN KEY (`fk_error`) REFERENCES `error` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte_error`
--

LOCK TABLES `reporte_error` WRITE;
/*!40000 ALTER TABLE `reporte_error` DISABLE KEYS */;
/*!40000 ALTER TABLE `reporte_error` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporte_motivo`
--

DROP TABLE IF EXISTS `reporte_motivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte_motivo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion_reporte_motivo` varchar(255) DEFAULT NULL,
  `fecha_hora_alta_reporte_motivo` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_reporte_motivo` datetime(6) DEFAULT NULL,
  `id_cuenta_informante_motivo` bigint DEFAULT NULL,
  `id_cuenta_reportada` bigint DEFAULT NULL,
  `fk_motivo` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmthbxcynxq3qxn5o1t3axvrlw` (`fk_motivo`),
  CONSTRAINT `FKmthbxcynxq3qxn5o1t3axvrlw` FOREIGN KEY (`fk_motivo`) REFERENCES `motivo` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte_motivo`
--

LOCK TABLES `reporte_motivo` WRITE;
/*!40000 ALTER TABLE `reporte_motivo` DISABLE KEYS */;
INSERT INTO `reporte_motivo` VALUES (1,'asdas','2023-10-06 12:37:05.570000',NULL,1,3,2),(2,'ss','2023-10-06 12:37:26.889000',NULL,1,4,2),(3,'s','2023-10-06 12:37:32.583000',NULL,1,4,1);
/*!40000 ALTER TABLE `reporte_motivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reunion_virtual`
--

DROP TABLE IF EXISTS `reunion_virtual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reunion_virtual` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `duracion_reunion_virtual` double DEFAULT NULL,
  `fecha_hora_alta_reunion_virtual` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_reunion_virtual` datetime(6) DEFAULT NULL,
  `link_reunion_virtual` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reunion_virtual`
--

LOCK TABLES `reunion_virtual` WRITE;
/*!40000 ALTER TABLE `reunion_virtual` DISABLE KEYS */;
INSERT INTO `reunion_virtual` VALUES (1,0.2326,'2023-09-16 18:49:00.042000','2023-09-16 18:49:35.134000','224a9404-e50e-477a-8efd-5bf79eb990e9'),(2,0.4862,'2023-09-16 18:51:00.754000','2023-09-16 18:51:49.045000','5f15f822-7ded-4b31-afaa-db6bcd55de8b'),(3,1.0286333333333333,'2023-09-20 09:53:02.278000','2023-09-20 09:54:33.409000','fe041ba8-8272-4ca6-9b87-33c3b7d5f009'),(4,1.3870833333333332,'2023-09-20 10:15:02.419000','2023-09-20 10:16:42.896000','4a7624b3-5e16-4392-9753-d6881c16b81c'),(5,0.4195833333333333,'2023-09-20 10:18:15.414000','2023-09-20 10:19:12.080000','2ed0f17b-a11b-4e3d-bdbe-a25a58d464b3'),(6,0.6242,'2023-09-20 10:19:54.391000','2023-09-20 10:20:58.731000','9e563d12-216e-453b-b27e-c25df50fe8ff'),(7,0.7922,'2023-09-29 20:21:45.235000','2023-09-29 20:25:02.794000','dcddd69d-58b2-437d-ac8f-bf25b97095e2'),(8,0,'2023-09-30 19:32:25.067000','2023-09-29 20:25:02.794000','c98e4e86-da17-4660-82ab-65a90cdb3b04'),(9,0,'2023-09-30 19:32:36.772000','2023-09-29 20:25:02.794000','4536e91d-d046-4e68-aca7-15c6d336d956'),(10,0,'2023-09-30 19:32:49.279000','2023-09-29 20:25:02.794000','71d562d8-36cf-4dec-bfd3-f2855bd37eb7'),(11,0,'2023-09-30 19:32:59.645000','2023-09-29 20:25:02.794000','eea50456-b1c6-4f23-a300-a02c3c33058c'),(12,0,'2023-09-30 19:33:36.089000','2023-09-29 20:25:02.794000','fc3efb75-e372-4d5c-aecd-f2b8e9de7486'),(13,0,'2023-09-30 19:33:44.407000','2023-09-29 20:25:02.794000','c3150d50-8331-44ce-8ef8-c38d321f40ab'),(14,0,'2023-09-30 19:35:36.107000','2023-09-29 20:25:02.794000','a3a1c685-4b16-4e64-9acf-a61e6d3f0a3c'),(15,0,'2023-09-30 19:35:45.867000','2023-09-29 20:25:02.794000','7b046d19-ac1d-4ef0-8e20-e44603b4c219'),(16,0,'2023-09-30 19:40:29.504000','2023-09-29 20:25:02.794000','33171e26-6e0c-4bbe-8a60-ae523ca9bec3'),(17,0,'2023-09-30 19:40:55.564000','2023-09-29 20:25:02.794000','8f111cb9-896a-4374-b97b-bc44ff6dad19'),(18,0,'2023-09-30 19:50:30.047000',NULL,'273619d3-575a-44ac-ba51-7920baf8744f'),(19,0,'2023-09-30 19:50:39.596000',NULL,'6198f1ce-b135-4fda-8c0c-69d2e787a866'),(20,0,'2023-09-30 19:50:48.363000',NULL,'c5747550-997a-4a9c-bec5-13b6c6293df4'),(21,0,'2023-09-30 19:51:00.809000',NULL,'a695dcff-165c-4df9-a2a4-a790988b88d1'),(22,0,'2023-09-30 19:51:09.175000',NULL,'b9f25273-7f7e-4599-9320-f2490fe539ad'),(23,0,'2023-09-30 19:51:17.710000',NULL,'f5404f29-4977-4967-b835-b65c90e26188'),(24,0,'2023-09-30 19:51:25.141000',NULL,'05f95465-48f4-4949-913a-ee8fbf728687'),(25,0,'2023-09-30 19:51:32.817000',NULL,'cc40d7a1-9d5f-4b16-a165-099f46927a6f'),(26,0,'2023-09-30 19:51:41.216000',NULL,'bd69d9d9-fab9-40ac-93b9-7a5fdc877617');
/*!40000 ALTER TABLE `reunion_virtual` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reunion_virtual_lista_detalle_participantes`
--

DROP TABLE IF EXISTS `reunion_virtual_lista_detalle_participantes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reunion_virtual_lista_detalle_participantes` (
  `reunion_virtual_id` bigint NOT NULL,
  `lista_detalle_participantes_id` bigint NOT NULL,
  UNIQUE KEY `UK_tjcl4x63ln2opb5vdka3w8b8g` (`lista_detalle_participantes_id`),
  KEY `FKgc184p8b8p492atw9gtltyxad` (`reunion_virtual_id`),
  CONSTRAINT `FKgc184p8b8p492atw9gtltyxad` FOREIGN KEY (`reunion_virtual_id`) REFERENCES `reunion_virtual` (`id`),
  CONSTRAINT `FKgwu4q3fg0h92a3x8y5pp0dda4` FOREIGN KEY (`lista_detalle_participantes_id`) REFERENCES `detalle_participante` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reunion_virtual_lista_detalle_participantes`
--

LOCK TABLES `reunion_virtual_lista_detalle_participantes` WRITE;
/*!40000 ALTER TABLE `reunion_virtual_lista_detalle_participantes` DISABLE KEYS */;
INSERT INTO `reunion_virtual_lista_detalle_participantes` VALUES (1,1),(1,2),(2,3),(2,4),(3,5),(3,6),(4,7),(4,8),(5,9),(5,10),(6,11),(6,12),(7,13),(7,14),(8,15),(9,16),(10,17),(11,18),(12,19),(13,20),(14,21),(15,22),(16,23),(17,24),(18,25),(19,26),(20,27),(21,28),(22,29),(23,30),(24,31),(25,32),(26,33);
/*!40000 ALTER TABLE `reunion_virtual_lista_detalle_participantes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_rol` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_rol` datetime(6) DEFAULT NULL,
  `nombre_rol` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'2023-06-23 00:00:00.000000',NULL,'usuario'),(2,'2023-06-23 00:00:00.000000',NULL,'admin'),(3,'2023-08-13 11:28:37.351000','2023-09-14 20:40:38.829000','soporte'),(9,'2023-09-08 18:40:14.047000','2023-09-08 18:48:36.471000','testerQA');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seguidores`
--

DROP TABLE IF EXISTS `seguidores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seguidores` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_seguidor` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_seguidor` datetime(6) DEFAULT NULL,
  `id_cuenta` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seguidores`
--

LOCK TABLES `seguidores` WRITE;
/*!40000 ALTER TABLE `seguidores` DISABLE KEYS */;
/*!40000 ALTER TABLE `seguidores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seguidos`
--

DROP TABLE IF EXISTS `seguidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seguidos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_seguido` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_seguido` datetime(6) DEFAULT NULL,
  `id_cuenta` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seguidos`
--

LOCK TABLES `seguidos` WRITE;
/*!40000 ALTER TABLE `seguidos` DISABLE KEYS */;
/*!40000 ALTER TABLE `seguidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipomotivo`
--

DROP TABLE IF EXISTS `tipomotivo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipomotivo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_tipo_motivo` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_tipo_motivo` datetime(6) DEFAULT NULL,
  `nombre_tipo_motivo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipomotivo`
--

LOCK TABLES `tipomotivo` WRITE;
/*!40000 ALTER TABLE `tipomotivo` DISABLE KEYS */;
INSERT INTO `tipomotivo` VALUES (1,'2023-06-23 00:00:00.000000',NULL,'usuario'),(2,'2023-06-23 00:00:00.000000',NULL,'plataforma');
/*!40000 ALTER TABLE `tipomotivo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `apellido_usuario` varchar(50) NOT NULL,
  `descripcion_usuario` varchar(200) DEFAULT NULL,
  `fecha_nacimiento` datetime(6) DEFAULT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `fk_cuenta` bigint DEFAULT NULL,
  `fk_idioma_aprendiz` bigint DEFAULT NULL,
  `fk_idioma_nativo` bigint DEFAULT NULL,
  `fk_pais` bigint DEFAULT NULL,
  `fk_usuario_puntuacion` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK89x0qgjnrc17n3sf585ktsc4y` (`fk_cuenta`),
  KEY `FKo55l7gwqf11k482w10xq7ux51` (`fk_idioma_aprendiz`),
  KEY `FKj37v8jvpmw7duvqbas2ucrp64` (`fk_idioma_nativo`),
  KEY `FKku9l80755h13smfh35g762weg` (`fk_pais`),
  KEY `FKem8gmlsejyphy3kvutoluiaw0` (`fk_usuario_puntuacion`),
  CONSTRAINT `FK89x0qgjnrc17n3sf585ktsc4y` FOREIGN KEY (`fk_cuenta`) REFERENCES `cuenta` (`id`),
  CONSTRAINT `FKem8gmlsejyphy3kvutoluiaw0` FOREIGN KEY (`fk_usuario_puntuacion`) REFERENCES `usuario_puntuacion` (`id`),
  CONSTRAINT `FKj37v8jvpmw7duvqbas2ucrp64` FOREIGN KEY (`fk_idioma_nativo`) REFERENCES `usuario_idioma` (`id`),
  CONSTRAINT `FKku9l80755h13smfh35g762weg` FOREIGN KEY (`fk_pais`) REFERENCES `pais` (`id`),
  CONSTRAINT `FKo55l7gwqf11k482w10xq7ux51` FOREIGN KEY (`fk_idioma_aprendiz`) REFERENCES `usuario_idioma` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Leal','Soy una persona apasionada por la tecnología.','1993-03-16 00:00:00.000000','Iván',1,7,8,9,1),(2,'Cortés','I love this platform','2000-02-15 00:00:00.000000','Natalia',2,9,10,9,2),(3,'Musaber','Soy un interesado en la tecnología.','2000-07-23 00:00:00.000000','Pablo',3,11,12,9,3),(4,'Herrera','Me gusta el arte..','1998-05-13 00:00:00.000000','Francoo',4,13,14,9,4),(5,'Riveros','Me gusta el futbol.','2000-09-21 00:00:00.000000','Sebastian',5,15,16,9,5),(6,'Lopez','','1997-06-18 00:00:00.000000','Juan',6,17,18,3,6),(7,'Doe','','1999-10-14 00:00:00.000000','Jhon',7,19,20,35,7),(8,'Rodriguez','','1985-06-13 00:00:00.000000','Luis',8,21,22,9,8),(9,'Gomez','','2005-09-07 00:00:00.000000','Maria',9,23,24,66,9),(10,'Martinez','','2002-06-14 00:00:00.000000','Laura',10,25,26,42,10),(11,'Almeida','','1994-06-17 00:00:00.000000','Andrea',11,27,28,38,11),(12,'Anderson','','2000-01-03 00:00:00.000000','Emily',12,29,30,9,12),(13,'Silva','','1996-05-21 00:00:00.000000','Joao',13,31,32,26,13),(14,'Mendoza','','1989-12-25 00:00:00.000000','Paula',14,33,34,117,14),(15,'Pena','','1995-04-13 00:00:00.000000','Ricardo',15,35,36,117,15),(16,'Rios','','2005-08-30 00:00:00.000000','Valentina',16,37,38,60,16),(17,'Mora','','1999-04-16 00:00:00.000000','Carolina',17,39,40,59,17),(18,'Wilson','','1990-10-18 00:00:00.000000','Lisa',18,41,42,38,18),(19,'Clark','','1998-07-07 00:00:00.000000','Samanta',19,43,44,142,19);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_calificacion`
--

DROP TABLE IF EXISTS `usuario_calificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_calificacion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_calificacion` datetime(6) DEFAULT NULL,
  `id_reunion_virtual` bigint DEFAULT NULL,
  `id_usuario_calificador` bigint DEFAULT NULL,
  `fk_calificacion` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKopyh1qys3f9qoupoc94c7vjcl` (`fk_calificacion`),
  CONSTRAINT `FKopyh1qys3f9qoupoc94c7vjcl` FOREIGN KEY (`fk_calificacion`) REFERENCES `calificacion` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_calificacion`
--

LOCK TABLES `usuario_calificacion` WRITE;
/*!40000 ALTER TABLE `usuario_calificacion` DISABLE KEYS */;
INSERT INTO `usuario_calificacion` VALUES (1,'2023-09-20 09:54:54.535000',3,5,5),(2,'2023-09-20 09:54:56.769000',3,1,5),(3,'2023-09-20 10:19:36.708000',5,1,2),(4,'2023-09-20 10:19:38.660000',5,5,5),(5,'2023-09-20 10:21:23.907000',6,5,3),(6,'2023-09-20 10:21:25.938000',6,1,3),(7,'2023-09-29 20:25:22.232000',7,5,5),(8,'2023-09-29 20:25:24.663000',7,4,5);
/*!40000 ALTER TABLE `usuario_calificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_idioma`
--

DROP TABLE IF EXISTS `usuario_idioma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_idioma` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fk_idioma` bigint DEFAULT NULL,
  `fk_nivel_idioma` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdcjvcc8e3sild76uaa4ivtmw6` (`fk_idioma`),
  KEY `FKsfaofti1tyws4drd8ribqw9ct` (`fk_nivel_idioma`),
  CONSTRAINT `FKdcjvcc8e3sild76uaa4ivtmw6` FOREIGN KEY (`fk_idioma`) REFERENCES `idioma` (`id`),
  CONSTRAINT `FKsfaofti1tyws4drd8ribqw9ct` FOREIGN KEY (`fk_nivel_idioma`) REFERENCES `nivel_idioma` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_idioma`
--

LOCK TABLES `usuario_idioma` WRITE;
/*!40000 ALTER TABLE `usuario_idioma` DISABLE KEYS */;
INSERT INTO `usuario_idioma` VALUES (1,1,3),(2,2,2),(3,1,3),(4,2,2),(5,1,3),(6,2,2),(7,2,4),(8,1,2),(9,1,3),(10,2,2),(11,2,3),(12,1,2),(13,2,3),(14,1,2),(15,2,3),(16,1,2),(17,2,3),(18,6,2),(19,1,1),(20,3,2),(21,2,4),(22,1,2),(23,1,1),(24,3,2),(25,3,3),(26,1,2),(27,2,1),(28,1,2),(29,2,3),(30,1,2),(31,2,3),(32,4,2),(33,2,1),(34,1,2),(35,2,4),(36,1,2),(37,4,3),(38,2,2),(39,5,1),(40,1,2),(41,2,3),(42,1,2),(43,1,4),(44,2,2);
/*!40000 ALTER TABLE `usuario_idioma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_interes`
--

DROP TABLE IF EXISTS `usuario_interes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_interes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_hora_alta_usuario_interes` datetime(6) DEFAULT NULL,
  `fecha_hora_fin_vigencia_interes` datetime(6) DEFAULT NULL,
  `fk_interes` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK92rm0nrqcf5r4gxnb87xp8d3i` (`fk_interes`),
  CONSTRAINT `FK92rm0nrqcf5r4gxnb87xp8d3i` FOREIGN KEY (`fk_interes`) REFERENCES `interes` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_interes`
--

LOCK TABLES `usuario_interes` WRITE;
/*!40000 ALTER TABLE `usuario_interes` DISABLE KEYS */;
INSERT INTO `usuario_interes` VALUES (1,'2023-07-14 01:02:36.417000',NULL,2),(2,'2023-07-14 01:02:36.610000',NULL,5),(3,'2023-07-14 01:04:03.108000',NULL,2),(4,'2023-07-14 01:04:39.658000',NULL,2),(5,'2023-08-22 19:38:19.136000','2023-09-16 09:49:27.352000',2),(6,'2023-08-22 19:50:58.565000','2023-09-30 19:39:41.097000',2),(7,'2023-08-22 19:50:58.568000','2023-09-30 19:39:41.097000',1),(8,'2023-08-22 19:50:58.600000','2023-09-30 19:39:41.097000',3),(9,'2023-08-22 19:55:59.303000','2023-09-30 19:38:24.066000',4),(10,'2023-08-22 19:55:59.305000','2023-09-30 19:38:24.066000',5),(11,'2023-08-22 19:55:59.306000','2023-09-30 19:38:24.066000',6),(12,'2023-08-22 20:06:13.865000','2023-09-20 10:11:51.946000',6),(13,'2023-08-22 20:06:13.869000','2023-09-20 10:11:51.946000',2),(14,'2023-08-22 20:06:13.873000','2023-09-20 10:11:51.946000',1),(15,'2023-08-22 20:06:13.874000','2023-09-20 10:11:51.946000',5),(16,'2023-08-22 20:09:21.972000','2023-09-30 19:38:27.785000',5),(17,'2023-08-22 20:09:21.974000','2023-09-30 19:38:27.785000',1),(18,'2023-08-22 20:09:21.975000','2023-09-30 19:38:27.785000',4),(19,'2023-09-14 22:16:43.324000','2023-09-16 09:49:27.352000',12),(20,'2023-09-14 22:16:43.506000','2023-09-16 09:49:27.352000',5),(21,'2023-09-14 22:16:43.687000','2023-09-16 09:49:27.352000',4),(22,'2023-09-16 09:19:04.759000','2023-09-16 09:49:27.352000',12),(23,'2023-09-16 09:19:04.939000','2023-09-16 09:49:27.352000',5),(24,'2023-09-16 09:19:05.147000','2023-09-16 09:49:27.352000',4),(25,'2023-09-16 09:19:34.746000','2023-09-16 09:49:27.352000',12),(26,'2023-09-16 09:19:34.929000','2023-09-16 09:49:27.352000',5),(27,'2023-09-16 09:19:35.109000','2023-09-16 09:49:27.352000',4),(28,'2023-09-16 09:23:37.967000','2023-09-20 10:11:51.946000',2),(29,'2023-09-16 09:27:55.448000','2023-09-20 10:11:51.946000',2),(30,'2023-09-16 09:28:26.725000','2023-09-20 10:11:51.946000',2),(31,'2023-09-16 09:49:27.535000',NULL,12),(32,'2023-09-16 09:49:27.719000',NULL,5),(33,'2023-09-16 09:49:27.900000',NULL,4),(34,'2023-09-18 11:56:45.326000','2023-09-30 19:38:24.066000',4),(35,'2023-09-18 11:56:45.501000','2023-09-30 19:38:24.066000',5),(36,'2023-09-18 11:56:45.676000','2023-09-30 19:38:24.066000',6),(37,'2023-09-20 10:11:52.125000',NULL,2),(38,'2023-09-29 20:23:29.192000','2023-09-30 19:38:27.785000',6),(39,'2023-09-29 20:23:29.386000','2023-09-30 19:38:27.785000',5),(40,'2023-09-29 20:23:29.569000','2023-09-30 19:38:27.785000',3),(41,'2023-09-29 20:38:48.628000',NULL,8),(42,'2023-09-29 20:38:48.812000',NULL,5),(43,'2023-09-29 20:44:06.095000',NULL,5),(44,'2023-09-29 20:44:06.278000',NULL,8),(45,'2023-09-29 20:47:25.123000',NULL,3),(46,'2023-09-29 20:47:25.307000',NULL,4),(47,'2023-09-29 20:50:16.754000',NULL,4),(48,'2023-09-29 20:50:16.935000',NULL,6),(49,'2023-09-29 20:52:41.605000',NULL,4),(50,'2023-09-29 20:52:41.787000',NULL,3),(51,'2023-09-29 20:55:02.102000','2023-09-30 19:46:29.574000',12),(52,'2023-09-29 20:55:02.283000','2023-09-30 19:46:29.574000',9),(53,'2023-09-29 20:56:22.350000',NULL,2),(54,'2023-09-29 20:58:39.024000',NULL,6),(55,'2023-09-29 20:58:39.206000',NULL,5),(56,'2023-09-29 21:00:15.051000','2023-09-30 19:44:56.347000',4),(57,'2023-09-29 21:00:15.242000','2023-09-30 19:44:56.347000',3),(58,'2023-09-29 21:02:13.205000',NULL,12),(59,'2023-09-29 21:02:13.784000',NULL,6),(60,'2023-09-29 21:06:29.807000',NULL,5),(61,'2023-09-29 21:06:29.995000',NULL,3),(62,'2023-09-29 21:07:50.749000',NULL,4),(63,'2023-09-29 21:07:50.929000',NULL,3),(64,'2023-09-29 21:09:07.986000',NULL,3),(65,'2023-09-29 21:11:08.359000',NULL,12),(66,'2023-09-29 21:11:08.554000',NULL,6),(67,'2023-09-30 19:34:39.082000','2023-09-30 19:38:24.066000',4),(68,'2023-09-30 19:34:39.260000','2023-09-30 19:38:24.066000',5),(69,'2023-09-30 19:34:39.438000','2023-09-30 19:38:24.066000',6),(70,'2023-09-30 19:36:46.240000','2023-09-30 19:38:27.785000',6),(71,'2023-09-30 19:36:46.413000','2023-09-30 19:38:27.785000',5),(72,'2023-09-30 19:36:46.586000','2023-09-30 19:38:27.785000',3),(73,'2023-09-30 19:38:24.239000',NULL,4),(74,'2023-09-30 19:38:24.415000',NULL,5),(75,'2023-09-30 19:38:24.588000',NULL,6),(76,'2023-09-30 19:38:27.955000',NULL,6),(77,'2023-09-30 19:38:28.124000',NULL,5),(78,'2023-09-30 19:38:28.294000',NULL,3),(79,'2023-09-30 19:39:41.270000',NULL,2),(80,'2023-09-30 19:39:41.442000',NULL,3),(81,'2023-09-30 19:42:25.955000','2023-09-30 19:44:56.347000',4),(82,'2023-09-30 19:42:26.128000','2023-09-30 19:44:56.347000',3),(83,'2023-09-30 19:43:11.849000','2023-09-30 19:44:56.347000',4),(84,'2023-09-30 19:43:12.020000','2023-09-30 19:44:56.347000',3),(85,'2023-09-30 19:44:10.857000','2023-09-30 19:44:56.347000',4),(86,'2023-09-30 19:44:11.031000','2023-09-30 19:44:56.347000',3),(87,'2023-09-30 19:44:56.523000',NULL,4),(88,'2023-09-30 19:44:56.701000',NULL,3),(89,'2023-09-30 19:46:29.753000',NULL,12),(90,'2023-09-30 19:46:29.929000',NULL,9);
/*!40000 ALTER TABLE `usuario_interes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_lista_intereses`
--

DROP TABLE IF EXISTS `usuario_lista_intereses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_lista_intereses` (
  `usuario_id` bigint NOT NULL,
  `lista_intereses_id` bigint NOT NULL,
  UNIQUE KEY `UK_9h3lpg7kc1ol12dm4audb1msf` (`lista_intereses_id`),
  KEY `FKp23c4nsxw7s8nfcsuaenckjm4` (`usuario_id`),
  CONSTRAINT `FKmphiljhr395dm0w8dai6tm8p0` FOREIGN KEY (`lista_intereses_id`) REFERENCES `usuario_interes` (`id`),
  CONSTRAINT `FKp23c4nsxw7s8nfcsuaenckjm4` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_lista_intereses`
--

LOCK TABLES `usuario_lista_intereses` WRITE;
/*!40000 ALTER TABLE `usuario_lista_intereses` DISABLE KEYS */;
INSERT INTO `usuario_lista_intereses` VALUES (1,5),(1,19),(1,20),(1,21),(1,22),(1,23),(1,24),(1,25),(1,26),(1,27),(1,31),(1,32),(1,33),(2,6),(2,7),(2,8),(2,79),(2,80),(3,9),(3,10),(3,11),(3,34),(3,35),(3,36),(3,67),(3,68),(3,69),(3,73),(3,74),(3,75),(4,12),(4,13),(4,14),(4,15),(4,28),(4,29),(4,30),(4,37),(5,16),(5,17),(5,18),(5,38),(5,39),(5,40),(5,70),(5,71),(5,72),(5,76),(5,77),(5,78),(6,41),(6,42),(7,43),(7,44),(8,45),(8,46),(9,47),(9,48),(10,49),(10,50),(11,51),(11,52),(11,89),(11,90),(12,53),(13,54),(13,55),(14,56),(14,57),(14,81),(14,82),(14,83),(14,84),(14,85),(14,86),(14,87),(14,88),(15,58),(15,59),(16,60),(16,61),(17,62),(17,63),(18,64),(19,65),(19,66);
/*!40000 ALTER TABLE `usuario_lista_intereses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_lista_usuario_calificacion`
--

DROP TABLE IF EXISTS `usuario_lista_usuario_calificacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_lista_usuario_calificacion` (
  `usuario_id` bigint NOT NULL,
  `lista_usuario_calificacion_id` bigint NOT NULL,
  UNIQUE KEY `UK_3pmk2oryt0nsglfq2gmdl4ne9` (`lista_usuario_calificacion_id`),
  KEY `FK9q95t46xvpk5hko9wahq1fxbn` (`usuario_id`),
  CONSTRAINT `FK9q95t46xvpk5hko9wahq1fxbn` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FKbbi6oiwawu8ndxk3k79t820mu` FOREIGN KEY (`lista_usuario_calificacion_id`) REFERENCES `usuario_calificacion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_lista_usuario_calificacion`
--

LOCK TABLES `usuario_lista_usuario_calificacion` WRITE;
/*!40000 ALTER TABLE `usuario_lista_usuario_calificacion` DISABLE KEYS */;
INSERT INTO `usuario_lista_usuario_calificacion` VALUES (1,1),(1,4),(1,5),(4,7),(5,2),(5,3),(5,6),(5,8);
/*!40000 ALTER TABLE `usuario_lista_usuario_calificacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_lista_usuario_logro`
--

DROP TABLE IF EXISTS `usuario_lista_usuario_logro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_lista_usuario_logro` (
  `usuario_id` bigint NOT NULL,
  `lista_usuario_logro_id` bigint NOT NULL,
  UNIQUE KEY `UK_8qy42rys8cnggapv9psb3i3v4` (`lista_usuario_logro_id`),
  KEY `FK20u25ema0i26mvs8547lus8me` (`usuario_id`),
  CONSTRAINT `FK20u25ema0i26mvs8547lus8me` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `FK5m7xjbagtufvrfoi2v8jvnupr` FOREIGN KEY (`lista_usuario_logro_id`) REFERENCES `usuario_logro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_lista_usuario_logro`
--

LOCK TABLES `usuario_lista_usuario_logro` WRITE;
/*!40000 ALTER TABLE `usuario_lista_usuario_logro` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario_lista_usuario_logro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_logro`
--

DROP TABLE IF EXISTS `usuario_logro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_logro` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_logro_conseguido` datetime(6) DEFAULT NULL,
  `fk_logro` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK65umbavvkdp08hlr2n8tqnqwe` (`fk_logro`),
  CONSTRAINT `FK65umbavvkdp08hlr2n8tqnqwe` FOREIGN KEY (`fk_logro`) REFERENCES `logro` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_logro`
--

LOCK TABLES `usuario_logro` WRITE;
/*!40000 ALTER TABLE `usuario_logro` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario_logro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_puntuacion`
--

DROP TABLE IF EXISTS `usuario_puntuacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_puntuacion` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `puntos_totales` int DEFAULT NULL,
  `fk_liga` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK87b3t0hkcalrlo5ote073aki5` (`fk_liga`),
  CONSTRAINT `FK87b3t0hkcalrlo5ote073aki5` FOREIGN KEY (`fk_liga`) REFERENCES `liga` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_puntuacion`
--

LOCK TABLES `usuario_puntuacion` WRITE;
/*!40000 ALTER TABLE `usuario_puntuacion` DISABLE KEYS */;
INSERT INTO `usuario_puntuacion` VALUES (1,41,NULL),(2,0,NULL),(3,0,NULL),(4,15,NULL),(5,42,NULL),(6,0,NULL),(7,0,NULL),(8,0,NULL),(9,0,NULL),(10,0,NULL),(11,0,NULL),(12,0,NULL),(13,0,NULL),(14,0,NULL),(15,0,NULL),(16,0,NULL),(17,0,NULL),(18,0,NULL),(19,0,NULL);
/*!40000 ALTER TABLE `usuario_puntuacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_puntuacion_lista_puntuacion`
--

DROP TABLE IF EXISTS `usuario_puntuacion_lista_puntuacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_puntuacion_lista_puntuacion` (
  `usuario_puntuacion_id` bigint NOT NULL,
  `lista_puntuacion_id` bigint NOT NULL,
  UNIQUE KEY `UK_81fyfsbd3rmu6j3irf823npfh` (`lista_puntuacion_id`),
  KEY `FK6dbi7tlehpicg8pj4hxqut1mu` (`usuario_puntuacion_id`),
  CONSTRAINT `FK6dbi7tlehpicg8pj4hxqut1mu` FOREIGN KEY (`usuario_puntuacion_id`) REFERENCES `usuario_puntuacion` (`id`),
  CONSTRAINT `FKdn0pmlq60p2c2hp84mj0hcp8p` FOREIGN KEY (`lista_puntuacion_id`) REFERENCES `puntuacion` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_puntuacion_lista_puntuacion`
--

LOCK TABLES `usuario_puntuacion_lista_puntuacion` WRITE;
/*!40000 ALTER TABLE `usuario_puntuacion_lista_puntuacion` DISABLE KEYS */;
INSERT INTO `usuario_puntuacion_lista_puntuacion` VALUES (1,2),(1,4),(1,6),(1,7),(1,9),(1,11),(4,1),(4,3),(4,13),(5,5),(5,8),(5,10),(5,12),(5,14);
/*!40000 ALTER TABLE `usuario_puntuacion_lista_puntuacion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-07 16:15:53
