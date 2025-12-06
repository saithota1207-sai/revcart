@echo off
echo 🚀 Starting RevCart Optimized All-in-One Container...

REM Stop and remove existing container
docker stop revcart-optimized 2>nul
docker rm revcart-optimized 2>nul

REM Create volumes for data persistence
docker volume create revcart-mysql-data 2>nul
docker volume create revcart-mongodb-data 2>nul

echo.
echo 🐳 Starting container with persistent data volumes...
docker run -d ^
    --name revcart-optimized ^
    -p 80:80 ^
    -p 8081:8081 ^
    -p 3306:3306 ^
    -p 27017:27017 ^
    -v revcart-mysql-data:/var/lib/mysql ^
    -v revcart-mongodb-data:/data/db ^
    --restart unless-stopped ^
    revcart-optimized:latest

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Container started successfully!
    echo.
    echo ⏳ Waiting for services to initialize (this may take 1-2 minutes)...
    
    REM Wait for services to be ready
    :wait_loop
    timeout /t 5 /nobreak >nul
    docker exec revcart-optimized /health-check.sh >nul 2>&1
    if %ERRORLEVEL% NEQ 0 (
        echo Still initializing...
        goto wait_loop
    )
    
    echo.
    echo 🎉 All services are ready!
    echo.
    echo 🌐 Application URLs:
    echo   Frontend: http://localhost
    echo   Backend API: http://localhost:8081
    echo   Health Check: http://localhost:8081/actuator/health
    echo.
    echo 💾 Database Connections:
    echo   MySQL: localhost:3306 (user: root, password: root, database: revcart_db)
    echo   MongoDB: localhost:27017 (database: revcart_db)
    echo.
    echo 📋 Container Management:
    echo   View logs: docker logs revcart-optimized
    echo   Follow logs: docker logs -f revcart-optimized
    echo   Stop: docker stop revcart-optimized
    echo   Restart: docker restart revcart-optimized
    echo   Remove: docker rm revcart-optimized
    echo.
    echo 🔍 Service Status:
    docker exec revcart-optimized supervisorctl status
) else (
    echo ❌ Failed to start container!
    echo.
    echo 🔍 Checking if image exists...
    docker images revcart-optimized:latest
    echo.
    echo 💡 If image doesn't exist, run: build-optimized.bat
    exit /b 1
)