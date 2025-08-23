pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY = 'your-registry.com'
        GRADLE_OPTS = '-Dorg.gradle.daemon=false'
    }
    
    tools {
        gradle 'gradle-8.5'
        jdk 'openjdk-17'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build & Test') {
            parallel {
                stage('Common Modules') {
                    steps {
                        sh './gradlew :mall-common:build :mall-mbg:build'
                    }
                }
                stage('Services') {
                    steps {
                        sh './gradlew :mall-admin:build :mall-auth:build :mall-gateway:build'
                    }
                }
                stage('Additional Services') {
                    steps {
                        sh './gradlew :mall-portal:build :mall-search:build :mall-monitor:build :mall-demo:build'
                    }
                }
            }
        }
        
        stage('Code Quality') {
            steps {
                sh './gradlew check'
            }
        }
        
        stage('Docker Build') {
            when {
                anyOf {
                    branch 'main'
                    branch 'develop'
                }
            }
            parallel {
                stage('Core Services') {
                    steps {
                        sh './gradlew :mall-gateway:docker :mall-auth:docker'
                    }
                }
                stage('Business Services') {
                    steps {
                        sh './gradlew :mall-admin:docker :mall-portal:docker'
                    }
                }
                stage('Support Services') {
                    steps {
                        sh './gradlew :mall-search:docker :mall-monitor:docker'
                    }
                }
            }
        }
        
        stage('Deploy to Dev') {
            when {
                branch 'develop'
            }
            steps {
                script {
                    sh '''
                        docker-compose -f docker-compose.dev.yml down
                        docker-compose -f docker-compose.dev.yml up -d
                    '''
                }
            }
        }
        
        stage('Deploy to Prod') {
            when {
                branch 'main'
            }
            steps {
                input message: 'Deploy to production?', ok: 'Deploy'
                script {
                    sh '''
                        kubectl apply -f k8s/
                        kubectl rollout status deployment/mall-gateway
                        kubectl rollout status deployment/mall-admin
                    '''
                }
            }
        }
    }
    
    post {
        always {
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'build/reports/tests/test',
                reportFiles: 'index.html',
                reportName: 'Test Report'
            ])
        }
        failure {
            emailext (
                subject: "Build Failed: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: "Build failed. Check console output at ${env.BUILD_URL}",
                to: "${env.CHANGE_AUTHOR_EMAIL}"
            )
        }
    }
}