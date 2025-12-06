#!/bin/bash

# Start Spring Boot backend in background
java -jar /app/app.jar &

# Start nginx in foreground
nginx -g "daemon off;"