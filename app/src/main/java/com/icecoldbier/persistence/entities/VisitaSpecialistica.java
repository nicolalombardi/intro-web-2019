package com.icecoldbier.persistence.entities;

import com.icecoldbier.utils.Utils;

import java.sql.Timestamp;

public class VisitaSpecialistica extends Visita{
    private VisitaPossibile tipo_visita;
    private boolean erogata;
    private Timestamp dataPrescrizione;
    private User medicoSpecialista;
    private Report report;
    private User medicoBase;


    public VisitaSpecialistica(int id, Paziente paziente, Timestamp dataErogazione, VisitaPossibile tipo_visita, boolean erogata, Timestamp dataPrescrizione, User medicoSpecialista, Report report, User medicoBase) {
        super(id, paziente, dataErogazione);
        this.tipo_visita = tipo_visita;
        this.erogata = erogata;
        this.dataPrescrizione = dataPrescrizione;
        this.medicoSpecialista = medicoSpecialista;
        this.report = report;
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

    public Timestamp getDataPrescrizione() {
        return dataPrescrizione;
    }

    public String getPrettyDataPrescrizione() {
        return Utils.parseTimestamp(dataPrescrizione);
    }

    public void setDataPrescrizione(Timestamp dataPrescrizione) {
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
