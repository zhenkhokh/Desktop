set rmi=..\..\..\..\..\RMI\src
set javaBin=C:\Program Files\Java\jdk1.6.0_45\bin

"%javaBin%\javac"  -cp root;serverRMI;clientRMI;%rmi% %rmi%\serverRMI\*.java %rmi%\clientRMI\*.java %rmi%\root\*.java Server.java



 
