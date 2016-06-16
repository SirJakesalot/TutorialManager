#!/bin/sh

# Including the current and lib directory in the classpath
# Only compiling .java files
# Saving the .class files in the classes directory
sudo javac -classpath .:../../lib/* *.java -d ../../classes/
