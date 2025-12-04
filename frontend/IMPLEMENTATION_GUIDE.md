# RevCart Implementation Guide

## Project Overview
RevCart is a full-stack e-commerce grocery delivery application built with:
- **Backend**: Spring Boot 3.3.5 with Java 23
- **Frontend**: Angular 18
- **Databases**: MySQL (structured data) + MongoDB (logs/notifications)

## Architecture

### Backend Services Implemented
1. **ProductService** - Product catalog management, search, filtering
2. **CartService** - Shopping cart operations with persistence
3. **OrderService** - Order creation, status tracking, cancellation
4. **PaymentService** - Payment processing, refunds, status management
5. **NotificationService** - User notifications (MongoDB)
6. **UserService** - Profile and address management

### Frontend Services Implemented
1. **AuthService** - User authentication with JWT
2. **ProductService** - Product data operations
3. **CartService** - Client-side cart management
4. **OrderService** - Order operations
5. **PaymentService** - Payment handling
6. **NotificationService** - Notification management
7. **UserService** - User profile operations

### Route Guards
- **AuthGuard** - Protects authenticated routes (cart, checkout, orders, profile)
- **AdminGuard** - Protects admin routes

## Setup Instructions

### Prerequisites
- Java 23+
- Node.js 18+
- MySQL 8.0+
- MongoDB 5.0+
- Angular CLI 18

### Backend Setup

1. **Database Configuration**
   - Update `backend/src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/revcart_db
   spring.datasource.username=root
   spring.datasource.password=root
   spring.data.mongodb.uri=mongodb://localhost:27017/revcart_logs
   ```

2. **Build and Run**
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```
   Backend runs on: `http://localhost:5258`

### Frontend Setup

1. **Install Dependencies**
   ```bash
   npm install
   ```

2. **Start Development Server**
   ```bash
   ng serve
   ```
   Frontend runs on: `http://localhost:4200`

## API Endpoints

### Authentication
- `POST /api/auth/signup` - Register new user
- `POST /api/auth/signin` - Login user

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/search?name={name}` - Search products
- `GET /api/products/categories` - Get all categories
- `POST /api/products` - Create product (Admin)
- `PUT /api/products/{id}` - Update product (Admin)
- `DELETE /api/products/{id}` - Delete product (Admin)

### Cart
- `GET /api/cart` - Get user cart
- `POST /api/cart/add` - Add item to cart
- `PUT /api/cart/update` - Update cart item
- `DELETE /api/cart/remove/{productId}` - Remove item from cart
- `DELETE /api/cart/clear` - Clear cart

### Orders
- `POST /api/orders` - Create order
- `GET /api/orders` - Get user orders
- `GET /api/orders/{orderId}` - Get order details
- `DELETE /api/orders/{orderId}` - Cancel order
- `PUT /api/orders/{orderId}/status` - Update order status (Admin)
- `GET /api/orders/admin/all` - Get all orders (Admin)
- `GET /api/orders/admin/status/{status}` - Get orders by status (Admin)

### Payments
- `POST /api/payments` - Create payment
- `POST /api/payments/{paymentId}/process` - Process payment
- `POST /api/payments/{paymentId}/fail` - Mark payment as failed
- `POST /api/payments/{paymentId}/refund` - Refund payment

### Notifications
- `GET /api/notifications` - Get user notifications
- `GET /api/notifications/unread` - Get unread notifications
- `GET /api/notifications/unread-count` - Get unread count
- `PUT /api/notifications/{notificationId}/read` - Mark as read
- `PUT /api/notifications/read-all` - Mark all as read

### User Profile
- `GET /api/user/profile` - Get user profile
- `PUT /api/user/profile` - Update profile
- `POST /api/user/change-password` - Change password
- `GET /api/user/addresses` - Get user addresses
- `POST /api/user/addresses` - Add address
- `PUT /api/user/addresses/{addressId}` - Update address
- `DELETE /api/user/addresses/{addressId}` - Delete address

## Key Features Implemented

### User Authentication
- JWT-based authentication
- Role-based access control (USER, ADMIN)
- Secure password storage with bcrypt
- Token persistence in localStorage

### Product Management
- Browse products by category
- Search and filter functionality
- Product details with images and descriptions
- Stock management
- Admin CRUD operations

### Shopping Cart
- Add/remove items
- Quantity adjustments
- Real-time price updates
- Cart persistence for logged-in users
- Local storage fallback for guests

### Order Management
- Order creation with delivery address
- Order status tracking (PENDING → CONFIRMED → PROCESSING → SHIPPED → DELIVERED)
- Order cancellation
- Order history
- Admin order management

### Payment Processing
- Multiple payment methods (UPI, CARD, COD, NET_BANKING)
- Payment status tracking
- Refund processing
- Transaction ID generation

### Notifications
- Order confirmation notifications
- Payment notifications
- Delivery updates
- Refund notifications
- Unread notification tracking

## Frontend Components

### Pages
- **Home** - Landing page with categories
- **Products** - Product listing with filters
- **Product Detail** - Individual product information
- **Cart** - Shopping cart management
- **Checkout** - Order placement
- **Login/Register** - Authentication
- **Profile** - User profile management
- **Orders** - Order history and tracking

### Components
- **Header** - Navigation and user menu
- **Footer** - Footer information
- **Product Card** - Product display component

## Database Schema

### MySQL Tables
- `users` - User accounts
- `products` - Product catalog
- `carts` - Shopping carts
- `cart_items` - Cart items
- `orders` - Orders
- `order_items` - Order items
- `payments` - Payment records
- `addresses` - User addresses

### MongoDB Collections
- `notifications` - User notifications
- `delivery_logs` - Delivery tracking logs

## Security Features
- JWT token-based authentication
- Spring Security configuration
- CORS enabled for frontend
- Password encryption with bcrypt
- Role-based authorization
- Request validation

## Error Handling
- Global exception handler
- Validation error responses
- Proper HTTP status codes
- User-friendly error messages

## Testing
Run tests with:
```bash
# Backend
mvn test

# Frontend
ng test
```

## Deployment

### Backend
```bash
mvn clean package
java -jar target/revcart-backend-1.0.0.jar
```

### Frontend
```bash
ng build --configuration production
# Deploy dist/ folder to web server
```

## Future Enhancements
- Real-time notifications with WebSocket
- Payment gateway integration (Razorpay/Stripe)
- Delivery tracking with maps
- Admin dashboard
- Analytics and reporting
- Email notifications
- SMS notifications
- Wishlist functionality
- Product reviews and ratings
- Recommendation engine

## Troubleshooting

### Backend Issues
- Ensure MySQL and MongoDB are running
- Check database credentials in application.properties
- Verify Java version is 23+

### Frontend Issues
- Clear node_modules and reinstall: `rm -rf node_modules && npm install`
- Clear Angular cache: `ng cache clean`
- Check that backend is running on port 5258

### CORS Issues
- Verify WebConfig.java has CORS configuration
- Check frontend URL is in allowed origins

## Support
For issues or questions, refer to the project documentation or create an issue in the repository.
