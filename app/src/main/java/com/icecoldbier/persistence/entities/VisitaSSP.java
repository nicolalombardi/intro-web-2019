package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class VisitaSSP extends Visita {
    private int id_visita;
    private boolean erogata;
    private Date dataPrescrizione;
    private SSP ssp;

    public VisitaSSP(int id, User paziente, Date dataErogazione, int id_visita, boolean erogata, Date dataPrescrizione, SSP ssp) {
        super(id, paziente, dataErogazione);
        this.id_visita = id_visita;
        this.erogata = erogata;
        this.dataPrescrizione = dataPrescrizione;
        this.ssp = ssp;
    }

    public int getId_visita() {
        return id_visita;
    }

    public void setId_visita(int id_visita) {
        this.id_visita = id_visita;
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
}
