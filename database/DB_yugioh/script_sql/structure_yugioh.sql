-- script per creazione tabelle per DB di carte Yugioh!

-- Potete lanciarlo con MYSQL workbrench

-- 3 tabelle create: Monster_Type,Card_Type,Yugioh_card

-- !!!NOTA BENE!!:lo script presuppone un DB già esistene denominato "CARDS",
-- 			se non esistente, fare query CREATE DATABASE YUGIOH_CARD

-- le parti non standard dei comandi è:
-- 		la clausola ENGINE=InnoDB che e' specifica di MySQL
--      senza la quale non e' possibile usare i comandi di
--      definizione dei vincoli di integrita'Â  referenziale
--      FOREIGN KEY


 create database CARDS;

USE CARDS;
-- ------------ FIRST TABLE ----------------
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
 Picture mediumblob,
 foreign key (Monster_Type_ID)  references monster_type(Monster_Type_ID),
 foreign key (Type_ID) references card_type(Type_ID)
)
ENGINE = InnoDB DEFAULT CHARSET=utf8;