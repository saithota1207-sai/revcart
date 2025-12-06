@echo off
setlocal enabledelayedexpansion

if "%1"=="" (
    echo 🐳 RevCart Container Management
    echo.
    echo Usage: manage-container.bat [command]
    echo.
    echo Commands:
    echo   build     - Build the optimized all-in-one image
    echo   start     - Start the container
    echo   stop      - Stop the container
    echo   restart   - Restart the container
    echo   logs      - Show container logs
    echo   status    - Show service status
    echo   shell     - Open shell in container
    echo   clean     - Remove container and volumes
    echo   health    - Check service health
    echo.
    exit /b 0
)

set CONTAINER_NAME=revcart-optimized
set IMAGE_NAME=revcart-optimized:latest

if "%1"=="build" (
    echo 🏗️ Building image...
    call build-optimized.bat
    goto :eof
)

if "%1"=="start" (
    echo 🚀 Starting container...
    call run-optimized.bat
    goto :eof
)

if "%1"=="stop" (
    echo 🛑 Stopping container...
    docker stop %CONTAINER_NAME%
    echo Container stopped.
    goto :eof
)

if "%1"=="restart" (
    echo 🔄 Restarting container...
    docker restart %CONTAINER_NAME%
    echo Container restarted.
    goto :eof
)

if "%1"=="logs" (
    echo 📋 Container logs:
    docker logs -f %CONTAINER_NAME%
    goto :eof
)

if "%1"=="status" (
    echo 📊 Service status:
    docker exec %CONTAINER_NAME% supervisorctl status 2>nul || echo "Container not running or not accessible"
    goto :eof
)

if "%1"=="shell" (
    echo 🐚 Opening shell in container...
    docker exec -it %CONTAINER_NAME% /bin/bash
    goto :eof
)

if "%1"=="clean" (
    echo 🧹 Cleaning up container and volumes...
    docker stop %CONTAINER_NAME% 2>nul
    docker rm %CONTAINER_NAME% 2>nul
    docker volume rm revcart-mysql-data revcart-mongodb-data 2>nul
    echo Cleanup completed.
    goto :eof
)

if "%1"=="health" (
    echo 🏥 Checking service health...
    docker exec %CONTAINER_NAME% /health-check.sh
    if !ERRORLEVEL! EQU 0 (
        echo ✅ All services are healthy
    ) else (
        echo ❌ Some services are not responding
    )
    goto :eof
)

echo ❌ Unknown command: %1
echo Run without arguments to see available commands.
exit /b 1