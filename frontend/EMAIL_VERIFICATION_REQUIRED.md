# Email Verification Required for New Users

## ✅ Implementation Complete

New users must verify their email with OTP before they can login.

---

## 🔄 New User Flow

### Step 1: User Registers
```
POST /api/auth/signup
{
  "name": "John Doe",
  "email": "user@example.com",
  "password": "password123",
  "phone": "9876543210",
  "address": "123 Main St"
}

Response:
{
  "message": "User registered successfully! OTP sent to email.",
  "email": "user@example.com",
  "requiresVerification": true
}
```

**What happens:**
- User created in database
- `isVerified` set to `false`
- OTP automatically sent to email
- User cannot login until verified

### Step 2: User Receives OTP
- 6-digit OTP sent to registered email
- OTP expires in 10 minutes

### Step 3: User Verifies Email
```
POST /api/auth/verify-otp?email=user@example.com&otp=123456

Response:
{
  "message": "Email verified successfully"
}
```

**What happens:**
- OTP validated
- `isVerified` set to `true`
- User can now login

### Step 4: User Logs In
```
POST /api/auth/signin
{
  "email": "user@example.com",
  "password": "password123"
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
  "profilePicture": null,
  "message": "Login successful"
}
```

---

## 🚫 Login Blocked Until Verified

If user tries to login before verifying email:

```
POST /api/auth/signin
{
  "email": "user@example.com",
  "password": "password123"
}

Response (400):
{
  "message": "Email not verified. Please verify your email first.",
  "requiresVerification": true,
  "email": "user@example.com"
}
```

---

## 📋 API Endpoints

### Register User (Auto-sends OTP)
```
POST /api/auth/signup
```

### Send OTP (Manual)
```
POST /api/auth/send-otp?email=user@example.com
```

### Verify OTP
```
POST /api/auth/verify-otp?email=user@example.com&otp=123456
```

### Login (Requires Verified Email)
```
POST /api/auth/signin
```

---

## 🔐 Security Features

✅ **OTP Verification**
- 6-digit random OTP
- 10-minute expiration
- Sent via Gmail SMTP
- Cleared after verification

✅ **Email Verification**
- `isVerified` flag in database
- Checked before login
- Cannot bypass verification

✅ **User Status**
- New users: `isVerified = false`
- Verified users: `isVerified = true`
- Google OAuth users: `isVerified = true` (auto-verified)

---

## 📊 Database Changes

### User Entity
```
- isVerified: Boolean (default: false)
- otp: String (6-digit code)
- otpExpiry: LocalDateTime (10 minutes)
```

### User States
- **New User**: `isVerified = false`, `otp = "123456"`, `otpExpiry = now + 10min`
- **Verified User**: `isVerified = true`, `otp = null`, `otpExpiry = null`
- **Google OAuth User**: `isVerified = true` (auto-verified)

---

## 🧪 Testing

### Test New User Registration
1. Register: `POST /api/auth/signup`
2. Check email for OTP
3. Verify: `POST /api/auth/verify-otp?email=...&otp=...`
4. Login: `POST /api/auth/signin`

### Test Login Before Verification
1. Register: `POST /api/auth/signup`
2. Try login immediately: `POST /api/auth/signin`
3. Should get error: "Email not verified"

### Test OTP Expiry
1. Register: `POST /api/auth/signup`
2. Wait 10+ minutes
3. Try verify: `POST /api/auth/verify-otp?email=...&otp=...`
4. Should get error: "OTP expired"

---

## 📧 Email Configuration

Gmail SMTP already configured in `application.properties`:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=bujjithota27084@gmail.com
spring.mail.password=vbanptcefhczvoqx
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

## ✨ Features

✅ **Auto OTP on Signup**
- OTP automatically sent when user registers
- No manual request needed

✅ **Email Verification Required**
- Cannot login without verifying email
- Prevents fake/invalid emails

✅ **OTP Expiry**
- OTP expires after 10 minutes
- User must request new OTP if expired

✅ **Resend OTP**
- User can request new OTP anytime
- Endpoint: `POST /api/auth/send-otp?email=...`

✅ **Google OAuth Auto-Verified**
- Google users auto-verified
- No OTP needed for Google login

---

## 🎯 Status

| Feature | Status |
|---------|--------|
| Auto OTP on signup | ✅ Done |
| Email verification required | ✅ Done |
| OTP expiry (10 min) | ✅ Done |
| Login blocked until verified | ✅ Done |
| Resend OTP | ✅ Done |
| Google OAuth auto-verified | ✅ Done |
| Email service | ✅ Done |
| Database schema | ✅ Done |

---

## 🚀 Ready to Use

Email verification is **fully implemented** and **production-ready**!

**Status**: ✅ COMPLETE
