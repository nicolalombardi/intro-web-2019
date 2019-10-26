CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    typ VARCHAR(16),
    username VARCHAR(128),
    pass VARCHAR(256)
);
CREATE TABLE visita_medica
(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE report
(
    id SERIAL PRIMARY KEY,
    esito TEXT NOT NULL
);

CREATE TABLE ricetta
(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE medico_base
(
    id_user INT PRIMARY KEY REFERENCES users(id) NOT NULL,
    nome VARCHAR(40) NOT NULL,
    cognome VARCHAR(40) NOT NULL,
    provincia_appartenenza VARCHAR(64) NOT NULL
);

CREATE TABLE paziente
(
    id_user INT PRIMARY KEY REFERENCES users(id) NOT NULL,
    nome VARCHAR(40) NOT NULL,
    cognome VARCHAR(40) NOT NULL,
    data_nascita DATE NOT NULL,
    luogo_nascita VARCHAR(40) NOT NULL,
    codice_fiscale VARCHAR(16) NOT NULL,
    sesso CHAR(1) NOT NULL,
    foto TEXT, 
    id_medico INT REFERENCES medico_base(id_user) NOT NULL,
    email TEXT NOT NULL,
    provincia_appartenenza VARCHAR(64) NOT NULL
);

CREATE TABLE visita_base(
    id SERIAL PRIMARY KEY,
    id_medico INT REFERENCES medico_base(id_user) NOT NULL,
    id_paziente INT REFERENCES paziente(id_user) NOT NULL
);

CREATE TABLE prescrive_ricetta(
    id_visita INT REFERENCES visita_base(id) NOT NULL,
    id_ricetta INT REFERENCES ricetta(id) NOT NULL
);

CREATE TABLE prescrive_visita(
    id_visita INT REFERENCES visita_base(id) NOT NULL,
    id_visita_medica INT REFERENCES visita_medica(id) NOT NULL   --WTF, visita medica la fa il medico di base quando vai da lui (vado dal medico perche ho mal di schiena e mi controlla la schiena xd), eventualmente poi ti prescrive una visita specialistica
    --id_visita_specialistica INT REFERENCES visita_specialistica(id) NOT NULL
);

CREATE TABLE medico_specialista
(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(40) NOT NULL,
    cognome VARCHAR(40) NOT NULL
);

CREATE TABLE visita_specialistica(
    id_medico INT REFERENCES medico_specialista(id) NOT NULL,
    id_paziente INT REFERENCES paziente(id_user) NOT NULL,
    id_report INT REFERENCES report(id) NOT NULL,
    ticket INT NOT NULL
);

CREATE TABLE servizio_sanitario_provinciale(
    id SERIAL PRIMARY KEY,
    provincia_appartenenza VARCHAR(64) NOT NULl
);
/*
Da aggiungere:
erogazione esami da parte di ssp
richiamo pazienti da parte di ssp per svolgere un esame in ssp o da medico specialista 
*/



