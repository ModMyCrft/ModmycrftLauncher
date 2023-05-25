@echo off
cd src
echo DELETING OUT...
del /q/s "../out"
echo DELETING TEMP...
del /q/s "../temp"
echo REMOVING OUT...
rmdir /q/s "../out"

echo REMOVING TEMP
rmdir /q/s "../temp"
echo DONE.
pause