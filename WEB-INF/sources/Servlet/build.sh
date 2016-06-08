#!/bin/sh

cd ../Model/
./build.sh
cd ../Servlet/

FOLDER=JakesTutorials
ROOT_DIR=/var/lib/tomcat7/webapps/$FOLDER/WEB-INF
LIB_FILES=$ROOT_DIR/lib/*
CURRENT_DIR=$ROOT_DIR/sources/Servlet/

CLASS_DIR=$ROOT_DIR/classes/
sudo javac -classpath $CURRENT_DIR:$LIB_FILES:$CLASS_DIR *.java -d $CLASS_DIR
sudo service tomcat7 restart
