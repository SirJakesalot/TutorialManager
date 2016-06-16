#!/bin/sh

cd ../Model/
./build.sh
cd ../Servlet/

sudo javac -classpath .:../../lib/*:../../classes/ *.java -d ../../classes/
#sudo service tomcat7 restart
