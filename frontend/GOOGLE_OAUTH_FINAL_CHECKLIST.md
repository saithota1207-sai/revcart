# Google OAuth - Final Setup Checklist

## ❌ Current Status
- ❌ Using placeholder credentials
- ❌ Error: "The OAuth client was not found"

## ✅ What You Need to Do

### 1. Get Real Credentials from Google Cloud Console
- [ ] Go to https://console.cloud.google.com/
- [ ] Create new project: "RevCart"
- [ ] Enable Google+ API
- [ ] Create OAuth 2.0 credentials (Web application)
- [ ] Add redirect URIs:
  - [ ] http://localhost:4200
  - [ ] http://localhost:8081
- [ ] Copy Client ID
- [ ] Copy Client Secret

### 2. Update Backend
- [ ] Open: `backend/src/main/resources/application.properties`
- [ ] Find: `spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID`
- [ ] Replace with real Client ID
- [ ] Find: `spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET`
- [ ] Replace with real Client Secret
- [ ] Save file

### 3. Update Frontend
- [ ] Open: `src/app/pages/auth/login/login.component.ts`
- [ ] Find line 37: `client_id: 'YOUR_GOOGLE_CLIENT_ID',`
- [ ] Replace with real Client ID
- [ ] Save file

### 4. Rebuild Backend
- [ ] Open terminal
- [ ] Run: `cd backend`
- [ ] Run: `mvn clean package -DskipTests`
- [ ] Wait for: `BUILD SUCCESS`

### 5. Restart Backend
- [ ] Run: `java -jar target/revcart-backend-1.0.0.jar`
- [ ] Wait for: `Started RevCartApplication`

### 6. Restart Frontend
- [ ] Open new terminal
- [ ] Run: `ng serve`
- [ ] Wait for: `✔ Compiled successfully`

### 7. Test
- [ ] Open http://localhost:4200/login
- [ ] Click "Sign in with Google"
- [ ] Complete authentication
- [ ] Should redirect to home page ✅

---

## 🎯 Key Points

1. **Use REAL credentials** - not placeholders
2. **Client ID** goes in both backend and frontend
3. **Client Secret** goes ONLY in backend
4. **Redirect URIs** must be exactly:
   - http://localhost:4200
   - http://localhost:8081
5. **Rebuild backend** after updating credentials
6. **Restart both** backend and frontend

---

## 📋 Verification

Before testing, verify:
- [ ] Backend `application.properties` has real Client ID
- [ ] Backend `application.properties` has real Client Secret
- [ ] Frontend `login.component.ts` line 37 has real Client ID
- [ ] Frontend and Backend Client IDs are the SAME
- [ ] Redirect URIs in Google Cloud Console are correct
- [ ] Backend rebuilt successfully
- [ ] Backend running on port 8081
- [ ] Frontend running on port 4200

---

## ✨ Once Complete

Google OAuth login will work! 🎉

You'll be able to:
- Click "Sign in with Google"
- Authenticate with Google account
- Auto-create user in database
- Get JWT token
- Access RevCart features

---

## 📞 Need Help?

Read: `GET_GOOGLE_CREDENTIALS_NOW.md`

It has step-by-step instructions with screenshots.
