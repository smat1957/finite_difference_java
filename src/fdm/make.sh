#!/bin/sh
javac $1/*.java
java $1/$2
rm $1/*.class
javac contour/*.java
java contour/Contour $1/data/fname
rm contour/*.class
