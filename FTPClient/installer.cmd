set autoPath="%userprofile%\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup"
set repo=\\FS2\Sheduler\Invoker\
copy /y %repo%enjoy.cmd %autoPath%
copy /y %repo%enjoy1.cmd %autoPath%
set i=C:\APP\Invoker_Invoker\
rem remove old version, recursive must be fix
if exist %i% (
del /s /q %i%\*
)
mkdir %i%
rem recussive copy
set j1=RMI\src\clientRMI
set j2=RMI\src\serverRMI
set j3=RMI\src\root
set j4=RMI
set j5=config

mkdir %i%%j1%
copy %repo%%j1%\*  %i%%j1%
mkdir %i%%j2%
copy %repo%%j2%\*  %i%%j2%
mkdir %i%%j3%
copy %repo%%j3%\*  %i%%j3%
copy %repo%%j4%\*  %i%%j4%
mkdir %i%%j5%
copy %repo%%j5%\*  %i%%j5%

copy /y %repo%* %i%
cd %i%
rem first start up
start startServer
start startServer1
