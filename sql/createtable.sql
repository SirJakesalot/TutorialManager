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
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS categories (
    id INTEGER AUTO_INCREMENT NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE tutorial_categories (
    tutorial_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    FOREIGN KEY(tutorial_id) REFERENCES tutorials(id) ON DELETE CASCADE,
    FOREIGN KEY(category_id) REFERENCES categories(id) ON DELETE CASCADE
);
