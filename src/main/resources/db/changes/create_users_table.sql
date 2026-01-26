
-- liquibase formatted sql

-- changeset ibrohim:001

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(100) NOT NULL,
                       email VARCHAR(150) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- changeset ibrohim:002
INSERT INTO users (username, email, password)
VALUES ('admin', 'admin@example.com', '1234');