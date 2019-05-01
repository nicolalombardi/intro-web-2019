CREATE DATABASE progetto;

CREATE TABLE paziente
(
    id INT SERIAL PRIMARY KEY,
    nome VARCHAR(40) NOT NULL,
    cognome VARCHAR(40) NOT NULL,
    data_nascita DATE NOT NULL,
    luogo_nascita VARCHAR(40) NOT NULL,
    codice_fiscale VARCHAR(16) NOT NULL,
    sesso CHAR(1) NOT NULL,
    foto TEXT, 
);

CREATE TABLE medico_base
(
    id INT SERIAL PRIMARY KEY,
    nome VARCHAR(40) NOT NULL,
    cognome VARCHAR(40) NOT NULL
);

CREATE TABLE medico_specialista
(
    id INT SERIAL PRIMARY KEY,
    nome VARCHAR(40) NOT NULL,
    cognome VARCHAR(40) NOT NULL
);

CREATE TABLE report
(
    id INT SERIAL PRIMARY KEY,
    esito TEXT NOT NULL
);

CREATE TABLE visita_medica
(
    id INT SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE farmaco
(
    id INT SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);


