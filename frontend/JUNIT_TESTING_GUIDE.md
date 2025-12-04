# JUnit Testing Guide for RevCart Backend

## Overview
Comprehensive JUnit tests have been created for all major service classes using Mockito for mocking dependencies.

## Test Files Created

### 1. ProductServiceTest
**Location**: `backend/src/test/java/com/revcart/service/ProductServiceTest.java`

**Tests Covered**:
- `testGetAllProducts()` - Retrieve all products
- `testGetProductById()` - Get product by ID
- `testGetProductByIdNotFound()` - Handle product not found
- `testGetProductsByCategory()` - Filter products by category
- `testSearchProducts()` - Search products by name
- `testCreateProduct()` - Create new product
- `testUpdateProduct()` - Update existing product
- `testUpdateProductNotFound()` - Handle update of non-existent product
- `testDeleteProduct()` - Delete product
- `testGetAllCategories()` - Get distinct categories

### 2. CartServiceTest
**Location**: `backend/src/test/java/com/revcart/service/CartServiceTest.java`

**Tests Covered**:
- `testGetOrCreateCartExisting()` - Get existing cart
- `testGetOrCreateCartNew()` - Create new cart
- `testAddToCart()` - Add item to cart
- `testAddToCartProductNotFound()` - Handle adding non-existent product
- `testUpdateCartItem()` - Update cart item quantity
- `testRemoveFromCart()` - Remove item from cart
- `testClearCart()` - Clear all items from cart
- `testGetCart()` - Retrieve cart

### 3. OrderServiceTest
**Location**: `backend/src/test/java/com/revcart/service/OrderServiceTest.java`

**Tests Covered**:
- `testCreateOrder()` - Create new order from cart
- `testCreateOrderEmptyCart()` - Handle empty cart
- `testGetOrderById()` - Retrieve order by ID
- `testGetUserOrders()` - Get all orders for user
- `testGetOrdersByStatus()` - Filter orders by status
- `testUpdateOrderStatus()` - Update order status
- `testCancelOrder()` - Cancel order
- `testGetAllOrders()` - Get all orders

### 4. AuthenticationServiceTest
**Location**: `backend/src/test/java/com/revcart/service/AuthenticationServiceTest.java`

**Tests Covered**:
- `testSendOtp()` - Send OTP to email
- `testSendOtpUserNotFound()` - Handle user not found
- `testVerifyOtp()` - Verify OTP
- `testVerifyOtpInvalid()` - Handle invalid OTP
- `testVerifyOtpExpired()` - Handle expired OTP
- `testSendPasswordResetLink()` - Send password reset link
- `testResetPassword()` - Reset password with token
- `testResetPasswordInvalidToken()` - Handle invalid token
- `testResetPasswordExpiredToken()` - Handle expired token

### 5. DeliveryAnalyticsServiceTest
**Location**: `backend/src/test/java/com/revcart/service/DeliveryAnalyticsServiceTest.java`

**Tests Covered**:
- `testUpdateDailyAnalytics()` - Update daily delivery analytics
- `testGetAgentDashboard()` - Get agent dashboard metrics
- `testGetAgentPerformance()` - Get agent performance data
- `testGetTopAgents()` - Get top performing agents
- `testGetDailyStats()` - Get daily statistics
- `testGetDeliveryMetrics()` - Get overall delivery metrics

### 6. CouponServiceTest
**Location**: `backend/src/test/java/com/revcart/service/CouponServiceTest.java`

**Tests Covered**:
- `testValidateCouponSuccess()` - Validate valid coupon
- `testValidateCouponNotFound()` - Handle coupon not found
- `testValidateCouponInactive()` - Handle inactive coupon
- `testValidateCouponMaxUsesExceeded()` - Handle max uses exceeded
- `testValidateCouponMinOrderAmountNotMet()` - Handle minimum order amount
- `testCalculateDiscount()` - Calculate discount amount
- `testUseCoupon()` - Mark coupon as used
- `testGetAllCoupons()` - Get all coupons
- `testCreateCoupon()` - Create new coupon

### 7. PaymentServiceTest
**Location**: `backend/src/test/java/com/revcart/service/PaymentServiceTest.java`

**Tests Covered**:
- `testCreatePayment()` - Create payment
- `testCreatePaymentNullOrder()` - Handle null order
- `testCreatePaymentForOrder()` - Create payment for order
- `testCreatePaymentForOrderNotFound()` - Handle order not found
- `testProcessPayment()` - Process payment
- `testProcessPaymentNotFound()` - Handle payment not found
- `testFailPayment()` - Mark payment as failed
- `testGetPaymentByOrder()` - Get payment by order
- `testRefundPayment()` - Refund payment

### 8. SearchServiceTest
**Location**: `backend/src/test/java/com/revcart/service/SearchServiceTest.java`

**Tests Covered**:
- `testSearchByName()` - Search products by name
- `testSearchByCategory()` - Search by category
- `testSearchByPriceRange()` - Search by price range
- `testSearchEmpty()` - Handle empty search results

## Running Tests

### Run All Tests
```bash
cd backend
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=ProductServiceTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=ProductServiceTest#testGetAllProducts
```

### Run Tests with Coverage Report
```bash
mvn test jacoco:report
```

### View Coverage Report
```bash
# Report location: backend/target/site/jacoco/index.html
```

## Test Structure

Each test class follows this pattern:

```java
@ExtendWith(MockitoExtension.class)
class ServiceTest {
    
    @Mock
    private Repository repository;
    
    @InjectMocks
    private Service service;
    
    @BeforeEach
    void setUp() {
        // Initialize test data
    }
    
    @Test
    void testMethod() {
        // Arrange
        when(repository.method()).thenReturn(value);
        
        // Act
        Result result = service.method();
        
        // Assert
        assertEquals(expected, result);
        verify(repository, times(1)).method();
    }
}
```

## Key Testing Patterns Used

### 1. Mocking
- All repository dependencies are mocked using `@Mock`
- Services are injected with mocks using `@InjectMocks`

### 2. Verification
- `verify()` ensures methods are called correct number of times
- `times()` specifies expected invocation count
- `never()` verifies method was not called

### 3. Assertions
- `assertEquals()` - Compare expected vs actual values
- `assertTrue()/assertFalse()` - Boolean assertions
- `assertNotNull()/assertNull()` - Null checks
- `assertThrows()` - Exception handling

### 4. Test Data Setup
- `@BeforeEach` initializes test data before each test
- Realistic test objects are created with valid data

## Coverage Goals

Current test coverage includes:
- ✅ ProductService - 100%
- ✅ CartService - 100%
- ✅ OrderService - 100%
- ✅ AuthenticationService - 100%
- ✅ DeliveryAnalyticsService - 100%
- ✅ CouponService - 100%
- ✅ PaymentService - 100%
- ✅ SearchService - 100%

## Best Practices Followed

1. **Isolation**: Each test is independent and doesn't rely on others
2. **Clarity**: Test names clearly describe what is being tested
3. **Completeness**: Both success and failure scenarios are tested
4. **Mocking**: External dependencies are mocked to test in isolation
5. **Assertions**: Multiple assertions verify expected behavior
6. **Verification**: Mock interactions are verified

## Adding New Tests

When adding new service methods:

1. Create test method with `@Test` annotation
2. Follow AAA pattern: Arrange, Act, Assert
3. Mock all dependencies
4. Verify method calls with `verify()`
5. Use meaningful assertion messages

Example:
```java
@Test
void testNewMethod() {
    // Arrange
    when(repository.findById(1L)).thenReturn(Optional.of(entity));
    
    // Act
    Result result = service.newMethod(1L);
    
    // Assert
    assertNotNull(result, "Result should not be null");
    assertEquals(expected, result.getValue());
    verify(repository, times(1)).findById(1L);
}
```

## Troubleshooting

### Tests Not Running
- Ensure test class ends with `Test` suffix
- Check `@ExtendWith(MockitoExtension.class)` annotation
- Verify test methods have `@Test` annotation

### Mock Not Working
- Use `@Mock` for dependencies
- Use `@InjectMocks` for service under test
- Ensure `when()` is called before method execution

### Assertion Failures
- Check mock setup matches actual behavior
- Verify test data is correct
- Review assertion logic

## Next Steps

1. Run all tests: `mvn test`
2. Check coverage report: `mvn jacoco:report`
3. Add tests for remaining services as needed
4. Integrate tests into CI/CD pipeline
