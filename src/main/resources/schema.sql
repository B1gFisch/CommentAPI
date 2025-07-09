-- Nur einmal manuell ausführen!
-- Erstelle Benutzer und Datenbank

CREATE USER commentuser WITH PASSWORD 'commentpass';
CREATE DATABASE commentsdb OWNER commentuser;

-- Danach ausführen:

CREATE TABLE IF NOT EXISTS comment
(
    id         SERIAL PRIMARY KEY,
    content    TEXT        NOT NULL,
    username   VARCHAR(50) NOT NULL,
    likes      INT DEFAULT 0,
    dislikes   INT DEFAULT 0,
    created_at TIMESTAMP   NOT NULL
);

CREATE TABLE IF NOT EXISTS admin_user
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(50) NOT NULL UNIQUE,
    password_hash TEXT        NOT NULL,
    role          VARCHAR(20) NOT NULL DEFAULT 'ADMIN'
);
