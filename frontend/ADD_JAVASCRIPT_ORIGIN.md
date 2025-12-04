# Add JavaScript Origin to Google Cloud Console

## Error: "The given origin is not allowed for the given client ID"

You need to add `http://localhost:4200` as an authorized JavaScript origin.

---

## Steps to Add JavaScript Origin

### 1. Go to Google Cloud Console
```
https://console.cloud.google.com/
```

### 2. Select Your Project
- Click "Select a Project" at top
- Select "RevCart" project

### 3. Go to Credentials
- Left sidebar → "Credentials"

### 4. Click Your OAuth 2.0 Client ID
- Find your client ID in the list
- Click on it to edit

### 5. Add JavaScript Origin
- Look for "Authorized JavaScript origins"
- Click "Add URI"
- Enter: `http://localhost:4200`
- Click "Save"

### 6. Wait 1-2 Minutes
- Google needs time to update

---

## Result

After adding the origin, you should have:

**Authorized JavaScript origins:**
- http://localhost:4200

**Authorized redirect URIs:**
- http://localhost:4200/auth/google-callback
- http://localhost:8081

---

## Test

1. Restart frontend: `ng serve`
2. Open http://localhost:4200/login
3. Click "Sign in with Google"
4. Should work now! ✅

---

## Screenshot Guide

```
Google Cloud Console
  ↓
Select Project: RevCart
  ↓
Credentials (left sidebar)
  ↓
Click OAuth 2.0 Client ID
  ↓
Authorized JavaScript origins
  ↓
Add URI: http://localhost:4200
  ↓
Save
  ↓
Done! ✅
```

---

## If You Can't Find It

1. Go to: https://console.cloud.google.com/apis/credentials
2. Look for "OAuth 2.0 Client IDs"
3. Click the one with type "Web application"
4. Add the JavaScript origin
5. Save

---

## That's It!

Once you add the JavaScript origin, Google OAuth will work! 🎉
