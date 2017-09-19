FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/TP-LOAD-BALANCER-1.0-SNAPSHOT.jar com.tp.loadbalancer.app.jar
ADD properties/loadbalancer.properties loadbalancer.properties
ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /com.tp.loadbalancer.app.jar --spring.config.location=file:loadbalancer.properties" ]


