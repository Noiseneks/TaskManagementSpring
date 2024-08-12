FROM bellsoft/liberica-openjdk-alpine:17
ENV	MYSQL_DATABASE=testdb \
    DB_USERNAME=root \
	DB_PASSWORD=testrootpass
WORKDIR /app
VOLUME /app /tmp
COPY build/libs/TaskManagementSpring-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar","app.jar"]