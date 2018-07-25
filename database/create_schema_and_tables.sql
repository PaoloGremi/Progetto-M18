DROP DATABASE  IF  EXISTS `CARDS`;
CREATE DATABASE  IF NOT EXISTS `CARDS`;
USE `CARDS`;
--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `customer_id` varchar(120) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
--
-- Table structure for table `trades`
--

DROP TABLE IF EXISTS `trades`;
CREATE TABLE `trades` (
  `trade_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `user1_id` varchar(120) NOT NULL,
  `user2_id` varchar(120) NOT NULL,
  `donedeal` tinyint(4) NOT NULL,
  `positive_end` tinyint(4) NOT NULL,
  PRIMARY KEY (`trade_id`),
  KEY `customer_id_idx` (`user1_id`,`user2_id`),
  KEY `trades_ibfk_2` (`user2_id`),
  CONSTRAINT `trades_ibfk_1` FOREIGN KEY (`user1_id`) REFERENCES `customers` (`customer_id`),
  CONSTRAINT `trades_ibfk_2` FOREIGN KEY (`user2_id`) REFERENCES `customers` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `card_Type`
--
DROP TABLE IF EXISTS `card_Type`;
CREATE TABLE `card_Type` (
  `Type_ID` int(11) NOT NULL,
  `Name` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`Type_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `cards`
--

DROP TABLE IF EXISTS `cards`;
CREATE TABLE `cards` (
  `card_id` int(11) NOT NULL,
  `customer_id` varchar(120) NOT NULL,
  `description_id` int(11) NOT NULL,
  `card_Type` varchar(45) NOT NULL,
  PRIMARY KEY (`card_id`),
  KEY `customer_id_idx` (`customer_id`),
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `cards_active`
--

DROP TABLE IF EXISTS `cards_active`;
CREATE TABLE `cards_active` (
  `trade_id` int(11) NOT NULL,
  `card_id` int(11) NOT NULL,
  `offer_col` int(11) NOT NULL,
  PRIMARY KEY (`trade_id`,`card_id`),
  KEY `card_id_idx` (`card_id`),
  CONSTRAINT `card_ibfk_1` FOREIGN KEY (`card_id`) REFERENCES `cards` (`card_id`),
  CONSTRAINT `trade_ibfk_2` FOREIGN KEY (`trade_id`) REFERENCES `trades` (`trade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `cards_old`
--

DROP TABLE IF EXISTS `cards_old`;
CREATE TABLE `cards_old` (
  `card_id` int(11) NOT NULL,
  `customer_id` varchar(120) NOT NULL,
  `description_id` int(11) NOT NULL,
  `card_Type` varchar(45) NOT NULL,
  `trade_id` int(11) NOT NULL,
  `offer_col` int(11) NOT NULL,
  KEY `customer_id_idx` (`customer_id`),
  KEY `card_id_idx` (`card_id`),
  KEY `trade_id_idx` (`trade_id`),
  CONSTRAINT `cards_old_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`),
  CONSTRAINT `cards_old_ibfk_2` FOREIGN KEY (`card_id`) REFERENCES `cards` (`card_id`),
  CONSTRAINT `cards_old_ibfk_3` FOREIGN KEY (`trade_id`) REFERENCES `trades` (`trade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


--
-- Table structure for table `monster_Type`
--

DROP TABLE IF EXISTS `monster_Type`;
CREATE TABLE `monster_Type` (
  `Monster_Type_ID` int(11) NOT NULL,
  `Name` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`Monster_Type_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `pokemon_card`
--

DROP TABLE IF EXISTS `pokemon_card`;
CREATE TABLE `pokemon_card` (
  `pokemon_description_id` int(11) NOT NULL,
  `Name` varchar(120) DEFAULT NULL,
  `Type` varchar(120) DEFAULT NULL,
  `Hp` int(11) DEFAULT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `Length` varchar(120) DEFAULT NULL,
  `Weigth` int(11) DEFAULT NULL,
  `Level` int(11) DEFAULT NULL,
  `Picture` mediumblob,
  PRIMARY KEY (`pokemon_description_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
CREATE TABLE `wishlist` (
  `customer_id` varchar(120) NOT NULL,
  `description_id` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`customer_id`,`description_id`,`type`),
  CONSTRAINT `fk_wishlist_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Table structure for table `yugioh_card`
--

DROP TABLE IF EXISTS `yugioh_card`;
CREATE TABLE `yugioh_card` (
  `yugioh_description_id` int(11) NOT NULL,
  `Name` varchar(120) DEFAULT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  `Reference` varchar(20) DEFAULT NULL,
  `Level` tinyint(4) DEFAULT NULL,
  `Atk` int(11) DEFAULT NULL,
  `Def` int(11) DEFAULT NULL,
  `Monster_Type_ID` int(11) DEFAULT NULL,
  `Type_ID` int(11) DEFAULT NULL,
  `Picture` mediumblob,
  PRIMARY KEY (`yugioh_description_id`),
  KEY `fk_yugioh_card_1_idx` (`Monster_Type_ID`),
  KEY `fk_yugioh_card_2_idx` (`Type_ID`),
  CONSTRAINT `fk_yugioh_card_1` FOREIGN KEY (`Monster_Type_ID`) REFERENCES `monster_Type` (`monster_type_id`),
  CONSTRAINT `fk_yugioh_card_2` FOREIGN KEY (`Type_ID`) REFERENCES `card_Type` (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
