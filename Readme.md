# Spring Security

This repository contains projects to help you understand the basics of Spring Security, along with advanced topics such
as DAO-based authentication, JWT-based security, and OAuth2 integration.

- [Introduction](#introduction)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Basic Spring Security](#basic-spring-security)
- [Spring Security with DAO-based Authentication](#spring-security-with-dao-based-authentication)
- [Spring Security with JWT](#spring-security-with-jwt)
- [Spring Security with OAuth2](#spring-security-with-oauth2)
- [Running the Application](#running-the-application)

## Introduction

This project provides a comprehensive guide to implementing security in Spring Boot applications. It includes four
modules demonstrating:

1. Basic Spring Security setup.
2. DAO-based authentication with a custom `UserDetailsService`.
3. JWT-based authentication for stateless security.
4. OAuth2 integration for securing REST APIs with third-party authentication.

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- A modern IDE (IntelliJ IDEA, Eclipse, etc.)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/niruparekh09/Spring-Security.git
   cd SpringSecurityV1
   ```
2. Build the project:

```bash
mvn clean install
```

## Project Structure

The project is divided into four main modules:

- `SpringSecurityV1/`: Demonstrates the basic setup of Spring Security.
- `SpringSecurityV2/`: Implements authentication using a custom UserDetailsService and a DAO-based approach.
- `SpringSecurityJWT/`: Shows how to secure a Spring Boot application using JWT tokens.
- `SpringSecurityOAuth/`: Demonstrates OAuth2 integration with an external provider (e.g., Google).

## Basic Spring Security

This module covers:

- Setting up Spring Security in a Spring Boot application.
- Configuring in-memory authentication.
- Understanding the default security configurations provided by Spring Security.

### Key Files:

- `SecurityConfig.java`: Configures basic security settings.
- `Application.java`: The main entry point of the Spring Boot application.

## Spring Security with DAO-based Authentication

This module covers:

- Customizing UserDetailsService to load user data from a database.
- Implementing DAO-based authentication with Spring Security.
- Configuring password encoding.

### Key Files:

- `SecurityConfig.java`: Configures DAO-based authentication.
- `UserDetailsServiceImpl.java`: Custom implementation of UserDetailsService.
- `User.java`: Entity representing the user.
- `UserRepository.java`: DAO interface for accessing user data.

## Spring Security with JWT

This module covers:

- Implementing JWT-based authentication in a Spring Boot application.
- Creating and validating JWT tokens.
- Securing REST APIs using JWT.

### Key Files:

- `JwtAuthenticationFilter.java`: Filter for JWT validation.
- `JwtTokenProvider.java`: Utility for creating and validating JWT tokens.
- `SecurityConfig.java`: Configures JWT-based security.
- `AuthController.java`: Controller for handling authentication requests.

## Spring Security with OAuth2

This module covers:

- Integrating OAuth2 for authentication using an external provider (e.g., Google, Facebook).
- Configuring OAuth2 in a Spring Boot application.
- Handling OAuth2 tokens and user information.

### Key Files:

- `SecurityConfig.java`: Configures OAuth2 login and resource server.
- `OAuth2LoginSuccessHandler.java`: Custom handler for successful OAuth2 logins.
- `OAuth2UserService.java`: Custom service for processing OAuth2 user details.

## Running the Application

To run any of the modules, navigate to the respective module directory and execute:

```bash
mvn spring-boot:run
```

You can then access the application at `http://localhost:8080`