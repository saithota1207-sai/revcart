# RevCart - Fixes Applied

## Issues Fixed

### 1. UPI Payment Details Form
**Problem**: Users couldn't enter UPI ID when selecting UPI payment method.

**Solution**: 
- Added `upiId` property to checkout component
- Added UPI ID input field in checkout template with placeholder examples
- Added validation to ensure UPI ID is provided when UPI payment is selected
- Added helpful text showing UPI format examples (paytm, googlepay, phonepe)

**Files Modified**:
- `src/app/pages/checkout/checkout.component.ts` - Added upiId property and validation
- `src/app/pages/checkout/checkout.component.html` - Added UPI input form

### 2. Orders Not Loading
**Problem**: Orders page shows error when trying to load user orders.

**Root Cause**: Backend service on port 5258 was not running due to port conflicts.

**Solution**:
- Fixed `AuthTokenFilter` class by adding `@Component` annotation so Spring can manage it as a bean
- Updated `SecurityConfig` to inject `AuthTokenFilter` instead of creating new instance
- Created `start-backend.bat` script to cleanly kill Java processes and restart backend

**Files Modified**:
- `backend/src/main/java/com/revcart/config/AuthTokenFilter.java` - Added @Component annotation
- `backend/src/main/java/com/revcart/config/SecurityConfig.java` - Changed to inject AuthTokenFilter

**How to Start Backend**:
1. Run the batch script: `start-backend.bat`
   OR
2. Manually:
   ```bash
   taskkill /IM java.exe /F
   timeout /t 3
   cd backend
   mvn clean spring-boot:run
   ```

## Backend Requirements

Ensure these services are running:
- **MySQL**: Database on localhost:3306
  - Database: `revcart_db`
  - Username: `root`
  - Password: `root`

- **MongoDB**: Document store on localhost:27017
  - Database: `revcart_logs`

## Testing the Fixes

### Test UPI Payment:
1. Go to Checkout page
2. Select "UPI Payment" option
3. Enter UPI ID (e.g., yourname@paytm)
4. Place order

### Test Orders Loading:
1. Ensure backend is running on port 5258
2. Go to Orders page
3. Orders should load from the backend

## API Endpoints Used

- **Orders**: `GET/POST http://localhost:8080/api/orders`
- **Payments**: `POST http://localhost:8080/api/payments`
- **Cart**: `GET/POST http://localhost:8080/api/cart`

## Notes

- Backend runs on port 8080 (configured in `application.properties`)
- Frontend runs on port 4200 (Angular default)
- CORS is configured to allow requests from `http://localhost:4200`
- Port changed from 5258 to 8080 to avoid port conflicts
- JWT authentication is enabled for all protected endpoints
