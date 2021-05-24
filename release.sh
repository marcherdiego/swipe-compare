#!/bin/bash
./gradlew clean && ./gradlew signJars

rm core/build/outputs/aar/core-debug.aar
rm core/build/outputs/aar/core-debug.aar.asc
mv core/build/libs/* core/build/outputs/aar
mv core/build/poms/pom-default.xml core/build/outputs/aar/core-1.0.0.pom
mv core/build/poms/pom-default.xml.asc core/build/outputs/aar/core-1.0.0.pom.asc
