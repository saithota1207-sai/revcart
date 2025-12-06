FROM ubuntu:22.04
ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update && apt-get install -y openjdk-17-jdk nodejs npm nginx mysql-server supervisor curl maven && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY . .
RUN cd frontend && npm install && npm run build && cp -r dist/revcart/* /var/www/html/
RUN cd backend && mvn clean package -DskipTests
RUN echo 'server{listen 80;location /{root /var/www/html;try_files $uri /index.html;}location /api/{proxy_pass http://localhost:8081/;}}' > /etc/nginx/sites-available/default
RUN echo 'server.port=8081\nspring.datasource.url=jdbc:mysql://localhost:3306/revcart_db?createDatabaseIfNotExist=true\nspring.datasource.username=root\nspring.datasource.password=root\nspring.jpa.hibernate.ddl-auto=update' > application.properties
RUN echo '[supervisord]\nnodaemon=true\n[program:mysql]\ncommand=/usr/bin/mysqld_safe\nuser=mysql\n[program:nginx]\ncommand=nginx -g "daemon off;"\n[program:backend]\ncommand=java -jar backend/target/revcart-backend-1.0.0.jar --spring.config.location=/app/application.properties' > /etc/supervisor/conf.d/app.conf
RUN echo '#!/bin/bash\nchown mysql:mysql /var/lib/mysql\nservice mysql start\nmysql -e "CREATE DATABASE IF NOT EXISTS revcart_db;"\nservice mysql stop\nexec supervisord' > /start.sh && chmod +x /start.sh
EXPOSE 80
CMD ["/start.sh"]