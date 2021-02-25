#!/bin/sh
javac thermalconduction03/*.java
java thermalconduction03/ThermalConduction03
rm thermalconduction03/*.class
javac contour/*.java
java contour/Contour thermalconduction03/data/fname
rm contour/*.class
