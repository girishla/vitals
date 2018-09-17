java -classpath %JAVA_HOME%\lib\tools.jar;C:\Users\lakshmg4\.m2\repository\com\force\api\force-wsc\42.0.0\force-wsc-42.0.0-uber.jar com.sforce.ws.tools.wsdlc src\main\resources\ent.wsdl .\target\wsc.jar

mvn deploy:deploy-file -Durl=file://.\localrepo  -Dfile=.\target\wsc.jar -DgroupId=com.local -DartifactId=wsc -Dpackaging=jar -Dversion=1.2