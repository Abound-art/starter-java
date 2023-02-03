FROM gradle:jdk19 as builder
COPY . /home/gradle/app
WORKDIR /home/gradle/app
RUN gradle build && tar -xf /home/gradle/app/build/distributions/starter-java.tar

FROM eclipse-temurin:19-alpine
COPY --from=builder /home/gradle/app/starter-java/ /app
WORKDIR /app
CMD ["bin/starter-java"]
