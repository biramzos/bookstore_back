FROM openjdk:17-oracle
WORKDIR /my-project
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/bookstore_back-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]