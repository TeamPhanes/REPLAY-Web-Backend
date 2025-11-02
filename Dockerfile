FROM amazoncorretto:21
COPY /build/libs/replay-0.0.1-SNAPSHOT.jar /backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]