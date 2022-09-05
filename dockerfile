FROM openjdk:11

WORKDIR /app

ARG MY_VAR

ENV START_MODULE_NAME="all-in-one"
ENV APP_VERSION=1.0-SNAPSHOT

COPY ${START_MODULE_NAME}/target/${START_MODULE_NAME}-${APP_VERSION}.jar /app
#COPY no_user.json /root/shelf/no_user/gson_handler_one_file/

CMD java -jar /app/${START_MODULE_NAME}-${APP_VERSION}.jar
