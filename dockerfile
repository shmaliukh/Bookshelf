FROM openjdk:11

WORKDIR /app

ENV APP_VERSION=1.0-SNAPSHOT
COPY core/target/core-${APP_VERSION}.jar /app
#COPY no_user.json /root/shelf/no_user/gson_handler_one_file/

CMD java -jar /app/core-${APP_VERSION}.jar
