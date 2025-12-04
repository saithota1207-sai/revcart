# Google OAuth Architecture & Flow Diagrams

## System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         FRONTEND (Angular)                       │
│                    http://localhost:4200                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │           Login Component                                │   │
│  │  ┌────────────────────────────────────────────────────┐  │   │
│  │  │ Email/Password Login Form                          │  │   │
│  │  └────────────────────────────────────────────────────┘  │   │
│  │  ┌────────────────────────────────────────────────────┐  │   │
│  │  │ Google Sign-In Button                              │  │   │
│  │  │ (Renders using Google's official button)           │  │   │
│  │  └────────────────────────────────────────────────────┘  │   │
│  └──────────────────────────────────────────────────────────┘   │
│                           │                                      │
│                           ▼                                      │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │           Auth Service                                   │   │
│  │  - login(email, password)                               │   │
│  │  - googleLogin(token)  ◄── NEW                          │   │
│  │  - logout()                                             │   │
│  │  - setCurrentUser()                                     │   │
│  └──────────────────────────────────────────────────────────┘   │
│                           │                                      │
│                           ▼                                      │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │           Local Storage                                  │   │
│  │  - token (JWT)                                          │   │
│  │  - currentUser (user details)                           │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ HTTP POST
                              │ /api/auth/oauth2/google
                              │ { token: "google_id_token" }
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                         BACKEND (Spring Boot)                    │
│                    http://localhost:8081                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │           OAuth2Controller                               │   │
│  │  POST /api/auth/oauth2/google                           │   │
│  │  - Receives Google ID token                             │   │
│  │  - Calls OAuth2Service                                  │   │
│  │  - Returns JWT token + user details                     │   │
│  └──────────────────────────────────────────────────────────┘   │
│                           │                                      │
│                           ▼                                      │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │           OAuth2Service                                  │   │
│  │  - authenticateWithGoogle(token)                        │   │
│  │  - Verify token with Google API                         │   │
│  │  - Extract user info (email, name, picture)             │   │
│  │  - Create/Update user in database                       │   │
│  │  - Generate JWT token                                   │   │
│  └──────────────────────────────────────────────────────────┘   │
│                           │                                      │
│                           ▼                                      │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │           User Repository                                │   │
│  │  - findByEmail(email)                                   │   │
│  │  - save(user)                                           │   │
│  └──────────────────────────────────────────────────────────┘   │
│                           │                                      │
│                           ▼                                      │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │           MySQL Database                                 │   │
│  │  - users table                                          │   │
│  │  - Stores user: email, name, password, role, etc.       │   │
│  └──────────────────────────────────────────────────────────┘   │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ HTTP Response
                              │ { token, user details }
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    GOOGLE API                                    │
│              https://accounts.google.com                         │
├─────────────────────────────────────────────────────────────────┤
│  - Verify ID token signature                                    │
│  - Validate audience (Client ID)                                │
│  - Return token validity                                        │
└─────────────────────────────────────────────────────────────────┘
```

## Google OAuth Flow Diagram

```
┌─────────────┐                                    ┌──────────────┐
│   User      │                                    │   Frontend   │
│  (Browser)  │                                    │   (Angular)  │
└──────┬──────┘                                    └──────┬───────┘
       │                                                   │
       │ 1. Click "Sign in with Google"                   │
       ├──────────────────────────────────────────────────►│
       │                                                   │
       │                                    2. Initialize Google
       │                                       Sign-In SDK
       │                                                   │
       │ 3. Google Auth Dialog Opens                       │
       │◄──────────────────────────────────────────────────┤
       │                                                   │
       │ 4. User enters Google credentials                 │
       │    and grants permissions                         │
       │                                                   │
       │ 5. Google returns ID token                        │
       │◄──────────────────────────────────────────────────┤
       │                                                   │
       │                                    6. Send token to
       │                                       backend
       │                                                   │
       │                                    ┌──────────────┴──────┐
       │                                    │                     │
       │                                    ▼                     │
       │                            ┌──────────────────┐          │
       │                            │  Backend         │          │
       │                            │  OAuth2Service   │          │
       │                            └────────┬─────────┘          │
       │                                     │                    │
       │                                     │ 7. Verify token
       │                                     │    with Google API
       │                                     │                    │
       │                            ┌────────▼─────────┐          │
       │                            │  Google API      │          │
       │                            │  Verification    │          │
       │                            └────────┬─────────┘          │
       │                                     │                    │
       │                                     │ 8. Token valid
       │                                     │                    │
       │                            ┌────────▼─────────┐          │
       │                            │  Create/Update   │          │
       │                            │  User in DB      │          │
       │                            └────────┬─────────┘          │
       │                                     │                    │
       │                            ┌────────▼─────────┐          │
       │                            │  Generate JWT    │          │
       │                            │  Token           │          │
       │                            └────────┬─────────┘          │
       │                                     │                    │
       │                    9. Return JWT + user details          │
       │◄──────────────────────────────────────────────────────────┤
       │                                                   │
       │                                    10. Store JWT in
       │                                        localStorage
       │                                                   │
       │                                    11. Redirect to
       │                                        home page
       │                                                   │
       │ 12. Logged in! ✅                                │
       │◄──────────────────────────────────────────────────┤
       │                                                   │
```

## Component Interaction Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    LOGIN COMPONENT                          │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ ngOnInit()                                           │   │
│  │ └─► initializeGoogleSignIn()                         │   │
│  │     └─► google.accounts.id.initialize()             │   │
│  │     └─► google.accounts.id.renderButton()           │   │
│  └──────────────────────────────────────────────────────┘   │
│                           │                                  │
│                           ▼                                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ User clicks Google button                            │   │
│  │ └─► handleGoogleLogin(response)                      │   │
│  │     └─► authService.googleLogin(token)              │   │
│  └──────────────────────────────────────────────────────┘   │
│                           │                                  │
└───────────────────────────┼──────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    AUTH SERVICE                             │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ googleLogin(token: string)                           │   │
│  │ └─► http.post('/api/auth/oauth2/google', {token})   │   │
│  │     └─► tap((response) => {                          │   │
│  │         setCurrentUser(response, response.token)     │   │
│  │     })                                               │   │
│  └──────────────────────────────────────────────────────┘   │
│                           │                                  │
│                           ▼                                  │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ setCurrentUser(user, token)                          │   │
│  │ └─► localStorage.setItem('currentUser', user)        │   │
│  │ └─► localStorage.setItem('token', token)             │   │
│  │ └─► currentUserSubject.next(user)                    │   │
│  │ └─► showWelcomeSubject.next(true)                    │   │
│  └──────────────────────────────────────────────────────┘   │
│                           │                                  │
└───────────────────────────┼──────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    LOGIN COMPONENT                          │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ Subscribe to googleLogin() response                  │   │
│  │ ├─► next: router.navigate(['/home'])                │   │
│  │ └─► error: alert(error.message)                     │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

## Data Flow Diagram

```
┌──────────────────────────────────────────────────────────────┐
│                    FRONTEND                                  │
│                                                              │
│  Google ID Token                                            │
│  ├─ Header: { alg: "RS256", kid: "..." }                   │
│  ├─ Payload: {                                              │
│  │   iss: "https://accounts.google.com",                   │
│  │   sub: "1234567890",                                    │
│  │   email: "user@gmail.com",                              │
│  │   name: "John Doe",                                     │
│  │   picture: "https://...",                               │
│  │   aud: "CLIENT_ID",                                     │
│  │   exp: 1234567890                                       │
│  │ }                                                        │
│  └─ Signature: "..."                                        │
│                                                              │
│  Sent to Backend:                                           │
│  POST /api/auth/oauth2/google                              │
│  { "token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjEifQ..." }     │
│                                                              │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│                    BACKEND                                   │
│                                                              │
│  OAuth2Service.authenticateWithGoogle(token)               │
│  ├─ Verify token with Google API                           │
│  ├─ Extract payload:                                        │
│  │  ├─ email: "user@gmail.com"                             │
│  │  ├─ name: "John Doe"                                    │
│  │  └─ picture: "https://..."                              │
│  ├─ Find user by email                                      │
│  ├─ If not exists:                                          │
│  │  └─ Create new User:                                     │
│  │     ├─ email: "user@gmail.com"                          │
│  │     ├─ name: "John Doe"                                 │
│  │     ├─ profilePicture: "https://..."                    │
│  │     ├─ password: "oauth2-{timestamp}"                   │
│  │     ├─ role: "USER"                                     │
│  │     ├─ isVerified: true                                 │
│  │     └─ phone: ""                                         │
│  ├─ Generate JWT token                                      │
│  └─ Return response:                                        │
│     {                                                       │
│       "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",  │
│       "type": "Bearer",                                     │
│       "id": 1,                                              │
│       "firstName": "John",                                  │
│       "lastName": "Doe",                                    │
│       "email": "user@gmail.com",                            │
│       "role": "USER",                                       │
│       "profilePicture": "https://...",                      │
│       "message": "Google login successful"                  │
│     }                                                       │
│                                                              │
└──────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌──────────────────────────────────────────────────────────────┐
│                    FRONTEND                                  │
│                                                              │
│  Store in localStorage:                                     │
│  ├─ token: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."       │
│  └─ currentUser: {                                          │
│     "id": 1,                                                │
│     "firstName": "John",                                    │
│     "lastName": "Doe",                                      │
│     "email": "user@gmail.com",                              │
│     "role": "USER",                                         │
│     "profilePicture": "https://..."                         │
│  }                                                          │
│                                                              │
│  Redirect to /home                                          │
│  User logged in! ✅                                         │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

## Database Schema

```
┌─────────────────────────────────────────────────────────────┐
│                    USERS TABLE (MySQL)                      │
├─────────────────────────────────────────────────────────────┤
│ Column              │ Type         │ Notes                   │
├─────────────────────┼──────────────┼─────────────────────────┤
│ id                  │ BIGINT       │ Primary Key             │
│ email               │ VARCHAR(255) │ Unique, from Google     │
│ name                │ VARCHAR(255) │ From Google             │
│ password            │ VARCHAR(255) │ Random for OAuth users  │
│ phone               │ VARCHAR(20)  │ Empty for OAuth users   │
│ address             │ TEXT         │ Null initially          │
│ profile_picture     │ VARCHAR(500) │ From Google             │
│ is_verified         │ BOOLEAN      │ True for OAuth users    │
│ role                │ VARCHAR(50)  │ USER, ADMIN, etc.       │
│ created_at          │ TIMESTAMP    │ Auto-generated          │
│ updated_at          │ TIMESTAMP    │ Auto-generated          │
└─────────────────────┴──────────────┴─────────────────────────┘

Example OAuth User:
┌─────────────────────────────────────────────────────────────┐
│ id: 1                                                       │
│ email: user@gmail.com                                       │
│ name: John Doe                                              │
│ password: $2a$10$... (oauth2-1234567890)                   │
│ phone: (empty)                                              │
│ address: (null)                                             │
│ profile_picture: https://lh3.googleusercontent.com/...     │
│ is_verified: true                                           │
│ role: USER                                                  │
│ created_at: 2024-01-15 10:30:00                            │
│ updated_at: 2024-01-15 10:30:00                            │
└─────────────────────────────────────────────────────────────┘
```

## Security Flow

```
┌─────────────────────────────────────────────────────────────┐
│                    SECURITY LAYERS                          │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│ Layer 1: Token Verification                                │
│ ├─ Google ID token received from frontend                  │
│ ├─ Backend verifies signature with Google's public keys    │
│ ├─ Validates audience (Client ID)                          │
│ ├─ Checks token expiration (1 hour)                        │
│ └─ Rejects if any validation fails                         │
│                                                              │
│ Layer 2: User Management                                   │
│ ├─ Extract email from verified token                       │
│ ├─ Find or create user in database                         │
│ ├─ Store profile picture from Google                       │
│ ├─ Set isVerified = true                                   │
│ └─ Generate random password (not used)                     │
│                                                              │
│ Layer 3: JWT Token Generation                              │
│ ├─ Generate JWT token with user email                      │
│ ├─ Set expiration to 24 hours                              │
│ ├─ Sign with application secret key                        │
│ └─ Return to frontend                                      │
│                                                              │
│ Layer 4: Session Management                                │
│ ├─ Frontend stores JWT in localStorage                     │
│ ├─ Frontend sends JWT in Authorization header              │
│ ├─ Backend validates JWT on each request                   │
│ ├─ Stateless authentication (no sessions)                  │
│ └─ CORS configured for frontend origin                     │
│                                                              │
│ Layer 5: Password Security                                 │
│ ├─ OAuth users get random password                         │
│ ├─ Password hashed with BCrypt                             │
│ ├─ Password never used for OAuth login                     │
│ ├─ Password reset still available if needed                │
│ └─ Email-based password reset with token                   │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

## Deployment Architecture

```
┌──────────────────────────────────────────────────────────────┐
│                    PRODUCTION SETUP                          │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ CDN / Load Balancer                                    │ │
│  │ (Optional: CloudFront, Nginx, etc.)                    │ │
│  └────────────────────────────────────────────────────────┘ │
│                      │                                       │
│                      ▼                                       │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ Frontend (Angular)                                     │ │
│  │ - Hosted on S3 / Vercel / Netlify                      │ │
│  │ - HTTPS enabled                                        │ │
│  │ - Google Sign-In script loaded                         │ │
│  └────────────────────────────────────────────────────────┘ │
│                      │                                       │
│                      ▼                                       │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ Backend (Spring Boot)                                  │ │
│  │ - Hosted on EC2 / ECS / Kubernetes                     │ │
│  │ - HTTPS enabled                                        │ │
│  │ - OAuth2 endpoint available                            │ │
│  │ - CORS configured for production domain                │ │
│  └────────────────────────────────────────────────────────┘ │
│                      │                                       │
│                      ▼                                       │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ Databases                                              │ │
│  │ - MySQL (RDS): User data                               │ │
│  │ - MongoDB (Atlas): Notifications, logs                 │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐ │
│  │ Google Cloud                                           │ │
│  │ - OAuth 2.0 credentials configured                     │ │
│  │ - Redirect URIs: production domain                     │ │
│  │ - API keys secured in environment variables            │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

## Summary

This architecture provides:
- ✅ Secure Google OAuth integration
- ✅ Automatic user creation and management
- ✅ JWT-based session management
- ✅ Profile picture storage
- ✅ Email auto-verification
- ✅ Seamless user experience
- ✅ Production-ready security
