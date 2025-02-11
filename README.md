# Contact Manager


## Overview
Contact Manager is a Spring Boot-based application designed to manage contact information efficiently. It provides a RESTful API for performing CRUD operations on contacts and integrates Swagger for API documentation.

## Technologies Used
- **JDK**: 21
- **Spring Boot**: 3.4.2
- **Database**: PostgreSQL
- **API Documentation**: Swagger
- **Build Tool**: Maven

## Prerequisites
Ensure you have the following installed on your system:
- JDK 21 ([Eclipse Adoptium](https://adoptium.net/))
- Maven ([Download](https://maven.apache.org/download.cgi))
- PostgreSQL ([Download](https://www.postgresql.org/download/))

## Setup and Installation

### Clone the Repository
```Bash
git clone https://github.com/your-username/contact-manager.git
cd contact-manager
```
1. Dependencies
```
mvn install
```
**2. update the `application.properties` file with your PC environment variables**
```
app.password-source.number-of-iteration=${PASSWORD_ITERATIONS}
app.password-source.salt=${PASSWORD_SALT}
app.password-source.pepper=${PASSWORD_PEPPER}
app.password-source.token-expiration-minutes=${TOKEN_EXPIRATION}
app.password-source.token-signing-key=${TOKEN_SIGNING_KEY}
```

**3. Run the server**
```
mvn spring-boot:run
```

## API Documentation
Once the application is running, you can access it:
Server Running on : http://localhost:8080/
Swagger UI : http://localhost:8080/swagger-ui/index.html

| Endpoint               | Method | Input             | Output  |
|------------------------|--------|-------------------|---------|
| `/api/v1/user `        | `POST` | `first name, ...` | register |
| `/api/v1/user/auth`    | `POST` | `email, passwoed` | `login` |
| `/api/v1/user/`    ... |`PUT`| `first name,...`  | `update`|
`...`

