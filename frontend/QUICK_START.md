# RevCart - Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Step 1: Prerequisites Check

```bash
# Check Java version (should be 17+)
java -version

# Check Node.js version (should be 18+)
node -v

# Check npm version
npm -v

# Check Maven version (should be 3.8+)
mvn -v
```

---

## Step 2: Start MySQL

### Windows
```bash
# Start MySQL service
net start MySQL80

# Or open MySQL Workbench and connect
```

### Linux
```bash
sudo systemctl start mysql
```

### Mac
```bash
brew services start mysql
```

### Create Database
```bash
mysql -u root -p
# Enter password when prompted

# In MySQL console:
CREATE DATABASE revcart;
EXIT;
```

---

## Step 3: Start Backend

### Terminal 1 - Backend

```bash
cd d:\RevCart_p1\backend

# Build and run
mvn clean install
mvn spring-boot:run
```

**Expected output**:
```
Started RevCartApplication in X seconds
Tomcat started on port(s): 9090
```

✅ Backend ready at: `http://localhost:9090`

---

## Step 4: Start Frontend

### Terminal 2 - Frontend

```bash
cd d:\RevCart_p1

# Install dependencies (first time only)
npm install

# Start development server
ng serve
```

Or:
```bash
npm start
```

**Expected output**:
```
✔ Compiled successfully.
Application bundle generated successfully.
```

✅ Frontend ready at: `http://localhost:4200`

---

## Step 5: Access Application

Open browser and go to:
```
http://localhost:4200
```

### Test Credentials

**Admin User**:
- Email: `admin@revcart.com`
- Password: `admin123`

**Regular User**:
- Email: `user@revcart.com`
- Password: `user123`

**Delivery Agent**:
- Email: `agent@revcart.com`
- Password: `agent123`

---

## 📋 Verification Checklist

- [ ] MySQL running and database created
- [ ] Backend running on http://localhost:9090
- [ ] Frontend running on http://localhost:4200
- [ ] Can access http://localhost:4200 in browser
- [ ] Can login with test credentials
- [ ] Can view products
- [ ] Can add items to cart

---

## 🔧 Common Commands

### Backend Commands

```bash
# Build only
mvn clean install

# Run development server
mvn spring-boot:run

# Build JAR
mvn clean package

# Run JAR
java -jar target/revcart-backend-1.0.0.jar

# Run with custom port
java -jar target/revcart-backend-1.0.0.jar --server.port=8080

# Run tests
mvn test

# Clean build artifacts
mvn clean
```

### Frontend Commands

```bash
# Install dependencies
npm install

# Start development server
ng serve

# Build for production
ng build --configuration production

# Run tests
ng test

# Run linting
ng lint

# Clear cache
ng cache clean
```

### Database Commands

```bash
# Connect to MySQL
mysql -u root -p

# Create database
CREATE DATABASE revcart;

# Show databases
SHOW DATABASES;

# Use database
USE revcart;

# Show tables
SHOW TABLES;

# Drop database
DROP DATABASE revcart;
```

---

## 🌐 API Endpoints

### Authentication
```
POST   /api/auth/signup
POST   /api/auth/signin
POST   /api/auth/forgot-password
POST   /api/auth/reset-password
```

### Products
```
GET    /api/products
GET    /api/products/{id}
GET    /api/products/category/{category}
GET    /api/products/search?name=...
POST   /api/products (Admin)
PUT    /api/products/{id} (Admin)
DELETE /api/products/{id} (Admin)
```

### Cart
```
GET    /api/cart
POST   /api/cart/add
PUT    /api/cart/update
DELETE /api/cart/remove/{productId}
DELETE /api/cart/clear
```

### Orders
```
POST   /api/orders
GET    /api/orders
GET    /api/orders/{id}
DELETE /api/orders/{id}
PUT    /api/orders/{id}/status
```

### Payments
```
POST   /api/payments
POST   /api/payments/{id}/process
POST   /api/payments/{id}/refund
```

### Delivery
```
POST   /api/delivery/agent/register
POST   /api/delivery/agent/login
GET    /api/delivery/available-agents
POST   /api/delivery/assign/{orderId}
PUT    /api/delivery/status/{orderId}
POST   /api/delivery/location/update
GET    /api/delivery/location/{orderId}
```

### Analytics
```
GET    /api/analytics/dashboard
GET    /api/analytics/sales
GET    /api/analytics/users
```

---

## 📱 Frontend Routes

```
/                          Home
/products                  Product Listing
/product/:id               Product Detail
/cart                      Shopping Cart
/checkout                  Checkout
/login                     Login
/register                  Registration
/profile                   User Profile
/orders                    Order History
/wishlist                  Wishlist
/coupons                   Coupons
/admin                     Admin Dashboard
/admin/product/new         Create Product
/admin/product/:id         Edit Product
/admin/analytics           Analytics Dashboard
/delivery-agent/register   Agent Registration
/delivery-agent/login      Agent Login
/delivery-agent/dashboard  Agent Dashboard
/delivery-agent/tracking   Delivery Tracking
```

---

## 🐛 Troubleshooting

### Backend won't start
```bash
# Check if port 9090 is in use
netstat -ano | findstr :9090

# Kill process on port 9090
taskkill /PID <PID> /F

# Try again
mvn spring-boot:run
```

### Frontend won't start
```bash
# Check if port 4200 is in use
netstat -ano | findstr :4200

# Kill process on port 4200
taskkill /PID <PID> /F

# Clear npm cache
npm cache clean --force

# Try again
ng serve
```

### MySQL connection error
```bash
# Check MySQL is running
mysql -u root -p

# If not running, start it
net start MySQL80

# Verify database exists
mysql -u root -p -e "SHOW DATABASES;"
```

### API not responding
```bash
# Check backend is running
curl http://localhost:9090/api/products

# Check logs
# Look for errors in backend console
```

---

## 📊 Project Structure

```
RevCart_p1/
├── backend/                    # Spring Boot backend
│   ├── src/main/java/
│   │   └── com/revcart/
│   │       ├── controller/     # REST controllers
│   │       ├── service/        # Business logic
│   │       ├── entity/         # Database entities
│   │       ├── repository/     # Data access
│   │       ├── config/         # Configuration
│   │       └── dto/            # Data transfer objects
│   └── pom.xml                 # Maven configuration
│
├── src/                        # Angular frontend
│   ├── app/
│   │   ├── pages/              # Page components
│   │   ├── components/         # Reusable components
│   │   ├── services/           # API services
│   │   ├── guards/             # Route guards
│   │   └── models/             # TypeScript interfaces
│   ├── assets/                 # Static assets
│   └── environments/           # Environment config
│
├── BUILD_INSTRUCTIONS.md       # Detailed build guide
├── TROUBLESHOOTING.md          # Troubleshooting guide
├── FINAL_IMPLEMENTATION.md     # Implementation details
└── QUICK_START.md              # This file
```

---

## 🎯 Next Steps

1. **Explore the application**
   - Login with test credentials
   - Browse products
   - Add items to cart
   - Place an order

2. **Test admin features**
   - Login as admin
   - Create/edit products
   - View analytics
   - Manage orders

3. **Test delivery features**
   - Register as delivery agent
   - View assigned orders
   - Update delivery status
   - Track location

4. **Customize**
   - Update colors in CSS
   - Modify product data
   - Configure email settings
   - Add your own features

---

## 📞 Support

For issues:
1. Check TROUBLESHOOTING.md
2. Review application logs
3. Verify all prerequisites
4. Check configuration files
5. Test API endpoints with Postman

---

## ✅ You're All Set!

RevCart is now running locally. Start exploring and building! 🎉

**Backend**: http://localhost:9090
**Frontend**: http://localhost:4200
