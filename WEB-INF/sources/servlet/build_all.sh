#!/bin/sh

cd ../model/
if ./build.sh ; then
    cd ../servlet/lib/
    if sudo javac -classpath .:../../../lib/*:../../../classes/ *.java -d ../../../classes/ ; then
		cd ../api/
		if sudo javac -classpath .:../../../lib/*:../../../classes/ *.java -d ../../../classes/ ; then
			cd ../pages/
			if sudo javac -classpath .:../../../lib/*:../../../classes/ *.java -d ../../../classes/ ; then
				sudo service tomcat7 restart
			fi
		fi
    fi
fi
