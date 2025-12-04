# MongoDB Aggregation Implementation - Complete Guide

## Overview
MongoDB aggregation pipeline has been implemented for advanced analytics including "Most Purchased Items" and "Top Categories by Revenue".

## Configuration

### MongoDB Connection
**File**: `application.properties`
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/revcart_db
spring.data.mongodb.auto-index-creation=true
```

### MongoDB Document
**File**: `OrderAnalytics.java`
```java
@Document(collection = "order_analytics")
public class OrderAnalytics {
    private Long productId;
    private String productName;
    private String category;
    private Integer purchaseCount;
    private BigDecimal totalRevenue;
    private Long lastUpdated;
}
```

## Implementation Details

### 1. MongoDB Repository with Aggregation Queries
**File**: `OrderAnalyticsRepository.java`

#### Query 1: Most Purchased Items (Top 10)
```java
@Aggregation(pipeline = {
    "{ $sort: { purchaseCount: -1 } }",
    "{ $limit: 10 }"
})
List<OrderAnalytics> findTopPurchasedItems();
```

**MongoDB Pipeline**:
```javascript
db.order_analytics.aggregate([
  { $sort: { purchaseCount: -1 } },
  { $limit: 10 }
])
```

**Result**: Returns 10 products sorted by purchase count (highest first)

#### Query 2: Top Items by Category
```java
@Aggregation(pipeline = {
    "{ $match: { category: ?0 } }",
    "{ $sort: { purchaseCount: -1 } }",
    "{ $limit: 5 }"
})
List<OrderAnalytics> findTopItemsByCategory(String category);
```

**MongoDB Pipeline**:
```javascript
db.order_analytics.aggregate([
  { $match: { category: "fruits" } },
  { $sort: { purchaseCount: -1 } },
  { $limit: 5 }
])
```

**Result**: Returns top 5 products in specified category

#### Query 3: Top Categories by Revenue
```java
@Aggregation(pipeline = {
    "{ $group: { _id: '$category', totalRevenue: { $sum: '$totalRevenue' }, count: { $sum: 1 } } }",
    "{ $sort: { totalRevenue: -1 } }"
})
List<Object> findTopCategoriesByRevenue();
```

**MongoDB Pipeline**:
```javascript
db.order_analytics.aggregate([
  { $group: { 
      _id: '$category', 
      totalRevenue: { $sum: '$totalRevenue' }, 
      count: { $sum: 1 } 
    } 
  },
  { $sort: { totalRevenue: -1 } }
])
```

**Result**: Groups by category and returns total revenue per category

### 2. MongoDB Analytics Service
**File**: `MongoAnalyticsService.java`

#### updateAnalytics()
- Reads all orders from MySQL
- Calculates purchase count and revenue for each product
- Stores aggregated data in MongoDB
- Called periodically or on-demand

```java
public void updateAnalytics() {
    // 1. Fetch all orders from MySQL
    List<Order> orders = orderRepository.findAll();
    
    // 2. Calculate metrics for each product
    Map<Long, OrderAnalytics> analyticsMap = new HashMap<>();
    for (Order order : orders) {
        for (OrderItem item : order.getOrderItems()) {
            // Aggregate purchase count and revenue
            analyticsMap.putIfAbsent(productId, new OrderAnalytics(...));
            analytics.setPurchaseCount(count + item.getQuantity());
            analytics.setTotalRevenue(revenue + (price * quantity));
        }
    }
    
    // 3. Save to MongoDB
    analyticsRepository.deleteAll();
    analyticsRepository.saveAll(analyticsMap.values());
}
```

#### getMostPurchasedItems()
- Returns top 10 most purchased products
- Uses MongoDB aggregation
- Sorted by purchase count (descending)

#### getTopCategoriesByRevenue()
- Groups products by category
- Calculates total revenue per category
- Returns sorted by revenue (descending)

#### getAnalyticsSummary()
- Combines multiple analytics
- Returns most purchased items and top categories

### 3. Updated Analytics Controller
**File**: `AnalyticsController.java`

#### New Endpoints

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/api/analytics/update-analytics` | POST | Trigger analytics update |
| `/api/analytics/most-purchased` | GET | Get top 10 most purchased items |
| `/api/analytics/top-categories` | GET | Get top categories by revenue |
| `/api/analytics/summary` | GET | Get analytics summary |

## API Usage Examples

### 1. Update Analytics
```bash
POST /api/analytics/update-analytics
```

**Response**:
```json
{
  "success": true,
  "message": "Analytics updated successfully"
}
```

### 2. Get Most Purchased Items
```bash
GET /api/analytics/most-purchased
```

**Response**:
```json
{
  "success": true,
  "data": [
    {
      "productId": 1,
      "productName": "Fresh Apples",
      "category": "fruits",
      "purchaseCount": 45,
      "totalRevenue": 5400.00
    },
    {
      "productId": 6,
      "productName": "Tomatoes",
      "category": "vegetables",
      "purchaseCount": 38,
      "totalRevenue": 3040.00
    }
  ]
}
```

### 3. Get Top Categories by Revenue
```bash
GET /api/analytics/top-categories
```

**Response**:
```json
{
  "success": true,
  "data": [
    {
      "_id": "fruits",
      "totalRevenue": 12500.00,
      "count": 8
    },
    {
      "_id": "vegetables",
      "totalRevenue": 9800.00,
      "count": 6
    }
  ]
}
```

### 4. Get Analytics Summary
```bash
GET /api/analytics/summary
```

**Response**:
```json
{
  "success": true,
  "data": {
    "mostPurchasedItems": [...],
    "topCategories": [...]
  }
}
```

## MongoDB Aggregation Pipeline Stages

### Stage 1: $match
Filters documents based on criteria
```javascript
{ $match: { category: "fruits" } }
```

### Stage 2: $group
Groups documents and performs aggregation
```javascript
{ $group: { 
    _id: '$category', 
    totalRevenue: { $sum: '$totalRevenue' },
    count: { $sum: 1 }
  } 
}
```

### Stage 3: $sort
Sorts documents
```javascript
{ $sort: { purchaseCount: -1 } }
```

### Stage 4: $limit
Limits number of documents
```javascript
{ $limit: 10 }
```

## Data Flow

```
1. Orders placed in MySQL
   ↓
2. POST /api/analytics/update-analytics triggered
   ↓
3. MongoAnalyticsService.updateAnalytics() called
   ↓
4. Reads all orders from MySQL
   ↓
5. Calculates metrics (purchase count, revenue)
   ↓
6. Stores aggregated data in MongoDB
   ↓
7. GET /api/analytics/most-purchased called
   ↓
8. MongoDB aggregation pipeline executes
   ↓
9. Results returned to frontend
```

## Performance Benefits

### Before (Java Stream API)
- Loads all data into memory
- Processes in application layer
- Slower for large datasets
- Higher memory usage

### After (MongoDB Aggregation)
- Processing done in database
- Optimized query execution
- Faster for large datasets
- Lower memory usage
- Scalable to millions of records

## Example Aggregation Queries

### Query 1: Most Purchased Items
```javascript
db.order_analytics.aggregate([
  { $sort: { purchaseCount: -1 } },
  { $limit: 10 }
])
```

### Query 2: Top Categories by Revenue
```javascript
db.order_analytics.aggregate([
  { $group: { 
      _id: '$category', 
      totalRevenue: { $sum: '$totalRevenue' },
      count: { $sum: 1 }
    } 
  },
  { $sort: { totalRevenue: -1 } }
])
```

### Query 3: Average Purchase Count by Category
```javascript
db.order_analytics.aggregate([
  { $group: { 
      _id: '$category', 
      avgPurchaseCount: { $avg: '$purchaseCount' }
    } 
  }
])
```

### Query 4: Products with Revenue > 5000
```javascript
db.order_analytics.aggregate([
  { $match: { totalRevenue: { $gt: 5000 } } },
  { $sort: { totalRevenue: -1 } }
])
```

## Integration with Frontend

### Angular Service
```typescript
getAnalyticsSummary(): Observable<any> {
  return this.http.get('/api/analytics/summary');
}

getMostPurchasedItems(): Observable<any> {
  return this.http.get('/api/analytics/most-purchased');
}

getTopCategories(): Observable<any> {
  return this.http.get('/api/analytics/top-categories');
}
```

### Display in Dashboard
```html
<div class="analytics-section">
  <h3>Most Purchased Items</h3>
  <table>
    <tr *ngFor="let item of mostPurchasedItems">
      <td>{{item.productName}}</td>
      <td>{{item.purchaseCount}}</td>
      <td>₹{{item.totalRevenue}}</td>
    </tr>
  </table>
</div>
```

## Maintenance

### Update Analytics Periodically
```bash
# Trigger update after each order
POST /api/analytics/update-analytics

# Or schedule via cron job
@Scheduled(fixedDelay = 3600000) // Every hour
public void updateAnalyticsPeriodically() {
    mongoAnalyticsService.updateAnalytics();
}
```

## Conclusion

✅ **MongoDB Aggregation Fully Implemented**

### Features:
- Top 10 most purchased items
- Top categories by revenue
- Category-specific top items
- Efficient database-level aggregation
- Scalable for large datasets
- Real-time analytics updates

### Performance:
- Database-level processing
- Reduced memory usage
- Faster query execution
- Optimized for analytics
