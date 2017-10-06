@echo on
chcp 1251
setlocal ENABLEDELAYEDEXPANSION
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_31\bin\
set JAVA="%JAVA_HOME%java"
set keys=-cp src;.;clientRMI;com.vniia;root
for /L %%v in (1,1,48) do (
%JAVA% %keys% Sheduller 1 %%v > temp.txt
set /p dir= < temp.txt
%JAVA% %keys% Sheduller 2 %%v > temp.txt
set /p bin= < temp.txt
%JAVA% %keys% Sheduller 3 %%v > temp.txt
set /p name= < temp.txt
set out=\\FS2\sheduler\updater\!bin!.xml
set params=!dir! "!bin!.exe" "!name!" "!dir!"
echo outDir !out!
echo params !params!
.\Vniia.Generator.exe !params! > !out!
)
del temp.txt
endlocal