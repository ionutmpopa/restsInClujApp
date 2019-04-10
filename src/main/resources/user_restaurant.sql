--- RESTAURANTS

create database ems;


DROP TABLE IF EXISTS restaurant;

DROP SEQUENCE IF EXISTS restaurant_id;

create sequence restaurant_id;

create table restaurant 
(
id INT PRIMARY KEY DEFAULT NEXTVAL('restaurant_id'), 
rest_name VARCHAR(50) not null, 
rest_address VARCHAR(150) not null, 
rest_type VARCHAR(20), 
capacity NUMERIC(6,2), 
description VARCHAR(100));



DROP TABLE IF EXISTS review;

DROP SEQUENCE IF EXISTS review_id_seq;
CREATE SEQUENCE review_id_seq;

CREATE TABLE review
(
  id INT DEFAULT NEXTVAL('review_id_seq'),
  restaurant_id INT REFERENCES restaurant(id),
  review VARCHAR(2000),
  date_of_visit DATE,
  date_of_review DATE,
  rating VARCHAR(25),
  CONSTRAINT review_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS role;

DROP SEQUENCE IF EXISTS role_id_seq;
CREATE SEQUENCE role_id_seq;

CREATE TABLE role (
  id numeric NOT NULL DEFAULT nextval('role_id_seq'::regclass) PRIMARY KEY,
  name varchar(255) DEFAULT NULL
);

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS app_user;

DROP SEQUENCE IF EXISTS user_id_seq;
CREATE SEQUENCE user_id_seq;

CREATE TABLE app_user (
  id        NUMERIC      NOT NULL DEFAULT nextval('user_id_seq' :: REGCLASS) PRIMARY KEY,
  email     VARCHAR(255) NOT NULL UNIQUE,
  last_name VARCHAR(255) NOT NULL,
  first_name      VARCHAR(255) NOT NULL,
  password  VARCHAR(255) NOT NULL,
  active BOOLEAN;
);


--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS user_role;
CREATE SEQUENCE user_role_id_seq;
CREATE TABLE user_role (
  id        NUMERIC      NOT NULL DEFAULT nextval('user_role_id_seq' :: REGCLASS) PRIMARY KEY,
  user_id numeric not null,
  role_id numeric not null,
  FOREIGN KEY ("user_id") REFERENCES app_user ("id"),
  FOREIGN KEY ("role_id") REFERENCES role ("id")
)


insert into role (name) values ('ROLE_ADMIN')
insert into role (name) values ('USER')