CREATE SCHEMA IF NOT EXISTS `poseidon`;
USE `poseidon`;

CREATE TABLE IF NOT EXISTS `poseidon`.`bid_list` (
  bid_list_id tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) ,
  type VARCHAR(30) ,
  bid_quantity DOUBLE ,
  ask_quantity DOUBLE ,
  bid DOUBLE ,
  ask DOUBLE ,
  benchmark VARCHAR(125),
  bid_list_date TIMESTAMP,
  commentary VARCHAR(125)  ,
  security VARCHAR(125)  ,
  status VARCHAR(10)  ,
  trader VARCHAR(125)  ,
  book VARCHAR(125)  ,
  creation_name VARCHAR(125)  ,
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125)  ,
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125)  ,
  deal_type VARCHAR(125)  ,
  source_list_id VARCHAR(125)  ,
  side VARCHAR(125)  ,
 PRIMARY KEY (`bid_list_id`));


CREATE TABLE IF NOT EXISTS `poseidon`.`trade`(
  trade_id tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) ,
  type VARCHAR(30) ,
  buy_quantity DOUBLE,
  sell_quantity DOUBLE,
  buy_price DOUBLE,
  sell_price DOUBLE,
  trade_date TIMESTAMP,
  security VARCHAR(125) ,
  status VARCHAR(10) ,
  trader VARCHAR(125) ,
  benchmark VARCHAR(125) ,
  book VARCHAR(125) ,
  creation_name VARCHAR(125) ,
  creation_date TIMESTAMP,
  revision_name VARCHAR(125) ,
  revision_date TIMESTAMP,
  deal_name VARCHAR(125) ,
  deal_type VARCHAR(125) ,
  source_list_id VARCHAR(125) ,
  side VARCHAR(125) ,
PRIMARY KEY (`trade_id`));


CREATE TABLE IF NOT EXISTS `poseidon`.`curve_point`(
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  curve_id tinyint(4) NOT NULL,
  as_of_date TIMESTAMP,
  term DOUBLE NOT NULL,
  value DOUBLE NOT NULL,
  creation_date TIMESTAMP,
PRIMARY KEY (`Id`));


CREATE TABLE IF NOT EXISTS `poseidon`.`rating` (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  moodys_rating VARCHAR(125) ,
  sand_p_rating VARCHAR(125) ,
  fitch_rating VARCHAR(125) ,
  order_number tinyint(4),
  PRIMARY KEY (`id`));


CREATE TABLE IF NOT EXISTS `poseidon`.`rule_name`(
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(125) ,
  `description` VARCHAR(125) ,
  `json` VARCHAR(125) ,
  `template` VARCHAR(512) ,
  `sql_str` VARCHAR(125) ,
  `sql_part` VARCHAR(125) ,
  PRIMARY KEY (`Id`));


CREATE TABLE IF NOT EXISTS `poseidon`.`user`(
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  username VARCHAR(125)  NOT NULL,
  password VARCHAR(125)  NOT NULL,
  fullname VARCHAR(125)  NOT NULL,
  role VARCHAR(125)  NOT NULL,
PRIMARY KEY (`Id`));


