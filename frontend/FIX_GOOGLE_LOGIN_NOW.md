# 🔧 Fix Google Login - Do This Now

## Problem
"Continue with Google" button shows but login fails

## Root Cause
You're using **placeholder credentials** instead of **real ones**

---

## ✅ Solution (3 Steps)

### Step 1: Get Real Google Credentials (5 min)

Go to: https://console.cloud.google.com/

1. Create new project: "RevCart"
2. Enable Google+ API
3. Create OAuth 2.0 credentials (Web application)
4. Add redirect URIs:
   - http://localhost:4200
   - http://localhost:8081
5. Copy Client ID and Client Secret

**Your credentials will look like:**
```
Client ID: 123456789-abcdefghijklmnop.apps.googleusercontent.com
Client Secret: GOCSPX-aBcDeFgHiJkLmNoPqRs
```

---

### Step 2: Update Backend (1 min)

Edit: `backend/src/main/resources/application.properties`

Replace:
```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
```

With your real credentials:
```properties
spring.security.oauth2.client.registration.google.client-id=123456789-abcdefghijklmnop.apps.googleusercontent.com
spring.security.oauth2.client.registration.google.client-secret=GOCSPX-aBcDeFgHiJkLmNoPqRs
```

Save file.

---

### Step 3: Update Frontend (1 min)

Edit: `src/app/pages/auth/login/login.component.ts`

Find line 37:
```typescript
client_id: 'YOUR_GOOGLE_CLIENT_ID',
```

Replace with your real Client ID:
```typescript
client_id: '123456789-abcdefghijklmnop.apps.googleusercontent.com',
```

Save file.

---

## 🔄 Rebuild & Restart

### Terminal 1 - Backend
```bash
cd backend
mvn clean package -DskipTests
java -jar target/revcart-backend-1.0.0.jar
```

### Terminal 2 - Frontend
```bash
ng serve
```

---

## ✅ Test

1. Open http://localhost:4200/login
2. Click "Sign in with Google"
3. Complete authentication
4. Should redirect to home page ✅

---

## 🐛 If Still Not Working

### Check 1: Verify Credentials Match

**Backend** (`application.properties`):
```
spring.security.oauth2.client.registration.google.client-id=123456789-abcdefghijklmnop.apps.googleusercontent.com
```

**Frontend** (`login.component.ts` line 37):
```typescript
client_id: '123456789-abcdefghijklmnop.apps.googleusercontent.com'
```

Should be **EXACTLY THE SAME**

### Check 2: Browser Console
```
1. Press F12
2. Go to Console tab
3. Click "Sign in with Google"
4. Look for error messages
```

### Check 3: Backend Logs
```
Look for error messages in backend console
```

---

## 📋 Checklist

- [ ] Google credentials obtained (not placeholders)
- [ ] Backend `application.properties` updated
- [ ] Frontend `login.component.ts` updated
- [ ] Backend rebuilt: `mvn clean package -DskipTests`
- [ ] Backend restarted
- [ ] Frontend restarted
- [ ] Test login works

---

## 🎯 That's It!

Follow these 3 steps and Google login will work! 🎉

**Key Point**: Use REAL credentials from Google Cloud Console, not placeholders.
