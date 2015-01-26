-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema miniblog
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema miniblog
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `miniblog` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `miniblog` ;

-- -----------------------------------------------------
-- Table `miniblog`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `miniblog`.`user` ;

CREATE TABLE IF NOT EXISTS `miniblog`.`user` (
  `id` INT(16) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `lastname` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NOT NULL,
  `email` VARCHAR(70) NOT NULL,
  `image` LONGBLOB NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT 0 COMMENT '\n',
  `modified_at` TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  INDEX `firstname` (`firstname` ASC),
  INDEX `lastname` (`lastname` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `miniblog`.`post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `miniblog`.`post` ;

CREATE TABLE IF NOT EXISTS `miniblog`.`post` (
  `id` INT(20) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(256) NOT NULL,
  `description` VARCHAR(1024) NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT 0,
  `modified_at` TIMESTAMP NOT NULL DEFAULT 0,
  `status` TINYINT(1) NOT NULL,
  `image` LONGBLOB NULL,
  `user_id` INT(16) NOT NULL,
  PRIMARY KEY (`id`, `user_id`),
  INDEX `description` (`description` ASC),
  INDEX `fk_post_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_post_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `miniblog`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `miniblog`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `miniblog`.`comment` ;

CREATE TABLE IF NOT EXISTS `miniblog`.`comment` (
  `id` INT(20) NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT 0,
  `modified_at` TIMESTAMP NOT NULL DEFAULT 0,
  `user_id1` INT(16) NOT NULL,
  `post_id` INT(20) NOT NULL,
  PRIMARY KEY (`id`, `user_id1`, `post_id`),
  INDEX `fk_comment_user1_idx` (`user_id1` ASC),
  INDEX `fk_comment_post1_idx` (`post_id` ASC),
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id1`)
    REFERENCES `miniblog`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `miniblog`.`post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `miniblog`.`token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `miniblog`.`token` ;

CREATE TABLE IF NOT EXISTS `miniblog`.`token` (
  `id` INT(20) NOT NULL AUTO_INCREMENT,
  `access_token` VARCHAR(100) NOT NULL,
  `created_at` TIMESTAMP NOT NULL DEFAULT 0,
  `expired` TIMESTAMP NOT NULL DEFAULT 0,
  `user_id` INT(16) NOT NULL,
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk_token_user1_idx` (`user_id` ASC),
  UNIQUE INDEX `access_token_UNIQUE` (`access_token` ASC),
  CONSTRAINT `fk_token_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `miniblog`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
