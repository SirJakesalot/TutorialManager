#!/bin/sh

FOLDER=TutorialManager
ROOT_DIR=/var/lib/tomcat7/webapps/$FOLDER/WEB-INF
CURRENT_DIR=$ROOT_DIR/sources/Model/
LIB_DIR=$ROOT_DIR/lib/*

CLASS_DIR=$ROOT_DIR/classes/

sudo javac -classpath $CURRENT_DIR:$LIB_DIR *.java -d $CLASS_DIR
