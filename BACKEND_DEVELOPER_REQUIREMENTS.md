# Backend Developer - Velocity App (QTO Platform)

## About the Role

We're seeking an experienced **Backend Developer** to build, test, and deploy the Velocity App (internally: **QTO - Quantum Task Orchestrator**), an enterprise service orchestration and order management platform managing telecommunications and cybersecurity services lifecycle.

---

## Project Overview

**QTO Platform** is a mature enterprise application managing:
- Multi-location order provisioning and tracking
- 15+ service types (DIA, Broadband, Ethernet, MPLS, UCaaS, MDR Cybersecurity, etc.)
- Service activation scheduling and lifecycle management
- Inventory management and billing disputes
- Invoice generation and financial tracking

**Scale**: 140+ JPA entities, 90+ REST endpoints, multi-tenant architecture

---

## Core Responsibilities

### 1. Building & Development

**Backend Services**:
- Develop and maintain Spring Boot backend services for service provisioning, activation, and lifecycle management
- Design and implement RESTful APIs using Spring MVC for order management, inventory, invoicing, and dispute resolution
- Build Spring-managed services following Manager/Service patterns for complex business logic
- Create JPA/Hibernate entity models with complex relationships and inheritance hierarchies
- Implement type-safe database queries using QueryDSL and JPA Criteria API
- Develop scheduled jobs using Quartz for background processing (disconnections, snapshots, cleanup)
- Build multi-tenant features with platform-level tenant isolation
- Implement authentication and user management

**Database & Persistence**:
- Design and maintain PostgreSQL database schema with 140+ tables
- Create and manage Liquibase migration scripts for version-controlled schema changes
- Optimize database queries and implement efficient data access patterns
- Develop database views for reporting and analytics

**Integration Development**:
- Implement Spring Security authentication flows
- Develop integrations with FTDI field services and CRM/Dataverse systems
- Implement JMS queue processing for asynchronous bulk import operations

**Security & Authorization**:
- Implement permission-based access control using Apache Shiro
- Configure Spring Security authorization
- Secure REST endpoints with role-based permissions
- Manage JWT token validation and session management

### 2. Testing & Quality Assurance

**Critical Testing Gap - Major Opportunity**:
- **Build comprehensive backend test suite** (currently minimal coverage)
- Write unit tests for Manager/Service layer using JUnit 5 and Mockito
- Develop integration tests for Spring-managed components using Spring Boot Test
- Create REST API endpoint tests using REST Assured
- Implement database integration tests with Testcontainers (PostgreSQL)
- Achieve minimum 70% code coverage across core business logic
- Perform code reviews ensuring test quality and maintainability
- Debug and troubleshoot complex multi-tier application issues

**Quality Standards**:
- Validate data integrity across complex entity relationships
- Ensure transaction consistency with Spring's declarative transaction management
- Verify multi-tenant data isolation and security boundaries

### 3. Deployment & Operations

**Application Deployment**:
- Deploy backend services (Gradle build, Spring Boot or similar container)
- Configure data sources and application resources
- Coordinate releases with frontend deployments

**Database Operations**:
- Execute Liquibase migrations for schema updates across environments
- Monitor database performance and optimize queries
- Manage database backups and disaster recovery procedures

**Monitoring & Troubleshooting**:
- Monitor application server logs and health metrics
- Investigate and resolve production issues with Spring, JPA, and REST services
- Optimize application performance and resource utilization
- Coordinate with frontend team on API contract changes

**CI/CD Pipeline Development** (Opportunity Area):
- Implement automated build pipelines (Gradle + Bun/npm builds)
- Configure automated testing in CI environment
- Set up code quality analysis (SonarQube)
- Implement security vulnerability scanning for dependencies
- Automate deployment workflows to staging and production

---

## Required Technical Skills

### Core Java Stack
- **Java 8-11+** (production uses Java 11)
- **Spring Boot / Spring Framework** expertise:
  - Spring MVC for REST API development
  - Spring-managed services and dependency injection
  - JPA 2.2 / Hibernate ORM (complex entity relationships, inheritance strategies)
  - Spring Data JPA and declarative transactions
- **Gradle** multi-module project management
- **SQL proficiency** with PostgreSQL or similar relational databases

### Build & Runtime
- **Gradle** for build and dependency management
- Experience with Spring Boot or similar embedded server
- Understanding of datasource configuration and JPA

### Essential Frameworks & Libraries
- **QueryDSL 5.x** for type-safe database queries
- **Liquibase or Flyway** for database version control
- **Apache Shiro or Spring Security** for authentication/authorization
- **Jackson** for JSON serialization/deserialization
- **Quartz Scheduler** for background job scheduling
- **Apache HttpClient** for external API integration

### Security & Authentication
- **OAuth 2.0 / OpenID Connect** authentication flows
- **Spring Security** integration
- **JWT token validation** and session management
- Permission-based access control patterns

### Development Practices
- RESTful API design with pagination, filtering, and export capabilities
- Multi-tier enterprise architecture (Presentation, API, Business Logic, Data Access)
- DAO/Repository pattern with Manager/Service layer separation
- Database version control and migration strategies
- Transaction management and data consistency patterns

---

## Strong Nice-to-Have Skills

### Advanced Integrations
- **User and group management**
- JMS/Message queues for asynchronous processing
- Apache POI for Excel report generation
- Freemarker or similar templating engines for emails

### Architecture & Operations
- Multi-tenant SaaS architecture patterns
- Microservices architecture and migration strategies
- Docker/Kubernetes for containerization
- CI/CD pipeline tools (Jenkins, GitLab CI, GitHub Actions)
- API documentation with Swagger/OpenAPI

### Testing Expertise
- **Spring Boot Test** for integration testing
- **REST Assured** for API testing
- **Testcontainers** for database integration tests
- **JaCoCo** for code coverage analysis
- **Mockito** for unit test mocking

---

## Domain Knowledge

### Business Context
- **Telecommunications**: Service provisioning workflows, activation processes, inventory management
- **Enterprise B2B Systems**: Complex organizational hierarchies, multi-tenant platforms
- **Order Management**: Multi-location orders, service lifecycle tracking, milestone-based workflows
- **Financial Systems**: Invoicing, billing, surcharges, dispute management

### System Features You'll Work On
- Order and service provisioning workflows
- Activation scheduling with engineer assignment
- Service inventory and location management
- Jeopardy (JEOP) tracking and issue resolution
- Invoice generation with complex charge calculations
- Dispute tracking and resolution workflows
- File attachment and document management
- Email notification system with dynamic templates
- Custom field system for flexible data models
- Reporting and analytics views

---

## Success Criteria

### Performance & Reliability
- ✅ Deliver reliable backend services with 99.9% uptime
- ✅ Maintain API response times <200ms for critical endpoints
- ✅ Ensure data integrity across complex multi-entity transactions
- ✅ Zero data loss during deployments and migrations

### Code Quality
- ✅ Achieve 70%+ test coverage for new backend code
- ✅ Write maintainable, well-documented code following project conventions
- ✅ Conduct thorough code reviews with constructive feedback
- ✅ Reduce technical debt through refactoring and modernization

### Deployment Excellence
- ✅ Execute smooth zero-downtime deployments
- ✅ Successfully manage database migrations without data loss
- ✅ Respond to production issues within defined SLA
- ✅ Implement monitoring and alerting for proactive issue detection

### Innovation & Improvement
- ✅ Identify and implement performance optimizations
- ✅ Modernize legacy code patterns while maintaining stability
- ✅ Contribute to CI/CD pipeline development
- ✅ Propose architectural improvements for scalability

---

## Team Collaboration

- Work closely with **Frontend Team** (Angular 16) on API contracts and integration
- Collaborate with **QA Team** to develop comprehensive test strategies
- Partner with **DevOps** on deployment automation and infrastructure
- Coordinate with **Product Team** on feature requirements and priorities
- Document APIs and provide technical guidance to stakeholders

---

## Growth Opportunities

### Immediate Impact Areas
- **Testing Infrastructure**: Build comprehensive test suite from ground up
- **CI/CD Pipeline**: Implement automated build, test, and deployment workflows
- **API Documentation**: Create Swagger/OpenAPI documentation for 90+ endpoints
- **Performance Optimization**: Profile and optimize database queries and API performance
- **Containerization**: Modernize deployment with Docker/Kubernetes

### Technical Leadership
- Mentor junior developers on Spring and enterprise patterns
- Drive architectural decisions for new features
- Lead migration efforts (monolith → microservices, architecture modernization)
- Establish coding standards and best practices

---

## Project Stats

- **Codebase**: Multi-module Maven project (5 modules)
- **Entities**: 140+ JPA entities with complex relationships
- **REST Endpoints**: 90+ API resources
- **Service Types**: 15+ specialized service implementations
- **Database Tables**: 140+ tables with version-controlled migrations
- **Architecture**: Multi-tier enterprise with multi-tenancy
- **Current Version**: 1.18.1-SNAPSHOT

---

## Why Join This Project?

1. **Complex Domain**: Work on sophisticated enterprise B2B telecommunications platform
2. **Modern Stack**: Java 21, Hibernate 6.2, Spring Boot, PostgreSQL
3. **Ownership Opportunity**: Lead testing infrastructure and CI/CD implementation
4. **Scale Impact**: Support enterprise customers with mission-critical service management
5. **Technical Growth**: Deep dive into enterprise Spring, multi-tenancy, and complex business logic
6. **Innovation**: Opportunity to modernize and improve established codebase

---

## Application Process

Interested candidates should be prepared to:
- Discuss experience with Spring Boot and enterprise patterns
- Demonstrate understanding of RESTful API design and JPA/Hibernate
- Show examples of testing strategies for backend services
- Explain approach to database schema design and migration strategies
- Describe experience with application server deployment and operations

---

*QTO Platform - Empowering Enterprise Service Management*
