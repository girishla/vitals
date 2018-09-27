rmdir .\localrepo\com /s /q

java -classpath "%JAVA_HOME%"\lib\tools.jar;"%HOMEPATH%"\.m2\repository\com\force\api\force-wsc\42.0.0\force-wsc-42.0.0-uber.jar com.sforce.ws.tools.wsdlc src\main\resources\ent.wsdl .\target\wsc.jar

call mvn deploy:deploy-file -Durl=file://.\localrepo  -Dfile=.\target\wsc.jar -DgroupId=com.local -DartifactId=wsc -Dpackaging=jar -Dversion=1.2

call mvn clean install -Dmaven.test.skip=true
