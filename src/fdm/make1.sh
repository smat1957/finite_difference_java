#!/bin/sh
javac fdmpotential/*.java
java fdmpotential/FDMPotential
rm fdmpotential/*.class
javac contour/*.java
java contour/Contour fdmpotential/data/fname
rm contour/*.class
