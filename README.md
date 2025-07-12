# RecolectandoAPI
**RecolectandoAPI** is a robust Java Spring Boot API that serves as the backend engine for the Recolectando platform.
This repository provides RESTful services—including user authentication—and manages all back-end business logic, security, and integrations.

> **Note:** This API is a **work in progress**. New features and endpoints are under development.
>

## 💡 Key Features
- **Robust Authentication**
    - Secure login and token-based authentication (JWT).
    - User registration endpoint.
    - JWT refresh mechanism for seamless session management.

- **Role-Based Security**
    - Protects endpoints with Spring Security.
    - Standard HTTP status codes for authentication (401/403 responses).

- **RESTful Endpoints**
    - Clean and intuitive API routes.
    - JSON input and output (easy for frontend/mobile integration).

- **Exception Handling**
    - Centralized and user-friendly error messages.
    - HTTP status codes tailored for success, error, and conflict scenarios.

- **Extensible Design**
    - Modular and service-oriented architecture.
    - Simple to extend with additional endpoints and business logic.

## 🚦 Authentication Endpoints Overview

| Endpoint | Method | Description                                | Auth Required |
| --- | --- |--------------------------------------------|---------------|
| `/auth/login` | POST | User login, creates access & refresh token | No            |
| `/auth/register` | POST | Registers a new user                       | Access Token  |
| `/auth/refresh` | POST | Renews access token                        | Refresh token |
All API requests and responses use standard JSON bodies.
Tokens must be included per endpoint requirements (access or refresh token).
## 🛠 Technologies
- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Lombok**
- **Maven/Gradle** for dependency management

## 🏗️ Project Status
This API is **actively developed**. Some endpoints or features may be incomplete; functionality and documentation are expanding rapidly.
### Planned/Upcoming:
- Extend resource management endpoints (CRUD)
- Improve role-based access control
- Error Logging
- More detailed API documentation (OpenAPI/Swagger)
- Unit and integration tests
- Performance enhancements

## 👷 Contributing
Contributions and feedback are very welcome!
Please submit issues or pull requests to help improve the API.
## 📎 Related
- **Front-End Application:** See the front-end repository for UI and client features. https://github.com/Matt-if/RecolectandoFront.git

## 📫 Contact
For issues or questions about the API, please open an issue on this repository.
**RecolectandoAPI**: Your data, securely managed.
_Built with Java, powered by community._
