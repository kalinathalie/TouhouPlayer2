# TouhouPlayer2
Download jdk 8:

https://sdlc-esd.oracle.com/ESD6/JSCDL/jdk/8u321-b07/df5ad55fdd604472a86a45a217032c7d/jdk-8u321-linux-x64.tar.gz?GroupName=JSC&FilePath=/ESD6/JSCDL/jdk/8u321-b07/df5ad55fdd604472a86a45a217032c7d/jdk-8u321-linux-x64.tar.gz&BHost=javadl.sun.com&File=jdk-8u321-linux-x64.tar.gz&AuthParam=1649891484_df931d2da67205b5a0d17e8ccec4f154&ext=.gz

Compile:
cd TouhouPlayer2
./gradlew shadowJar -Dorg.gradle.java.home="/Downloads/jdk1.8.0_321/"
mv build/libs/TouhouPlayer2-1.0-SNAPSHOT-all.jar src/

Execute:
cd src/
/Downloads/jdk1.8.0_321/bin/java -jar TouhouPlayer2-1.0-SNAPSHOT-all.jar