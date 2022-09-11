# openjdk17 base image
FROM openjdk:17-oracle
# jar 파일이 복사되는 위치
ENV APP_HOME=/usr/app/
# 작업 시작 위치
WORKDIR $APP_HOME
# jar 파일 복사
COPY build/libs/*.jar api-gateway.jar
# application port
EXPOSE 8000
# 실행
CMD ["java", "-jar", "api-gateway.jar"]