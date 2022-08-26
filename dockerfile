FROM openjdk:11

WORKDIR /app

ARG APP_VERSION=1.0-SNAPSHOT
COPY target/bookshelf_project-${APP_VERSION}.jar /app
#COPY no_user.json /root/shelf/no_user/gson_handler_one_file/
#/shelf/no_user/gson_handler_one_file

CMD java -jar /app/bookshelf_project-${APP_VERSION}.jar
