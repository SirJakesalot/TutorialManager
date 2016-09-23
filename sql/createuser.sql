GRANT ALL ON tutorialdb.* TO 'tutorialdb_admin'@'localhost' IDENTIFIED BY 'administrator';
GRANT SELECT ON tutorialdb.* TO 'tutorialdb_user'@'localhost' IDENTIFIED BY 'user';
FLUSH PRIVILEGES;