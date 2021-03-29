FROM openjdk:8
VOLUME /tmp
ADD target/storage-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]