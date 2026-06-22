# Proyecto Microservicios — DSY1103 FullStack I

## Integrantes

* Román Suárez
* Kevin Schergel
* Carlos Labbé

## Descripción

Sistema de E-Commerce basado en arquitectura de microservicios con Spring Boot.

El proyecto considera microservicios independientes para la gestión de usuarios, productos, inventario, pedidos, pagos, envíos, proveedores, sucursales, empleados y reportes.

La arquitectura incorpora Eureka Server para el registro y descubrimiento de servicios, API Gateway para centralizar el acceso a los microservicios, OpenFeign para comunicación entre microservicios, SpringDoc OpenAPI para documentación Swagger, Spring HATEOAS para respuestas con enlaces relacionados y pruebas unitarias con JUnit y Mockito.

Cada microservicio utiliza configuración en formato YAML y persistencia en MySQL.

## Microservicios

| Servicio       | Puerto | BD        | Responsable    |
| -------------- | -----: | --------- | -------------- |
| eureka-server  |   8761 | No aplica | Equipo         |
| api-gateway    |   8080 | No aplica | Equipo         |
| ms-usuarios    |   8081 | prueba1   | Carlos Labbé   |
| ms-productos   |   8082 | prueba1   | Carlos Labbé   |
| ms-inventario  |   8083 | prueba2   | Carlos Labbé   |
| ms-pedidos     |   8084 | prueba3   | Carlos Labbé   |
| ms-pagos       |   8085 | prueba4   | Kevin Schergel |
| ms-envios      |   8086 | prueba5   | Kevin Schergel |
| ms-proveedores |   8087 | prueba1   | Román Suárez   |
| ms-sucursales  |   8088 | prueba1   | Román Suárez   |
| ms-empleados   |   8089 | prueba6   | Román Suárez   |
| ms-reportes    |   8090 | prueba7   | Román Suárez   |

## Tecnologías

* Java 17
* Spring Boot 3.5.x
* Spring Cloud
* Eureka Server
* Eureka Discovery Client
* API Gateway
* Spring Cloud OpenFeign
* SpringDoc OpenAPI / Swagger
* Spring HATEOAS
* Spring Boot Starter Test
* JUnit 5
* Mockito
* MySQL 8 / Laragon
* Lombok
* Bean Validation
* SLF4J
* Configuración YAML

## Funcionalidades implementadas

### Eureka Server

El proyecto cuenta con un servidor Eureka encargado del registro y descubrimiento de microservicios.

URL de acceso:

```text
http://localhost:8761
```

### API Gateway

El proyecto cuenta con un API Gateway que centraliza las solicitudes hacia los microservicios registrados en Eureka.

Puerto del Gateway:

```text
http://localhost:8080
```

Ejemplos de rutas mediante Gateway:

```text
http://localhost:8080/api/v1/usuarios
http://localhost:8080/api/v1/perfiles
http://localhost:8080/api/v1/productos
http://localhost:8080/api/v1/categorias
```

### SpringDoc OpenAPI / Swagger

Los microservicios cuentan con documentación de endpoints mediante Swagger.

Ejemplo en ms-usuarios:

```text
http://localhost:8081/doc/swagger-ui.html
```

Ejemplo en ms-productos:

```text
http://localhost:8082/doc/swagger-ui.html
```

### Spring HATEOAS

Se implementan endpoints con HATEOAS para entregar recursos con enlaces relacionados.

Ejemplos en ms-usuarios:

```text
http://localhost:8081/api/v1/usuarios/hateoas
http://localhost:8081/api/v1/perfiles/hateoas
```

Ejemplos en ms-productos:

```text
http://localhost:8082/api/v1/productos/hateoas
http://localhost:8082/api/v1/categorias/hateoas
```

### JUnit y Mockito

Se implementan pruebas unitarias con JUnit 5 y Mockito para validar la lógica de servicios sin depender directamente de la base de datos.

Ejemplos:

* UsuarioServiceTest
* PerfilServiceTest
* ProductoServiceTest

### Configuración YAML

Los microservicios utilizan archivos de configuración en formato YAML:

```text
application.yml
application.yaml
```

Esto permite configurar puertos, conexión a base de datos, Eureka Client, Swagger y otras propiedades de forma ordenada.

## Pasos para ejecutar

### Requisitos

* Java 17
* MySQL corriendo en puerto 3306
* Maven
* Laragon o servidor MySQL equivalente

### Bases de datos

Crear en MySQL antes de ejecutar:

```sql
CREATE DATABASE prueba1;
CREATE DATABASE prueba2;
CREATE DATABASE prueba3;
CREATE DATABASE prueba4;
CREATE DATABASE prueba5;
CREATE DATABASE prueba6;
CREATE DATABASE prueba7;
```

## Orden de inicio recomendado

### 1. Iniciar Eureka Server

```bash
cd eureka-server
mvn spring-boot:run
```

Verificar en:

```text
http://localhost:8761
```

### 2. Iniciar microservicios

Ejecutar cada microservicio en su respectiva carpeta:

```bash
cd [carpeta-microservicio]
mvn spring-boot:run
```

Orden recomendado:

1. ms-usuarios (8081)
2. ms-productos (8082)
3. ms-inventario (8083)
4. ms-pedidos (8084)
5. ms-pagos (8085)
6. ms-envios (8086)
7. ms-proveedores (8087)
8. ms-sucursales (8088)
9. ms-empleados (8089)
10. ms-reportes (8090)

### 3. Iniciar API Gateway

```bash
cd gateway
mvn spring-boot:run
```

El Gateway queda disponible en:

```text
http://localhost:8080
```

## Verificación de servicios

### Eureka Server

Revisar que los microservicios aparezcan registrados en:

```text
http://localhost:8761
```

Ejemplos esperados:

* API-GATEWAY
* MS-USUARIOS
* MS-PRODUCTOS
* MS-INVENTARIO
* MS-PEDIDOS
* MS-PAGOS

### Swagger

Verificar documentación de cada microservicio:

```text
http://localhost:8081/doc/swagger-ui.html
http://localhost:8082/doc/swagger-ui.html
```

### Gateway

Probar acceso centralizado:

```text
http://localhost:8080/api/v1/usuarios
http://localhost:8080/api/v1/productos
http://localhost:8080/api/v1/categorias
```

## Pruebas

Para ejecutar las pruebas unitarias de un microservicio:

```bash
cd [carpeta-microservicio]
mvn test
```

Las pruebas utilizan JUnit y Mockito para validar la lógica de los servicios y simular el comportamiento de los repositorios.

## Consideraciones

* Cada microservicio debe estar configurado con su propio puerto.
* Eureka Server debe iniciarse antes que los microservicios.
* API Gateway debe iniciarse después de Eureka y de los microservicios que se quieran consumir.
* Las bases de datos deben estar creadas antes de levantar los servicios.
* Los servicios se registran en Eureka usando el nombre configurado en `spring.application.name`.
* Las rutas del Gateway utilizan `lb://` para resolver los microservicios registrados en Eureka.
