FROM maven:3.6.1-jdk-11

ADD src api/src
ADD pom.xml api/pom.xml

WORKDIR api

RUN apt-get update && \
    mvn clean install

CMD ["java", "-jar", "target/app.jar", "--spring.profiles.active=docker"]
