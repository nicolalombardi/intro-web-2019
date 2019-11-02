package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class VisitaSSP extends Visita {
    private int id_visita;
    private boolean erogata;
    private Date dataPrescrizione;
    private Integer idSSP;

    public VisitaSSP(int id, int id_visita, boolean erogata, Date dataPrescrizione, Date dataErogazione, Integer idSSP, Integer idPaziente) {
        super(id, idPaziente, dataErogazione);
        this.id_visita = id_visita;
        this.erogata = erogata;
        this.dataPrescrizione = dataPrescrizione;
        this.idSSP = idSSP;
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

    public Integer getIdSSP() {
        return idSSP;
    }

    public void setIdSSP(Integer idSSP) {
        this.idSSP = idSSP;
    }

}
