set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_31\bin\
set JAVAC="%JAVA_HOME%javac"
set keys=-cp src;src\clientRMI;src\com\vniia;.;src\root;commons-net-3.3.jar
%JAVAC% %keys% src\*.java src\clientRMI\*.java src\com\vniia\*.java src\root\*.java
