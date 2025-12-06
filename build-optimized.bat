@echo off
echo 🏗️ Building RevCart Optimized All-in-One Docker Image...

REM Clean up existing containers and images
docker stop revcart-optimized 2>nul
docker rm revcart-optimized 2>nul

echo.
echo 📦 Building image (this may take a few minutes)...
docker build -f Dockerfile.optimized -t revcart-optimized:latest .

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✅ Build completed successfully!
    echo.
    echo 📊 Image size:
    docker images revcart-optimized:latest --format "table {{.Repository}}\t{{.Tag}}\t{{.Size}}"
    echo.
    echo 🚀 To run the container:
    echo run-optimized.bat
    echo.
    echo 🌐 Or manually:
    echo docker run -d --name revcart-optimized -p 80:80 -p 8081:8081 -p 3306:3306 -p 27017:27017 revcart-optimized:latest
) else (
    echo ❌ Build failed! Check the output above for errors.
    exit /b 1
)