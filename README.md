# Proyecto Microservicios — DSY1103 FullStack I

## Integrantes
- Román Suárez
- Kevin Schergel
- Carlos Labbé
## Descripción
Sistema de E-Commerce basado en arquitectura de microservicios con Spring Boot.
Cada microservicio tiene su propia base de datos y se comunica via FeignClient.

## Microservicios

| Microservicio | Puerto | BD | Responsable |
|---|---|---|---|
| ms-usuarios | 8081 | prueba1 | [Compañero] |
| ms-productos | 8082 | prueba1 | [Compañero] |
| ms-inventario | 8083 | prueba2 | [Compañero] |
| ms-pedidos | 8084 | prueba3 | [Compañero] |
| ms-pagos | 8085 | prueba4 | [Compañero] |
| ms-envios | 8086 | prueba5 | [Compañero] |
| ms-proveedores | 8087 | prueba1 | Román Suárez |
| ms-sucursales | 8088 | prueba1 | Román Suárez |
| ms-empleados | 8089 | prueba6 | Román Suárez |
| ms-reportes | 8090 | prueba7 | Román Suárez |

## Tecnologías
- Java 17
- Spring Boot 3.4.5
- Spring Cloud OpenFeign
- MySQL 8 / Laragon
- Liquibase / Flyway / CommandLineRunner
- Lombok, Bean Validation, SLF4J

## Pasos para ejecutar

### Requisitos
- Java 17
- MySQL corriendo en puerto 3306
- Maven

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

### Orden de inicio recomendado
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

Cada microservicio se ejecuta con:
```bash
cd [carpeta-microservicio]
mvn spring-boot:run
```
