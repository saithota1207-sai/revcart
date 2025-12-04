# RevCart - E-Commerce Grocery Delivery Application

## Project Overview
RevCart is a modern full-stack grocery delivery application built with Angular 18 frontend and Spring Boot backend. It provides a seamless shopping experience with features like user authentication, product catalog, shopping cart, and order management.

## Technology Stack

### Frontend (Angular 18)
- **Framework**: Angular 18 with standalone components
- **Styling**: Bootstrap 5 with custom SCSS
- **State Management**: Services with RxJS
- **HTTP Client**: Angular HttpClient
- **Routing**: Angular Router with guards
- **Build Tool**: Angular CLI

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.2.0
- **Security**: Spring Security with JWT
- **Database**: MySQL with JPA/Hibernate
- **Build Tool**: Maven
- **Java Version**: 17

## Project Structure

```
RevCart_p1/
├── src/                          # Angular Frontend
│   ├── app/
│   │   ├── components/           # Reusable components
│   │   │   ├── header/
│   │   │   ├── footer/
│   │   │   └── product-card/
│   │   ├── pages/               # Page components
│   │   │   ├── home/
│   │   │   ├── products/
│   │   │   ├── product-detail/
│   │   │   ├── cart/
│   │   │   ├── checkout/
│   │   │   ├── auth/
│   │   │   ├── profile/
│   │   │   └── orders/
│   │   ├── services/            # Business logic
│   │   │   ├── auth.service.ts
│   │   │   ├── cart.service.ts
│   │   │   └── product.service.ts
│   │   ├── models/              # TypeScript interfaces
│   │   └── guards/              # Route guards
│   ├── assets/                  # Static assets
│   └── environments/            # Environment configs
└── backend/                     # Spring Boot Backend
    ├── src/main/java/com/revcart/
    │   ├── entity/              # JPA entities
    │   ├── repository/          # Data repositories
    │   ├── service/             # Business services
    │   ├── controller/          # REST controllers
    │   ├── config/              # Configuration classes
    │   └── dto/                 # Data transfer objects
    └── src/main/resources/
        ├── application.properties
        └── data.json
```

## Key Features

### User Features
- **Authentication**: Login/Register with secure password encryption
- **Product Catalog**: Browse 64+ products across 8 categories
- **Search & Filter**: Find products by name, category, and price
- **Shopping Cart**: Add/remove items with quantity management
- **Product Details**: Detailed product information and images
- **Responsive Design**: Mobile-friendly UI with modern styling

### Admin Features
- **Product Management**: CRUD operations for products
- **User Management**: View and manage user accounts
- **Order Management**: Track and manage orders

### Technical Features
- **Security**: Spring Security with password encryption
- **Database**: MySQL with JPA/Hibernate ORM
- **API**: RESTful APIs with proper HTTP status codes
- **CORS**: Cross-origin resource sharing enabled
- **Validation**: Input validation on both frontend and backend

## Database Schema

### Users Table
- id (Primary Key)
- name
- email (Unique)
- password (Encrypted)
- phone
- address
- role (USER/ADMIN)
- created_at

### Products Table
- id (Primary Key)
- name
- category
- price
- unit
- image
- description
- stock_quantity

## API Endpoints

### Authentication
- `POST /api/auth/signin` - User login
- `POST /api/auth/signup` - User registration

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `GET /api/products/category/{category}` - Get products by category
- `GET /api/products/search?name=` - Search products

## Color Scheme
- **Primary**: #452829 (Dark Brown)
- **Secondary**: #57595B (Charcoal Gray)
- **Background**: #F3E8DF (Cream)
- **Accent**: #E8D1C5 (Light Beige)

## Setup Instructions

### Prerequisites
- Node.js 18+
- Angular CLI 18
- Java 17+
- Maven 3.6+
- MySQL 8.0+

### Frontend Setup
```bash
cd RevCart_p1
npm install
ng serve
```

### Backend Setup
```bash
cd backend
mvn spring-boot:run
```

### Database Setup
- Create MySQL database: `revcart_db`
- Username: `root`
- Password: `root`

## Default Admin User
- Email: `saithota1207@gmail.com`
- Password: `admin123`

## Development Team
- **Developer**: Sai Thota
- **Email**: saithota1207@gmail.com
- **Phone**: 9000795258
- **Location**: Chennai, India

## Future Enhancements
- Payment gateway integration
- Order tracking system
- Push notifications
- Mobile app development
- Advanced analytics dashboard