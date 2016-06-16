# CS 122b Extra Credit Project
#
# Run the script to create the database and its tables
# mysql -u root -p < create_tables.sql
# mysql -u root -p -D tutorialdb < data.sql

DROP DATABASE IF EXISTS tutorialdb;
CREATE DATABASE tutorialdb;
USE tutorialdb;

CREATE TABLE IF NOT EXISTS tutorials (
    id INTEGER AUTO_INCREMENT NOT NULL,
    category_id INTEGER NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS categories (
    id INTEGER AUTO_INCREMENT NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);
