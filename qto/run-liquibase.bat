@echo off
SET M2=C:\Users\nigel.arugay\.m2\repository
SET QTO=C:\Users\nigel.arugay\Downloads\velocity-back-up\qto
SET CP=.
SET CP=%CP%;%M2%\org\liquibase\liquibase-core\4.23.0\liquibase-core-4.23.0.jar
SET CP=%CP%;C:\Users\nigel.arugay\Downloads\velocity-back-up\qto\wildfly\modules\org\postgresql\main\postgresql-42.7.4.jar
SET CP=%CP%;%M2%\org\slf4j\slf4j-api\1.7.36\slf4j-api-1.7.36.jar
SET CP=%CP%;%M2%\org\slf4j\slf4j-reload4j\1.7.36\slf4j-reload4j-1.7.36.jar
SET CP=%CP%;%M2%\ch\qos\reload4j\reload4j\1.2.19\reload4j-1.2.19.jar
SET CP=%CP%;%M2%\org\yaml\snakeyaml\2.0\snakeyaml-2.0.jar
SET CP=%CP%;%M2%\commons-io\commons-io\2.15.1\commons-io-2.15.1.jar
SET CP=%CP%;%QTO%\qto-database\target\qto-database-1.18.1-SNAPSHOT.jar

cd /d %QTO%
java -cp "%CP%" RunLiquibase
