FROM openjdk:11

WORKDIR /app

COPY target/Bookshelf_project-1.0-SNAPSHOT.jar /app

VOLUME shelf_volume

CMD java -jar /app/Bookshelf_project-1.0-SNAPSHOT.jar
