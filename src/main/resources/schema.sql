CREATE TABLE users (
                       id IDENTITY PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255),
                       phone VARCHAR(50),
                       address VARCHAR(255),
                       city VARCHAR(100),
                       state VARCHAR(100),
                       postal_code VARCHAR(20),
                       is_preferred BOOLEAN,
                       birth_date DATE
);