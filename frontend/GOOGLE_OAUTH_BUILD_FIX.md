# Google OAuth Build Fix - RESOLVED ✅

## Issue
Build failed with compilation errors:
- Missing `JacksonFactory` import
- Type mismatch for `User.Role` (String vs Enum)
- Type mismatch for JWT token generation

## Root Cause
1. Google API client library requires `google-http-client-gson` dependency
2. User entity uses `Role` enum, not String
3. JwtUtils only had `generateJwtToken(Authentication)` method, needed `generateJwtTokenFromEmail(String)`

## Solution Applied

### 1. Updated pom.xml
Added missing dependency:
```xml
<dependency>
    <groupId>com.google.http-client</groupId>
    <artifactId>google-http-client-gson</artifactId>
    <version>1.43.3</version>
</dependency>
```

### 2. Fixed OAuth2Service.java
- Changed import from `JacksonFactory` to `GsonFactory`
- Fixed Role assignment: `user.setRole(User.Role.USER)` instead of `user.setRole("USER")`
- Used correct JWT method: `jwtUtils.generateJwtTokenFromEmail(email)`

### 3. Updated JwtUtils.java
Added new method:
```java
public String generateJwtTokenFromEmail(String email) {
    return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(getSigningKey())
            .compact();
}
```

## Build Status
✅ **BUILD SUCCESS**
- All 89 source files compiled successfully
- JAR file created: `target/revcart-backend-1.0.0.jar`
- No compilation errors
- No warnings

## Next Steps
1. Start backend: `java -jar target/revcart-backend-1.0.0.jar`
2. Start frontend: `ng serve`
3. Test Google login at `http://localhost:4200/login`

## Files Modified
- `backend/pom.xml` - Added google-http-client-gson dependency
- `backend/src/main/java/com/revcart/service/OAuth2Service.java` - Fixed imports and types
- `backend/src/main/java/com/revcart/config/JwtUtils.java` - Added generateJwtTokenFromEmail method

## Verification
Run: `mvn clean package -DskipTests`
Expected: BUILD SUCCESS ✅
