FROM openjdk:8u171-alpine3.7
RUN apk --no-cache add curl
RUN mkdir /opt
RUN mkdir /opt/terminal-api-project
RUN mkdir /opt/terminal-api-project/config
COPY *.jar /opt/terminal-api-project/terminal-api-project.jar
WORKDIR /opt/terminal-api-project
CMD java -Dmicronaut.config.files=config/application.yml -jar terminal-api-project.jar