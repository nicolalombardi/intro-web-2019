CREATE TYPE user_type AS ENUM ('paziente', 'medico_base', 'medico_specialista', 'ssn');

CREATE TABLE users(
    id SERIAL PRIMARY KEY,
    typ user_type,
    username VARCHAR(128),
    pass VARCHAR(256),
    nome VARCHAR(40) NOT NULL,
    cognome VARCHAR(40) NOT NULL,
    provincia_appartenenza VARCHAR(64) NOT NULL
);

CREATE TABLE ricetta(
    id SERIAL PRIMARY KEY,
    farmaco VARCHAR(100) NOT NULL,
    prescritta BOOLEAN NOT NULL --se è stata proposta dal medico specialista o se è stata accettata dal medico di base
);

CREATE TABLE report(
    id SERIAL PRIMARY KEY,
    esito TEXT NOT NULL,
    id_ricetta INT REFERENCES ricetta(id)
);


CREATE TABLE paziente(
    id_user INT PRIMARY KEY REFERENCES users(id) NOT NULL,
    data_nascita DATE NOT NULL,
    luogo_nascita VARCHAR(40) NOT NULL,
    codice_fiscale VARCHAR(16) NOT NULL,
    sesso CHAR(1) NOT NULL,
    foto TEXT, 
    id_medico INT REFERENCES users(id) NOT NULL,
    email TEXT NOT NULL
);

/* questi sono da prendere dal link */
CREATE TABLE elenco_visite_possibili(
    id SERIAL PRIMARY KEY,
    praticante user_type NOT NULL,
    nome VARCHAR(40) NOT NULL,
    descrizione TEXT NOT NULL,
    costo_ticket INT NOT NULL
);

CREATE TABLE visita_base(
    id SERIAL PRIMARY KEY,
    id_medico INT REFERENCES users(id) NOT NULL,
    id_paziente INT REFERENCES paziente(id_user) NOT NULL,
    data_erogazione DATE NOT NULL
);

CREATE TABLE visita_specialistica(
    id SERIAL PRIMARY KEY,
    id_visita INT REFERENCES elenco_visite_possibili(id) NOT NULL,
    erogata BOOLEAN NOT NULL,
    data_prescrizione DATE NOT NULL,
    data_erogazione DATE NOT NULL,
    id_medico INT REFERENCES users(id) NOT NULL,
    id_paziente INT REFERENCES paziente(id_user) NOT NULL,
    id_report INT REFERENCES report(id)
);

CREATE TABLE visita_ssp(
    id SERIAL PRIMARY KEY,
    id_visita INT REFERENCES elenco_visite_possibili(id) NOT NULL,
    erogata BOOLEAN NOT NULL,
    data_prescrizione DATE NOT NULL,
    data_erogazione DATE,
    id_ssp INT REFERENCES users(id) NOT NULL,
    id_paziente INT REFERENCES paziente(id_user) NOT NULL
);

CREATE TABLE prescrive_ricetta(
    id_visita INT REFERENCES visita_base(id) NOT NULL,
    id_ricetta INT REFERENCES ricetta(id) NOT NULL
);

CREATE TABLE prescrive_visita_specialistica(
    id_visita INT REFERENCES visita_base(id) NOT NULL,
    id_visita_specialistica INT REFERENCES visita_specialistica(id) NOT NULL
);

CREATE TABLE prescrive_visita_ssp(
    id_visita INT REFERENCES visita_base(id) NOT NULL,
    id_visita_ssp INT REFERENCES visita_ssp(id) NOT NULL
);

CREATE TABLE servizio_sanitario_provinciale(
    id SERIAL PRIMARY KEY,
    provincia_appartenenza VARCHAR(64) NOT NULL
);