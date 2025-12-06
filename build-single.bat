@echo off
echo Building single container with all services...

docker build -f Dockerfile.single -t revcart-all-in-one .

if %ERRORLEVEL% EQU 0 (
    echo Build successful!
    echo To run: docker run -d --name revcart -p 80:80 revcart-all-in-one
) else (
    echo Build failed!
)