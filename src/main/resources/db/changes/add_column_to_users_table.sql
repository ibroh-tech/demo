-- liquibase formatted sql

-- changeset ibrohim:002

ALTER TABLE users ADD COLUMN password VARCHAR(255);