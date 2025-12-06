@echo off
echo Building RevCart All-in-One Docker Image...

REM Stop and remove existing container if running
docker stop revcart-all-in-one 2>nul
docker rm revcart-all-in-one 2>nul

REM Build the all-in-one image
docker build -f Dockerfile.all-in-one -t revcart-all-in-one:latest .

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Build completed successfully!
    echo.
    echo To run the container:
    echo docker run -d --name revcart-all-in-one -p 80:80 -p 8081:8081 -p 3306:3306 -p 27017:27017 revcart-all-in-one:latest
    echo.
    echo Access your application at:
    echo Frontend: http://localhost
    echo Backend API: http://localhost:8081
    echo MySQL: localhost:3306
    echo MongoDB: localhost:27017
) else (
    echo ❌ Build failed!
    exit /b 1
)