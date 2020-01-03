package com.icecoldbier.persistence.entities;

public class VisitaSpecialisticaOrSSP {
    private VisitaSpecialistica visitaSpecialistica;
    private VisitaSSP visitaSSP;
    private boolean isSSP;

    public VisitaSpecialisticaOrSSP(VisitaSpecialistica visitaSpecialistica){
        this.isSSP = false;
        this.visitaSpecialistica = visitaSpecialistica;
    }
    public VisitaSpecialisticaOrSSP(VisitaSSP visitaSSP){
        this.isSSP = true;
        this.visitaSSP = visitaSSP;
    }

    public boolean isSSP(){
        return isSSP;
    }

    public VisitaSpecialistica getVisitaSpecialistica() {
        return visitaSpecialistica;
    }

    public VisitaSSP getVisitaSSP() {
        return visitaSSP;
    }
}
