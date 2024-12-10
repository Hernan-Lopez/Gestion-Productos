FROM eclipse-temurin:21.0.5_11-jdk
EXPOSE 8080
COPY target/gestionproductos-0.0.1-SNAPSHOT.jar product-api.jar
ENTRYPOINT [ "java", "-jar", "/product-api.jar" ]