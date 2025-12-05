@echo off
echo Verifying deployment status...
echo.

echo === Docker Containers ===
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
echo.

echo === Network Status ===
docker network ls | findstr revcart-network
echo.

echo === Application Health Check ===
echo Checking backend health...
curl -s http://localhost:8081/actuator/health 2>nul || echo "Backend not responding"
echo.

echo Checking frontend...
curl -s -I http://localhost:4200 2>nul || echo "Frontend not responding"
echo.

echo === Container Logs (last 10 lines) ===
echo Backend logs:
docker logs --tail 10 revcart-backend 2>nul || echo "No backend container"
echo.
echo Frontend logs:
docker logs --tail 10 revcart-frontend 2>nul || echo "No frontend container"

pause