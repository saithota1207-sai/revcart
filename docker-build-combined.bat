@echo off
echo Building combined RevCart Docker image...

docker build -t sai2017sai/revcart-app:latest .

echo Combined Docker image built successfully!
docker images | findstr revcart-app