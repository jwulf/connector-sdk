FROM eclipse-temurin:17-jre
RUN mkdir /opt/app

ADD target/connector-runtime-job-worker-*-with-dependencies.jar /opt/app

# Using entry point to allow downstream images to add JVM arguments using CMD
ENTRYPOINT ["java", "-cp", "/opt/app/*", "io.camunda.connector.runtime.jobworker.Main"]
