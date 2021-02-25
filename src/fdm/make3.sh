#!/bin/sh
javac thermalconduction01/*.java
java thermalconduction01/ThermalConduction01
rm thermalconduction01/*.class
javac contour/*.java
java contour/Contour thermalconduction01/data/fname
rm contour/*.class
