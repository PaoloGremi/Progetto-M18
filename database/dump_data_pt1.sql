--
-- Dumping data for table `card_Type`
--

LOCK TABLES `card_Type` WRITE;
INSERT INTO `card_Type` VALUES (1,'Normal monster'),(2,'Effect monster'),(3,'Fusion monster'),(4,'Ritual monster'),(5,'Normal spell'),(6,'Continuous spell'),(7,'Equip spell'),(8,'Field spell'),(9,'Quick-Play spell'),(10,'Ritual spell'),(11,'Normal trap'),(12,'Continuous trap'),(13,'Counter trap'),(14,'Synchro monster');
UNLOCK TABLES;

--
-- Dumping data for table `cards`
--

LOCK TABLES `cards` WRITE;
INSERT INTO `cards` VALUES (1,'USER-1',23,'pokemon'),(2,'USER-1',54,'pokemon'),(3,'USER-1',12,'yugioh'),(4,'USER-2',15,'pokemon'),(5,'USER-1',12,'yugioh'),(6,'USER-2',38,'pokemon'),(7,'USER-3',18,'pokemon'),(8,'USER-2',72,'pokemon'),(9,'USER-2',100,'pokemon'),(10,'USER-2',4,'yugioh'),(11,'USER-2',22,'yugioh'),(12,'USER-2',14,'yugioh'),(13,'USER-2',20,'yugioh'),(14,'USER-1',86,'pokemon'),(15,'USER-2',99,'pokemon'),(16,'USER-1',6,'yugioh'),(17,'USER-1',24,'yugioh'),(18,'USER-1',18,'pokemon'),(19,'USER-3',5,'pokemon'),(20,'USER-2',45,'pokemon'),(21,'USER-3',68,'pokemon'),(22,'USER-3',6,'yugioh'),(23,'USER-3',85,'pokemon'),(24,'USER-3',4,'yugioh'),(25,'USER-2',39,'yugioh'),(26,'USER-3',62,'pokemon'),(27,'USER-4',94,'pokemon'),(28,'USER-4',67,'pokemon'),(29,'USER-4',20,'pokemon'),(30,'USER-4',33,'yugioh'),(31,'USER-4',30,'pokemon'),(32,'USER-4',52,'pokemon'),(33,'USER-4',27,'yugioh'),(34,'USER-4',30,'yugioh'),(35,'USER-5',23,'pokemon'),(36,'USER-5',67,'pokemon'),(37,'USER-5',9,'yugioh'),(38,'USER-5',10,'yugioh'),(39,'USER-5',35,'pokemon'),(40,'USER-5',47,'pokemon'),(41,'USER-5',2,'yugioh'),(42,'USER-5',18,'pokemon'),(43,'USER-6',45,'pokemon'),(44,'USER-6',42,'pokemon'),(45,'USER-6',13,'yugioh'),(46,'USER-6',24,'yugioh'),(47,'USER-6',83,'pokemon'),(48,'USER-6',5,'pokemon'),(49,'USER-6',29,'yugioh'),(50,'USER-6',82,'pokemon'),(51,'USER-7',10,'pokemon'),(52,'USER-7',55,'pokemon'),(53,'USER-7',20,'pokemon'),(54,'USER-7',55,'pokemon'),(55,'USER-7',80,'pokemon'),(56,'USER-7',28,'pokemon'),(57,'USER-7',9,'pokemon'),(58,'USER-7',89,'pokemon');
UNLOCK TABLES;

--
-- Dumping data for table `cards_active`
--

LOCK TABLES `cards_active` WRITE;
INSERT INTO `cards_active` VALUES (1,3,2),(1,5,2),(1,9,1),(1,15,1);
UNLOCK TABLES;

--
-- Dumping data for table `cards_old`
--

LOCK TABLES `cards_old` WRITE;
UNLOCK TABLES;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
INSERT INTO `customers` VALUES ('USER-1','TeoGore','G4NG777Yeah'),('USER-2','CiccioGamer89','Il0veNutell4'),('USER-3','FedeDG','Th3Or!g!n4l'),('USER-4','ArmaMar','Marcolino1!'),('USER-5','LostBoi','D3pr3ss3d43v4h!'),('USER-6','GalloRob','G4ll0R0b!'),('USER-7','Alisaki','S0R0m4n4?');
UNLOCK TABLES;

--
-- Dumping data for table `monster_Type`
--

LOCK TABLES `monster_Type` WRITE;
INSERT INTO `monster_Type` VALUES (1,'Aqua'),(2,'Beast'),(3,'Beast-Warrior'),(4,'Dinosaur'),(5,'Dragon'),(6,'Fairy'),(7,'Fiend'),(8,'Fish'),(9,'Insect'),(10,'Machine'),(11,'Plant'),(12,'Pyro'),(13,'Reptile'),(14,'Rock'),(15,'Sea Serpent'),(16,'Spellcaster'),(17,'Thunder'),(18,'Warrior'),(19,'Winged Beast'),(20,'Zombie'),(21,'Psychic');
UNLOCK TABLES;

--
-- Dumping data for table `trades`
--

LOCK TABLES `trades` WRITE;
INSERT INTO `trades` VALUES (1,'2018-07-21','USER-2','USER-1',0,0);
UNLOCK TABLES;

--
-- Dumping data for table `wishlist`
--

LOCK TABLES `wishlist` WRITE;
INSERT INTO `wishlist` VALUES ('USER-1',1,'pokemon'),('USER-1',1,'yugioh'),('USER-2',12,'yugioh'),('USER-3',11,'yugioh'),('USER-3',17,'pokemon'),('USER-3',23,'yugioh'),('USER-4',34,'pokemon'),('USER-4',34,'yugioh'),('USER-4',65,'pokemon'),('USER-5',1,'yugioh'),('USER-5',22,'pokemon'),('USER-5',36,'yugioh'),('USER-5',43,'pokemon'),('USER-6',18,'yugioh'),('USER-6',34,'yugioh'),('USER-6',87,'pokemon'),('USER-6',99,'pokemon');
UNLOCK TABLES;
