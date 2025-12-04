# Google OAuth Implementation - Step by Step

## Complete Implementation Checklist

### ✅ Backend Implementation (COMPLETED)

#### Step 1: Dependencies Added
- [x] `spring-boot-starter-oauth2-client` added to pom.xml
- [x] `google-api-client` (v1.35.2) added to pom.xml

#### Step 2: Configuration Added
- [x] Google OAuth properties added to application.properties
- [x] OAuth2 endpoint whitelisted in SecurityConfig.java

#### Step 3: Backend Files Created
- [x] `OAuth2Controller.java` - Handles `/api/auth/oauth2/google` endpoint
- [x] `OAuth2Service.java` - Verifies Google token and manages user
- [x] `GoogleTokenRequest.java` - DTO for token request

### ✅ Frontend Implementation (COMPLETED)

#### Step 1: Google Script Added
- [x] Google Sign-In script added to index.html

#### Step 2: Login Component Updated
- [x] `login.component.ts` - Added Google Sign-In initialization
- [x] `login.component.html` - Added Google Sign-In button

#### Step 3: Auth Service Updated
- [x] `auth.service.ts` - Added `googleLogin()` method

### 📋 Configuration Required (NEXT STEPS)

## Step 1: Get Google OAuth Credentials

### 1.1 Create Google Cloud Project
```
1. Go to https://console.cloud.google.com/
2. Click "Select a Project" → "New Project"
3. Enter project name: "RevCart"
4. Click "Create"
5. Wait for project to be created
```

### 1.2 Enable Google+ API
```
1. In Google Cloud Console, search for "Google+ API"
2. Click on "Google+ API"
3. Click "Enable"
4. Wait for API to be enabled
```

### 1.3 Create OAuth 2.0 Credentials
```
1. Go to "Credentials" in left sidebar
2. Click "Create Credentials" → "OAuth 2.0 Client ID"
3. If prompted, click "Configure OAuth consent screen"
4. Select "External" user type
5. Fill in required fields:
   - App name: "RevCart"
   - User support email: your-email@gmail.com
   - Developer contact: your-email@gmail.com
6. Click "Save and Continue"
7. Skip scopes (click "Save and Continue")
8. Skip test users (click "Save and Continue")
9. Review and click "Back to Dashboard"
10. Click "Create Credentials" → "OAuth 2.0 Client ID" again
11. Select "Web application"
12. Enter name: "RevCart Web Client"
13. Add Authorized redirect URIs:
    - http://localhost:4200
    - http://localhost:8081
14. Click "Create"
15. Copy Client ID and Client Secret
```

### 1.4 Save Credentials
```
Client ID: ___________________________________
Client Secret: ___________________________________
```

## Step 2: Update Backend Configuration

### 2.1 Update application.properties
```bash
# File: backend/src/main/resources/application.properties

# Find this section:
# Google OAuth2 Configuration
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET

# Replace with your credentials:
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID_HERE
spring.security.oauth2.client.registration.google.client-secret=YOUR_CLIENT_SECRET_HERE
```

## Step 3: Update Frontend Configuration

### 3.1 Update login.component.ts
```bash
# File: src/app/pages/auth/login/login.component.ts

# Find line 37:
client_id: 'YOUR_GOOGLE_CLIENT_ID',

# Replace with your Client ID:
client_id: 'YOUR_CLIENT_ID_HERE',
```

## Step 4: Build Backend

### 4.1 Clean and Build
```bash
cd backend
mvn clean package -DskipTests
```

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] Total time: XX.XXs
```

### 4.2 Verify JAR Created
```bash
# Check if JAR exists
dir target\revcart-backend-1.0.0.jar
```

## Step 5: Run Application

### 5.1 Start Backend (Terminal 1)
```bash
cd backend
java -jar target/revcart-backend-1.0.0.jar
```

Expected output:
```
Started RevCartApplication in X.XXX seconds
```

### 5.2 Start Frontend (Terminal 2)
```bash
ng serve
```

Expected output:
```
✔ Compiled successfully.
Application bundle generated successfully.
```

## Step 6: Test Google Login

### 6.1 Open Application
```
1. Open browser
2. Navigate to http://localhost:4200/login
3. Verify page loads
```

### 6.2 Test Google Sign-In
```
1. Look for "Sign in with Google" button
2. Click the button
3. Google authentication dialog should appear
4. Sign in with your Google account
5. Grant permissions if prompted
6. Should redirect to home page
7. Verify user is logged in
8. Check profile picture is displayed
```

### 6.3 Verify User Created
```
1. Open MongoDB Compass
2. Connect to mongodb://localhost:27017
3. Navigate to revcart_db → users collection
4. Verify new user document created with:
   - email: your-google-email
   - name: your-google-name
   - profilePicture: google-picture-url
   - isVerified: true
   - role: USER
```

### 6.4 Verify JWT Token
```
1. Open browser DevTools (F12)
2. Go to Application → Local Storage
3. Look for "token" key
4. Verify JWT token is stored
5. Token should start with "eyJ"
```

## Step 7: Test Subsequent Login

### 7.1 Logout
```
1. Click logout button
2. Verify redirected to login page
3. Verify localStorage cleared
```

### 7.2 Login Again
```
1. Click "Sign in with Google"
2. Complete authentication
3. Should redirect to home page
4. Verify same user details displayed
5. Verify profile picture still shows
```

## Step 8: Test Error Scenarios

### 8.1 Invalid Token
```
1. Open browser console
2. Manually send invalid token to backend
3. Should receive error: "Invalid Google token"
```

### 8.2 Network Error
```
1. Stop backend
2. Try to login with Google
3. Should show error message
4. Restart backend
5. Try again - should work
```

## Troubleshooting During Setup

### Issue: "Google is not defined"
**Solution:**
- Check Google Sign-In script loaded in index.html
- Verify script tag: `<script src="https://accounts.google.com/gsi/client" async defer></script>`
- Clear browser cache
- Restart frontend

### Issue: "Client ID mismatch"
**Solution:**
- Verify Client ID in frontend matches backend
- Check for typos or extra spaces
- Regenerate credentials if unsure
- Update both files with new credentials

### Issue: "Redirect URI mismatch"
**Solution:**
- Go to Google Cloud Console
- Edit OAuth 2.0 credentials
- Verify redirect URIs include:
  - http://localhost:4200
  - http://localhost:8081
- Save changes
- Restart backend

### Issue: "CORS error"
**Solution:**
- Verify backend running on port 8081
- Check CORS configuration in SecurityConfig.java
- Verify http://localhost:4200 in allowed origins
- Restart backend

### Issue: "User not created in database"
**Solution:**
- Verify MongoDB running: `mongod`
- Check MongoDB connection in application.properties
- Verify User entity has required fields
- Check backend logs for errors
- Restart backend

### Issue: "JWT token not stored"
**Solution:**
- Check localStorage enabled in browser
- Verify response contains "token" field
- Check browser console for JavaScript errors
- Verify auth.service.ts setCurrentUser() called

## Verification Checklist

After completing all steps:

- [ ] Google credentials obtained
- [ ] Backend configuration updated
- [ ] Frontend configuration updated
- [ ] Backend built successfully
- [ ] Backend running on port 8081
- [ ] Frontend running on port 4200
- [ ] Login page loads
- [ ] Google Sign-In button visible
- [ ] Google authentication works
- [ ] User created in database
- [ ] JWT token stored in localStorage
- [ ] Redirected to home page
- [ ] Profile picture displayed
- [ ] Subsequent login works
- [ ] Logout works
- [ ] Error handling works

## Quick Reference

### Important URLs
- Google Cloud Console: https://console.cloud.google.com/
- Frontend: http://localhost:4200/login
- Backend API: http://localhost:8081/api/auth/oauth2/google
- MongoDB: mongodb://localhost:27017/revcart_db

### Important Files
- Backend config: `backend/src/main/resources/application.properties`
- Frontend config: `src/app/pages/auth/login/login.component.ts`
- Backend endpoint: `backend/src/main/java/com/revcart/controller/OAuth2Controller.java`
- Frontend service: `src/app/services/auth.service.ts`

### Important Ports
- Backend: 8081
- Frontend: 4200
- MongoDB: 27017

### Important Credentials
- Client ID: [From Google Cloud Console]
- Client Secret: [From Google Cloud Console]

## Next Steps After Setup

1. ✅ Test with multiple Google accounts
2. ✅ Test on different browsers
3. ✅ Test on mobile devices
4. ✅ Add more OAuth providers (Facebook, GitHub)
5. ✅ Implement account linking
6. ✅ Add social login to registration page
7. ✅ Deploy to production

## Support Resources

- Google OAuth Documentation: https://developers.google.com/identity/protocols/oauth2
- Google Sign-In for Web: https://developers.google.com/identity/sign-in/web
- Spring Security OAuth2: https://spring.io/projects/spring-security-oauth2-client
- Angular HttpClient: https://angular.io/guide/http

## Summary

You have successfully implemented Google OAuth login for RevCart! Users can now:
- Sign in with their Google account
- Automatically create account on first login
- Store profile picture from Google
- Get JWT token for authenticated sessions
- Use all RevCart features after login

Enjoy! 🎉
