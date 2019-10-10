FROM openjdk:11-jdk

RUN mkdir /docker
WORKDIR /docker

COPY . .
RUN bash gradlew build
