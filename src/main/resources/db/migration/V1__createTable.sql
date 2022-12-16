
CREATE TABLE IF NOT EXISTS heroe
(
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
        id int NOT NULL AUTO_INCREMENT,
        name varchar(255) DEFAULT NOT NULL,
        pass varchar(700) DEFAULT NOT NULL,
        PRIMARY KEY (id)
);