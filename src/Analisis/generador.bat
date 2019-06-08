SET JFLEX_HOME= C:\libs\jflex-1.6.1
cd C:\Users\erick\Documents\NetBeansProjects\InterpreteJava\src\Analisis\
java -jar %JFLEX_HOME%\lib\jflex-1.6.1.jar scanner.jflex

pause


SET JFLEX_HOME= C:\libs\java-cup-11b
cd C:\Users\erick\Documents\NetBeansProjects\InterpreteJava\src\Analisis\ 
 java -jar %JFLEX_HOME%\java-cup-11b.jar -parser parser parser.cup

pause