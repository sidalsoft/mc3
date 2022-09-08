FROM gradle:7-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean bootJar --no-daemon

FROM openjdk:17.0.1-jdk-slim

EXPOSE 8082

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/MC3-0.0.1-SNAPSHOT.jar /app/mc3.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/mc3.jar"]