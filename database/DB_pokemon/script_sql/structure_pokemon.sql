-- script per creazione tabelle per DB di carte Pokemon

-- Potete lanciarlo con MYSQL workbrench

-- 1 tabelle create: Pokemon_card

-- !!!NOTA BENE!!:lo script presuppone un DB già esistene denominato "Pokemon_card",
-- 			se non esistente, fare query CREATE DATABASE Pokemon_card

-- le parti non standard dei comandi è:
-- 		la clausola ENGINE=InnoDB 
use Pokemon_card;
create table pokemon_card
( Cards_ID int primary key,
  Name varchar(120),
  Type varchar(120),
  Hp int,
  Description varchar(1000),
  Length varchar(120),
  Weigth int,
  Level int,
  Picture mediumblob
)ENGINE = InnoDB DEFAULT CHARSET=utf8;


