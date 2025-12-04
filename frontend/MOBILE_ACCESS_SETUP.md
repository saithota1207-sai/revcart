# Mobile Access Setup Guide

## Problem
When accessing the application from a phone on the same network, you get "localhost refused" error because `localhost` only works on the same machine.

## Solution
The application now automatically detects your computer's IP address and connects to it from mobile devices.

## How It Works

### Frontend Changes
- Created `ApiConfigService` that dynamically determines the backend URL based on the current hostname
- All services now use `ApiConfigService` instead of hardcoded `localhost:8081`
- When accessed from a phone, the app automatically uses the phone's network IP instead of localhost

### Backend Changes
- Updated CORS configuration to allow requests from any IP on port 4200
- This enables cross-origin requests from mobile devices

## Setup Instructions

### 1. Find Your Computer's IP Address

**Windows:**
```bash
ipconfig
```
Look for "IPv4 Address" under your network adapter (usually something like `192.168.x.x`)

**Mac/Linux:**
```bash
ifconfig
```

### 2. Start Backend Server
```bash
cd backend
mvn spring-boot:run
```
Backend runs on: `http://YOUR_IP:8081`

### 3. Start Frontend Server
```bash
cd RevCart_p1
ng serve --host 0.0.0.0
```
Frontend runs on: `http://YOUR_IP:4200`

### 4. Access from Phone
Open your phone's browser and navigate to:
```
http://YOUR_IP:4200
```

Replace `YOUR_IP` with your computer's actual IP address (e.g., `http://192.168.1.100:4200`)

## Example
If your computer's IP is `192.168.1.100`:
- Desktop: `http://localhost:4200`
- Phone: `http://192.168.1.100:4200`

Both will automatically connect to the backend at `http://192.168.1.100:8081`

## Troubleshooting

### Still getting "localhost refused"?
1. Make sure both frontend and backend are running
2. Check your firewall isn't blocking port 8081
3. Verify your phone is on the same network as your computer
4. Try accessing the backend directly: `http://YOUR_IP:8081/api/products`

### Can't find your IP?
- Windows: Open Command Prompt and type `ipconfig`
- Look for IPv4 Address (usually starts with 192.168 or 10.0)

### Port already in use?
- Backend: Change port in `application.properties`
- Frontend: Use `ng serve --port 5000` for different port
