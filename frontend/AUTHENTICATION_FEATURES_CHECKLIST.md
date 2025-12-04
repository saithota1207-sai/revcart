# Authentication & User Management - Implementation Checklist

## Feature 1: User Registration and Login (JWT + Spring Security) ✅ FULLY IMPLEMENTED

### Registration
- **Endpoint**: `POST /api/auth/signup`
- **Parameters**: name, email, password, phone, address
- **Features**:
  - ✅ Email uniqueness validation
  - ✅ Password encoding (BCrypt)
  - ✅ User creation with default role (USER)
  - ✅ Phone and address storage
- **Response**:
```json
{
  "message": "User registered successfully!"
}
```

### Login
- **Endpoint**: `POST /api/auth/signin`
- **Parameters**: email, password
- **Features**:
  - ✅ JWT token generation
  - ✅ User authentication
  - ✅ Role information returned
  - ✅ Profile picture support
- **Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "role": "USER",
  "profilePicture": null,
  "message": "Login successful"
}
```

### JWT Configuration
- **File**: `application.properties`
- **Secret**: `revCartSecretKeyForJWTTokenGeneration2024`
- **Expiration**: 86400000 ms (24 hours)
- **Token Filter**: `AuthTokenFilter.java`

### Spring Security
- **File**: `SecurityConfig.java`
- **Features**:
  - ✅ BCrypt password encoding
  - ✅ JWT authentication filter
  - ✅ CORS configuration
  - ✅ CSRF disabled (stateless)
  - ✅ Session management (STATELESS)

---

## Feature 2: Role-Based Access Control ✅ FULLY IMPLEMENTED

### Roles Supported
- **USER**: Regular customer (default)
- **ADMIN**: Administrator
- **DELIVERY_AGENT**: Delivery personnel (in DeliveryAgent entity)

### User Entity
- **File**: `User.java`
- **Role Field**: Enum (USER, ADMIN)
- **Default Role**: USER

### Role-Based Endpoints
- **Admin Only**: `/api/admin/**`, `/api/orders/admin/**`
- **Authenticated**: `/api/user/**`, `/api/orders/**`, `/api/cart/**`
- **Public**: `/api/auth/**`, `/api/products/**`, `/api/coupons/**`

### Security Configuration
```java
.authorizeHttpRequests(authz -> authz
    .requestMatchers("/api/admin/**").hasRole("ADMIN")
    .requestMatchers("/api/orders/admin/**").hasRole("ADMIN")
    .anyRequest().authenticated()
)
```

### Method-Level Security
- **Annotation**: `@PreAuthorize("hasRole('ADMIN')")`
- **Example**: Delivery agent assignment requires ADMIN role

---

## Feature 3: Profile Creation & Management ✅ FULLY IMPLEMENTED

### User Profile Fields
- **Name**: Full name
- **Email**: Unique email address
- **Phone**: Contact number
- **Address**: Delivery address
- **First Name**: First name
- **Last Name**: Last name
- **Profile Picture**: User avatar (LONGTEXT)
- **Role**: User role
- **Created At**: Account creation timestamp

### Profile Endpoints

#### Get Profile
```
GET /api/user/profile
```

**Response**:
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phone": "9876543210",
  "address": "123 Main St, City",
  "profilePicture": "base64_encoded_image",
  "role": "USER"
}
```

#### Update Profile
```
PUT /api/user/profile
Body: {
  "firstName": "John",
  "lastName": "Doe",
  "phone": "9876543210",
  "address": "123 Main St, City",
  "profilePicture": "base64_encoded_image"
}
```

**Response**:
```json
{
  "message": "Profile updated successfully"
}
```

### Address Management

#### Get All Addresses
```
GET /api/user/addresses
```

**Response**:
```json
[
  {
    "id": 1,
    "name": "Home",
    "addressLine": "123 Main St",
    "city": "New York",
    "state": "NY",
    "pincode": "10001",
    "phone": "9876543210",
    "isDefault": true
  }
]
```

#### Add Address
```
POST /api/user/addresses
Body: {
  "name": "Office",
  "addressLine": "456 Business Ave",
  "city": "New York",
  "state": "NY",
  "pincode": "10002",
  "phone": "9876543210",
  "isDefault": false
}
```

#### Update Address
```
PUT /api/user/addresses/{addressId}
Body: { address details }
```

#### Delete Address
```
DELETE /api/user/addresses/{addressId}
```

### Address Entity
- **File**: `Address.java`
- **Fields**: name, addressLine, city, state, pincode, phone, isDefault
- **Default Address**: Only one address can be default

---

## Feature 4: Password Reset (Email-Based) ✅ FULLY IMPLEMENTED

### Forgot Password
- **Endpoint**: `POST /api/auth/forgot-password`
- **Parameter**: email
- **Features**:
  - ✅ Reset token generation (UUID)
  - ✅ Token expiry (1 hour)
  - ✅ Email notification
  - ✅ Reset link generation
- **Response**:
```json
{
  "message": "Password reset link sent to email"
}
```

### Reset Password
- **Endpoint**: `POST /api/auth/reset-password`
- **Parameters**: token, newPassword
- **Features**:
  - ✅ Token validation
  - ✅ Token expiry check
  - ✅ Password encoding
  - ✅ Token cleanup
- **Response**:
```json
{
  "message": "Password reset successfully"
}
```

### Change Password
- **Endpoint**: `POST /api/user/change-password`
- **Parameters**: currentPassword, newPassword
- **Features**:
  - ✅ Current password verification
  - ✅ Password encoding
  - ✅ Authentication required
- **Response**:
```json
{
  "message": "Password changed successfully"
}
```

### User Entity Fields
- `resetToken`: String - Reset token
- `resetTokenExpiry`: LocalDateTime - Token expiry time

### Email Service
- **File**: `EmailService.java`
- **Method**: `sendPasswordResetEmail(email, resetLink)`
- **Configuration**: Gmail SMTP

---

## Feature 5: Account Verification Using OTP/Email ✅ FULLY IMPLEMENTED

### Send OTP
- **Endpoint**: `POST /api/auth/send-otp`
- **Parameter**: email
- **Features**:
  - ✅ 6-digit OTP generation
  - ✅ OTP expiry (10 minutes)
  - ✅ Email notification
  - ✅ User lookup
- **Response**:
```json
{
  "message": "OTP sent to email"
}
```

### Verify OTP
- **Endpoint**: `POST /api/auth/verify-otp`
- **Parameters**: email, otp
- **Features**:
  - ✅ OTP validation
  - ✅ Expiry check
  - ✅ Account verification
  - ✅ OTP cleanup
- **Response**:
```json
{
  "message": "Email verified successfully"
}
```

### User Entity Fields
- `otp`: String - One-time password
- `otpExpiry`: LocalDateTime - OTP expiry time
- `isVerified`: Boolean - Account verification status

### OTP Generation
```java
private String generateOtp() {
    return String.format("%06d", new Random().nextInt(999999));
}
```

### Email Service
- **Method**: `sendOtpEmail(email, otp)`
- **Configuration**: Gmail SMTP

---

## API Endpoints Summary

### Authentication Endpoints
| Endpoint | Method | Purpose | Auth |
|----------|--------|---------|------|
| `/api/auth/signup` | POST | Register user | No |
| `/api/auth/signin` | POST | Login user | No |
| `/api/auth/send-otp` | POST | Send OTP | No |
| `/api/auth/verify-otp` | POST | Verify OTP | No |
| `/api/auth/forgot-password` | POST | Request password reset | No |
| `/api/auth/reset-password` | POST | Reset password | No |

### User Profile Endpoints
| Endpoint | Method | Purpose | Auth |
|----------|--------|---------|------|
| `/api/user/profile` | GET | Get profile | Yes |
| `/api/user/profile` | PUT | Update profile | Yes |
| `/api/user/change-password` | POST | Change password | Yes |
| `/api/user/addresses` | GET | Get addresses | Yes |
| `/api/user/addresses` | POST | Add address | Yes |
| `/api/user/addresses/{id}` | PUT | Update address | Yes |
| `/api/user/addresses/{id}` | DELETE | Delete address | Yes |

---

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(120) NOT NULL,
    phone VARCHAR(15),
    address TEXT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    profile_picture LONGTEXT,
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    otp VARCHAR(6),
    otp_expiry TIMESTAMP,
    is_verified BOOLEAN DEFAULT false,
    reset_token VARCHAR(255),
    reset_token_expiry TIMESTAMP
);
```

### Addresses Table
```sql
CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(50),
    address_line VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50) NOT NULL,
    pincode VARCHAR(10) NOT NULL,
    phone VARCHAR(15),
    is_default BOOLEAN DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

---

## Security Features

### Password Security
- ✅ BCrypt encoding
- ✅ Password validation
- ✅ Current password verification
- ✅ Password reset with token

### JWT Security
- ✅ Token generation
- ✅ Token validation
- ✅ Token expiry (24 hours)
- ✅ Bearer token format

### OTP Security
- ✅ 6-digit random OTP
- ✅ 10-minute expiry
- ✅ One-time use
- ✅ Email verification

### Email Security
- ✅ Gmail SMTP
- ✅ TLS encryption
- ✅ Authentication required
- ✅ Async email sending

### Access Control
- ✅ Role-based authorization
- ✅ Method-level security
- ✅ Authentication required
- ✅ CORS configuration

---

## Email Configuration

### SMTP Settings
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=bujjithota27084@gmail.com
spring.mail.password=vbanptcefhczvoqx
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Email Templates
- OTP Email: Contains 6-digit OTP
- Password Reset: Contains reset link
- Verification: Account verification confirmation

---

## Testing Scenarios

### Registration
1. Register with valid email
2. Register with duplicate email (should fail)
3. Register with invalid email format (should fail)

### Login
1. Login with correct credentials
2. Login with wrong password (should fail)
3. Login with non-existent email (should fail)

### OTP Verification
1. Send OTP to email
2. Verify with correct OTP
3. Verify with wrong OTP (should fail)
4. Verify after expiry (should fail)

### Password Reset
1. Request password reset
2. Reset with valid token
3. Reset with expired token (should fail)
4. Reset with invalid token (should fail)

### Profile Management
1. Get profile
2. Update profile fields
3. Add address
4. Set default address
5. Update address
6. Delete address

---

## Conclusion

✅ **ALL AUTHENTICATION & USER MANAGEMENT FEATURES ARE FULLY IMPLEMENTED**

### Summary:
1. **User Registration & Login** - ✅ Complete with JWT
2. **Role-Based Access** - ✅ Admin, Customer, Delivery Agent
3. **Profile Management** - ✅ Complete with addresses
4. **Password Reset** - ✅ Email-based with token
5. **OTP Verification** - ✅ Email-based with expiry

### Key Strengths:
- Secure password encoding (BCrypt)
- JWT token authentication
- Role-based authorization
- Email verification
- Password reset mechanism
- Address management
- Profile customization
- Comprehensive error handling
