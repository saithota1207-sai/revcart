# RevCart API Testing Guide

## Base URL
```
http://localhost:8080/api
```

## Authentication APIs

### 1. User Registration
**POST** `/auth/signup`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "phone": "9876543210",
  "address": "123 Main Street, City"
}
```

**Response:**
```json
{
  "message": "User registered successfully!"
}
```

### 2. User Login
**POST** `/auth/signin`

**Request Body:**
```json
{
  "email": "saithota1207@gmail.com",
  "password": "admin123"
}
```

**Response:**
```json
{
  "message": "Login successful",
  "user": {
    "id": 1,
    "name": "Sai Thota",
    "email": "saithota1207@gmail.com",
    "role": "ADMIN"
  }
}
```

## Product APIs

### 1. Get All Products
**GET** `/products`

**Response:**
```json
[
  {
    "id": 1,
    "name": "Fresh Apples",
    "category": "fruits",
    "price": 120.00,
    "unit": "1kg",
    "image": "https://images.unsplash.com/photo-1560806887-1e4cd0b6cbd6?w=300&h=200&fit=crop",
    "description": "Fresh red apples",
    "stockQuantity": 100
  }
]
```

### 2. Get Product by ID
**GET** `/products/{id}`

**Example:** `/products/1`

### 3. Get Products by Category
**GET** `/products/category/{category}`

**Example:** `/products/category/fruits`

### 4. Search Products
**GET** `/products/search?name={searchTerm}`

**Example:** `/products/search?name=apple`

## Testing with Postman

### Setup
1. Create new collection "RevCart API"
2. Set base URL variable: `{{baseUrl}}` = `http://localhost:8080/api`

### Test Scenarios

#### Authentication Flow
1. Register new user
2. Login with credentials
3. Verify response contains user data

#### Product Operations
1. Get all products
2. Filter by category
3. Search by name
4. Get specific product details

### Sample Postman Collection
```json
{
  "info": {
    "name": "RevCart API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8080/api"
    }
  ],
  "item": [
    {
      "name": "Auth",
      "item": [
        {
          "name": "Register User",
          "request": {
            "method": "POST",
            "header": [
              {
                "key": "Content-Type",
                "value": "application/json"
              }
            ],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"name\": \"Test User\",\n  \"email\": \"test@example.com\",\n  \"password\": \"password123\"\n}"
            },
            "url": {
              "raw": "{{baseUrl}}/auth/signup",
              "host": ["{{baseUrl}}"],
              "path": ["auth", "signup"]
            }
          }
        }
      ]
    }
  ]
}
```

## cURL Examples

### Register User
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "password": "password123"
  }'
```

### Login User
```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "email": "saithota1207@gmail.com",
    "password": "admin123"
  }'
```

### Get All Products
```bash
curl -X GET http://localhost:8080/api/products
```

### Search Products
```bash
curl -X GET "http://localhost:8080/api/products/search?name=apple"
```