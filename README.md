# Product Management Microservice

---

## **Descripción del Microservicio**
El microservicio **Product Management API** permite realizar operaciones CRUD para gestionar productos. Este sistema incluye estadísticas de productos por categoría, autenticación basada en roles y una base de datos H2 en memoria.

### Operaciones principales:
- **Productos:** Crear, actualizar, eliminar, listar y buscar productos.
- **Usuarios:** Gestión básica de autenticación y roles.
- **Estadísticas:** Consultar la cantidad de productos por categoría.

---

## **Instrucciones para Configurar y Ejecutar el Microservicio**

### **1. Instalación de Java**

- **Java 17+ es necesario para ejecutar el microservicio.**
- Descarga Java desde su sitio oficial:
  - [OpenJDK](https://openjdk.org/projects/jdk/17/)
  - [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
  
- Verifica la instalación con:
  ```bash
  java --version

---

### **2. Instalación de Docker**

Docker se utiliza para ejecutar el microservicio como un contenedor.

- Descarga e instala Docker desde:
  - [Docker Desktop (Windows/Mac)](https://www.docker.com/products/docker-desktop)
  - [Docker para Linux](https://docs.docker.com/engine/install/)

- Verifica la instalación con:
  ```bash
  docker --version
