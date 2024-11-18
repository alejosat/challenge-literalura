# Literalura API 🚀💻

## Descripción

Literalura API es una aplicación basada en Spring Boot diseñada para la gestión de libros y autores. Esta API consume datos del servicio de [Gutendex](https://gutendex.com/), que ofrece información sobre libros del dominio público. Además, permite realizar búsquedas de libros por título, idioma, y filtrar autores vivos en un año determinado.

## Características

- **Importación de libros**: Obtiene libros desde Gutendex y los guarda en la base de datos local.
- **Gestión de libros**: Listado, búsqueda y eliminación de libros.
- **Gestión de autores**: Listado de autores y filtrado de autores vivos.
- **Búsquedas avanzadas**: Filtrado por idioma y temas de libros.
- **Documentación Swagger**: Incluye una interfaz interactiva para probar los endpoints.

## Tecnologías utilizadas

- Java 17
- Spring Boot 3
- JPA (Hibernate)
- PostgreSQL
- Swagger para documentación
- Gutendex API

## Requisitos previos

Antes de comenzar, asegúrate de tener instalados los siguientes programas:

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Git](https://git-scm.com/)

## Instalación y configuración en local

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno local:

### 1. Clonar el repositorio

```bash
git clone https://github.com/Alejo-Santis/literalura-api.git
cd literalura-api
```
## Configurar la base de datos PostgreSQL
Crea una base de datos en PostgreSQL para el proyecto:

```sql
CREATE DATABASE literalura_db;
```

Configura las credenciales en el archivo application.properties o application.yml:

### application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
## Instalar dependencias

```bash
mvn clean install
```
## Ejecutar la aplicación

```bash
mvn spring-boot:run
```
La API estará disponible en [http://localhost:8080/api/v1/books](http://localhost:8080/api/v1/books).

## Documentación de la API con Swagger

Swagger proporciona una interfaz gráfica para interactuar con los endpoints de la API.
- URL de Swagger: http://localhost:8080/swagger-ui/index.html

Ejemplo de endpoints documentados:
- `GET /api/v1/books/import:` Importa libros desde la API de Gutendex.
- `GET /api/v1/books:` Lista todos los libros.
- `GET /api/v1/books/search?title={title}:` Busca libros por título.
- `GET /api/v1/books/authors:` Obtiene una lista de autores.
- `GET /api/v1/books/authors/living?year={year}:` Lista autores vivos en el año especificado.
- `GET /api/v1/books/language?language={language}:` Filtra libros por idioma.
- `DELETE /api/v1/books/{id}:` Elimina un libro por su ID.
- `GET /api/v1/books/subjects?subject={subject}:` Filtra libros por tema.

## Estructura del proyecto
```
literalura-api
├── src
│   ├── main
│   │   ├── java/com/literalura/api
│   │   │   ├── controller         # Controladores de la API
│   │   │   ├── dto                # Data Transfer Objects
│   │   │   ├── mapper             # Mappers para transformar entidades a DTOs
│   │   │   ├── model              # Entidades del proyecto
│   │   │   ├── repository         # Repositorios para la persistencia de datos
│   │   │   ├── service            # Servicios para la lógica de negocio
│   │   └── resources
│   │       ├── application.properties # Configuración de la aplicación
│   └── test                       # Pruebas unitarias y de integración
└── README.md                      # Documentación del proyecto
```
## Ejemplos de uso
1. Importar libros
```bash
curl -X GET "http://localhost:8080/api/v1/books/import"
```
2. Buscar libros por título
```bash
curl -X GET "http://localhost:8080/api/v1/books/search?title=orgullo"
```
3. Listar autores vivos en un año específico
```bash
curl -X GET "http://localhost:8080/api/v1/books/authors/living?year=2023"
```
## Contribuciones
Si deseas contribuir a este proyecto:

1. Haz un fork del repositorio.
2. Crea una nueva rama (git checkout -b feature/nueva-funcionalidad).
3. Realiza tus cambios y haz commit (git commit -am 'Añadir nueva funcionalidad').
4. Push a los cambios a la rama (git push origin feature/nueva-funcionalidad).
5. Abre un Pull Request.
   
## Licencia
Este proyecto es para fines educativos y de estudio. No está destinado para producción. Consulta con el autor para otros usos.

<hr>

¡Gracias por utilizar Literalura API! Si tienes alguna pregunta o sugerencia, no dudes en contactarme.

### Explicación del contenido

- **Instalación**: Pasos detallados para instalar y configurar el proyecto en local.
- **Documentación Swagger**: Indica cómo acceder y usar Swagger para probar los endpoints.
- **Estructura del proyecto**: Describe la organización de archivos del proyecto.
- **Ejemplos de uso**: Proporciona comandos de ejemplo para interactuar con la API.
- **Contribuciones**: Describe el proceso para contribuir al proyecto.

Si necesitas ajustes o deseas agregar más detalles, no dudes en pedírmelo. ¡Espero que esto te ayude a presentar tu trabajo de manera clara y profesional! 🚀
