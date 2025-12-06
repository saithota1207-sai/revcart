@echo off
echo 🚀 AWS Deployment Script for RevCart All-in-One

echo.
echo 📦 Step 1: Build and push image to Docker Hub
call build-optimized.bat
if %ERRORLEVEL% NEQ 0 exit /b 1

echo.
echo 🐳 Step 2: Push to Docker Hub
docker tag revcart-optimized:latest saithota1207sai/revcart-all-in-one:latest
docker push saithota1207sai/revcart-all-in-one:latest

echo.
echo ☁️ Step 3: AWS Deployment Options
echo.
echo Choose deployment method:
echo 1. EC2 Instance (Simple)
echo 2. ECS Fargate (Managed)
echo 3. Elastic Beanstalk (Easy)
echo.
set /p choice="Enter choice (1-3): "

if "%choice%"=="1" goto ec2
if "%choice%"=="2" goto ecs
if "%choice%"=="3" goto eb
echo Invalid choice
exit /b 1

:ec2
echo.
echo 🖥️ EC2 Deployment Commands:
echo.
echo 1. Launch EC2 instance with Docker installed
echo 2. SSH to instance and run:
echo    docker run -d --name revcart -p 80:80 -v /data/mysql:/var/lib/mysql --restart unless-stopped saithota1207sai/revcart-all-in-one:latest
echo.
echo 3. Configure security group to allow port 80
goto end

:ecs
echo.
echo 🐋 ECS Fargate Deployment:
echo.
echo 1. Create ECS cluster
echo 2. Register task definition using aws-ecs-task.json
echo 3. Create service with task definition
echo.
echo Commands:
echo aws ecs create-cluster --cluster-name revcart-cluster
echo aws ecs register-task-definition --cli-input-json file://aws-ecs-task.json
echo aws ecs create-service --cluster revcart-cluster --service-name revcart-service --task-definition revcart-all-in-one --desired-count 1
goto end

:eb
echo.
echo 🌱 Elastic Beanstalk Deployment:
echo.
echo 1. Create Dockerrun.aws.json:
echo {
echo   "AWSEBDockerrunVersion": "1",
echo   "Image": {
echo     "Name": "saithota1207sai/revcart-all-in-one:latest"
echo   },
echo   "Ports": [{"ContainerPort": "80"}]
echo }
echo.
echo 2. Deploy using EB CLI:
echo eb init
echo eb create revcart-env
echo eb deploy
goto end

:end
echo.
echo ✅ Image ready for AWS deployment!
echo Image: saithota1207sai/revcart-all-in-one:latest