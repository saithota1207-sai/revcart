pipeline {
    agent any
    
    environment {
        DOCKER_HUB_USERNAME = 'saithota1207sai'
        DOCKER_HUB_CREDS = credentials('dockerhub-creds')
        ALL_IN_ONE_IMAGE = "${DOCKER_HUB_USERNAME}/revcart-all-in-one"
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
        
        stage('Test Backend') {
            steps {
                dir('backend') {
                    echo 'Running backend tests...'
                    bat 'mvn test'
                }
            }
        }
        
        stage('Build All-in-One Image') {
            steps {
                echo 'Building all-in-one Docker image...'
                bat "docker build -f Dockerfile.aws -t ${ALL_IN_ONE_IMAGE}:${BUILD_NUMBER} -t ${ALL_IN_ONE_IMAGE}:latest ."
            }
        }
        
        stage('Docker Hub Login') {
            steps {
                echo 'Logging into Docker Hub...'
                bat "echo %DOCKER_HUB_CREDS_PSW% | docker login -u %DOCKER_HUB_CREDS_USR% --password-stdin"
            }
        }
        
        stage('Push Docker Image') {
            steps {
                echo 'Pushing all-in-one image to Docker Hub...'
                bat "docker push ${ALL_IN_ONE_IMAGE}:${BUILD_NUMBER}"
                bat "docker push ${ALL_IN_ONE_IMAGE}:latest"
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    echo 'Stopping and removing existing container...'
                    bat 'docker stop revcart-all-in-one 2>nul || echo "No container to stop"'
                    bat 'docker rm revcart-all-in-one 2>nul || echo "No container to remove"'
                    
                    echo 'Creating volumes for data persistence...'
                    bat 'docker volume create revcart-data 2>nul || echo "Volume exists"'
                    
                    echo 'Deploying all-in-one container...'
                    bat "docker run -d --name revcart-all-in-one -p 80:80 -v revcart-data:/var/lib/mysql --restart unless-stopped ${ALL_IN_ONE_IMAGE}:latest"
                    
                    echo 'Deployment completed successfully'
                }
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
            echo "Application available at: http://localhost"
            echo "Ready for AWS deployment with image: ${ALL_IN_ONE_IMAGE}:latest"
        }
        failure {
            echo 'Pipeline failed. Check logs for details.'
        }
    }
}