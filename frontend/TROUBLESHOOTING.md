# RevCart Troubleshooting Guide

## Maven Build Issues

### Error: "No plugin found for prefix 'spring-root'"

**Cause**: Invalid Maven command used

**Solution**:
```bash
# CORRECT commands:
mvn clean install
mvn spring-boot:run
mvn clean package

# INCORRECT (don't use):
mvn spring-root:...
mvn spring-boot-maven-plugin:...
```

### Error: "BUILD FAILURE"

**Steps to fix**:

1. Clear Maven cache:
```bash
mvn clean
```

2. Update dependencies:
```bash
mvn dependency:resolve
```

3. Rebuild:
```bash
mvn clean install
```

4. If still failing, check Java version:
```bash
java -version
# Should be 17 or higher
```

---

## Backend Issues

### Port 9090 Already in Use

**Windows**:
```bash
netstat -ano | findstr :9090
taskkill /PID <PID> /F
```

**Linux/Mac**:
```bash
lsof -i :9090
kill -9 <PID>
```

### MySQL Connection Failed

**Check**:
1. MySQL is running
2. Database exists: `CREATE DATABASE revcart;`
3. Credentials in `application.properties` are correct
4. Port 3306 is accessible

**Test connection**:
```bash
mysql -u root -p -h localhost
```

### WebSocket Connection Error

**Check**:
1. Backend is running on port 9090
2. WebSocket endpoints are accessible
3. CORS is configured correctly
4. Browser supports WebSocket

**Test**:
```bash
curl -i -N -H "Connection: Upgrade" -H "Upgrade: websocket" http://localhost:9090/ws-notifications
```

### JWT Token Issues

**Check**:
1. JWT secret is configured in `application.properties`
2. Token is being sent in Authorization header
3. Token format: `Bearer <token>`

**Debug**:
```bash
# Check token in browser console
localStorage.getItem('token')
```

---

## Frontend Issues

### Port 4200 Already in Use

**Windows**:
```bash
netstat -ano | findstr :4200
taskkill /PID <PID> /F
```

**Linux/Mac**:
```bash
lsof -i :4200
kill -9 <PID>
```

### npm install Fails

**Solution**:
```bash
# Clear npm cache
npm cache clean --force

# Delete node_modules and package-lock.json
rm -rf node_modules package-lock.json

# Reinstall
npm install
```

### Angular Build Error

**Solution**:
```bash
# Update Angular CLI
npm install -g @angular/cli@18

# Clear Angular cache
ng cache clean

# Rebuild
ng build
```

### API Connection Error

**Check**:
1. Backend is running on port 9090
2. API URL in `environment.ts` is correct
3. CORS is enabled on backend
4. Network tab shows requests

**Debug**:
```typescript
// In browser console
fetch('http://localhost:9090/api/products')
  .then(r => r.json())
  .then(d => console.log(d))
```

---

## Database Issues

### MySQL Not Starting

**Windows**:
```bash
# Start MySQL service
net start MySQL80

# Or use MySQL Workbench
```

**Linux**:
```bash
sudo systemctl start mysql
```

**Mac**:
```bash
brew services start mysql
```

### Database Connection Timeout

**Solution**:
1. Check MySQL is running
2. Verify credentials
3. Check firewall settings
4. Increase connection timeout in `application.properties`:

```properties
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.maximum-pool-size=5
```

### Tables Not Created

**Solution**:
1. Ensure `spring.jpa.hibernate.ddl-auto=update` is set
2. Check application logs for errors
3. Manually create tables if needed

---

## Email Service Issues

### Emails Not Sending

**Check**:
1. SMTP configuration is correct
2. Gmail app password is used (not regular password)
3. Less secure apps is enabled (if using Gmail)
4. Check application logs

**Gmail Setup**:
1. Enable 2-factor authentication
2. Generate app password
3. Use app password in `application.properties`

```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

---

## Performance Issues

### Slow API Responses

**Solutions**:
1. Check database query performance
2. Add indexes to frequently queried columns
3. Enable query caching
4. Check server resources (CPU, RAM)

### High Memory Usage

**Solutions**:
1. Increase heap size:
```bash
java -Xmx1024m -Xms512m -jar revcart-backend-1.0.0.jar
```

2. Check for memory leaks in logs
3. Optimize database queries

### WebSocket Lag

**Solutions**:
1. Check network latency
2. Reduce message frequency
3. Optimize message payload size
4. Check server resources

---

## Security Issues

### CORS Error

**Solution**: Update `SecurityConfig.java`:
```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
}
```

### 403 Forbidden Error

**Check**:
1. User has required role
2. JWT token is valid
3. Endpoint is not protected
4. CORS credentials are enabled

### 401 Unauthorized Error

**Check**:
1. JWT token is present
2. Token is not expired
3. Token format is correct: `Bearer <token>`
4. Secret key matches

---

## Logging & Debugging

### Enable Debug Logging

**application.properties**:
```properties
logging.level.root=INFO
logging.level.com.revcart=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.springframework.security=DEBUG
```

### View Logs

**Backend**:
```bash
# Real-time logs
tail -f logs/application.log

# Search logs
grep "ERROR" logs/application.log
```

**Frontend**:
```bash
# Browser console
F12 -> Console tab

# Network tab
F12 -> Network tab
```

---

## Reset Everything

### Complete Reset

```bash
# Backend
cd backend
mvn clean
rm -rf target/
rm -rf .m2/

# Frontend
cd frontend
rm -rf node_modules/
rm -rf dist/
npm cache clean --force

# Database
mysql -u root -p
DROP DATABASE revcart;
CREATE DATABASE revcart;
```

Then rebuild and restart.

---

## Getting Help

1. **Check logs first**
   - Backend: `target/logs/application.log`
   - Frontend: Browser console (F12)

2. **Search error message**
   - Google the exact error
   - Check Stack Overflow

3. **Verify prerequisites**
   - Java 17+
   - Node.js 18+
   - MySQL 8.0+
   - Maven 3.8+

4. **Check configuration**
   - `application.properties`
   - `environment.ts`
   - Database credentials

5. **Test endpoints**
   - Use Postman or curl
   - Check API responses
   - Verify data flow

---

## Quick Fixes

| Issue | Quick Fix |
|-------|-----------|
| Build fails | `mvn clean install` |
| Port in use | Kill process on port |
| DB connection error | Check MySQL is running |
| API not responding | Restart backend |
| Frontend not loading | Clear browser cache |
| WebSocket error | Check backend logs |
| Email not sending | Verify SMTP config |
| Token expired | Login again |
| CORS error | Check SecurityConfig |
| Memory error | Increase heap size |

---

## Contact Support

For unresolved issues:
1. Collect error logs
2. Note exact steps to reproduce
3. Check system specifications
4. Review configuration files
5. Contact development team
