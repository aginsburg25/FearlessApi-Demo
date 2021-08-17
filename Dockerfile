FROM openjdk:11
EXPOSE 3000
WORKDIR /app
COPY target/
ENTRYPOINT ["java","-jar","/app.jar"]