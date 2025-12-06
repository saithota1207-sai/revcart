@echo off
echo Stopping RevCart application...

docker stop revcart-frontend revcart-backend
docker rm revcart-frontend revcart-backend
docker network rm revcart-network 2>nul

echo Application stopped!