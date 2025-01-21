# Tenpo Backend

Tenpo Backend es una aplicación Spring Boot diseñada para gestionar transacciones con funciones como limitación de tasa y documentación OpenAPI.

- Author: Rodrigo Espinoza A.
- Email: rodrigo.espinoza.aguayo@gmail.com

## Pre requisitos

- Java 17 or higher
- Gradle 7.0 or higher
- Docker (optional for running with Docker)

## Instalación

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/codelious/tenpo-backend.git
   cd tenpo-backend
   ```

2. Construir el proyecto usando Gradle:

   ```bash
   ./gradlew build
   ```

## Ejecutando la Aplicación

### Usando Gradle

1. Ejecutar la aplicación:

   ```bash
   ./gradlew bootRun
   ```

2. La aplicación se iniciará y será accesible en `http://localhost:8080`.

### Usando Docker (Opcional)

1. Construir la imagen Docker:

   ```bash
   docker build -t tenpo-backend .
   ```

2. Ejecutar el contenedor de Docker:

   ```bash
   docker run -p 8080:8080 tenpo-backend
   ```

## Accediendo a la documentación API

La documentación de la API se genera automáticamente utilizando Swagger y está accesible en:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Características

- **Rate Limiting**: Limita el número de solicitudes por cliente para evitar abusos.
- **Transaction Management**: Transacciones create, read, update, y delete.
- **OpenAPI Documentation**: Documentación API generada automáticamente.

## Endpoints

Consulte la interfaz de usuario de Swagger para obtener una lista detallada de los endpoints y su uso.

## Testing

Ejecutar los test usando Gradle:

```bash
./gradlew test
```
