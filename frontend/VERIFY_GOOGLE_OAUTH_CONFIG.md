# Verify Google OAuth Configuration

## Quick Verification Steps

### Step 1: Check Backend Configuration
```bash
# Open: backend/src/main/resources/application.properties
# Look for these lines:

spring.security.oauth2.client.registration.google.client-id=???
spring.security.oauth2.client.registration.google.client-secret=???
```

**✅ CORRECT** - Real credentials:
```
spring.security.oauth2.client.registration.google.client-id=123456789-abcdefghijklmnop.apps.googleusercontent.com
spring.security.oauth2.client.registration.google.client-secret=GOCSPX-aBcDeFgHiJkLmNoPqRs
```

**❌ WRONG** - Placeholder values:
```
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
```

---

### Step 2: Check Frontend Configuration
```bash
# Open: src/app/pages/auth/login/login.component.ts
# Look for line 37:

client_id: '???'
```

**✅ CORRECT** - Real Client ID:
```typescript
client_id: '123456789-abcdefghijklmnop.apps.googleusercontent.com'
```

**❌ WRONG** - Placeholder:
```typescript
client_id: 'YOUR_GOOGLE_CLIENT_ID'
```

---

### Step 3: Verify They Match

**Backend Client ID:**
```
123456789-abcdefghijklmnop.apps.googleusercontent.com
```

**Frontend Client ID:**
```
123456789-abcdefghijklmnop.apps.googleusercontent.com
```

Should be **EXACTLY THE SAME** ✅

---

### Step 4: Check Google Cloud Console

1. Go to https://console.cloud.google.com/
2. Select your project
3. Go to Credentials
4. Click your OAuth 2.0 Client ID
5. Verify:
   - Client ID matches backend and frontend
   - Authorized redirect URIs include:
     - http://localhost:4200
     - http://localhost:8081

---

## If Configuration is Wrong

### Fix Backend
1. Open `backend/src/main/resources/application.properties`
2. Replace placeholder values with real credentials
3. Save file
4. Rebuild: `mvn clean package -DskipTests`
5. Restart backend

### Fix Frontend
1. Open `src/app/pages/auth/login/login.component.ts`
2. Replace placeholder Client ID with real one (line 37)
3. Save file
4. Restart frontend: `ng serve`

---

## Test After Fixing

1. Open http://localhost:4200/login
2. Click "Sign in with Google"
3. Should work now ✅

---

## Common Issues

| Issue | Cause | Fix |
|-------|-------|-----|
| Button shows but login fails | Placeholder credentials | Use real credentials |
| "Invalid Client ID" error | Frontend Client ID wrong | Update login.component.ts |
| "Redirect URI mismatch" | URIs not in Google Console | Add http://localhost:4200 and http://localhost:8081 |
| "Invalid token" error | Backend Client ID/Secret wrong | Update application.properties |
| CORS error | Backend CORS not configured | Already configured, restart backend |

---

## Quick Fix (3 Steps)

1. **Get real credentials** from Google Cloud Console
2. **Update backend** `application.properties` with credentials
3. **Update frontend** `login.component.ts` line 37 with Client ID
4. **Rebuild backend** and **restart both** backend and frontend
5. **Test** at http://localhost:4200/login

Done! ✅
