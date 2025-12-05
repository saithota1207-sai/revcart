@echo off
echo Creating Docker network for RevCart application...
docker network create revcart-network 2>nul || echo Network already exists
echo Docker network setup complete.