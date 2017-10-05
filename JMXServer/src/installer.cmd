set autoPath="C:\Users\%username%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup"
set repo=\\FS2\Sheduler\Invoker\
copy %repo%enjoy.cmd %autoPath%
copy %repo%enjoy1.cmd %autoPath%
echo %autoPath%
set i=C:\APP\Invoker\
rem remove old version, recursive must be fix
if exist %i% (
del %i%
)
mkdir C:\APP
mkdir %i%
rem Copy -Recurse %repo% %i%
rem recussive copy
set j1=RMI\src\clientRMI
set j2=RMI\src\serverRMI
set j3=RMI\src\root
set j4=config
mkdir %i%%j1%
copy %repo%%j1%\*  %i%%j1%
mkdir %i%%j2%
copy %repo%%j2%\*  %i%%j2%
mkdir %i%%j3%
copy %repo%%j3%\*  %i%%j3%
mkdir %i%%j4%
copy %repo%%j4%\*  %i%%j4%

copy %repo%* %i%
cd %i%
rem first start up
startServer.bat
startServer1.bat