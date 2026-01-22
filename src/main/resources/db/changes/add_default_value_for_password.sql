-- liquibase formatted sql

-- changeset ibrohim:003


UPDATE users
SET password = '1234'
WHERE password IS NULL;
