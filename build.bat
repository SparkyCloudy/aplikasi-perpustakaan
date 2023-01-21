@echo off

set build=%JAVA_HOME%\bin\javac
set run=%JAVA_HOME%\bin\java

rem Compile all Java source files
xcopy /y "src\.env" out
%build% -d out/ -cp lib/*;src src/*.java

rem Run the main class
%run% -cp lib/*;out Main

@exit