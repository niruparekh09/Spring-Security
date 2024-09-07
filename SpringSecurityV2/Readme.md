# Detailed Explanation Of DAO Based Auth

### 1. **Incoming Request**

- When a client sends an HTTP request to access a secured endpoint, it must include an `Authorization` header containing
  the credentials (username and password) encoded in Base64. For example:
  ```
  Authorization: Basic dXNlcm5hbWU6cGFzc3dvcmQ=
  ```
- The request is intercepted by the Spring Security filter chain, which checks if the request is authenticated and
  authorized.

### 2. **Security Filter Chain**

- The **SecurityFilterChain** is a series of filters that the request must pass through before reaching the
  application’s resources (like your controllers).
- The main filter handling authentication in your configuration is the `BasicAuthenticationFilter`. This filter:
    - Extracts the `Authorization` header from the request.
    - Decodes the Base64-encoded username and password.
    - Creates an `Authentication` token using the decoded credentials.
    - Delegates the authentication process to the `AuthenticationManager`.

### 3. **AuthenticationManager and ProviderManager**

- The `AuthenticationManager` is the entry point for authentication. In most Spring Security setups, it is implemented
  by `ProviderManager`.
- The `ProviderManager` delegates the actual authentication to one or more `AuthenticationProvider` instances. Each
  provider is responsible for a specific type of authentication (e.g., in-memory, JDBC, LDAP).
- In your configuration, `DaoAuthenticationProvider` is the provider used for DAO-based authentication, which means it
  will use a `UserDetailsService` to retrieve user information from a database.

### 4. **DaoAuthenticationProvider**

- The `DaoAuthenticationProvider` is responsible for authenticating the user by:
    - **Retrieving User Details**: Calls the `UserDetailsService` to fetch the user’s details from the database.
    - **Password Verification**: Compares the provided password with the stored password using a `PasswordEncoder`.
    - **Creating Authentication Object**: If authentication is successful, it creates an `Authentication` object
      containing the user’s details and authorities (roles).
    - **Handling Authentication Exceptions**: If the user is not found or the password doesn’t match, it throws an
      exception like `UsernameNotFoundException` or `BadCredentialsException`.

### 5. **UserDetailsService (NrvUserDetailService)**

- Your custom `NrvUserDetailService` implements `UserDetailsService`, a core interface in Spring Security that loads
  user-specific data.
- When the `DaoAuthenticationProvider` calls the `loadUserByUsername()` method, the following happens:
    1. The `UserRepo` (which is typically a Spring Data JPA repository) is queried to find the `User` entity by the
       given username.
    2. If the user is found, the `User` entity is wrapped in a `UserPrincipal` object, which implements `UserDetails`.
    3. If the user is not found, a `UsernameNotFoundException` is thrown.

### 6. **UserPrincipal**

- The `UserPrincipal` class is your custom implementation of `UserDetails`, which is a central interface in Spring
  Security.
- **Key Methods in `UserPrincipal`:**
    - `getAuthorities()`: Returns the roles or permissions granted to the user. In your case, it wraps the user’s role
      in a `SimpleGrantedAuthority`.
    - `getPassword()`: Returns the user’s password, which is stored in the `User` entity.
    - `getUsername()`: Returns the user’s username.
    - **Other Methods**: `isAccountNonExpired()`, `isAccountNonLocked()`, `isCredentialsNonExpired()`, and `isEnabled()`
      return `true`, meaning the account is active and usable.

### 7. **Password Encoding**

- The `DaoAuthenticationProvider` uses a `PasswordEncoder` to compare the provided password with the one stored in the
  database.
- In your configuration, you’re using `NoOpPasswordEncoder`, which means passwords are stored in plain text and compared
  directly. **(Note: This is not recommended for production as it is insecure. In real applications, you should use a
  stronger encoder like `BCryptPasswordEncoder`.)**
- If the passwords match, the user is considered authenticated.

### 8. **Authentication Success**

- Once authenticated, the `DaoAuthenticationProvider` creates an `Authentication` object. This object contains:
    - The authenticated user’s details (`UserPrincipal`).
    - The user’s authorities (roles).
    - Whether the authentication is successful or not.
- This `Authentication` object is stored in the `SecurityContextHolder`, making it available throughout the session or
  request.

### 9. **SecurityContextHolder**

- The `SecurityContextHolder` is a container that holds the `Authentication` object, representing the currently
  authenticated user.
- This information is accessible throughout the application, allowing you to check if the user is authenticated and what
  roles they have.
- It can be accessed using `SecurityContextHolder.getContext().getAuthentication()`.

### 10. **Filter Chain Continues**

- After successful authentication, the request continues down the filter chain.
- If the request is authorized (based on roles or permissions), it reaches the intended controller or endpoint.
- If not, Spring Security returns an appropriate error response (like a 403 Forbidden).

### 11. **Response**

- If the authentication and authorization checks pass, the controller processes the request and returns the desired
  response.
- If any step fails (like invalid credentials or insufficient permissions), Spring Security intercepts and returns an
  appropriate error, like 401 Unauthorized or 403 Forbidden.
