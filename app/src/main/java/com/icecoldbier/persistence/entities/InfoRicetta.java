package com.icecoldbier.persistence.entities;

import com.icecoldbier.utils.Utils;

import java.sql.Timestamp;

public class InfoRicetta {
    private Timestamp dataPrescrizione;
    private String farmaco;
    private User medicoBase;
    private Paziente paziente;
    private boolean acquistabile;
    private Integer id;

    public InfoRicetta(Timestamp dataPrescrizione, String farmaco, User medicoBase, Paziente paziente) {
        this.dataPrescrizione = dataPrescrizione;
        this.farmaco = farmaco;
        this.medicoBase = medicoBase;
        this.paziente = paziente;
    }

    public InfoRicetta(Timestamp dataPrescrizione, String farmaco, boolean acquistabile, Integer id, User medicoBase) {
        this.dataPrescrizione = dataPrescrizione;
        this.farmaco = farmaco;
        this.acquistabile = acquistabile;
        this.id = id;
        this.medicoBase = medicoBase;
    }

    public Timestamp getDataPrescrizione() {
        return dataPrescrizione;
    }

    public void setDataPrescrizione(Timestamp dataPrescrizione) {
        this.dataPrescrizione = dataPrescrizione;
    }

    public String getPrettyDataPrescrizione() {
        return Utils.parseTimestamp(dataPrescrizione);
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

    public boolean isAcquistabile() {
        return acquistabile;
    }

    public void setAcquistabile(boolean acquistabile) {
        this.acquistabile = acquistabile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
