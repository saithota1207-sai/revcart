@echo off
echo Testing RevCart Backend...

echo.
echo 1. Testing backend connection:
curl -s http://localhost:8081/api/debug/product-count

echo.
echo.
echo 2. Testing products endpoint:
curl -s http://localhost:8081/api/products

echo.
echo.
echo 3. Testing categories:
curl -s http://localhost:8081/api/products/categories

pause