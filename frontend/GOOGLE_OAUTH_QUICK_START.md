# Google OAuth Quick Start

## What Was Implemented

✅ Backend OAuth2 endpoint for Google token verification
✅ User auto-creation/update on first Google login
✅ JWT token generation for authenticated sessions
✅ Frontend Google Sign-In button integration
✅ Automatic user profile picture storage
✅ Email verification for OAuth users

## Quick Setup (5 minutes)

### 1. Get Google Credentials
- Go to https://console.cloud.google.com/
- Create OAuth 2.0 credentials (Web application)
- Add redirect URIs: `http://localhost:4200`, `http://localhost:8081`
- Copy Client ID and Client Secret

### 2. Update Backend
Edit `backend/src/main/resources/application.properties`:
```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
```

### 3. Update Frontend
Edit `src/app/pages/auth/login/login.component.ts` line 37:
```typescript
client_id: 'YOUR_CLIENT_ID'
```

### 4. Rebuild & Run
```bash
# Backend
cd backend
mvn clean package -DskipTests
java -jar target/revcart-backend-1.0.0.jar

# Frontend (new terminal)
ng serve
```

### 5. Test
- Open http://localhost:4200/login
- Click "Sign in with Google"
- Complete authentication
- Should redirect to home page

## Files Modified/Created

### Backend
- ✅ `pom.xml` - Added OAuth2 dependencies
- ✅ `application.properties` - Added Google OAuth config
- ✅ `OAuth2Controller.java` - New endpoint
- ✅ `OAuth2Service.java` - New service
- ✅ `GoogleTokenRequest.java` - New DTO
- ✅ `SecurityConfig.java` - Already allows OAuth2 endpoint

### Frontend
- ✅ `login.component.ts` - Added Google Sign-In logic
- ✅ `login.component.html` - Added Google button
- ✅ `auth.service.ts` - Added googleLogin() method
- ✅ `index.html` - Added Google Sign-In script

## API Endpoint

```
POST /api/auth/oauth2/google
Content-Type: application/json

{
  "token": "google_id_token"
}

Response:
{
  "token": "jwt_token",
  "type": "Bearer",
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "user@example.com",
  "role": "USER",
  "profilePicture": "https://...",
  "message": "Google login successful"
}
```

## How It Works

1. User clicks "Sign in with Google" button
2. Google Sign-In dialog opens
3. User authenticates with Google
4. Frontend receives ID token
5. Frontend sends token to backend
6. Backend verifies token with Google API
7. Backend creates/updates user in database
8. Backend returns JWT token
9. Frontend stores JWT and logs in user
10. User redirected to home page

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Google button not showing | Check Google script loaded in index.html |
| "Invalid token" error | Verify Client ID matches in frontend & backend |
| CORS error | Ensure http://localhost:4200 in CORS config |
| User not created | Check MongoDB/MySQL connection |
| Token expired | Google tokens expire after 1 hour |

## Security Features

- ✅ Token verified with Google API (not just decoded)
- ✅ Random password for OAuth users
- ✅ Auto email verification
- ✅ JWT token with 24-hour expiry
- ✅ Profile picture stored securely
- ✅ Role-based access control maintained

## Next: Additional OAuth Providers

To add more providers (Facebook, GitHub, etc.):
1. Add OAuth2 client registration in application.properties
2. Create similar controller/service for each provider
3. Add button in login template
4. Test with provider credentials

## Support

For detailed setup guide, see: `GOOGLE_OAUTH_SETUP.md`
