FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/kelnic-startup-builder.jar app.jar
COPY reports ./reports
EXPOSE 8080
ENV STRIPE_SECRET_KEY=sk_test_...
ENV AYRSHARE_API_KEY=your_ayrshare_key
CMD ["java", "-jar", "app.jar"]
