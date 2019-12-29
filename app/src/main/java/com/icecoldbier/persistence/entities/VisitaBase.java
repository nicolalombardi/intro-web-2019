package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class VisitaBase extends Visita{

    private User medicoBase;

    public VisitaBase(int id, User paziente, Date dataErogazione, User medicoBase) {
        super(id, paziente, dataErogazione);
        this.medicoBase = medicoBase;
    }

    public User getMedicoBase() {
        return medicoBase;
    }

    public void setMedicoBase(User medicoBase) {
        this.medicoBase = medicoBase;
    }
}
