# AGENTS.md - UserService Development Guide

This is a microservice within an E-Commerce system responsible for managing user profiles and addresses. Read this before making changes.

## Architecture Overview

**UserService** is a Spring Boot 3.5.13 REST microservice integrated into a microservices architecture via Eureka service discovery.

### Core Pattern: Service → ServiceImpl → Repository → Entity → DTO
- **Entity Layer** (`entity/`): JPA-mapped database models (User, Address)
- **Repository Layer** (`repo/`): Spring Data JPA repositories for database access
- **Service Interface** (`Service/`): Abstract contract for business logic
- **Service Implementation** (`ServiceImpl/`): Concrete business logic with Lombok-generated boilerplate
- **DTO Layer** (`dto/`): Data Transfer Objects for API requests/responses

### Key Design Decisions
1. **One-to-One User-Address Relationship**: User entity has a OneToOne relationship with Address (not a collection). Address updates are tied to specific users.
2. **Lombok Boilerplate Reduction**: All entities use `@Getter`, `@Setter`, `@AllArgsConstructor`, `@NoArgsConstructor` to eliminate getters/setters.
3. **DTOs for Requests**: Request/response objects are separate from JPA entities (e.g., `updateAddressRequestDto`, `AddressDto`).
4. **Eureka Integration**: Service registers with Eureka at startup for dynamic service discovery.

## Tech Stack

| Component | Version | Notes |
|-----------|---------|-------|
| Java | 17 | Required for Spring Boot 3.5.13 |
| Spring Boot | 3.5.13 | Parent POM provides dependency management |
| Spring Data JPA | Latest (from parent) | ORM layer |
| MySQL | Runtime via mysql-connector-j | Database driver |
| Lombok | Latest (from parent) | Annotation processing configured in maven-compiler-plugin |
| Spring Cloud | 2025.0.2 | Eureka client for service discovery |
| Spring Security | Latest (from parent) | Authentication (default credentials: brajesh/brajesh) |

## Build & Development

### Maven Commands
```bash
# Compile with Lombok annotation processing
mvn clean compile

# Run tests (Spring Security Test included)
mvn test

# Build JAR
mvn clean package

# Run application (DevTools enabled for hot reload)
mvn spring-boot:run
```

### Critical Configuration (application.yml)
- **MySQL**: localhost:3306/user-service (auto-creates DB if missing)
- **Eureka**: http://localhost:8761/eureka/ (must be running)
- **Port**: 8081
- **Security**: Default user `brajesh`/`brajesh`
- **JPA**: DDL auto-update enabled, SQL logging enabled for debugging
- **DevTools**: Hot reload enabled on classpath changes

## Current State & Incomplete Areas

⚠️ **Important**: Service methods are stub implementations returning `null`:
- `createUser()` - Not implemented
- `getUserInfo()` - Not implemented  
- `updateAddress()` - Not implemented

The `updateAddressRequestDto.java` file is **incomplete/corrupted** (line 16 is malformed). Needs fixing before use.

## Package Structure & Conventions

```
com.user
├── UserServiceApplication        Main entry point (@SpringBootApplication)
├── entity/                        JPA entities with Lombok annotations
│   ├── User                       Primary domain model
│   └── Address                    Related entity (OneToOne with User)
├── repo/                          Spring Data JPA repositories
│   └── UserRepo                   Extends JpaRepository<User, Long>
├── Service/                       Interface defining contracts
│   └── UserService                Interface for user operations
├── ServiceImpl/                    Implementation with @Service annotation
│   └── UserServiceImpl             Uses @RequiredArgsConstructor for DI
└── dto/                           Request/response objects
    ├── AddressDto                 For address data transfer
    └── updateAddressRequestDto    For update operations (NEEDS FIXING)
```

**Naming Conventions to Follow**:
- Entities: PascalCase (User, Address)
- Repository interfaces: {Entity}Repo (UserRepo)
- Service interfaces: {Entity}Service in `Service/` package
- Service implementations: {Entity}ServiceImpl in `ServiceImpl/` package with `@Service` + `@Slf4j` + `@RequiredArgsConstructor`
- DTOs: camelCaseDto or {action}RequestDto in `dto/` package

## Dependency Injection Pattern

UserService uses **constructor injection with Lombok**:
```java
@Service
@RequiredArgsConstructor  // Generates constructor from final fields
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;  // Must be final for @RequiredArgsConstructor
    // ...
}
```

**Note**: Current UserServiceImpl uses non-final `userRepo` field - breaks the pattern. Should be fixed to `private final UserRepo userRepo;`.

## Database & JPA Conventions

- **ID Strategy**: `@GeneratedValue(strategy = GenerationType.IDENTITY)` for auto-increment
- **Table Names**: `@Table(name = "users")`, `@Table(name = "addresses")` (lowercase, plural)
- **Unique Constraints**: Email is unique in User entity
- **Nullable Constraints**: Username is NOT NULL
- **Relationships**: OneToOne between User and Address (no cascade settings defined - be careful)

## Data Flow Example: updateAddress

Expected flow (currently returns null):
1. Client sends `updateAddressRequestDto` (userId, street, city, etc.)
2. `UserServiceImpl.updateAddress()` receives request
3. Retrieve User entity via `userRepo.findById(userId)`
4. Create/update Address entity from DTO
5. Persist via `userRepo.save(user)`
6. Return `ResponseEntity` with updated user or address

## Integration Points

### Eureka Service Discovery
- Registered service name: "UserService" (from application.yml)
- Other microservices can discover this service at: `http://UserService/...`
- Ensure Eureka server is running on http://localhost:8761/eureka/

### External Dependencies (Incomplete)
- No REST clients defined yet for calling other services
- May need to add Feign/RestTemplate for cross-service communication

## Common Tasks

### Adding a New User Endpoint
1. Add method to `UserService` interface
2. Implement in `UserServiceImpl` with business logic
3. Inject `UserRepo` via constructor (already injected)
4. Use `userRepo.save()`, `userRepo.findById()`, etc.
5. Create corresponding DTO if request/response needs transformation
6. Controller layer needs to be created (currently missing)

### Querying Users
- `UserRepo` extends `JpaRepository<User, Long>` - has default methods
- For custom queries, add `@Query` or method name conventions (e.g., `findByEmail(String email)`)

### Updating JPA Configuration
- DDL auto-update is enabled - schema auto-generates
- Change `hibernate.ddl-auto` to `validate` in production
- SQL logging (`show-sql: true`) should be disabled in production

## Lombok Annotation Processing
Compilation requires annotation processor configuration (see pom.xml lines 115-137). If adding new annotated classes, ensure Maven builds include Lombok processor in both compile and test phases.

## Security Considerations

⚠️ **Current Issues**:
- Default security credentials hardcoded in application.yml
- No JWT/OAuth2 implementation visible
- `spring-security-test` available but no security tests seen
- Spring Security is included but likely needs controller-level protection

These should be addressed before production.

## Testing

Test infrastructure in place:
- `spring-boot-starter-test` for unit/integration tests  
- `spring-security-test` for security testing
- MySQL/DevTools enable rapid test cycles

Create tests in `src/test/java/com/user/` following the project structure.

