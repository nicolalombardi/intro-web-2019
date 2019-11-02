package com.icecoldbier.persistence.entities;

import java.sql.Date;

public abstract class Visita {
    private int id;
    private int id_paziente;
    private Date dataErogazione;

    public Visita(int id, int id_paziente, Date dataErogazione) {
        this.id = id;
        this.id_paziente = id_paziente;
        this.dataErogazione = dataErogazione;
    }

    public int getId() {
        return id;
    }

    public int getId_paziente() {
        return id_paziente;
    }

    public Date getDataErogazione() {
        return dataErogazione;
    }
}
