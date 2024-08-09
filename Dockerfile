# 멀티 스테이지 빌드의 첫 번째 스테이지
# Gradle 빌드에 필요한 환경 구축
FROM gradle:8.7.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon --stacktrace

# 두 번째 스테이지에서는 경량 베이스 이미지 사용
FROM openjdk:17-jdk-alpine
COPY --from=build /home/gradle/src/build/libs/*.jar mssaemv2.jar
ENV TZ Asia/Seoul
CMD ["java", "-jar", "-Xmx512M", "-Dspring.profiles.active=main", "mssaemv2.jar"]