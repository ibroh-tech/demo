-- liquibase formatted sql

-- changeset ibrohim:003

INSERT INTO users (username, email, password)
VALUES ('testadmin', 'testadmin@example.com', 'test1234');