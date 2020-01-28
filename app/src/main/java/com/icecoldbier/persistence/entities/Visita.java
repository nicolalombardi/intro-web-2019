package com.icecoldbier.persistence.entities;

import com.icecoldbier.utils.Utils;

import java.sql.Timestamp;

public abstract class Visita {
    private int id;
    private Paziente paziente;
    private Timestamp dataErogazione;

    public Visita(int id, Paziente paziente, Timestamp dataErogazione) {
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

    public Paziente getPaziente() {
        return paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    public Timestamp getDataErogazione() {
        return dataErogazione;
    }

    public void setDataErogazione(Timestamp dataErogazione) {
        this.dataErogazione = dataErogazione;
    }

    public String getPrettyDataErogazione() {
        return Utils.parseTimestamp(dataErogazione);

    }
}
