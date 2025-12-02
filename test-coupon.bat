@echo off
echo Testing Coupon API...

echo.
echo 1. Testing coupon validation with SAVE10:
curl -X POST "http://localhost:8081/api/coupons/validate" ^
-H "Content-Type: application/json" ^
-d "{\"code\":\"SAVE10\",\"orderAmount\":500}"

echo.
echo.
echo 2. Testing coupon validation with WELCOME20:
curl -X POST "http://localhost:8081/api/coupons/validate" ^
-H "Content-Type: application/json" ^
-d "{\"code\":\"WELCOME20\",\"orderAmount\":300}"

echo.
echo.
echo 3. Getting all coupons:
curl -s http://localhost:8081/api/coupons

pause