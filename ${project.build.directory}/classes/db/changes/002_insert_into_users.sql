-- liquibase formatted sql

-- changeset ibrohim:002

INSERT INTO users (username, email, password)
VALUES ('admin', 'admin@example.com', '1234');