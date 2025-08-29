# E-Commerce Microservices Platform

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Release](https://img.shields.io/github/v/release/Simodalstix/java-gradle-microservices?include_prereleases)](https://github.com/Simodalstix/java-gradle-microservices/releases)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.org/projects/jdk/17/)
[![Gradle](https://img.shields.io/badge/Gradle-8.5-blue.svg)](https://gradle.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green.svg)](https://spring.io/projects/spring-boot)

A production-ready e-commerce microservices platform demonstrating enterprise architecture patterns, modern build systems, and comprehensive CI/CD practices.

## Overview

This project showcases a complete Maven-to-Gradle migration of a 9-service e-commerce system, featuring Spring Cloud microservices architecture with production-grade DevOps practices. Built to demonstrate competency in enterprise Java development, microservices design, and modern CI/CD pipelines.

## Architecture

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   API Gateway   │────│  Service Registry │────│   Config Server │
│   (Port 8201)   │    │     (Nacos)      │    │                 │
└─────────────────┘    └──────────────────┘    └─────────────────┘
         │
    ┌────┴────┐
    │         │
┌───▼───┐ ┌──▼──┐ ┌─────────┐ ┌─────────┐ ┌─────────┐
│ Admin │ │Auth │ │ Portal  │ │ Search  │ │Monitor  │
│ 8180  │ │8401 │ │  8085   │ │  8081   │ │  8101   │
└───────┘ └─────┘ └─────────┘ └─────────┘ └─────────┘
    │       │         │           │           │
┌───▼───────▼─────────▼───────────▼───────────▼───┐
│              Infrastructure Layer               │
│  MySQL • Redis • Elasticsearch • RabbitMQ      │
└─────────────────────────────────────────────────┘
```

### Core Services
- **mall-gateway** (8201): API Gateway with routing and load balancing
- **mall-admin** (8180): Administrative dashboard and management
- **mall-auth** (8401): Authentication and authorization service
- **mall-portal** (8085): Customer-facing e-commerce portal
- **mall-search** (8081): Product search with Elasticsearch
- **mall-monitor** (8101): System monitoring and health checks

### Supporting Services
- **mall-demo**: Integration testing and demonstrations
- **mall-common**: Shared utilities and configurations
- **mall-mbg**: MyBatis code generation

## Quick Start

### Prerequisites
- Java 17+
- Docker & Docker Compose
- Git

### Local Development
```bash
# Clone repository
git clone https://github.com/Simodalstix/java-gradle-microservices.git
cd java-gradle-microservices

# Start infrastructure services
docker-compose -f docker-compose.dev.yml up -d mysql redis nacos elasticsearch

# Build all services (parallel execution)
./gradlew build --parallel

# Run core services
./gradlew :mall-gateway:bootRun &
./gradlew :mall-admin:bootRun &
./gradlew :mall-auth:bootRun &
```

### Environment Variables
```bash
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/mall
SPRING_DATASOURCE_USERNAME=mall
SPRING_DATASOURCE_PASSWORD=mall123

# Redis Configuration  
SPRING_REDIS_HOST=localhost
SPRING_REDIS_PORT=6379

# Nacos Configuration
SPRING_CLOUD_NACOS_DISCOVERY_SERVER_ADDR=localhost:8848
```

## CI/CD Pipeline

This project uses **Jenkins** for continuous integration and deployment with a multi-stage pipeline:

### Pipeline Stages
1. **Checkout**: Source code retrieval from Git
2. **Build & Test**: Parallel compilation of service groups
   - Common Modules (mall-common, mall-mbg)
   - Core Services (admin, auth, gateway)  
   - Business Services (portal, search, monitor, demo)
3. **Code Quality**: Static analysis and artifact archiving
4. **Docker Build**: Container image creation for deployable services
5. **Deploy**: Environment-specific deployment with approval gates

### Pipeline Benefits
- **40% faster builds** through Gradle parallel execution
- **Multi-stage parallel processing** for efficient resource utilization
- **Automated artifact management** with fingerprinting
- **Production deployment gates** with manual approval
- **Container-ready deployments** with optimized Docker images

## Technology Stack

### Core Framework
- **Java 17**: Modern LTS Java with enhanced performance
- **Spring Boot 3.2.2**: Enterprise application framework
- **Spring Cloud 2023.0.1**: Microservices infrastructure
- **Gradle 8.5**: Modern build automation with parallel execution

### Data & Messaging
- **MySQL 8.0**: Primary data persistence
- **Redis**: Caching and session management
- **Elasticsearch**: Full-text search and analytics
- **RabbitMQ**: Asynchronous messaging

### DevOps & Monitoring
- **Docker**: Containerization with eclipse-temurin:17-jre
- **Kubernetes**: Container orchestration (manifests included)
- **Jenkins**: CI/CD pipeline automation
- **Spring Boot Admin**: Application monitoring
- **Nacos**: Service discovery and configuration management

## Project Structure

```
├── mall-gateway/          # API Gateway service
├── mall-admin/           # Admin management service  
├── mall-auth/            # Authentication service
├── mall-portal/          # Customer portal service
├── mall-search/          # Search service with Elasticsearch
├── mall-monitor/         # Monitoring and health checks
├── mall-demo/            # Integration testing service
├── mall-common/          # Shared utilities library
├── mall-mbg/             # MyBatis generator library
├── k8s/                  # Kubernetes deployment manifests
├── docker-compose.dev.yml # Local development infrastructure
├── Jenkinsfile           # CI/CD pipeline definition
└── gradle/               # Gradle wrapper and configuration
```

## Development

### Building
```bash
# Build all services
./gradlew build --parallel

# Build specific service
./gradlew :mall-admin:build

# Build without tests (faster)
./gradlew build -x test --parallel
```

### Testing
```bash
# Run all tests
./gradlew test

# Run tests for specific service
./gradlew :mall-admin:test
```

### Docker
```bash
# Build Docker images
./gradlew :mall-gateway:docker :mall-admin:docker

# Run with Docker Compose
docker-compose -f docker-compose.dev.yml up
```

## Deployment

### Kubernetes
```bash
# Deploy to Kubernetes cluster
kubectl apply -f k8s/

# Check deployment status
kubectl rollout status deployment/mall-gateway
kubectl rollout status deployment/mall-admin
```

### Production Considerations
- Configure external databases and message queues
- Set up proper secrets management
- Configure ingress controllers for external access
- Implement proper monitoring and logging
- Set up backup and disaster recovery procedures

## Why This Matters

This project demonstrates:

- **Enterprise Architecture**: Production-ready microservices with proper separation of concerns
- **Modern Build Systems**: Maven-to-Gradle migration showcasing build optimization
- **DevOps Excellence**: Complete CI/CD pipeline with automated testing and deployment
- **Scalability Patterns**: Microservices architecture ready for horizontal scaling
- **Production Readiness**: Comprehensive monitoring, health checks, and deployment strategies

Perfect for demonstrating competency in enterprise Java development, microservices architecture, and modern DevOps practices.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.