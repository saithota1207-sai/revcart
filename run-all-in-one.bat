@echo off
echo Starting RevCart All-in-One Container...

REM Stop and remove existing container if running
docker stop revcart-all-in-one 2>nul
docker rm revcart-all-in-one 2>nul

REM Run the all-in-one container
docker run -d ^
    --name revcart-all-in-one ^
    -p 80:80 ^
    -p 8081:8081 ^
    -p 3306:3306 ^
    -p 27017:27017 ^
    -v revcart-mysql-data:/var/lib/mysql ^
    -v revcart-mongodb-data:/data/db ^
    revcart-all-in-one:latest

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Container started successfully!
    echo.
    echo Waiting for services to initialize...
    timeout /t 30 /nobreak >nul
    echo.
    echo 🌐 Application URLs:
    echo Frontend: http://localhost
    echo Backend API: http://localhost:8081
    echo.
    echo 💾 Database Connections:
    echo MySQL: localhost:3306 (user: root, password: root)
    echo MongoDB: localhost:27017
    echo.
    echo 📋 Container Management:
    echo View logs: docker logs revcart-all-in-one
    echo Stop container: docker stop revcart-all-in-one
    echo Remove container: docker rm revcart-all-in-one
) else (
    echo ❌ Failed to start container!
    exit /b 1
)