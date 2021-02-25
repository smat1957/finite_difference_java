#!/bin/sh
javac fdmpsizeta/*.java
java fdmpsizeta/FDMPsiZeta
rm fdmpsizeta/*.class
javac contour/*.java
java contour/Contour fdmpsizeta/data/fname
rm contour/*.class
