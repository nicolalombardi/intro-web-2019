package com.icecoldbier.persistence.entities;

import java.sql.Timestamp;

public class VisitaBase extends Visita{
    private User medicoBase;
    private Ricetta ricetta;


    public VisitaBase(int id, Paziente paziente, Timestamp dataErogazione, User medicoBase, Ricetta ricetta) {
        super(id, paziente, dataErogazione);
        this.medicoBase = medicoBase;
        this.ricetta = ricetta;
        System.out.println(dataErogazione.toString() + " " + dataErogazione.getTime());
    }

    public User getMedicoBase() {
        return medicoBase;
    }

    public void setMedicoBase(User medicoBase) {
        this.medicoBase = medicoBase;
    }

    public Ricetta getRicetta() {
        return ricetta;
    }

    public void setRicetta(Ricetta ricetta) {
        this.ricetta = ricetta;
    }
}
