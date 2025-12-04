# Delivery Agent System - Setup & Usage Guide

## Quick Start

### 1. Register a Delivery Agent
```bash
POST http://localhost:8081/api/delivery/agent/register
Parameters:
  email=john@delivery.com
  password=password123
  name=John Doe
  phone=9876543210
```

**Response**:
```json
{
  "message": "Registration successful",
  "agent": {
    "id": 1,
    "name": "John Doe",
    "email": "john@delivery.com",
    "phone": "9876543210",
    "status": "AVAILABLE"
  }
}
```

### 2. Login Delivery Agent
```bash
POST http://localhost:8081/api/delivery/agent/login
Parameters:
  email=john@delivery.com
  password=password123
```

**Response**:
```json
{
  "message": "Login successful",
  "agent": {
    "id": 1,
    "name": "John Doe",
    "email": "john@delivery.com",
    "status": "AVAILABLE"
  }
}
```

### 3. Assign Agent to Order (Admin)
```bash
POST http://localhost:8081/api/delivery/assign/123
```

**Response**:
```json
{
  "message": "Agent assigned",
  "agent": {
    "id": 1,
    "name": "John Doe",
    "status": "BUSY"
  }
}
```

### 4. Update Delivery Status
```bash
PUT http://localhost:8081/api/delivery/status/123?status=IN_TRANSIT
```

**Response**:
```json
{
  "message": "Status updated"
}
```

### 5. Update Agent Location
```bash
POST http://localhost:8081/api/delivery/location/update
Parameters:
  latitude=28.6139
  longitude=77.2090
```

**Response**:
```json
{
  "message": "Location updated",
  "latitude": 28.6139,
  "longitude": 77.2090
}
```

### 6. Get Delivery Location
```bash
GET http://localhost:8081/api/delivery/location/123
```

**Response**:
```json
{
  "latitude": 28.6139,
  "longitude": 77.2090,
  "timestamp": 1701234567890
}
```

## Complete Delivery Workflow

### Step 1: Agent Registration
```bash
curl -X POST "http://localhost:8081/api/delivery/agent/register?email=agent1@delivery.com&password=pass123&name=Agent One&phone=9876543210"
```

### Step 2: Agent Login
```bash
curl -X POST "http://localhost:8081/api/delivery/agent/login?email=agent1@delivery.com&password=pass123"
```

### Step 3: Get Available Agents
```bash
curl "http://localhost:8081/api/delivery/available-agents"
```

### Step 4: Assign Agent to Order
```bash
curl -X POST "http://localhost:8081/api/delivery/assign/1"
```

### Step 5: Update Order Status - SHIPPED
```bash
curl -X PUT "http://localhost:8081/api/delivery/status/1?status=SHIPPED"
```

### Step 6: Update Location
```bash
curl -X POST "http://localhost:8081/api/delivery/location/update?latitude=28.6139&longitude=77.2090"
```

### Step 7: Update Order Status - IN_TRANSIT
```bash
curl -X PUT "http://localhost:8081/api/delivery/status/1?status=IN_TRANSIT"
```

### Step 8: Update Location (Multiple Times)
```bash
curl -X POST "http://localhost:8081/api/delivery/location/update?latitude=28.6200&longitude=77.2150"
curl -X POST "http://localhost:8081/api/delivery/location/update?latitude=28.6250&longitude=77.2200"
```

### Step 9: Update Order Status - DELIVERED
```bash
curl -X PUT "http://localhost:8081/api/delivery/status/1?status=DELIVERED"
```

### Step 10: Verify Delivery Logs in MongoDB
```bash
mongosh
use revcart_db
db.delivery_logs.find({ orderId: 1 }).pretty()
```

## MongoDB Delivery Logs

### View All Delivery Logs
```javascript
db.delivery_logs.find().pretty()
```

### View Logs for Specific Order
```javascript
db.delivery_logs.find({ orderId: 1 }).sort({ timestamp: -1 })
```

### View Logs for Specific Agent
```javascript
db.delivery_logs.find({ deliveryAgentId: 1 }).sort({ timestamp: -1 })
```

### View Logs by Status
```javascript
db.delivery_logs.find({ status: "IN_TRANSIT" })
```

### Count Deliveries by Agent
```javascript
db.delivery_logs.aggregate([
  { $group: { _id: "$deliveryAgentId", count: { $sum: 1 } } }
])
```

## API Reference

### Delivery Agent Endpoints

#### Register Agent
```
POST /api/delivery/agent/register
Parameters: email, password, name, phone
Returns: Agent object with status AVAILABLE
```

#### Login Agent
```
POST /api/delivery/agent/login
Parameters: email, password
Returns: Agent object
```

#### Get Available Agents
```
GET /api/delivery/available-agents
Returns: List of agents with status AVAILABLE
```

#### Get Agent Details
```
GET /api/delivery/agent/{agentId}
Returns: Agent object with all details
```

#### Update Agent Status
```
PUT /api/delivery/agent/{agentId}/status
Parameters: status (AVAILABLE, BUSY, OFFLINE)
Returns: Success message
```

### Delivery Order Endpoints

#### Assign Agent to Order
```
POST /api/delivery/assign/{orderId}
Returns: Assigned agent object
```

#### Update Delivery Status
```
PUT /api/delivery/status/{orderId}
Parameters: status (PENDING, CONFIRMED, SHIPPED, IN_TRANSIT, DELIVERED, CANCELLED)
Returns: Success message
```

### Location Tracking Endpoints

#### Update Agent Location
```
POST /api/delivery/location/update
Parameters: latitude, longitude
Returns: Location confirmation
```

#### Get Delivery Location
```
GET /api/delivery/location/{orderId}
Returns: Current location with timestamp
```

## Order Status Flow

```
PENDING
   ↓
CONFIRMED
   ↓
SHIPPED (Agent picks up order)
   ↓
IN_TRANSIT (Agent on the way)
   ↓
DELIVERED (Order delivered)
```

## Agent Status Flow

```
AVAILABLE (Ready to accept deliveries)
   ↓
BUSY (Delivering an order)
   ↓
AVAILABLE (Delivery completed)
   ↓
OFFLINE (Agent offline)
```

## Testing with Postman

### 1. Create Collection: "Delivery System"

### 2. Add Requests:

**Register Agent**
- Method: POST
- URL: http://localhost:8081/api/delivery/agent/register
- Params: email, password, name, phone

**Login Agent**
- Method: POST
- URL: http://localhost:8081/api/delivery/agent/login
- Params: email, password

**Get Available Agents**
- Method: GET
- URL: http://localhost:8081/api/delivery/available-agents

**Assign Agent**
- Method: POST
- URL: http://localhost:8081/api/delivery/assign/1

**Update Status**
- Method: PUT
- URL: http://localhost:8081/api/delivery/status/1
- Params: status=IN_TRANSIT

**Update Location**
- Method: POST
- URL: http://localhost:8081/api/delivery/location/update
- Params: latitude=28.6139, longitude=77.2090

**Get Location**
- Method: GET
- URL: http://localhost:8081/api/delivery/location/1

## Troubleshooting

### Issue: "No available delivery agents"
**Solution**: Register and ensure agent status is AVAILABLE
```bash
POST /api/delivery/agent/register
```

### Issue: "Order not found"
**Solution**: Ensure order exists in database
```bash
GET /api/orders/1
```

### Issue: "Invalid credentials"
**Solution**: Check email and password are correct
```bash
POST /api/delivery/agent/login
```

### Issue: MongoDB logs not appearing
**Solution**: Ensure MongoDB is running and collection exists
```bash
mongosh
use revcart_db
db.delivery_logs.find()
```

## Performance Tips

1. **Batch Location Updates**: Update location every 30 seconds instead of continuously
2. **Use Indexes**: MongoDB indexes on orderId, deliveryAgentId, timestamp
3. **Archive Old Logs**: Move old delivery logs to archive collection
4. **Cache Agent Data**: Cache available agents list
5. **Optimize Queries**: Use projection to fetch only needed fields

## Security Recommendations

1. **Hash Passwords**: Use BCrypt for password hashing
2. **Add JWT**: Implement JWT token authentication
3. **Rate Limiting**: Limit location update frequency
4. **Audit Logging**: Log all status changes
5. **Encryption**: Encrypt sensitive data in transit

## Next Steps

1. ✅ Delivery agent registration and login
2. ✅ Real-time delivery tracking
3. ✅ MongoDB delivery logs
4. ⏳ Implement analytics dashboard
5. ⏳ Add map integration
6. ⏳ Implement proof of delivery
