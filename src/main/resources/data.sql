CREATE SCHEMA IF NOT EXISTS `poseidon` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `poseidon`;

CREATE TABLE IF NOT EXISTS `poseidon`.`bidlist` (
  bid_list_id tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  type VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  bid_quantity DOUBLE ,
  ask_quantity DOUBLE ,
  bid DOUBLE ,
  ask DOUBLE ,
  benchmark VARCHAR(125),
  bid_list_date TIMESTAMP,
  commentary VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' ,
  security VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' ,
  status VARCHAR(10) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' ,
  trader VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' ,
  book VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' ,
  creation_name VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' ,
  creation_date TIMESTAMP ,
  revision_name VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' ,
  revision_date TIMESTAMP ,
  deal_name VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' ,
  deal_type VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' ,
  source_list_id VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' ,
  side VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' ,
 PRIMARY KEY (`bid_list_id`))
 ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `poseidon`.`trade`(
  trade_id tinyint(4) NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  type VARCHAR(30) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  buy_quantity DOUBLE,
  sell_quantity DOUBLE,
  buy_price DOUBLE,
  sell_price DOUBLE,
  trade_date TIMESTAMP,
  security VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  status VARCHAR(10) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  trader VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  benchmark VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  book VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  creation_name VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  creation_date TIMESTAMP,
  revision_name VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  revision_date TIMESTAMP,
  deal_name VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  deal_type VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  source_list_id VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  side VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
PRIMARY KEY (`trade_id`))
ENGINE = InnoDB
 DEFAULT CHARACTER SET = utf8mb4
 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `poseidon`.`curvePoint`(
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  curve_id tinyint(4) NOT NULL,
  as_of_date TIMESTAMP,
  term DOUBLE NOT NULL,
  value DOUBLE NOT NULL,
  creation_date TIMESTAMP,
PRIMARY KEY (`Id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `poseidon`.`rating` (
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  moodys_rating VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  sand_p_rating VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  fitch_rating VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  order_number tinyint(4),
  PRIMARY KEY (`id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `poseidon`.`ruleName`(
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  `description` VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  `json` VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  `template` VARCHAR(512) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  `sql_str` VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  `sql_part` VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci',
  PRIMARY KEY (`Id`))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `poseidon`.`Users`(
  id tinyint(4) NOT NULL AUTO_INCREMENT,
  username VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  password VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  fullname VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  role VARCHAR(125) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
PRIMARY KEY (`Id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

TRUNCATE TABLE `Users`;

INSERT IGNORE INTO `Users`(id, fullname, username, password, role) values
(45, 'Administrator', 'admin', '$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u', 'ADMIN'),
(46, 'User', 'user', '$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u', 'USER');
