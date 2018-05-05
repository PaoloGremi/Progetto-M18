-- script per creazione tabelle per DB di carte Yugioh!

-- Potete lanciarlo con MYSQL workbrench

-- 3 tabelle create: Monster_Type,Card_Type,Yugioh_card

-- !!!NOTA BENE!!:lo script presuppone un DB già esistene denominato "YUGIOH",
-- 			se non esistente, fare query CREATE DATABASE YUGIOH

-- le parti non standard dei comandi è:
-- 		la clausola ENGINE=InnoDB che e' specifica di MySQL
--      senza la quale non e' possibile usare i comandi di
--      definizione dei vincoli di integrita'Â  referenziale
--      FOREIGN KEY

USE YUGIOH;
create table Monster_Type
( Monster_Type_ID int primary key,
  Name varchar(120)
);
-- ---------- SECOND TABLE ---------------
create table Card_Type
( Type_ID int primary key,
  Name varchar(120)
);
-- ---------- THIRD TABLE ---------------

--  foreign at the end
create table Yugioh_card
(
 Cards_ID int primary key,
 Name varchar(120),
 Description varchar(1000),
 Reference varchar(20),
 Level tinyint,
 Atk int,
 Def int,
 Monster_Type_ID int ,
 Type_ID  int,
 Picture blob,
 foreign key (Monster_Type_ID)  references monster_type(Monster_Type_ID),
 foreign key (Type_ID) references card_type(Type_ID)
)
ENGINE = InnoDB DEFAULT CHARSET=utf8;
-- -------------------------------------------------------------------------------
-- Inserimento tuple:
-- !! NB al URL: Parte dalla HOME !!!
-- sostituire nel URL "scrivereilpercorsodellacartella"  con URL di dove hai posizionato la cartella


-- insert for MONSTER_TYPE

LOAD DATA LOCAL INFILE './scrivereilpercorsodellacartella/DB_yugioh/csv_yugioh/monster_type.csv'
INTO TABLE monster_type
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n';

-- insert for CARD_TYPE

LOAD DATA LOCAL INFILE './scrivereilpercorsodellacartella/DB_yugioh/csv_yugioh/types.csv'
INTO TABLE card_type
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\r\n';

-- insert for yugioh_card

LOAD DATA LOCAL INFILE './scrivereilpercorsodellacartella/DB_yugioh/csv_yugioh/100rows.csv'
INTO TABLE yugioh_card
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';




