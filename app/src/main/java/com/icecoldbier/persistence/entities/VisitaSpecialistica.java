package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class VisitaSpecialistica extends Visita{
    private int id_visita;
    private boolean erogata;
    private Date dataPrescrizione;
    private User medicoSpecialista;
    private Report report;
    private User medicoBase;


    public VisitaSpecialistica(int id, User paziente, Date dataErogazione, int id_visita, boolean erogata, Date dataPrescrizione, User medicoSpecialista, Report report, User medicoBase) {
        super(id, paziente, dataErogazione);
        this.id_visita = id_visita;
        this.erogata = erogata;
        this.dataPrescrizione = dataPrescrizione;
        this.medicoSpecialista = medicoSpecialista;
        this.report = report;
        this.medicoBase = medicoBase;
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

    public User getMedicoSpecialista() {
        return medicoSpecialista;
    }

    public void setMedicoSpecialista(User medicoSpecialista) {
        this.medicoSpecialista = medicoSpecialista;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public User getMedicoBase() {
        return medicoBase;
    }

    public void setMedicoBase(User medicoBase) {
        this.medicoBase = medicoBase;
    }
}
