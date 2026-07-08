# Evaluador de Crédito — Banco del Austro

Programador: Jeremy Saul Rodriguez Perez

Proyecto:

- `riesgos-mock` — Microservicio B, simula score y deudas vía gRPC.
- `orquestador` — Microservicio A, expone REST al frontend, consume `riesgos-mock` por gRPC.
- `frontend` — Angular, consume el orquestador vía REST.


## Requisitos

- Java 17+, Maven
- Node.js + npm
- Docker (para levantar Postgres)

## 1. Levantar la base de datos

Desde la raíz del proyecto:

```bash
docker compose up -d postgres
```

Esto levanta Postgres 16 en el puerto `5432` con usuario `bda`, contraseña `bda`, base `bda_db`. Es necesario porque el orquestador se conecta a esta base explícita (no usa contenedores descartables) para que el comportamiento sea el mismo en cualquier máquina.

Para verificar que está corriendo:

```bash
docker compose ps
```

Para detenerla:

```bash
docker compose down
```

(los datos persisten en un volumen; si además quieres borrar los datos, usa `docker compose down -v`)

## 2. Levantar riesgos-mock (Microservicio B)

En una terminal:

```bash
cd riesgos-mock
mvn quarkus:dev
```

Queda escuchando en:
- HTTP (Dev UI): `http://localhost:8081`
- gRPC: `localhost:9001`

## 3. Levantar orquestador (Microservicio A)

**Importante:** requiere que Postgres (paso 1) y riesgos-mock (paso 2) ya estén corriendo.

En otra terminal:

```bash
cd orquestador
mvn quarkus:dev
```

Queda escuchando en `http://localhost:8082`. Endpoints:
- `POST /v1/credit-evaluations`
- `GET /v1/credit-evaluations`

## 4. Levantar el frontend

En otra terminal:

```bash
cd frontend
npm install   # solo la primera vez
npx ng serve
```

Queda escuchando en `http://localhost:4200`.

## Orden recomendado de arranque

1. `docker compose up -d postgres`
2. `riesgos-mock` (`mvn quarkus:dev`)
3. `orquestador` (`mvn quarkus:dev`)
4. `frontend` (`npx ng serve`)

Cada uno en su propia terminal, todos corriendo al mismo tiempo. Para detener cada Quarkus, `Ctrl+C` en su terminal (o `q` + enter en la consola interactiva de dev mode).
