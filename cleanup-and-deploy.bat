@echo off
echo Cleaning up existing containers and images...

REM Stop and remove containers
docker stop revcart-frontend revcart-backend 2>nul
docker rm revcart-frontend revcart-backend 2>nul

REM Remove old images (optional)
docker rmi saithota1207sai/revcart-frontend:latest 2>nul
docker rmi saithota1207sai/revcart-backend:latest 2>nul

REM Create network if not exists
docker network create revcart-network 2>nul

echo Cleanup completed. You can now run your Jenkins pipeline.
echo.
echo If you want to manually deploy:
echo 1. Build images: docker build -t saithota1207sai/revcart-frontend:latest frontend/
echo 2. Build backend: docker build -t saithota1207sai/revcart-backend:latest backend/
echo 3. Run backend: docker run -d --name revcart-backend -p 8081:8081 --network revcart-network saithota1207sai/revcart-backend:latest
echo 4. Run frontend: docker run -d --name revcart-frontend -p 4200:4200 --network revcart-network saithota1207sai/revcart-frontend:latest

pause