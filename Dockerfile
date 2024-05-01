FROM openjdk:21-jdk
LABEL authors="Shubham Varshney"
LABEL maintainer="Shubham Varshney"

ENV JAVA_HOME="/usr/lib/jvm/default-jvm"

ENV PATH=$PATH:${JAVA_HOME}/bin
ADD build/libs/template-service-java-springboot-0.0.1.jar app.jar

# Expose the port for JMX
EXPOSE 8009

EXPOSE 8080

ENTRYPOINT ["java", "-Xms64M", "-Xmx128M", "-Dcom.sun.management.jmxremote=true", "-Dcom.sun.management.jmxremote.port=8009", "-Dcom.sun.management.jmxremote.ssl=false", "-Dcom.sun.management.jmxremote.authenticate=false", "-jar", "app.jar"]
