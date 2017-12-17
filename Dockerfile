FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/filmoji-server-0.0.1.jar server.jar
ENV JAVA_OPTS=""
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /server.jar