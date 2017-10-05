rem space and "(" requres quotos
set rmi=RMI\src
rem use 6 or 7 only
set javaVersion=6
set locAddr=localhost
set port=9999
set JAVA_HOME=C:\Program Files\Java\jre%javaVersion%\bin\
set JAVA="%JAVA_HOME%java"
set path="C:\Program Files (x86)\Java\jre%javaVersion%\bin\"
if exist %path% (
set JAVA="C:\Program Files (x86)\Java\jre%javaVersion%\bin\java"
)
set params=reserved rmiregistry.exe %port% %locAddr%
set keys=-cp clientRMI;serverRMI;.;"%rmi%";RMI\guava-18.0.jar;RMI\commons-net-3.3.jar -Djavax.net.ssl.keyStore=config/keystore -Djavax.net.ssl.keyStorePassword=password
%JAVA% %keys% ServiceUpdater
%JAVA% %keys% Server %params%