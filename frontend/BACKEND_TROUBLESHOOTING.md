# Backend Troubleshooting Guide

## Common Issues & Solutions

### 1. Database Connection Issues

**Problem:** `Cannot get a connection, pool error`

**Solution:**
```properties
# Check application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/revcart_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
```

**Steps:**
1. Ensure MySQL is running
2. Create database: `CREATE DATABASE revcart_db;`
3. Verify credentials in application.properties
4. Check MySQL port (default 3306)

---

### 2. MongoDB Connection Issues

**Problem:** `Failed to connect to MongoDB`

**Solution:**
```properties
# Check application.properties
spring.data.mongodb.uri=mongodb://localhost:27017/revcart_logs
```

**Steps:**
1. Ensure MongoDB is running
2. Check MongoDB port (default 27017)
3. Verify database name: `revcart_logs`
4. Check connection string format

---

### 3. JWT Token Issues

**Problem:** `JWT token is expired` or `Invalid JWT token`

**Solution:**
```properties
# Check JWT configuration
revcart.app.jwtSecret=revCartSecretKeyForJWTTokenGeneration2024
revcart.app.jwtExpirationMs=86400000
```

**Steps:**
1. Verify JWT secret key is set
2. Check token expiration time (86400000 ms = 24 hours)
3. Ensure token is sent in Authorization header: `Bearer <token>`
4. Check system time synchronization

---

### 4. Email Configuration Issues

**Problem:** `Failed to send email` or `Authentication failed`

**Solution:**
```properties
# Gmail SMTP Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

**Steps:**
1. Use Gmail app password (not regular password)
2. Enable "Less secure app access" or use app password
3. Verify SMTP settings
4. Check firewall/network settings

---

### 5. CORS Issues

**Problem:** `Access to XMLHttpRequest blocked by CORS policy`

**Solution:**
```java
// SecurityConfig.java
configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
configuration.setAllowedHeaders(Arrays.asList("*"));
configuration.setAllowCredentials(true);
```

**Steps:**
1. Verify frontend URL in CORS configuration
2. Check allowed methods
3. Ensure credentials are allowed
4. Clear browser cache

---

### 6. Authentication Issues

**Problem:** `User not found` or `Invalid credentials`

**Solution:**
1. Verify user exists in database
2. Check password encoding (BCrypt)
3. Ensure email is correct
4. Check user role assignment

**SQL:**
```sql
SELECT * FROM users WHERE email = 'user@example.com';
```

---

### 7. Null Pointer Exceptions

**Problem:** `NullPointerException` in services

**Solution:**
1. Check if entity is properly initialized
2. Verify repository methods return Optional
3. Use `.orElseThrow()` or `.orElse(null)`
4. Add null checks before accessing properties

---

### 8. Transaction Issues

**Problem:** `LazyInitializationException` or transaction errors

**Solution:**
```java
@Transactional
public List<Order> getUserOrders(User user) {
    return orderRepository.findByUserOrderByOrderDateDesc(user);
}
```

**Steps:**
1. Add `@Transactional` annotation to service methods
2. Ensure session is open when accessing lazy properties
3. Use `FetchType.EAGER` if needed
4. Check transaction boundaries

---

### 9. Validation Issues

**Problem:** `Validation failed` or `Invalid input`

**Solution:**
```java
@NotBlank
@Email
private String email;

@NotBlank
@Size(min = 6, max = 40)
private String password;
```

**Steps:**
1. Check DTO validation annotations
2. Verify input meets validation rules
3. Check error messages in response
4. Add custom validators if needed

---

### 10. Port Already in Use

**Problem:** `Port 5258 already in use`

**Solution:**
```properties
# Change port in application.properties
server.port=5259
```

**Or kill existing process:**
```bash
# Windows
netstat -ano | findstr :5258
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :5258
kill -9 <PID>
```

---

## Build Issues

### Maven Build Fails

**Problem:** `BUILD FAILURE`

**Solution:**
```bash
# Clean and rebuild
mvn clean install

# Skip tests
mvn clean install -DskipTests

# Update dependencies
mvn dependency:resolve
```

---

### Compilation Errors

**Problem:** `Compilation error`

**Solution:**
1. Check Java version (should be 23)
2. Verify all imports are correct
3. Check for missing dependencies in pom.xml
4. Rebuild project: `mvn clean compile`

---

## Runtime Issues

### Application Won't Start

**Problem:** `Application failed to start`

**Solution:**
1. Check logs for error messages
2. Verify all required properties are set
3. Check database connectivity
4. Verify port is available
5. Check for missing beans

---

### Endpoint Returns 404

**Problem:** `404 Not Found`

**Solution:**
1. Verify endpoint path is correct
2. Check controller mapping
3. Verify HTTP method (GET, POST, etc.)
4. Check request headers
5. Verify authentication/authorization

---

### Endpoint Returns 500

**Problem:** `500 Internal Server Error`

**Solution:**
1. Check server logs for stack trace
2. Verify request body format
3. Check database state
4. Verify all required fields are provided
5. Check for null pointer exceptions

---

## Performance Issues

### Slow Queries

**Problem:** `Slow database queries`

**Solution:**
1. Add indexes to frequently queried columns
2. Use pagination for large result sets
3. Optimize JPA queries
4. Use `@Query` for complex queries
5. Monitor query execution time

---

### High Memory Usage

**Problem:** `OutOfMemoryError`

**Solution:**
```bash
# Increase heap size
java -Xmx1024m -Xms512m -jar target/revcart-backend-1.0.0.jar
```

---

## Debugging

### Enable Debug Logging

```properties
# application.properties
logging.level.com.revcart=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

### Check Logs

```bash
# View logs
tail -f logs/application.log

# Search for errors
grep ERROR logs/application.log
```

---

## Testing Endpoints

### Using cURL

```bash
# Login
curl -X POST http://localhost:5258/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password"}'

# Get Products
curl -X GET http://localhost:5258/api/products

# Create Order (with token)
curl -X POST http://localhost:5258/api/orders \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"deliveryAddress":"123 Main St","phoneNumber":"1234567890"}'
```

---

## Support

For additional help:
1. Check application logs
2. Review error messages carefully
3. Verify configuration settings
4. Check database state
5. Review Code Issues Panel

---

**Last Updated:** Final Verification
**Status:** ✅ All issues documented and resolved
