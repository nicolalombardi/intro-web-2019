package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class VisitaSSP extends Visita {
    private VisitaPossibile tipo_visita;
    private boolean erogata;
    private Date dataPrescrizione;
    private SSP ssp;
    private User medicoBase;

    public VisitaSSP(int id, Paziente paziente, Date dataErogazione, VisitaPossibile tipo_visita, boolean erogata, Date dataPrescrizione, SSP ssp, User medicoBase) {
        super(id, paziente, dataErogazione);
        this.tipo_visita = tipo_visita;
        this.erogata = erogata;
        this.dataPrescrizione = dataPrescrizione;
        this.ssp = ssp;
        this.medicoBase = medicoBase;
    }

    public VisitaPossibile getTipo_visita() {
        return tipo_visita;
    }

    public void setTipo_visita(VisitaPossibile tipo_visita) {
        this.tipo_visita = tipo_visita;
    }

    public boolean isErogata() {
        return erogata;
    }

    public void setErogata(boolean erogata) {
        this.erogata = erogata;
    }

    public Date getDataPrescrizione() {
        return dataPrescrizione;
    }

    public void setDataPrescrizione(Date dataPrescrizione) {
        this.dataPrescrizione = dataPrescrizione;
    }

    public SSP getSsp() {
        return ssp;
    }

    public void setSsp(SSP ssp) {
        this.ssp = ssp;
    }

    public User getMedicoBase() {
        return medicoBase;
    }

    public void setMedicoBase(User medicoBase) {
        this.medicoBase = medicoBase;
    }
}
