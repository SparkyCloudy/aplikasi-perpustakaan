@echo off

mkdir out

rem Compile all Java source files
xcopy /y "src\.env" out
javac -d out/ -cp lib/*;src src/*.java

rem Run the main class
java -cp lib/*;out Main

@exit