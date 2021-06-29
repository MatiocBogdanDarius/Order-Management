create database if not exists PT2021_30224_Matioc_Bogdan_Assignment_3_DB;
use PT2021_30224_Matioc_Bogdan_Assignment_3_DB;

drop table if exists client;
create table client (
	id int(11) not null auto_increment primary key,
	name varchar(45) default null,
	address varchar(45) default null,
    email varchar(45) default null,
	age int default null,
    isDelete int
);

drop table if exists product;
create table product (
	id int(11) not null auto_increment primary key,
	name varchar(45) default null,
	stock int default null,
    price double default null,
    isDelete int
);

drop table if exists _order;
CREATE TABLE _order (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    idClient INT NOT NULL,
    date DATETIME NOT NULL,
    CONSTRAINT orderClient FOREIGN KEY (idClient)
        REFERENCES client (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);

drop table if exists buyList;
CREATE TABLE buyList (
    id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    idOrder INT NOT NULL,
    idProduct INT NOT NULL,
    quantity INT NOT NULL,
    price DOUBLE NOT NULL,
    CONSTRAINT buylistProduct FOREIGN KEY (idProduct)
        REFERENCES product (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT buyListOrder FOREIGN KEY (idOrder)
        REFERENCES _order (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
);


