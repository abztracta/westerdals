DROP SCHEMA IF EXISTS `pg3100_assignment3`;
CREATE SCHEMA `pg3100_assignment3`;

CREATE TABLE IF NOT EXISTS `pg3100_assignment3`.`albums` (
  `artist` VARCHAR(25) NOT NULL,
  `title` VARCHAR(240) NOT NULL,
  `tracks` SMALLINT(2) NOT NULL,
  `released` SMALLINT(4) NOT NULL,
  `genre` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`artist`, `title`)
);

INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Deep Purple', 'In Rock', 8, 1970, 'Rock');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Bruce Springsteen', 'Darkness on the Edge of Town', 5, 1978, 'Rock');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Terje Rypdal', 'Terje Rypdal', 5, 1971, 'Jazz');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Dewey Redman', 'The Struggle Continues', 4, 1982, 'Jazz');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Ole Paus', 'Garman', 10, 1972, 'Viser');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Odd Nordstoga', 'Luring', 9, 2004, 'Viser');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Bob Marley', 'Exodus', 7, 1977, 'Reggae');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Peter Tosh', 'Legalize It', 11, 1976, 'Reggae');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Bobby Bare', 'This I Believe', 9, 1971, 'Country');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Emmylou Harris', 'Blue Kentucky Girl', 11, 1979, 'Country');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Miles Davis', 'Bitches Brew', 10, 1970, 'Jazz');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Chick Corea', 'Children\'s Song', 15, 1983, 'Jazz');
INSERT INTO `pg3100_assignment3`.`albums` VALUES ('Chris Spedding', 'Hurt', 8, 1977, 'Rock');