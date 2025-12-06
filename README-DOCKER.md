# Docker Compose Setup

This setup uses separate containers:
- **MySQL** (mysql:8.0) on port 3306
- **MongoDB** (mongo:7.0) on port 27017
- **App** (Frontend + Backend) on ports 4200 and 8081

## Quick Start

```bash
docker-run.bat
```

## Manual Steps

```bash
# Build backend
cd backend
mvn clean package -DskipTests
cd ..

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

## Access

- Frontend: http://localhost:4200
- Backend API: http://localhost:8081
- MySQL: localhost:3306 (root/root, database: revcart_db)
- MongoDB: localhost:27017

## Container Management

```bash
# View status
docker-compose ps

# Restart services
docker-compose restart

# Stop and remove (keeps data)
docker-compose down

# Stop and remove with data
docker-compose down -v
```
