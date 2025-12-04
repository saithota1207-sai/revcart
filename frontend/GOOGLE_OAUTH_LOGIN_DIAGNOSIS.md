# Google OAuth Login - Diagnosis Guide

## Problem: Button shows but login fails

### ⚠️ Most Common Cause: Placeholder Credentials

Your `application.properties` still has:
```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
```

These are **PLACEHOLDERS** - you need **REAL** credentials from Google Cloud Console.

---

## 🔧 Step 1: Get Real Google Credentials

### 1.1 Create Google Cloud Project
```
1. Go to https://console.cloud.google.com/
2. Click "Select a Project" → "New Project"
3. Name: "RevCart"
4. Click "Create"
```

### 1.2 Enable Google+ API
```
1. Search for "Google+ API"
2. Click "Enable"
```

### 1.3 Create OAuth 2.0 Credentials
```
1. Go to "Credentials" (left sidebar)
2. Click "Create Credentials" → "OAuth 2.0 Client ID"
3. If prompted, click "Configure OAuth consent screen"
4. Select "External"
5. Fill in app name: "RevCart"
6. Click "Save and Continue" (skip other steps)
7. Click "Create Credentials" → "OAuth 2.0 Client ID" again
8. Select "Web application"
9. Name: "RevCart Web"
10. Add Authorized redirect URIs:
    - http://localhost:4200
    - http://localhost:8081
11. Click "Create"
12. Copy Client ID and Client Secret
```

### 1.4 Your Credentials Look Like:
```
Client ID: 123456789-abcdefghijklmnop.apps.googleusercontent.com
Client Secret: GOCSPX-aBcDeFgHiJkLmNoPqRs
```

---

## 🔧 Step 2: Update Backend Configuration

Edit: `backend/src/main/resources/application.properties`

**BEFORE** (wrong):
```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
```

**AFTER** (correct):
```properties
spring.security.oauth2.client.registration.google.client-id=123456789-abcdefghijklmnop.apps.googleusercontent.com
spring.security.oauth2.client.registration.google.client-secret=GOCSPX-aBcDeFgHiJkLmNoPqRs
```

---

## 🔧 Step 3: Update Frontend Configuration

Edit: `src/app/pages/auth/login/login.component.ts`

Find line 37:
```typescript
client_id: 'YOUR_GOOGLE_CLIENT_ID',
```

Replace with:
```typescript
client_id: '123456789-abcdefghijklmnop.apps.googleusercontent.com',
```

---

## 🔧 Step 4: Rebuild Backend

```bash
cd backend
mvn clean package -DskipTests
```

Expected output:
```
[INFO] BUILD SUCCESS
```

---

## 🔧 Step 5: Restart Backend

```bash
java -jar target/revcart-backend-1.0.0.jar
```

Expected output:
```
Started RevCartApplication
```

---

## 🔧 Step 6: Restart Frontend

```bash
ng serve
```

Expected output:
```
✔ Compiled successfully
```

---

## 🔧 Step 7: Test Login

1. Open http://localhost:4200/login
2. Click "Sign in with Google"
3. Google dialog should open
4. Sign in with your Google account
5. Should redirect to home page ✅

---

## 🐛 If Still Not Working

### Check 1: Browser Console
```
1. Open http://localhost:4200/login
2. Press F12 (DevTools)
3. Go to Console tab
4. Click "Sign in with Google"
5. Look for error messages
```

**Common errors:**
- `"Invalid Client ID"` → Frontend Client ID is wrong
- `"Redirect URI mismatch"` → Check Google Cloud Console
- `"CORS error"` → Backend CORS issue

### Check 2: Backend Logs
```
1. Look at backend console output
2. Search for "Google" or "oauth"
3. Look for error messages
```

**Common errors:**
- `"Invalid Google token"` → Backend Client ID/Secret wrong
- `"Client ID mismatch"` → Credentials don't match

### Check 3: Verify Credentials Match

**Frontend** (`login.component.ts` line 37):
```typescript
client_id: '123456789-abcdefghijklmnop.apps.googleusercontent.com'
```

**Backend** (`application.properties`):
```properties
spring.security.oauth2.client.registration.google.client-id=123456789-abcdefghijklmnop.apps.googleusercontent.com
```

Both should be **EXACTLY THE SAME**

### Check 4: Verify Redirect URIs

Go to Google Cloud Console:
1. Credentials → Your OAuth 2.0 Client ID
2. Check "Authorized redirect URIs" includes:
   - `http://localhost:4200`
   - `http://localhost:8081`

---

## ✅ Quick Checklist

- [ ] Google Cloud Project created
- [ ] Google+ API enabled
- [ ] OAuth 2.0 credentials created
- [ ] Client ID copied (not placeholder)
- [ ] Client Secret copied (not placeholder)
- [ ] Backend `application.properties` updated with real credentials
- [ ] Frontend `login.component.ts` updated with real Client ID
- [ ] Redirect URIs include http://localhost:4200 and http://localhost:8081
- [ ] Backend rebuilt: `mvn clean package -DskipTests`
- [ ] Backend restarted
- [ ] Frontend restarted
- [ ] MySQL running
- [ ] MongoDB running
- [ ] Test login works

---

## 🎯 Most Likely Issue

**99% of the time, the problem is:**
- Using placeholder credentials instead of real ones
- Frontend and Backend Client IDs don't match
- Redirect URIs not configured in Google Cloud Console

**Solution:**
1. Get real credentials from Google Cloud Console
2. Update both backend and frontend with same Client ID
3. Rebuild and restart both
4. Test again

---

## 📞 Need Help?

1. Check all credentials are real (not "YOUR_GOOGLE_CLIENT_ID")
2. Verify frontend and backend have same Client ID
3. Check browser console for errors (F12)
4. Check backend logs for errors
5. Verify redirect URIs in Google Cloud Console
