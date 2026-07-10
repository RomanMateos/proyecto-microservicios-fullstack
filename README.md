# Proyecto Microservicios — DSY1103 FullStack I

## Integrantes
* Román Suárez
* Kevin Schergel
* Carlos Labbé

## Descripción
Sistema de E-Commerce basado en una arquitectura distribuida de microservicios con Spring Boot. El proyecto considera componentes independientes y desacoplados para la gestión integral del negocio, incorporando patrones de resiliencia, descubrimiento dinámico y suite de pruebas automatizadas.

La arquitectura incorpora **Eureka Server** para el registro y descubrimiento de servicios, **API Gateway** para centralizar y enrutar los accesos a los microservicios, **OpenFeign** para la comunicación síncrona inter-servicio, **SpringDoc OpenAPI** para la auto-documentación interactiva de endpoints (Swagger), **Spring HATEOAS** para la madurez de la API (Hypermedia) y pruebas unitarias aisladas con **JUnit 5** y **Mockito**.

---

## Matriz de Arquitectura y Responsables

| Servicio | Puerto | Base de Datos (MySQL) | Formato Config. | Responsable |
| :--- | :---: | :---: | :---: | :--- |
| **eureka-server** | `8761` | No aplica | YAML | Equipo |
| **api-gateway** | `8080` | No aplica | YAML | Equipo |
| **ms-usuarios** | `8081` | `prueba1` | YAML | Carlos Labbé |
| **ms-productos** | `8082` | `prueba1` | YAML | Carlos Labbé |
| **ms-inventario** | `8083` | `prueba2` | YAML | Carlos Labbé |
| **ms-pedidos** | `8098` | `db_pedidos` | Properties | Carlos Labbé |
| **ms-pagos** | `8085` | `prueba4` | Properties | Kevin Schergel |
| **ms-envios** | `8086` | `prueba5` | YAML | Kevin Schergel |
| **ms-proveedores** | `8087` | `prueba1` | YAML | Román Suárez |
| **ms-sucursales** | `8088` | `prueba1` | YAML | Román Suárez |
| **ms-empleados** | `8089` | `prueba6` | YAML | Román Suárez |
| **ms-reportes** | `8090` | `prueba7` | YAML | Román Suárez |

---

## Tecnologías Utilizadas
* **Java 17** & **Spring Boot 3.4.x / 3.5.x**
* **Spring Cloud Navigation** (Eureka Server & Discovery Client)
* **Spring Cloud Gateway** (Enrutamiento dinámico mediante `lb://`)
* **Spring Cloud OpenFeign** (Clientes HTTP declarativos)
* **Resilience & Timeouts** (Configuración adaptativa de conexión y lectura para Feign)
* **SpringDoc OpenAPI / Swagger UI**
* **Spring HATEOAS**
* **Flyway Migration** / **Hibernate Framework**
* **JUnit 5** & **Mockito** (Pruebas unitarias de aislamiento)
* **MySQL 8** (Persistencia a través de Laragon/Native)
* **Lombok** & **Bean Validation**

---

## Funcionalidades Críticas e Implementadas

### 1. Descubrimiento y Enrutamiento Centralizado
* **Eureka Server:** Actúa como las páginas amarillas del ecosistema. Los servicios se registran de forma dinámica usando su identificador único `spring.application.name`. Acceso: `http://localhost:8761`.
* **API Gateway:** Centraliza el punto de entrada al cliente externo en el puerto `8080`, eliminando el acoplamiento a IPs físicas mediante resolución dinámica de balanceo de carga (`lb://`).
    * *Ejemplo:* `http://localhost:8080/api/v1/usuarios`

### 2. Tolerancia a Fallos y Desacoplamiento (Resiliencia)
Los microservicios clave de comunicación inter-servicio (como `ms-pagos` y `ms-pedidos`) implementan **tolerancia a fallos en la capa Feign** mediante propiedades de resiliencia localizadas para evitar bloqueos por hilos atascados:
* `connectTimeout: 3000` (Máximo 3 segundos para establecer el canal físico).
* `readTimeout: 5000` (Máximo 5 segundos para esperar el flujo de respuesta del microservicio destino).

### 3. Documentación Interactiva y HATEOAS
* Documentación autogenerada viva vía Swagger: `http://localhost:8081/doc/swagger-ui.html`
* Hypermedia como motor del estado de la aplicación mediante enlaces autodescriptivos en recursos REST.

### 4. Cobertura de Código y Pruebas Unitarias
El proyecto cuenta con suites robustas de pruebas que validan las reglas de negocio en la capa `@Service` mediante dobles de prueba (`@Mock` / `@InjectMocks`) de repositorios y clientes externos sin golpear la base de datos de producción:
* **Módulos con Cobertura:** `UsuarioServiceTest`, `PerfilServiceTest`, `ProductoServiceTest` y `PagoServiceTest`.

---

## Pasos para la Ejecución del Ecosistema

### Requisitos Previos
* Java 17 instalado correctamente (`java -version`).
* Motor de MySQL activo en el puerto estándar `3306`.
* Instancias de Base de Datos creadas de manera previa en el motor SQL:

```sql
CREATE DATABASE prueba1;
CREATE DATABASE prueba2;
CREATE DATABASE db_pedidos;     -- Base de datos actualizada para Pedidos
CREATE DATABASE prueba4;
CREATE DATABASE prueba5;
CREATE DATABASE prueba6;
CREATE DATABASE prueba7;