package com.icecoldbier.persistence.entities;

import java.sql.Date;

public abstract class Visita {
    private int id;
    private User paziente;
    private Date dataErogazione;

    public Visita(int id, User paziente, Date dataErogazione) {
        this.id = id;
        this.paziente = paziente;
        this.dataErogazione = dataErogazione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getPaziente() {
        return paziente;
    }

    public void setPaziente(User paziente) {
        this.paziente = paziente;
    }

    public Date getDataErogazione() {
        return dataErogazione;
    }

    public void setDataErogazione(Date dataErogazione) {
        this.dataErogazione = dataErogazione;
    }
}
