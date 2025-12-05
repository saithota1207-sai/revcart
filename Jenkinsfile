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
        
        stage('Test') {
            parallel {
                stage('Frontend Tests') {
                    steps {
                        dir("${FRONTEND_DIR}") {
                            bat 'npm test -- --watch=false --browsers=ChromeHeadless'
                        }
                    }
                }
                stage('Backend Tests') {
                    steps {
                        dir("${BACKEND_DIR}") {
                            bat 'mvn test'
                        }
                    }
                }
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    // Stop existing processes
                    bat '''
                        taskkill /F /IM java.exe /T 2>nul || echo "No Java processes to kill"
                        taskkill /F /IM node.exe /T 2>nul || echo "No Node processes to kill"
                    '''
                    
                    // Deploy Frontend
                    dir("${FRONTEND_DIR}") {
                        bat 'start /B npm start'
                    }
                    
                    // Deploy Backend
                    dir("${BACKEND_DIR}") {
                        bat 'start /B mvn spring-boot:run'
                    }
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