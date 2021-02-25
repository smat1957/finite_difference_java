#!/bin/sh
javac thermalconduction02/*.java
java thermalconduction02/ThermalConduction02
rm thermalconduction02/*.class
javac contour/*.java
java contour/Contour thermalconduction02/data/fname
rm contour/*.class
