@echo off
echo Starting combined RevCart application...

echo Stopping existing container...
docker stop revcart-app 2>nul
docker rm revcart-app 2>nul

echo Starting combined container...
docker run -d --name revcart-app -p 80:80 -p 8081:8081 sai2017sai/revcart-app:latest

echo Application started!
echo Frontend and Backend available at: http://localhost