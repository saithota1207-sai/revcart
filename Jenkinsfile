pipeline {
    agent any
    
    tools {
        nodejs 'NodeJS'
        maven 'Maven'
        jdk 'JDK17'
    }
    
    environment {
        FRONTEND_DIR = 'frontend'
        BACKEND_DIR = 'backend'
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saithota1207-sai/revcart.git'
            }
        }
        
        stage('Build Frontend') {
            steps {
                dir("${FRONTEND_DIR}") {
                    bat 'npm install'
                    bat 'npm run build'
                }
            }
        }
        
        stage('Build Backend') {
            steps {
                dir("${BACKEND_DIR}") {
                    bat 'mvn clean compile'
                    bat 'mvn package -DskipTests'
                }
            }
        }
        
        stage('Test Backend') {
            steps {
                dir("${BACKEND_DIR}") {
                    script {
                        try {
                            bat 'mvn test'
                        } catch (Exception e) {
                            echo "Tests failed but continuing with deployment: ${e.getMessage()}"
                            currentBuild.result = 'UNSTABLE'
                        }
                    }
                }
            }
        }
        
        stage('Deploy Frontend') {
            steps {
                dir("${FRONTEND_DIR}") {
                    bat 'start /B npm start'
                }
            }
        }
        
        stage('Deploy Backend') {
            steps {
                dir("${BACKEND_DIR}") {
                    bat 'start /B mvn spring-boot:run'
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}