FROM openjdk:11
COPY "./target/w2m-0.0.1-SNAPSHOT.jar" "w2m_app.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "w2m_app.jar"]
