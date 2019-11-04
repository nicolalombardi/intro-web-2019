package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class Ticket {
    private Date data;
    private String tipoVisita;
    private String nomeVisita;
    private float costo;

    public Ticket(Date data, String tipoVisita, String nomeVisita, float costo) {
        this.data = data;
        this.tipoVisita = tipoVisita;
        this.nomeVisita = nomeVisita;
        this.costo = costo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }

    public String getNomeVisita() {
        return nomeVisita;
    }

    public void setNomeVisita(String nomeVisita) {
        this.nomeVisita = nomeVisita;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
}
