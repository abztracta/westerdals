CREATE SCHEMA IF NOT EXISTS pj2100;
USE pj2100;

DROP TABLE IF EXISTS studentprograms;
CREATE TABLE studentprograms
(
	uid int unsigned auto_increment not null,
	name varchar(255) not null,

	primary key (uid),
	unique (name)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
	uid int unsigned auto_increment not null,
	email varchar(255) not null,
	password varchar(255) not null,
	firstname varchar(255) not null,
	lastname varchar(255) not null,
	dateofbirth date not null,
	studentprogram int unsigned not null,
	date_joined timestamp default current_timestamp,
	access_level tinyint(1) unsigned default 0,

	primary key (uid),
	unique (email),
	foreign key (studentprogram) references studentprograms (uid)
);

DROP TABLE IF EXISTS news;
CREATE TABLE news
(
  uid int unsigned auto_increment not null,
  title varchar(255) not null,
  author int unsigned not null,
  posted timestamp default current_timestamp,
  post varchar(20000) not null,

  primary key (uid),
  foreign key (author) REFERENCES users (uid)
);

DROP TABLE IF EXISTS studentcommunity;
CREATE TABLE studentcommunity
(
  uid int unsigned auto_increment not null,
  name varchar(255) not null,
  abbrevation varchar(16) not null,
  title varchar(255) not null,
  description varchar(20000) not null,
  color varchar(6) not null default '000000',

  primary key (uid)
);

DROP TABLE IF EXISTS communitymembers;
CREATE TABLE communitymembers
(
  studentcommunity int unsigned not null,
  user int unsigned not null,
  leader tinyint(1) default 0 not null,

  primary key(studentcommunity, user),
  foreign key (studentcommunity) references studentcommunity (uid),
  foreign key (user) references users (uid)
);

DROP TABLE IF EXISTS activities;
CREATE TABLE activities
(
  uid int unsigned auto_increment not null,
  title varchar(255) not null,
  location varchar(255) not null,
  date timestamp not null,
  host int unsigned not null,
  description varchar(20000) not null,
  image varchar(255) not null,

  primary key (uid),
  foreign key (host) references studentcommunity (uid)
);