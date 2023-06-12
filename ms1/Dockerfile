FROM eclipse-temurin:17-jdk-jammy AS base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

ENTRYPOINT ["./mvnw", "spring-boot:run"]

FROM base AS local
CMD ["-Dspring-boot.run.profiles=local"]

FROM base AS development
CMD ["-Dspring-boot.run.profiles=development"]

FROM base AS build
RUN ./mvnw package

FROM eclipse-temurin:17-jre-jammy AS production
EXPOSE 8080
COPY --from=build /app/target/ms1-*.jar /ms1.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/ms1.jar"]