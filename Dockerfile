FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD build/libs/test-tools.jar app.jar
ADD storage tmp
CMD "java" "-jar" "/app.jar"