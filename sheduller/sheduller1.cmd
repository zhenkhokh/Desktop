set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_31\bin\
set JAVA="%JAVA_HOME%java"
set keys=-cp src;.;clientRMI;com.vniia;root;commons-net-3.3.jar
del .\*.class
copy .\src\Sheduller.class .
%JAVA% %keys% Sheduller