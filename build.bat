@echo off
mkdir temp
cd src
javac -cp "../resources/launcherLib/discord-rpc.jar" -d ../temp net/modmycrft/launcher/log/*.java net/modmycrft/launcher/main/*.java net/modmycrft/launcher/backgrounds/*.java			
cd..
mkdir out
echo COPYING...
copy /y src\net\modmycrft\launcher\backgrounds\*.png temp\net\modmycrft\launcher\backgrounds\
mkdir temp\net\modmycrft\launcher\DONT_OPEN_THIS
echo COPYING...
xcopy "src\net\modmycrft\launcher\DONT_OPEN_THIS" "temp\net\modmycrft\launcher\DONT_OPEN_THIS\" /s/h/e/k/f/c/y
cd temp
echo CREATING A JAR...
jar cfm ../out/Launcher.jar ../resources/META-INF/MANIFEST.MF net/ 
cd..
echo DONE. LAUNCHER IN OUT/
pause
