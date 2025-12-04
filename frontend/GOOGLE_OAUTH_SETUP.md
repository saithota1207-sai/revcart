# Google OAuth Login Setup Guide

## Overview
This guide explains how to set up Google OAuth 2.0 login for RevCart application.

## Backend Setup

### 1. Dependencies Added
The following dependencies have been added to `pom.xml`:
- `spring-boot-starter-oauth2-client` - OAuth2 client support
- `google-api-client` - Google API client library

### 2. Configuration Files

#### application.properties
Add the following Google OAuth configuration:
```properties
# Google OAuth2 Configuration
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=profile,email
spring.security.oauth2.client.provider.google.user-name-attribute=sub
```

### 3. Backend Files Created

#### OAuth2Controller.java
- Location: `backend/src/main/java/com/revcart/controller/OAuth2Controller.java`
- Endpoint: `POST /api/auth/oauth2/google`
- Accepts Google ID token and returns JWT token with user details

#### OAuth2Service.java
- Location: `backend/src/main/java/com/revcart/service/OAuth2Service.java`
- Verifies Google ID token using Google API client
- Creates or updates user in database
- Generates JWT token for session management

#### GoogleTokenRequest.java
- Location: `backend/src/main/java/com/revcart/dto/GoogleTokenRequest.java`
- DTO to receive Google ID token from frontend

### 4. Security Configuration
- OAuth2 endpoint is already whitelisted in `SecurityConfig.java`
- CORS is configured to allow requests from `http://localhost:4200`

## Frontend Setup

### 1. Files Updated

#### login.component.ts
- Added Google Sign-In initialization in `ngOnInit()`
- Added `handleGoogleLogin()` method to process Google token
- Calls `authService.googleLogin()` with the token

#### login.component.html
- Added Google Sign-In button container
- Button renders using Google's official button styling

#### auth.service.ts
- Added `googleLogin(token: string)` method
- Sends token to backend and stores JWT response

#### index.html
- Added Google Sign-In script: `https://accounts.google.com/gsi/client`

## Getting Google OAuth Credentials

### Step 1: Create Google Cloud Project
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select existing one
3. Enable Google+ API

### Step 2: Create OAuth 2.0 Credentials
1. Go to "Credentials" in the left sidebar
2. Click "Create Credentials" → "OAuth 2.0 Client ID"
3. Choose "Web application"
4. Add authorized redirect URIs:
   - `http://localhost:4200` (Frontend)
   - `http://localhost:8081` (Backend)
5. Copy the Client ID and Client Secret

### Step 3: Configure in Application

#### Backend Configuration
Update `application.properties`:
```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET
```

#### Frontend Configuration
Update `login.component.ts` line 37:
```typescript
client_id: 'YOUR_GOOGLE_CLIENT_ID'
```

## API Endpoint

### Google Login
**POST** `/api/auth/oauth2/google`

**Request Body:**
```json
{
  "token": "google_id_token_from_frontend"
}
```

**Response:**
```json
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

## User Flow

1. User clicks "Sign in with Google" button on login page
2. Google Sign-In dialog appears
3. User authenticates with Google account
4. Frontend receives ID token from Google
5. Frontend sends token to backend `/api/auth/oauth2/google`
6. Backend verifies token with Google API
7. Backend creates/updates user in database
8. Backend returns JWT token
9. Frontend stores JWT and redirects to home page

## Testing

### Manual Testing
1. Start backend: `java -jar target/revcart-backend-1.0.0.jar`
2. Start frontend: `ng serve`
3. Navigate to `http://localhost:4200/login`
4. Click "Sign in with Google"
5. Complete Google authentication
6. Should redirect to home page with logged-in user

### API Testing with Postman
1. Get Google ID token from frontend console (after clicking button)
2. Send POST request to `http://localhost:8081/api/auth/oauth2/google`
3. Body: `{"token": "google_id_token"}`
4. Should receive JWT token in response

## Troubleshooting

### Issue: "Invalid Google token"
- Verify Client ID matches in both frontend and backend
- Check token hasn't expired (tokens expire after 1 hour)
- Ensure Google+ API is enabled in Cloud Console

### Issue: CORS error
- Verify `http://localhost:4200` is in CORS configuration
- Check browser console for specific CORS error message

### Issue: User not created
- Check MongoDB/MySQL connection
- Verify User entity has required fields
- Check application logs for database errors

### Issue: Google button not rendering
- Verify Google Sign-In script is loaded in index.html
- Check browser console for JavaScript errors
- Ensure `googleSignInButton` div exists in HTML

## Security Notes

1. **Token Verification**: Backend verifies token with Google API, not just decoding
2. **Password**: OAuth users get random password (not used for login)
3. **Email Verification**: OAuth users are automatically verified
4. **Profile Picture**: Stored from Google account
5. **JWT Token**: 24-hour expiration for session management

## Next Steps

1. Replace `YOUR_GOOGLE_CLIENT_ID` with actual credentials
2. Replace `YOUR_GOOGLE_CLIENT_SECRET` with actual credentials
3. Rebuild backend: `mvn clean package -DskipTests`
4. Restart backend and frontend
5. Test Google login flow
