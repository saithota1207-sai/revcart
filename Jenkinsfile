pipeline {
    agent any
    
    environment {
        DOCKER_HUB_USERNAME = 'saithota1207sai'
        DOCKER_HUB_CREDS = credentials('dockerhub-creds')
        FRONTEND_IMAGE = "${DOCKER_HUB_USERNAME}/revcart-frontend"
        BACKEND_IMAGE = "${DOCKER_HUB_USERNAME}/revcart-backend"
        BUILD_NUMBER = "${env.BUILD_NUMBER}"
    }
    
    tools {
        nodejs '18'
        maven '3.9.9'
        jdk '17'
    }
    
    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
                echo 'Code checked out successfully'
            }
        }
        
        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    echo 'Installing frontend dependencies...'
                    bat 'npm install'
                    echo 'Building Angular application...'
                    bat 'npm run build'
                }
            }
        }
        
        stage('Build Backend') {
            steps {
                dir('backend') {
                    echo 'Building Spring Boot application...'
                    bat 'mvn clean package -DskipTests'
                }
            }
        }
        
        stage('Docker Build') {
            parallel {
                stage('Build Frontend Image') {
                    steps {
                        dir('frontend') {
                            echo 'Building frontend Docker image...'
                            bat "docker build -t ${FRONTEND_IMAGE}:${BUILD_NUMBER} -t ${FRONTEND_IMAGE}:latest ."
                        }
                    }
                }
                stage('Build Backend Image') {
                    steps {
                        dir('backend') {
                            echo 'Building backend Docker image...'
                            bat "docker build -t ${BACKEND_IMAGE}:${BUILD_NUMBER} -t ${BACKEND_IMAGE}:latest ."
                        }
                    }
                }
            }
        }
        
        stage('Docker Hub Login') {
            steps {
                echo 'Logging into Docker Hub...'
                bat "echo %DOCKER_HUB_CREDS_PSW% | docker login -u %DOCKER_HUB_CREDS_USR% --password-stdin"
            }
        }
        
        stage('Push Docker Images') {
            parallel {
                stage('Push Frontend Image') {
                    steps {
                        echo 'Pushing frontend image to Docker Hub...'
                        bat "docker push ${FRONTEND_IMAGE}:${BUILD_NUMBER}"
                        bat "docker push ${FRONTEND_IMAGE}:latest"
                    }
                }
                stage('Push Backend Image') {
                    steps {
                        echo 'Pushing backend image to Docker Hub...'
                        bat "docker push ${BACKEND_IMAGE}:${BUILD_NUMBER}"
                        bat "docker push ${BACKEND_IMAGE}:latest"
                    }
                }
            }
        }
        
        stage('Deploy') {
            steps {
                echo 'Stopping and removing existing containers...'
                bat '''
                    docker stop revcart-frontend revcart-backend || echo "No containers to stop"
                    docker rm revcart-frontend revcart-backend || echo "No containers to remove"
                '''
                
                echo 'Deploying backend container...'
                bat "docker run -d --name revcart-backend -p 8081:8081 --network revcart-network ${BACKEND_IMAGE}:latest || docker run -d --name revcart-backend -p 8081:8081 ${BACKEND_IMAGE}:latest"
                
                echo 'Deploying frontend container...'
                bat "docker run -d --name revcart-frontend -p 4200:4200 --network revcart-network ${FRONTEND_IMAGE}:latest || docker run -d --name revcart-frontend -p 4200:4200 ${FRONTEND_IMAGE}:latest"
                
                echo 'Deployment completed successfully'
            }
        }
    }
    
    post {
        always {
            echo 'Cleaning up Docker images...'
            bat '''
                docker image prune -f
                docker logout
            '''
        }
        success {
            echo 'Pipeline completed successfully!'
            echo "Frontend available at: http://localhost:4200"
            echo "Backend available at: http://localhost:8081"
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }
}