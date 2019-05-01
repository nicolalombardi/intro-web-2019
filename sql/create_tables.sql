CREATE TABLE paziente
(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(40) NOT NULL,
    cognome VARCHAR(40) NOT NULL,
    data_nascita DATE NOT NULL,
    luogo_nascita VARCHAR(40) NOT NULL,
    codice_fiscale VARCHAR(16) NOT NULL,
    sesso CHAR(1) NOT NULL,
    foto TEXT, 
    id_medico INT REFERENCES medico_base(id) NOT NULL
);

CREATE TABLE visita_base(
    id SERIAL NOT NULL,
    id_medico INT REFERENCES medico_base(id) NOT NULL,
    id_paziente INT REFERENCES paziente(id) NOT NULL,
);

CREATE TABLE prescrive_ricetta(
    id_visita INT REFERENCES visita_base(id) NOT NULL,
    id_ricetta INT REFERENCES ricetta(id) NOT NULL,
);

CREATE TABLE prescrive_visita(
    id_visita INT REFERENCES visita_base(id) NOT NULL,
    id_visita_medica INT REFERENCES visita_medica(id) NOT NULL,
);

CREATE TABLE medico_base
(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(40) NOT NULL,
    cognome VARCHAR(40) NOT NULL
);

CREATE TABLE medico_specialista
(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(40) NOT NULL,
    cognome VARCHAR(40) NOT NULL
);

CREATE TABLE report
(
    id SERIAL PRIMARY KEY,
    esito TEXT NOT NULL
);

CREATE TABLE visita_specialistica(
    id_medico INT REFERENCES medico_specialista(id) NOT NULL,
    id_paziente INT REFERENCES paziente(id) NOT NULL,
    id_report INT REFERENCES report(id) NOT NULL,
    ticket INT NOT NULL
);


CREATE TABLE visita_medica
(
    id INT SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE ricetta
(
    id INT SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);


