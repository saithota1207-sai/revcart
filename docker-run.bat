@echo off
echo Starting RevCart application...

echo Creating network...
docker network create revcart-network 2>nul

echo Stopping existing containers...
docker stop revcart-backend revcart-frontend 2>nul
docker rm revcart-backend revcart-frontend 2>nul

echo Starting backend...
docker run -d --name revcart-backend -p 8081:8081 --network revcart-network revcart-backend

echo Waiting for backend to start...
timeout /t 10 /nobreak

echo Starting frontend...
docker run -d --name revcart-frontend -p 4200:4200 --network revcart-network revcart-frontend

echo Application started!
echo Frontend: http://localhost:4200
echo Backend: http://localhost:8081