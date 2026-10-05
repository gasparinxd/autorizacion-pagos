# Autorización de Pagos

[![docker-compose](https://github.com/gasparinxd/autorizacion-pagos/actions/workflows/docker-compose.yml/badge.svg)](https://github.com/gasparinxd/autorizacion-pagos/actions/workflows/docker-compose.yml)
[![devcontainer](https://github.com/gasparinxd/autorizacion-pagos/actions/workflows/devcontainer.yml/badge.svg)](https://github.com/gasparinxd/autorizacion-pagos/actions/workflows/devcontainer.yml)

Sistema base para la autorización de pagos.

- **backend/**: API REST con Spring Boot 4 (Java 21), PostgreSQL, RabbitMQ y documentación OpenAPI (Swagger UI).
- **frontend/**: aplicación web en React (Vite), servida con Nginx en Docker.
- **docker-compose.yml**: levanta PostgreSQL, RabbitMQ, backend y frontend a la vez.

## Requisitos

- Docker y Docker Compose (o GitHub Codespaces, ver abajo)

Para desarrollo local sin Docker: Java 21+ y Node.js 22+.

## Levantar con Docker

```bash
docker compose up --build
```

| Servicio            | URL                                     |
|---------------------|-----------------------------------------|
| Frontend            | http://localhost:3000                   |
| Backend (API)       | http://localhost:8080/api/pagos         |
| Swagger UI          | http://localhost:8080/swagger-ui.html   |
| OpenAPI (JSON)      | http://localhost:8080/v3/api-docs       |
| Health              | http://localhost:8080/actuator/health   |
| RabbitMQ Management | http://localhost:15672 (pagos / pagos)  |
| PostgreSQL          | localhost:5432                          |

Los valores de BD, RabbitMQ y puertos se leen del archivo `.env` (no versionado). Si no existe, se usan los valores por defecto: usuario/contraseña `pagos` y los puertos de la tabla.

Para detener: `docker compose down` (añade `-v` para borrar los datos).

## GitHub Codespaces

1. En GitHub: **Code → Codespaces → Create codespace on main**.
2. Al arrancar, el Codespace ejecuta `docker compose up -d --build` automáticamente (tarda 2–4 minutos la primera vez).
3. Abre la pestaña **Ports** y entra al puerto **3000 (Frontend)**. Si aparece *HTTP 401*, cambia la visibilidad del puerto a **Public**.

Si un Codespace se creó antes de un cambio en `.devcontainer.json`, usa **Codespaces: Rebuild Container** desde la paleta de comandos.

## Desarrollo local

```bash
# Infraestructura
docker compose up -d db rabbitmq

# Backend (http://localhost:8080)
cd backend
./mvnw spring-boot:run

# Frontend (http://localhost:5173, redirige /api al backend)
cd frontend
npm install
npm run dev
```

## API

| Método | Ruta              | Descripción      |
|--------|-------------------|------------------|
| GET    | `/api/pagos`      | Listar pagos     |
| GET    | `/api/pagos/{id}` | Obtener un pago  |
| POST   | `/api/pagos`      | Registrar pago   |

```bash
curl -X POST http://localhost:8080/api/pagos \
  -H "Content-Type: application/json" \
  -d '{"monto":150.75,"moneda":"USD"}'
```

## Integración continua

- `docker-compose.yml` (workflow): ejecuta `mvnw package`, levanta los cuatro servicios y comprueba BD, RabbitMQ, API, OpenAPI y frontend.
- `devcontainer.yml` (workflow): construye el devcontainer como lo hace Codespaces y verifica que el stack arranca solo.
