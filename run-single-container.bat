@echo off
echo Building and running single container with all services...

REM Build the container
docker build -f Dockerfile.aws -t revcart-single .

REM Stop existing container
docker stop revcart-single 2>nul
docker rm revcart-single 2>nul

REM Run the single container
docker run -d ^
  --name revcart-single ^
  -p 80:80 ^
  -v revcart-mysql:/var/lib/mysql ^
  -v revcart-mongo:/data/db ^
  revcart-single

echo Container started! Access at http://localhost