@echo off
echo Building backend...
cd backend
call mvn clean package -DskipTests
cd ..

echo Starting Docker Compose...
docker-compose up -d

echo.
echo Services starting...
echo Frontend: http://localhost:4200
echo Backend: http://localhost:8081
echo MySQL: localhost:3306 (root/root)
echo MongoDB: localhost:27017
echo.
echo View logs: docker-compose logs -f
echo Stop: docker-compose down
