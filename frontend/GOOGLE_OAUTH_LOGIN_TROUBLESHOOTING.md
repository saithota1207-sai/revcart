# Google OAuth Login Troubleshooting

## Issue: "Continue with Google" button visible but login fails

### Step 1: Verify Google Credentials Are Set

Check `backend/src/main/resources/application.properties`:

```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
```

**❌ WRONG** (placeholder values):
```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
```

**✅ CORRECT** (actual values):
```properties
spring.security.oauth2.client.registration.google.client-id=123456789-abcdefg.apps.googleusercontent.com
spring.security.oauth2.client.registration.google.client-secret=GOCSPX-aBcDeFgHiJkLmNoPqRs
```

### Step 2: Verify Frontend Client ID

Check `src/app/pages/auth/login/login.component.ts` line 37:

**❌ WRONG**:
```typescript
client_id: 'YOUR_GOOGLE_CLIENT_ID'
```

**✅ CORRECT**:
```typescript
client_id: '123456789-abcdefg.apps.googleusercontent.com'
```

### Step 3: Check Browser Console

1. Open browser DevTools (F12)
2. Go to Console tab
3. Click "Sign in with Google"
4. Look for errors like:
   - `"Invalid Client ID"` → Frontend Client ID is wrong
   - `"Redirect URI mismatch"` → Check Google Cloud Console settings
   - `"CORS error"` → Backend CORS not configured

### Step 4: Check Backend Logs

Look for errors like:
```
Google authentication failed: Invalid Google token
Google authentication failed: Client ID mismatch
```

### Step 5: Verify Redirect URIs in Google Cloud Console

1. Go to https://console.cloud.google.com/
2. Select your project
3. Go to Credentials
4. Click on your OAuth 2.0 Client ID
5. Verify Authorized redirect URIs include:
   - `http://localhost:4200`
   - `http://localhost:8081`

### Step 6: Rebuild Backend

After updating credentials:
```bash
cd backend
mvn clean package -DskipTests
java -jar target/revcart-backend-1.0.0.jar
```

### Step 7: Restart Frontend

```bash
ng serve
```

### Step 8: Test Again

1. Open http://localhost:4200/login
2. Click "Sign in with Google"
3. Complete authentication
4. Check browser console for errors
5. Check backend logs for errors

---

## Common Error Messages & Solutions

### Error: "Invalid Client ID"
**Cause**: Frontend Client ID doesn't match Google credentials
**Solution**: 
1. Copy exact Client ID from Google Cloud Console
2. Update `login.component.ts` line 37
3. Restart frontend: `ng serve`

### Error: "Redirect URI mismatch"
**Cause**: Redirect URI not configured in Google Cloud Console
**Solution**:
1. Go to Google Cloud Console
2. Edit OAuth 2.0 Client ID
3. Add `http://localhost:4200` to Authorized redirect URIs
4. Save and wait 1-2 minutes
5. Try again

### Error: "CORS error"
**Cause**: Backend CORS not allowing frontend origin
**Solution**:
1. Check `SecurityConfig.java` has `http://localhost:4200` in CORS
2. Verify backend running on port 8081
3. Restart backend

### Error: "Invalid Google token"
**Cause**: Backend Client ID doesn't match token
**Solution**:
1. Copy exact Client Secret from Google Cloud Console
2. Update `application.properties`
3. Rebuild backend: `mvn clean package -DskipTests`
4. Restart backend

### Error: "User not created"
**Cause**: Database connection issue
**Solution**:
1. Verify MySQL running: `mysql -u root -p`
2. Verify MongoDB running: `mongod`
3. Check backend logs for database errors
4. Restart backend

---

## Debug Checklist

- [ ] Google Cloud Project created
- [ ] OAuth 2.0 credentials created
- [ ] Client ID copied correctly
- [ ] Client Secret copied correctly
- [ ] Frontend Client ID updated (line 37)
- [ ] Backend Client ID updated (application.properties)
- [ ] Backend Client Secret updated (application.properties)
- [ ] Redirect URIs include http://localhost:4200
- [ ] Redirect URIs include http://localhost:8081
- [ ] Backend rebuilt: `mvn clean package -DskipTests`
- [ ] Backend restarted
- [ ] Frontend restarted: `ng serve`
- [ ] MySQL running
- [ ] MongoDB running
- [ ] No errors in browser console
- [ ] No errors in backend logs

---

## Testing Steps

### 1. Verify Backend is Running
```bash
curl http://localhost:8081/api/auth/oauth2/google
```
Should return 405 (Method Not Allowed) - that's OK, means endpoint exists

### 2. Check Backend Logs
Look for:
```
Started RevCartApplication
```

### 3. Check Frontend is Running
Open http://localhost:4200/login
Should see login page with "Sign in with Google" button

### 4. Test Google Button
1. Click "Sign in with Google"
2. Google dialog should open
3. If not, check browser console for errors

### 5. Complete Authentication
1. Sign in with Google account
2. Grant permissions
3. Should redirect to home page

---

## If Still Not Working

### Check Backend Logs
```bash
# Look for errors in backend console output
# Should see: "Google login successful" or error message
```

### Check Browser Console
```javascript
// Open DevTools (F12) → Console
// Look for any error messages
// Try clicking button again and check console
```

### Check Network Tab
```
1. Open DevTools (F12) → Network
2. Click "Sign in with Google"
3. Look for POST request to /api/auth/oauth2/google
4. Check response status and body
```

### Verify Credentials Format

**Client ID** should look like:
```
123456789-abcdefghijklmnopqrstuvwxyz.apps.googleusercontent.com
```

**Client Secret** should look like:
```
GOCSPX-aBcDeFgHiJkLmNoPqRsT
```

---

## Quick Fix Checklist

1. ✅ Get real Google credentials (not placeholders)
2. ✅ Update backend application.properties
3. ✅ Update frontend login.component.ts
4. ✅ Rebuild backend: `mvn clean package -DskipTests`
5. ✅ Restart backend: `java -jar target/revcart-backend-1.0.0.jar`
6. ✅ Restart frontend: `ng serve`
7. ✅ Test login at http://localhost:4200/login

---

## Support

If still having issues:
1. Check all credentials are correct (not placeholders)
2. Verify redirect URIs in Google Cloud Console
3. Check browser console for errors
4. Check backend logs for errors
5. Restart both backend and frontend
