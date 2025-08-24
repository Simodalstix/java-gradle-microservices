pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY = 'your-registry.com'
        GRADLE_OPTS = '-Dorg.gradle.daemon=false -Dorg.gradle.parallel=true'
        GRADLE_USER_HOME = '.gradle'
    }
    
    tools {
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
                        sh 'chmod +x gradlew'
                        sh './gradlew :mall-common:build :mall-mbg:build -x test'
                    }
                }
                stage('Core Services') {
                    steps {
                        sh './gradlew :mall-admin:build :mall-auth:build :mall-gateway:build -x test'
                    }
                }
                stage('Business Services') {
                    steps {
                        sh './gradlew :mall-portal:build :mall-search:build :mall-monitor:build :mall-demo:build -x test'
                    }
                }
            }
        }
        
        stage('Code Quality') {
            steps {
                sh './gradlew check -x test'
                archiveArtifacts artifacts: '**/build/libs/*.jar', fingerprint: true
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