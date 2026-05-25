ALTER USER 'bususer'@'%' IDENTIFIED BY 'AayushCode@5252';
GRANT ALL PRIVILEGES ON busticketbooking.* TO 'bususer'@'%';
FLUSH PRIVILEGES;
