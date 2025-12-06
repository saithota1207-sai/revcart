# All-in-One Docker Container

This Docker image contains:
- Frontend (Angular + Nginx) on port 4200
- Backend (Spring Boot) on port 8081
- MySQL on port 3306
- MongoDB on port 27017

## Build

```bash
build.bat
```

## Run

```bash
docker run -d -p 4200:4200 -p 8081:8081 -p 3306:3306 -p 27017:27017 --name revcart revcart-all-in-one
```

## Access

- Frontend: http://localhost:4200
- Backend API: http://localhost:8081
- MySQL: localhost:3306 (user: root, password: root, database: revcart)
- MongoDB: localhost:27017

## Logs

```bash
docker logs revcart
```

## Stop

```bash
docker stop revcart
docker rm revcart
```
