/* ****************************************************************************
** Author: Eunji Elly Lee
** Created: Jan 11, 2022
** Description: As a database of inventories, it creates and manages tables
** that contain information about users, roles, groups, items, and categories.
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
-- Table `inventorydb`.`group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`group` (
  `group_id` INT(11) NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`group_id`));

-- -----------------------------------------------------
-- Table `inventorydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inventorydb`.`user` (
  `email` VARCHAR(40) NOT NULL,
  `active` TINYINT(1) NOT NULL DEFAULT '1',
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `group` INT(11) NOT NULL,
  `role` INT(11) NOT NULL,
  `uuid` VARCHAR(50),
  PRIMARY KEY (`email`),
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role`)
    REFERENCES `inventorydb`.`role` (`role_id`),
  CONSTRAINT `fk_user_group`
    FOREIGN KEY (`group`)
    REFERENCES `inventorydb`.`group` (`group_id`));

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

INSERT INTO `role` VALUES (1, 'system admin');
INSERT INTO `role` VALUES (2, 'regular user');
INSERT INTO `role` VALUES (3, 'group admin');

INSERT INTO `category` (`category_name`) VALUES ('kitchen');
INSERT INTO `category` (`category_name`) VALUES ('washroom');
INSERT INTO `category` (`category_name`) VALUES ('living room');
INSERT INTO `category` (`category_name`) VALUES ('basement');
INSERT INTO `category` (`category_name`) VALUES ('bedrooom');
INSERT INTO `category` (`category_name`) VALUES ('garage');
INSERT INTO `category` (`category_name`) VALUES ('office');
INSERT INTO `category` (`category_name`) VALUES ('other');

INSERT INTO `group` (`group_name`) VALUES ('SYSTEM');
INSERT INTO `group` (`group_name`) VALUES ('RED');
INSERT INTO `group` (`group_name`) VALUES ('BLUE');

INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`group`,`role`)
	VALUES ('cprg.352d+admin@gmail.com', true, 'Admin', 'Admin', 'password', 1, 1);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`group`,`role`)
	VALUES ('cprg.352d+admin2@gmail.com', true, 'Admin2', 'Admin2', 'password', 2, 3);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`group`,`role`)
	VALUES ('cprg.352d+anne@gmail.com', true, 'Anne', 'Annerson', 'password', 2, 2);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`group`,`role`)
	VALUES ('cprg.352d+barb@gmail.com', true, 'Barb', 'Barber', 'password', 2, 2);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`group`,`role`)
	VALUES ('cprg.352d+admin3@gmail.com', true, 'Admin3', 'Admin3', 'password', 3, 3);
INSERT INTO `user` (`email`,`active`,`first_name`,`last_name`,`password`,`group`,`role`)
	VALUES ('cprg.352d+elly@gmail.com', true, 'Elly', 'Lee', 'password', 3, 2);

INSERT INTO `item` (`category`,`item_name`,`price`,`owner`) VALUES (1, 'blender', 29.99, 'cprg.352d+anne@gmail.com');
INSERT INTO `item` (`category`,`item_name`,`price`,`owner`) VALUES (1, 'toaster', 19.99, 'cprg.352d+anne@gmail.com');
INSERT INTO `item` (`category`,`item_name`,`price`,`owner`) VALUES (3, 'lamp', 5, 'cprg.352d+anne@gmail.com');
INSERT INTO `item` (`category`,`item_name`,`price`,`owner`) VALUES (6, 'winter tires', 200, 'cprg.352d+anne@gmail.com');
INSERT INTO `item` (`category`,`item_name`,`price`,`owner`) VALUES (5, 'dresser', 50, 'cprg.352d+anne@gmail.com');