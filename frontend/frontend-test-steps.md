# Frontend Testing Steps

## 🚀 **Quick Test Procedure**

### **Step 1: Start Both Servers**
```bash
# Terminal 1 - Backend
cd d:\RevCart_p1\backend
mvn spring-boot:run  # or use VS Code Java Extension

# Terminal 2 - Frontend  
cd d:\RevCart_p1
ng serve
```

### **Step 2: Open Browser**
Navigate to: `http://localhost:4200`

### **Step 3: Test Authentication**
1. **Register New User**
   - Go to Register page
   - Fill form: Name, Email, Phone, Password
   - Check animated animal appears on password field
   - Submit and verify success message
   - Should redirect to login

2. **Login Test**
   - Use registered credentials OR
   - Admin: `saithota1207@gmail.com` / `admin123`
   - Verify JWT token stored in localStorage
   - Should redirect to home page

### **Step 4: Test Product Features**
1. **Home Page**
   - Verify hero section loads
   - Check category cards display
   - Click category to filter products

2. **Products Page**
   - Verify products load from backend
   - Test category filtering
   - Test search functionality
   - Test price range filter

3. **Add to Cart**
   - Click "Add to Cart" on any product
   - Verify popup notification appears
   - Check cart icon updates with count

### **Step 5: Test Cart Functionality**
1. **Cart Page**
   - Navigate to cart
   - Verify items display horizontally
   - Test quantity increment/decrement
   - Test remove item functionality
   - Verify price calculations

### **Step 6: Test UI/UX**
1. **Design Elements**
   - Verify earthy color scheme (#D6A99D, #FBF3D5, #D6DAC8, #9CAFAA)
   - Check glassmorphism effects
   - Verify no tilting animations on hover
   - Test responsive design on mobile

2. **Interactions**
   - Test all buttons and links
   - Verify loading states
   - Check error handling

### **Step 7: Test Integration**
1. **Backend Connection**
   - Open browser DevTools → Network tab
   - Perform actions and verify API calls
   - Check for CORS errors
   - Verify JWT tokens in request headers

2. **Data Persistence**
   - Add items to cart
   - Refresh page
   - Verify cart persists (if logged in)

## 🔍 **Common Issues to Check**

### **Backend Issues**
- [ ] Port 8080 not available
- [ ] MySQL not running
- [ ] Database connection errors
- [ ] JWT secret key issues

### **Frontend Issues**
- [ ] Port 4200 not available
- [ ] CORS errors in console
- [ ] API endpoint mismatches
- [ ] Authentication token issues

### **Integration Issues**
- [ ] API base URL incorrect
- [ ] Request/Response format mismatches
- [ ] Authentication headers missing
- [ ] Error handling not working

## 🎯 **Success Criteria**
- ✅ Both servers start without errors
- ✅ User can register and login
- ✅ Products load from database
- ✅ Cart operations work correctly
- ✅ UI displays with correct styling
- ✅ No console errors
- ✅ Responsive design works
- ✅ Add to cart notifications appear

## 🚨 **If Something Fails**
1. Check browser console for errors
2. Check backend logs for exceptions
3. Verify MySQL is running
4. Check API endpoints in Network tab
5. Verify JWT tokens are being sent
6. Check CORS configuration

## 📱 **Mobile Testing**
1. Open Chrome DevTools
2. Toggle device toolbar (Ctrl+Shift+M)
3. Test on different screen sizes
4. Verify touch interactions work
5. Check responsive layout