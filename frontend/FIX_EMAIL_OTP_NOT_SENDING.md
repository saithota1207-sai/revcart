# Fix: OTP Email Not Sending

## Problem
OTP verification page appears but no email is being sent.

## Root Cause
Gmail requires an **App Password** for SMTP, not your regular Gmail password.

---

## Solution: Generate Gmail App Password

### Step 1: Enable 2-Factor Authentication
1. Go to: https://myaccount.google.com/
2. Click "Security" (left sidebar)
3. Scroll to "2-Step Verification"
4. Click "2-Step Verification"
5. Follow steps to enable 2FA
6. Verify with your phone

### Step 2: Generate App Password
1. Go to: https://myaccount.google.com/apppasswords
2. Select "Mail" and "Windows Computer"
3. Click "Generate"
4. Google will show a 16-character password
5. **Copy this password** (without spaces)

Example:
```
abcd efgh ijkl mnop
```
Copy as: `abcdefghijklmnop`

### Step 3: Update application.properties
Edit: `backend/src/main/resources/application.properties`

Replace:
```properties
spring.mail.password=vbanptcefhczvoqx
```

With your 16-character App Password:
```properties
spring.mail.password=abcdefghijklmnop
```

### Step 4: Rebuild Backend
```bash
cd backend
mvn clean package -DskipTests
```

### Step 5: Restart Backend
```bash
java -jar target/revcart-backend-1.0.0.jar
```

### Step 6: Test
1. Register new user
2. Check email for OTP
3. Should receive OTP now! ✅

---

## Alternative: Use Different Email Service

If Gmail doesn't work, use SendGrid or Mailgun:

### SendGrid Setup
1. Create account: https://sendgrid.com/
2. Get API key
3. Update `application.properties`:
```properties
spring.mail.host=smtp.sendgrid.net
spring.mail.port=587
spring.mail.username=apikey
spring.mail.password=YOUR_SENDGRID_API_KEY
```

### Mailgun Setup
1. Create account: https://www.mailgun.com/
2. Get SMTP credentials
3. Update `application.properties`:
```properties
spring.mail.host=smtp.mailgun.org
spring.mail.port=587
spring.mail.username=YOUR_MAILGUN_EMAIL
spring.mail.password=YOUR_MAILGUN_PASSWORD
```

---

## Verify Email Configuration

### Check Backend Logs
Look for errors like:
```
Failed to send email: Authentication failed
Failed to send email: Connection refused
```

### Test Email Endpoint
Create a test endpoint to verify email sending works.

---

## Common Issues

### Issue: "Authentication failed"
**Solution**: App Password is incorrect or 2FA not enabled

### Issue: "Connection refused"
**Solution**: Gmail SMTP server blocked or wrong port

### Issue: "Invalid credentials"
**Solution**: Email or password has spaces or special characters

---

## Quick Fix Checklist

- [ ] Enable 2-Factor Authentication on Gmail
- [ ] Generate App Password from Google Account
- [ ] Copy 16-character App Password (no spaces)
- [ ] Update `application.properties` with App Password
- [ ] Rebuild backend: `mvn clean package -DskipTests`
- [ ] Restart backend
- [ ] Test registration and OTP sending

---

## After Fixing

1. Restart backend
2. Register new user
3. Check email for OTP
4. Enter OTP to verify
5. Login ✅

---

## Status

Once you update the Gmail App Password, OTP emails will be sent successfully!
