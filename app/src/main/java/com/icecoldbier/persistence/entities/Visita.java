package com.icecoldbier.persistence.entities;

import java.sql.Date;

public abstract class Visita {
    public enum VisitaType{
        BASE,
        SPECIALISTICA,
        SSP
    }
    private int id;
    private int id_paziente;
    private Date dataErogazione;
    private VisitaType visitaType;

    public Visita(int id, int id_paziente, Date dataErogazione, VisitaType visitaType) {
        this.id = id;
        this.id_paziente = id_paziente;
        this.dataErogazione = dataErogazione;
        this.visitaType = visitaType;
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

    public VisitaType getVisitaType() {
        return visitaType;
    }
}
