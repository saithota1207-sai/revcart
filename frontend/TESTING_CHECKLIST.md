# RevCart Testing Checklist

## 🚀 **Backend Testing**

### **1. Database Connection**
- [ ] MySQL server running on localhost:3306
- [ ] Database `revcart_db` created automatically
- [ ] All tables created (users, products, cart, orders, addresses)
- [ ] Sample data loaded (admin user, products)

### **2. Authentication APIs**
- [ ] POST `/api/auth/signup` - User registration
- [ ] POST `/api/auth/signin` - User login
- [ ] JWT token generation working
- [ ] Password encryption with BCrypt

### **3. Product APIs**
- [ ] GET `/api/products` - Get all products
- [ ] GET `/api/products/{id}` - Get product by ID
- [ ] GET `/api/products/category/{category}` - Get by category
- [ ] GET `/api/products/search?name={name}` - Search products

### **4. Cart APIs (Authenticated)**
- [ ] GET `/api/cart` - Get user cart
- [ ] POST `/api/cart/add` - Add item to cart
- [ ] PUT `/api/cart/update` - Update cart item
- [ ] DELETE `/api/cart/remove/{productId}` - Remove item
- [ ] DELETE `/api/cart/clear` - Clear cart

### **5. User Profile APIs**
- [ ] GET `/api/user/profile` - Get user profile
- [ ] PUT `/api/user/profile` - Update profile
- [ ] POST `/api/user/change-password` - Change password

### **6. Address APIs**
- [ ] GET `/api/user/addresses` - Get user addresses
- [ ] POST `/api/user/addresses` - Add address
- [ ] PUT `/api/user/addresses/{id}` - Update address
- [ ] DELETE `/api/user/addresses/{id}` - Delete address

### **7. Order APIs**
- [ ] POST `/api/orders` - Create order
- [ ] GET `/api/orders` - Get user orders
- [ ] GET `/api/orders/{orderId}` - Get specific order

### **8. Admin APIs**
- [ ] GET `/api/admin/users` - Get all users
- [ ] POST `/api/admin/products` - Add product
- [ ] PUT `/api/admin/products/{id}` - Update product
- [ ] DELETE `/api/admin/products/{id}` - Delete product
- [ ] GET `/api/admin/orders` - Get all orders

## 🎨 **Frontend Testing**

### **1. Authentication Pages**
- [ ] Login page loads correctly
- [ ] Registration page loads correctly
- [ ] Form validation working
- [ ] Password show/hide toggle
- [ ] Animated animal for password fields
- [ ] Error messages display properly
- [ ] Success redirects working

### **2. Home Page**
- [ ] Hero section displays
- [ ] Category cards working
- [ ] Featured products loading
- [ ] Navigation working
- [ ] Responsive design

### **3. Products Page**
- [ ] Product grid displays
- [ ] Category filtering works
- [ ] Price range filtering
- [ ] Search functionality
- [ ] Add to cart buttons work
- [ ] Product detail navigation

### **4. Product Detail Page**
- [ ] Product information displays
- [ ] Image gallery working
- [ ] Quantity selector
- [ ] Add to cart functionality
- [ ] Related products section

### **5. Cart Page**
- [ ] Cart items display horizontally
- [ ] Quantity increment/decrement
- [ ] Remove item functionality
- [ ] Price calculations correct
- [ ] Checkout button working
- [ ] Empty cart state

### **6. User Profile**
- [ ] Profile information displays
- [ ] Edit profile functionality
- [ ] Address management
- [ ] Order history
- [ ] Change password

### **7. Checkout Process**
- [ ] Address selection
- [ ] Payment method selection
- [ ] Order summary
- [ ] Place order functionality

### **8. UI/UX Features**
- [ ] Glassmorphism design applied
- [ ] Earthy color scheme consistent
- [ ] Animations working (no tilting)
- [ ] Add to cart notifications
- [ ] Responsive on mobile
- [ ] Loading states
- [ ] Error handling

## 🔧 **Integration Testing**

### **1. Frontend-Backend Integration**
- [ ] Login connects to backend API
- [ ] Registration connects to backend API
- [ ] Products load from backend
- [ ] Cart operations sync with backend
- [ ] JWT tokens handled correctly
- [ ] Error responses handled

### **2. Database Integration**
- [ ] User data persists correctly
- [ ] Product data loads from database
- [ ] Cart data saves to database
- [ ] Order data creates properly
- [ ] Address data manages correctly

### **3. Security Testing**
- [ ] JWT authentication working
- [ ] Protected routes secured
- [ ] Admin routes restricted
- [ ] CORS configured properly
- [ ] Password encryption secure

## 📱 **Browser Testing**
- [ ] Chrome compatibility
- [ ] Firefox compatibility
- [ ] Edge compatibility
- [ ] Mobile responsive
- [ ] Tablet responsive

## 🚀 **Performance Testing**
- [ ] Backend startup time < 30 seconds
- [ ] Frontend load time < 3 seconds
- [ ] API response times < 500ms
- [ ] Database queries optimized
- [ ] Image loading optimized

## 🎯 **Test Credentials**
- **Admin**: `saithota1207@gmail.com` / `admin123`
- **Test User**: Register new user for testing

## 📋 **Test URLs**
- **Frontend**: http://localhost:4200
- **Backend**: http://localhost:8080
- **API Docs**: http://localhost:8080/api

---

## ✅ **Testing Status**
- [ ] Backend Setup Complete
- [ ] Frontend Setup Complete
- [ ] Database Connected
- [ ] APIs Working
- [ ] UI/UX Complete
- [ ] Integration Working
- [ ] Ready for Production