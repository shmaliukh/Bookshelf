FROM openjdk:11

WORKDIR /app

ARG START_MODULE_NAME_ARG
ENV MODULE_NAME=${START_MODULE_NAME_ARG}

ARG APP_VERSION_ARG
ENV APP_VERSION=${APP_VERSION_ARG}

COPY ${MODULE_NAME}/target/${MODULE_NAME}-${APP_VERSION}.jar /app
#COPY no_user.json /root/shelf/no_user/gson_handler_one_file/

CMD java -jar /app/${MODULE_NAME}-${APP_VERSION}.jar
