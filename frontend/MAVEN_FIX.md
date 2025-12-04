# Maven Build Error - Solution

## Error Message
```
[ERROR] No plugin found for prefix 'spring-root' in the current project and in the plugin groups 
[org.apache.maven.plugins, org.codehaus.mojo] available from the repositories
```

---

## Root Cause

This error occurs when you use an **invalid Maven command prefix**. The command `mvn spring-root:...` does not exist.

---

## ✅ Correct Maven Commands

### Build Commands

```bash
# Clean and install dependencies
mvn clean install

# Clean only
mvn clean

# Install dependencies
mvn install

# Compile only
mvn compile

# Package as JAR
mvn clean package
```

### Run Commands

```bash
# Run Spring Boot application
mvn spring-boot:run

# Run with specific port
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"

# Run with debug mode
mvn spring-boot:run -X
```

### Test Commands

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=CartControllerTest

# Skip tests during build
mvn clean package -DskipTests
```

### Other Commands

```bash
# Check dependencies
mvn dependency:tree

# Resolve dependencies
mvn dependency:resolve

# Update dependencies
mvn dependency:update-sources

# Clean local repository
mvn dependency:purge-local-repository
```

---

## ❌ Incorrect Commands (Don't Use)

```bash
# WRONG - spring-root doesn't exist
mvn spring-root:...

# WRONG - incorrect plugin name
mvn spring-boot-maven-plugin:...

# WRONG - typo in command
mvn spring-boot:rrun

# WRONG - missing colon
mvn spring-boot run
```

---

## 🔧 Step-by-Step Fix

### 1. Navigate to Backend Directory
```bash
cd d:\RevCart_p1\backend
```

### 2. Clean Previous Build
```bash
mvn clean
```

### 3. Install Dependencies
```bash
mvn install
```

### 4. Run Application
```bash
mvn spring-boot:run
```

### Expected Output
```
[INFO] BUILD SUCCESS
[INFO] Started RevCartApplication in X seconds
[INFO] Tomcat started on port(s): 9090
```

---

## 🚀 Quick Build & Run

### One-Command Build
```bash
mvn clean install && mvn spring-boot:run
```

### Build JAR and Run
```bash
mvn clean package
java -jar target/revcart-backend-1.0.0.jar
```

### Run with Custom Port
```bash
java -jar target/revcart-backend-1.0.0.jar --server.port=8080
```

---

## 🔍 Troubleshooting

### If Build Still Fails

#### 1. Check Java Version
```bash
java -version
# Should be 17 or higher
```

#### 2. Check Maven Version
```bash
mvn -v
# Should be 3.8 or higher
```

#### 3. Clear Maven Cache
```bash
# Windows
rmdir /s %USERPROFILE%\.m2\repository

# Linux/Mac
rm -rf ~/.m2/repository
```

#### 4. Rebuild
```bash
mvn clean install
```

### If Port 9090 is in Use

#### Windows
```bash
# Find process using port 9090
netstat -ano | findstr :9090

# Kill the process (replace PID with actual process ID)
taskkill /PID <PID> /F

# Try again
mvn spring-boot:run
```

#### Linux/Mac
```bash
# Find process using port 9090
lsof -i :9090

# Kill the process
kill -9 <PID>

# Try again
mvn spring-boot:run
```

### If MySQL Connection Fails

```bash
# Check MySQL is running
mysql -u root -p

# If not running, start it
# Windows
net start MySQL80

# Linux
sudo systemctl start mysql

# Mac
brew services start mysql

# Create database
mysql -u root -p -e "CREATE DATABASE revcart;"
```

---

## 📋 Maven Lifecycle Phases

Understanding Maven phases helps you use correct commands:

```
validate      → Validate project structure
compile       → Compile source code
test          → Run unit tests
package       → Package as JAR/WAR
verify        → Verify package integrity
install       → Install to local repository
deploy        → Deploy to remote repository
```

### Examples

```bash
# Run up to compile phase
mvn compile

# Run up to test phase
mvn test

# Run up to package phase
mvn package

# Run up to install phase
mvn install

# Run all phases
mvn clean install
```

---

## 🎯 Common Maven Commands Reference

| Command | Purpose |
|---------|---------|
| `mvn clean` | Remove build artifacts |
| `mvn compile` | Compile source code |
| `mvn test` | Run tests |
| `mvn package` | Create JAR/WAR |
| `mvn install` | Install to local repo |
| `mvn clean install` | Clean and install |
| `mvn spring-boot:run` | Run Spring Boot app |
| `mvn dependency:tree` | Show dependencies |
| `mvn help:describe` | Describe plugin |

---

## ✅ Verification

After running `mvn spring-boot:run`, verify:

1. **Check Backend is Running**
```bash
curl http://localhost:9090/api/products
```

2. **Check Logs**
```
Look for: "Started RevCartApplication in X seconds"
Look for: "Tomcat started on port(s): 9090"
```

3. **Check Database Connection**
```
Look for: "HikariPool-1 - Starting"
Look for: "HikariPool-1 - Pool is ready to accept connections"
```

---

## 📚 Additional Resources

- [Maven Official Documentation](https://maven.apache.org/guides/)
- [Spring Boot Maven Plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/)
- [Maven Lifecycle](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)

---

## 💡 Pro Tips

1. **Always use `mvn clean` before building**
   ```bash
   mvn clean install
   ```

2. **Skip tests for faster builds**
   ```bash
   mvn clean package -DskipTests
   ```

3. **Use offline mode if dependencies are cached**
   ```bash
   mvn clean install -o
   ```

4. **Check for dependency conflicts**
   ```bash
   mvn dependency:tree
   ```

5. **Update all dependencies**
   ```bash
   mvn versions:use-latest-versions
   ```

---

## 🎉 Success!

If you see this output, Maven is working correctly:

```
[INFO] BUILD SUCCESS
[INFO] Total time: X.XXX s
[INFO] Finished at: YYYY-MM-DDTHH:MM:SS+05:30
```

---

## 📞 Still Having Issues?

1. Check TROUBLESHOOTING.md
2. Review application logs
3. Verify all prerequisites
4. Check pom.xml syntax
5. Clear Maven cache and rebuild

---

**Remember**: Always use correct Maven command prefixes!
- ✅ `mvn spring-boot:run`
- ❌ `mvn spring-root:...`
