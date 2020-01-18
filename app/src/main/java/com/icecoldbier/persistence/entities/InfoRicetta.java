package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class InfoRicetta {
    private Date data;
    private String farmaco;
    private User medicoBase;
    private Paziente paziente;

    public InfoRicetta(Date data, String farmaco, User medicoBase, Paziente paziente) {
        this.data = data;
        this.farmaco = farmaco;
        this.medicoBase = medicoBase;
        this.paziente = paziente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getFarmaco() {
        return farmaco;
    }

    public void setFarmaco(String farmaco) {
        this.farmaco = farmaco;
    }

    public User getMedicoBase() {
        return medicoBase;
    }

    public void setMedicoBase(User medicoBase) {
        this.medicoBase = medicoBase;
    }

    public Paziente getPaziente() {
        return paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }
}
