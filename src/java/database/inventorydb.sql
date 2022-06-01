/* ****************************************************************************
** Author: Eunji Elly Lee
** Created: Jan 11, 2022
** Description: As a database of inventories, it creates and manages tables
** that contain information about users, roles, families, items, and categories.
**************************************************************************** */

DROP SCHEMA IF EXISTS `inventorydb`;
CREATE SCHEMA IF NOT EXISTS `inventorydb` DEFAULT CHARACTER SET latin1;
USE `inventorydb`;

-- -----------------------------------------------------
-- Table `inventorydb`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`category` (
  `category_id` INT(11) NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`category_id`));

-- -----------------------------------------------------
-- Table `inventorydb`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`role` (
  `role_id` INT(11) NOT NULL,
  `role_name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`role_id`));

-- -----------------------------------------------------
-- Table `inventorydb`.`family`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`family` (
  `family_id` INT(11) NOT NULL AUTO_INCREMENT,
  `family_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`family_id`));

-- -----------------------------------------------------
-- Table `inventorydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`user` (
  `email` VARCHAR(40) NOT NULL,
  `active` TINYINT(1) NOT NULL DEFAULT '1',
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `family` INT(11) NOT NULL,
  `role` INT(11) NOT NULL,
  `uuid` VARCHAR(50),
  PRIMARY KEY (`email`),
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role`)
    REFERENCES `inventorydb`.`role` (`role_id`),
  CONSTRAINT `fk_user_family`
    FOREIGN KEY (`family`)
    REFERENCES `inventorydb`.`family` (`family_id`));

-- -----------------------------------------------------
-- Table `inventorydb`.`item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`item` (
  `item_id` INT(11) NOT NULL AUTO_INCREMENT,
  `category` INT(11) NOT NULL,
  `item_name` VARCHAR(45) NOT NULL,
  `price` DOUBLE NOT NULL,
  `owner` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`item_id`),
  CONSTRAINT `fk_items_categories`
    FOREIGN KEY (`category`)
    REFERENCES `inventorydb`.`category` (`category_id`),
  CONSTRAINT `fk_items_owner`
    FOREIGN KEY (`owner`)
    REFERENCES `inventorydb`.`user` (`email`)
    ON DELETE CASCADE);
