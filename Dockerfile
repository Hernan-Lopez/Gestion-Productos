FROM eclipse-temurin:21.0.5_11-jdk
EXPOSE 8080
ADD target/product-api.jar product-api.jar
ENTRYPOINT [ "java","-jar","/product-api.jar" ]