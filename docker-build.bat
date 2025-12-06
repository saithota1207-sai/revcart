@echo off
echo Building Docker images...

echo Building backend image...
cd backend
docker build -t revcart-backend .
cd ..

echo Building frontend image...
cd frontend
docker build -t revcart-frontend .
cd ..

echo Docker images built successfully!
docker images | findstr revcart