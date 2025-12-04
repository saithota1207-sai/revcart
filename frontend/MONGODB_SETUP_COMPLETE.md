# MongoDB Setup Complete - Quick Reference

## MongoDB Version
- **Version**: 8.2.1
- **Connection**: mongodb://localhost:27017/revcart_db
- **Status**: ✅ Running and Connected

## Collections Created

### order_analytics
Stores aggregated product analytics data

**Fields**:
- `_id`: MongoDB ObjectId
- `productId`: Long (indexed)
- `productName`: String
- `category`: String (indexed)
- `purchaseCount`: Integer (indexed)
- `totalRevenue`: BigDecimal (indexed)
- `lastUpdated`: Long

**Indexes**:
- `productId: 1`
- `category: 1`
- `purchaseCount: -1`
- `totalRevenue: -1`

## API Endpoints

### 1. Update Analytics
```bash
POST http://localhost:8081/api/analytics/update-analytics
```
Reads all orders from MySQL and updates MongoDB analytics

**Response**:
```json
{
  "success": true,
  "message": "Analytics updated successfully"
}
```

### 2. Get Most Purchased Items (Top 10)
```bash
GET http://localhost:8081/api/analytics/most-purchased
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
GET http://localhost:8081/api/analytics/top-categories
```

**Response**:
```json
{
  "success": true,
  "data": [
    {
      "_id": "electronics",
      "totalRevenue": 180000.00,
      "count": 1
    },
    {
      "_id": "sports",
      "totalRevenue": 22400.00,
      "count": 1
    },
    {
      "_id": "fruits",
      "totalRevenue": 7920.00,
      "count": 2
    }
  ]
}
```

### 4. Get Analytics Summary
```bash
GET http://localhost:8081/api/analytics/summary
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

## MongoDB Aggregation Queries

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

### Query 3: Top Items by Category
```javascript
db.order_analytics.aggregate([
  { $match: { category: 'fruits' } },
  { $sort: { purchaseCount: -1 } },
  { $limit: 5 }
])
```

### Query 4: Average Purchase Count by Category
```javascript
db.order_analytics.aggregate([
  { $group: { 
      _id: '$category', 
      avgPurchaseCount: { $avg: '$purchaseCount' }
    } 
  }
])
```

## MongoDB Shell Commands

### Connect to MongoDB
```bash
mongosh
```

### Switch to Database
```javascript
use revcart_db
```

### View Collections
```javascript
db.getCollectionNames()
```

### View All Analytics
```javascript
db.order_analytics.find().pretty()
```

### View Indexes
```javascript
db.order_analytics.getIndexes()
```

### Count Documents
```javascript
db.order_analytics.countDocuments()
```

### Delete All Analytics
```javascript
db.order_analytics.deleteMany({})
```

## Backend Integration

### MongoAnalyticsService Methods

1. **updateAnalytics()**
   - Reads all orders from MySQL
   - Calculates metrics for each product
   - Stores in MongoDB
   - Call: `POST /api/analytics/update-analytics`

2. **getMostPurchasedItems()**
   - Returns top 10 products by purchase count
   - Call: `GET /api/analytics/most-purchased`

3. **getTopCategoriesByRevenue()**
   - Groups by category, sums revenue
   - Call: `GET /api/analytics/top-categories`

4. **getAnalyticsSummary()**
   - Combines multiple analytics
   - Call: `GET /api/analytics/summary`

## Sample Data

Initial sample data is automatically loaded on backend startup:

| Product | Category | Purchases | Revenue |
|---------|----------|-----------|---------|
| Fresh Apples | fruits | 45 | ₹5,400 |
| Tomatoes | vegetables | 38 | ₹3,040 |
| Milk | dairy | 52 | ₹2,860 |
| Bread | bakery | 35 | ₹1,225 |
| Smartphone | electronics | 12 | ₹180,000 |
| Football | sports | 28 | ₹22,400 |
| Bananas | fruits | 42 | ₹2,520 |
| Onions | vegetables | 55 | ₹1,650 |
| Cheese | dairy | 18 | ₹3,600 |
| Croissant | bakery | 48 | ₹1,200 |

## Testing the APIs

### Using cURL

```bash
# Update analytics
curl -X POST http://localhost:8081/api/analytics/update-analytics

# Get most purchased items
curl http://localhost:8081/api/analytics/most-purchased

# Get top categories
curl http://localhost:8081/api/analytics/top-categories

# Get summary
curl http://localhost:8081/api/analytics/summary
```

### Using Postman

1. Create new request
2. Set method to GET/POST
3. Enter URL: `http://localhost:8081/api/analytics/{endpoint}`
4. Send request

## Performance Notes

- **Aggregation Pipeline**: Executed at database level
- **Indexes**: Optimized for sorting and filtering
- **Memory**: Efficient - no in-memory processing
- **Scalability**: Handles millions of records efficiently

## Troubleshooting

### MongoDB Connection Failed
```
Error: connect ECONNREFUSED 127.0.0.1:27017
```
**Solution**: Start MongoDB service
```bash
mongosh
```

### Collections Not Found
```
Error: collection not found
```
**Solution**: Restart backend to trigger MongoInitializer
```bash
# Kill and restart backend
taskkill /F /IM java.exe
java -jar target/revcart-backend-1.0.0.jar
```

### No Data in Analytics
```
Error: empty result set
```
**Solution**: Call update-analytics endpoint
```bash
POST /api/analytics/update-analytics
```

## Next Steps

1. ✅ MongoDB configured and running
2. ✅ Collections and indexes created
3. ✅ Sample data loaded
4. ✅ Aggregation queries working
5. ✅ API endpoints functional

**Ready for production use!**
