# Challenge Back-end Literalura 

Este es un sencillo programa de consola que permite obtener información de libros consumiendo una API, almacenarla en una base de datos relacional y hacer consultas en la base de datos

## Tecnologías Usadas

- **Java**: Lenguaje de programación.
- **Springboot: Configuración de la aplicación en spring.
- **Maven**: Herramienta de gestión de dependencias.
- **PostgreSQL**:Gestor de bases de datos relacionales SQL.
- **Dependencias** Jackson-bind, JPA, postgresql
  

## Requisitos Previos

Antes de ejecutar el programa, asegúrate de tener lo siguiente:

- Java JDK 22
- PostgreSQL y una base de datos creada

## Uso del Programa

Para usar el programa es necesario reemplazar la configuración para conectarse a la base de datos
con su información o con sus variables de entorno en el apartado application.properties:

spring.datasource.url=jdbc:postgresql://${DB_Host}/${DB_Name}
spring.datasource.username=${DB_User}
spring.datasource.password=${DB_Password}

EL programa tiene 7 funciones
1 - Buscar libro por titulo en la web (API)
2 - Mostrar libros registrados (base de datos)
3 - Mostrar autores registrados (base de datos)
4 - Mostrar autores vivos por año (base de datos)
5 - Mostrar libros por idioma (base de datos)
6 - Top 10 libros más descargados (base de datos)
0 - Salir (cierre de la aplicación

## Problemas Conocidos

Al requisitar un libro cuyo lenguaje no se encuentre en las opciones del enum el program lanza un mensjae de error personalizado y se cierra

## Contribuciones

Si quieres mejorar este proyecto, ¡las contribuciones son bienvenidas! Puedes hacer un fork del repositorio, hacer tus cambios y luego enviar un pull request.
