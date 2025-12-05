# Jenkins Pipeline Setup Guide

## Prerequisites

### Jenkins Configuration
1. **Install Required Plugins:**
   - Docker Pipeline
   - NodeJS Plugin
   - Maven Integration Plugin
   - Credentials Plugin

2. **Configure Global Tools:**
   - **NodeJS 18:** Go to Manage Jenkins → Global Tool Configuration → NodeJS
     - Name: `18`
     - Install automatically: Check
     - Version: NodeJS 18.x.x
   
   - **Maven 3.9.9:** Go to Manage Jenkins → Global Tool Configuration → Maven
     - Name: `3.9.9`
     - Install automatically: Check
     - Version: 3.9.9
   
   - **JDK 17:** Go to Manage Jenkins → Global Tool Configuration → JDK
     - Name: `17`
     - Install automatically: Check
     - Version: OpenJDK 17

3. **Setup Docker Hub Credentials:**
   - Go to Manage Jenkins → Manage Credentials
   - Add new Username/Password credential
   - ID: `dockerhub-creds`
   - Username: `saithota1207sai`
   - Password: Your Docker Hub password/token

### Docker Setup
1. Ensure Docker is installed and running on Jenkins agent
2. Run the network setup script: `setup-network.bat`

## Pipeline Features

### Stages Overview
1. **Checkout Code** - Pulls latest code from repository
2. **Build Frontend** - npm install + ng build (Angular)
3. **Build Backend** - mvn clean package (Spring Boot)
4. **Docker Build** - Parallel build of frontend and backend images
5. **Docker Hub Login** - Authenticate with Docker Hub
6. **Push Docker Images** - Push both images with build number and latest tags
7. **Deploy** - Remove old containers and deploy new ones

### Port Configuration
- **Frontend (NGINX):** Port 80
- **Backend (Spring Boot):** Port 8080

### Docker Images
- Frontend: `saithota1207sai/revcart-frontend:latest`
- Backend: `saithota1207sai/revcart-backend:latest`

## Running the Pipeline

1. Create a new Pipeline job in Jenkins
2. Configure SCM to point to your repository
3. Set Pipeline script path to `Jenkinsfile`
4. Run the pipeline

## Manual Deployment (Alternative)

If you need to deploy manually:

```bash
# Build images
docker build -t saithota1207sai/revcart-frontend:latest ./frontend
docker build -t saithota1207sai/revcart-backend:latest ./backend

# Create network
docker network create revcart-network

# Deploy backend
docker run -d --name revcart-backend -p 8080:8080 --network revcart-network saithota1207sai/revcart-backend:latest

# Deploy frontend
docker run -d --name revcart-frontend -p 80:80 --network revcart-network saithota1207sai/revcart-frontend:latest
```

## Accessing the Application

After successful deployment:
- **Frontend:** http://localhost:4200
- **Backend API:** http://localhost:8081

## Troubleshooting

### Common Issues
1. **Port conflicts:** Ensure ports 80 and 8081 are available
2. **Docker network:** Run `setup-network.bat` if containers can't communicate
3. **Build failures:** Check tool configurations in Jenkins Global Tool Configuration
4. **Permission issues:** Ensure Jenkins user has Docker permissions

### Logs
- Check Jenkins console output for build logs
- Use `docker logs revcart-frontend` and `docker logs revcart-backend` for runtime logs