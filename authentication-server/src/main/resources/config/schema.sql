DROP DATABASE IF EXISTS `restaurant`;
CREATE DATABASE `restaurant` ;


CREATE TABLE `user_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(25) NOT NULL,
  `description` varchar(50) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO `restaurant`.`user_type` (`type`, `description`) VALUES ('internal', 'Internal User');
INSERT INTO `restaurant`.`user_type` (`type`, `description`) VALUES ('external', 'External User');
INSERT INTO `restaurant`.`user_type` (`type`, `description`) VALUES ('external-client', 'External Client');
