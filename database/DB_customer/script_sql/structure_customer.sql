-- Crea database CUSTOMERS e la tabella customers
-- Popolarlo con dumping_data_customers.sql

create database CUSTOMERS;
use CUSTOMERS;
create table customers
    ( ID int primary key,
      customer mediumblob
    )ENGINE = InnoDB DEFAULT CHARSET=utf8;
