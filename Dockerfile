FROM openjdk:17-oracle
WORKDIR /my-project
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/bookstore_back-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-clp2hp146foc73cc9ba0-a.ohio-postgres.render.com:5432/bookstore_nu9p
ENV SPRING_DATASOURCE_USERNAME=imramo00
ENV SPRING_DATASOURCE_PASSWORD=dN90a4bq5zJeFCJOqcKFcb917Sp2eRtl
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
ENV SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "app.jar"]