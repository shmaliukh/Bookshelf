FROM openjdk:11

WORKDIR /app

ARG START_MODULE_NAME_ARG
ENV MODULE_TO_START=${START_MODULE_NAME_ARG}

ARG APP_VERSION_ARG
ENV APP_VERSION=${APP_VERSION_ARG}

COPY ${MODULE_TO_START}/target/${MODULE_TO_START}-${APP_VERSION}.jar /app
#COPY no_user.json /root/shelf/no_user/gson_handler_one_file/

CMD java -jar /app/${MODULE_TO_START}-${APP_VERSION}.jar
