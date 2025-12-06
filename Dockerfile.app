FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# Install dependencies
RUN apt-get update && apt-get install -y \
    curl \
    supervisor \
    nginx \
    openjdk-17-jre \
    && rm -rf /var/lib/apt/lists/*

# Install Node.js 18
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy backend
COPY backend/target/*.jar /app/backend.jar

# Copy and build frontend
COPY frontend/package*.json /app/frontend/
WORKDIR /app/frontend
RUN npm install
COPY frontend/ /app/frontend/
RUN npm run build

# Configure nginx
RUN rm /etc/nginx/sites-enabled/default
COPY nginx.conf /etc/nginx/sites-enabled/revcart
RUN cp -r /app/frontend/dist/revcart/* /var/www/html/ || cp -r /app/frontend/dist/frontend/* /var/www/html/ || cp -r /app/frontend/dist/* /var/www/html/

# Supervisor configuration
COPY supervisord-app.conf /etc/supervisor/conf.d/supervisord.conf

EXPOSE 4200 8081

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
