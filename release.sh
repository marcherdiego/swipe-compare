#!/bin/bash
./gradlew clean && ./gradlew signJars

versionTemp=$(grep "libraryVersion" "build.gradle" | awk '{print $3}')
version=$(echo "$versionTemp" | sed -e 's|["'\'']||g')

rm core/build/outputs/aar/core-debug.aar
rm core/build/outputs/aar/core-debug.aar.asc
mv core/build/libs/* core/build/outputs/aar
mv core/build/poms/pom-default.xml core/build/outputs/aar/core-${version}.pom
mv core/build/poms/pom-default.xml.asc core/build/outputs/aar/core-${version}.pom.asc
