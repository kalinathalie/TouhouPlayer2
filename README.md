# TouhouPlayer2
other java

export PATH_TO_FX=/opt/openjfx-18_linux-x64_bin-sdk/javafx-sdk-18/lib
javac --module-path $PATH_TO_FX --add-modules javafx.fxml,javafx.controls Main.java

java 8

https://sdlc-esd.oracle.com/ESD6/JSCDL/jdk/8u321-b07/df5ad55fdd604472a86a45a217032c7d/jdk-8u321-linux-x64.tar.gz?GroupName=JSC&FilePath=/ESD6/JSCDL/jdk/8u321-b07/df5ad55fdd604472a86a45a217032c7d/jdk-8u321-linux-x64.tar.gz&BHost=javadl.sun.com&File=jdk-8u321-linux-x64.tar.gz&AuthParam=1649891484_df931d2da67205b5a0d17e8ccec4f154&ext=.gz
/home/kali/Downloads/jdk1.8.0_321/bin/javac Main.java
/home/kali/Downloads/jdk1.8.0_321/bin/jar cfm Touhou.jar application/META-INF/MANIFEST.MF application/Touhou.class
/home/kali/Downloads/jdk1.8.0_321/bin/java -jar Touhou.jar