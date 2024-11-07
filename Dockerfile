# Step 1: Base image 설정 (JDK 17 사용)
FROM openjdk:17-jdk-slim

# Step 2: 작업 디렉토리 설정
WORKDIR /spring-server

# Step 3: 애플리케이션 JAR 파일 복사
COPY build/libs/final_project-0.0.1-SNAPSHOT.jar /app.jar

# Step 4: wait-for-it.sh 스크립트 복사
COPY wait-for-it.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/wait-for-it.sh

# Step 5: MySQL이 준비될 때까지 대기 후 애플리케이션 실행
ENTRYPOINT ["wait-for-it.sh", "mysql-container", "3306", "--", "java", "-jar", "/app.jar"]
