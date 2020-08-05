DROP TABLE people IF EXISTS;

CREATE TABLE people  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

DROP TABLE address IF EXISTS;

CREATE TABLE address (
    address_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    person_id BIGINT,
    address_1 VARCHAR(20),
    address_2 VARCHAR(20)
);
