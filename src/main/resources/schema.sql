-- Tablas de usuarios y productos
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     username VARCHAR(255),
    password VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS products (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(255),
    description VARCHAR(500),
    price DOUBLE PRECISION
    );
