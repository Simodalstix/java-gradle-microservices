# Contributing to E-Commerce Microservices Platform

## Development Workflow

### Branch Naming
- `feature/description` - New features
- `bugfix/description` - Bug fixes  
- `hotfix/description` - Critical production fixes
- `refactor/description` - Code refactoring

### Pull Request Process

1. **Fork and Clone**
   ```bash
   git clone https://github.com/your-username/java-gradle-microservices.git
   cd java-gradle-microservices
   ```

2. **Create Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Development Standards**
   - Follow existing code style and patterns
   - Add tests for new functionality
   - Update documentation as needed
   - Ensure all tests pass: `./gradlew test`

4. **Commit Messages**
   ```
   type(scope): description
   
   Examples:
   feat(admin): add user management endpoints
   fix(gateway): resolve routing timeout issue
   docs(readme): update deployment instructions
   ```

5. **Pull Request Requirements**
   - Clear description of changes
   - Reference related issues
   - All CI checks passing
   - Code review approval

### Code Standards

- **Java**: Follow Oracle Java conventions
- **Testing**: JUnit 5 with meaningful test names
- **Documentation**: Update README for significant changes
- **Dependencies**: Justify new dependencies in PR description

### Local Development

```bash
# Setup development environment
docker-compose -f docker-compose.dev.yml up -d

# Run tests before committing
./gradlew test --parallel

# Build all services
./gradlew build --parallel
```

## Questions?

Open an issue for questions about contributing or development setup.