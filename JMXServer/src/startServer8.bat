rem space and "(" requres quotos
set rmi=RMI\src
set javaVersion=8
set path1="C:\Program Files\Java\jre8\bin\"
set path2="C:\Program Files (x86)\Java\jre8\bin\"
if exist %path1% (
set JAVA_HOME=%path1%
)else (
set JAVA_HOME=%path2%
)
%JAVA_HOME%java -cp clientRMI;serverRMI;.;"%rmi%" -Djavax.net.ssl.keyStore=config/keystore -Djavax.net.ssl.keyStorePassword=password Server "%javaVersion%" "rmiregistry.exe" "9999" "localhost"
