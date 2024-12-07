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

---

### **3. Ejecución del Microservicio con Docker**

1. **Descargar la imagen del microservicio desde Docker Hub:**
   ```bash
   docker pull hernanlopezg/product-api

2. **Ejecutar el contenedor:**
   ```bash
   docker run -p 8080:8080 hernanlopezg/product-api

Esto ejecutará el microservicio y lo expondrá en el puerto 8080.

---

### **4. Acceso a la API y Herramientas**

1. **Swagger UI:**
   La documentación interactiva del microservicio está disponible en:
   - [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

   Desde esta interfaz puedes probar las operaciones de la API REST.

2. **Base de Datos H2:**
   Accede a la consola de la base de datos H2 para visualizar y consultar datos directamente:
   - URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

   **Configuración para la base de datos H2:**
   - **JDBC URL:** `jdbc:h2:mem:productos`
   - **Usuario:** `sa`
   - **Clave:** *(dejar en blanco)*

---

### **5. Credenciales para Autenticación**

El sistema utiliza autenticación basada en roles para acceder a las operaciones del microservicio. A continuación se detallan las credenciales disponibles:

| **Rol**            | **Usuario** | **Clave**       | **Permisos**                        |
|---------------------|-------------|-----------------|--------------------------------------|
| **Usuario**         | `user1`     | `password123`   | Acceso a operaciones de `products`. |
| **Administrador**   | `admin`     | `clave123`      | Acceso a operaciones de `users` y `products`. |
| **Estadísticas**    | *Ambos usuarios* | N/A         | Acceso a operaciones de `statistics`. |

---

## **Notas Adicionales**

1. **Datos Persistentes:** 
   Este microservicio utiliza una base de datos en memoria (H2). Los datos cargados se reinician al detener el contenedor.

2. **Actualizaciones:**
   Para actualizar a la última versión del microservicio, ejecuta:
   ```bash
   docker pull hernanlopezg/product-api
