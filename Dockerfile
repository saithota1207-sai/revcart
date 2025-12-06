FROM ubuntu:22.04

# Avoid prompts from apt
ENV DEBIAN_FRONTEND=noninteractive

# Install dependencies
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    gnupg \
    supervisor \
    nginx \
    openjdk-17-jre \
    && rm -rf /var/lib/apt/lists/*

# Install Node.js 18
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - \
    && apt-get install -y nodejs \
    && rm -rf /var/lib/apt/lists/*

# Install MySQL
RUN apt-get update && apt-get install -y mysql-server \
    && rm -rf /var/lib/apt/lists/*

# Install MongoDB
RUN wget -qO - https://www.mongodb.org/static/pgp/server-7.0.asc | apt-key add - \
    && echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/7.0 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-7.0.list \
    && apt-get update \
    && apt-get install -y mongodb-org \
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

# MySQL initialization
RUN mkdir -p /var/run/mysqld && chown -R mysql:mysql /var/run/mysqld
RUN service mysql start && \
    mysql -e "CREATE DATABASE IF NOT EXISTS revcart;" && \
    mysql -e "CREATE USER IF NOT EXISTS 'revcart'@'localhost' IDENTIFIED BY 'revcart123';" && \
    mysql -e "GRANT ALL PRIVILEGES ON revcart.* TO 'revcart'@'localhost';" && \
    mysql -e "FLUSH PRIVILEGES;"

# MongoDB initialization
RUN mkdir -p /data/db && chown -R mongodb:mongodb /data/db

# Supervisor configuration
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

EXPOSE 4200 8081 3306 27017

CMD ["/usr/bin/supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
