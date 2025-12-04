# Get Google OAuth Credentials - Step by Step

## Error: "The OAuth client was not found"

This means you're using placeholder values. You need **REAL** credentials.

---

## 🔧 Get Real Credentials (10 minutes)

### Step 1: Go to Google Cloud Console
```
https://console.cloud.google.com/
```

### Step 2: Create Project
```
1. Click "Select a Project" at top
2. Click "New Project"
3. Name: RevCart
4. Click "Create"
5. Wait for project to be created
```

### Step 3: Enable Google+ API
```
1. Search for "Google+ API" in search bar
2. Click on "Google+ API"
3. Click "Enable"
4. Wait for it to enable
```

### Step 4: Create OAuth Credentials
```
1. Go to "Credentials" (left sidebar)
2. Click "Create Credentials"
3. Select "OAuth 2.0 Client ID"
4. If prompted, click "Configure OAuth consent screen"
5. Select "External"
6. Fill in:
   - App name: RevCart
   - User support email: your-email@gmail.com
   - Developer contact: your-email@gmail.com
7. Click "Save and Continue"
8. Click "Save and Continue" (skip scopes)
9. Click "Save and Continue" (skip test users)
10. Click "Back to Dashboard"
```

### Step 5: Create Web Application Credentials
```
1. Click "Create Credentials"
2. Select "OAuth 2.0 Client ID"
3. Select "Web application"
4. Name: RevCart Web
5. Under "Authorized redirect URIs", add:
   - http://localhost:4200
   - http://localhost:8081
6. Click "Create"
7. A popup will show your credentials
```

### Step 6: Copy Your Credentials
```
You'll see:
- Client ID: 123456789-abcdefghijklmnop.apps.googleusercontent.com
- Client Secret: GOCSPX-aBcDeFgHiJkLmNoPqRs

COPY THESE - you need them next
```

---

## 📝 Update Backend Configuration

Edit: `backend/src/main/resources/application.properties`

Find these lines:
```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
```

Replace with your real credentials:
```properties
spring.security.oauth2.client.registration.google.client-id=123456789-abcdefghijklmnop.apps.googleusercontent.com
spring.security.oauth2.client.registration.google.client-secret=GOCSPX-aBcDeFgHiJkLmNoPqRs
```

**Save the file.**

---

## 📝 Update Frontend Configuration

Edit: `src/app/pages/auth/login/login.component.ts`

Find line 37:
```typescript
client_id: 'YOUR_GOOGLE_CLIENT_ID',
```

Replace with your real Client ID:
```typescript
client_id: '123456789-abcdefghijklmnop.apps.googleusercontent.com',
```

**Save the file.**

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
3. Should work now! ✅

---

## ⚠️ Important

- **Client ID** and **Client Secret** are different
- **Client ID** goes in both backend AND frontend
- **Client Secret** goes ONLY in backend
- Keep **Client Secret** private - never share it
- Make sure redirect URIs are exactly:
  - http://localhost:4200
  - http://localhost:8081

---

## 🐛 If Still Getting Error

1. Verify Client ID is correct (not placeholder)
2. Verify Client Secret is correct (not placeholder)
3. Verify redirect URIs in Google Cloud Console
4. Rebuild backend: `mvn clean package -DskipTests`
5. Restart both backend and frontend
6. Try again

---

## ✨ That's It!

Once you have real credentials and update both files, it will work! 🎉
