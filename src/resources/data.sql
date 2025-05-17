CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50),
  password VARCHAR(50)
);
INSERT INTO users(username, password) VALUES('admin', 'admin123');

CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  description VARCHAR(255)
);
INSERT INTO products(name, description) VALUES('Lapicero', 'Bolígrafo azul');
