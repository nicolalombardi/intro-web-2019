package com.icecoldbier.persistence.entities;

import com.icecoldbier.utils.Utils;

import java.sql.Timestamp;

public class Ticket {
    private Timestamp dataErogazione;
    private String tipoVisita;
    private String nomeVisita;
    private float costo;

    public Ticket(Timestamp dataErogazione, String tipoVisita, String nomeVisita, float costo) {
        this.dataErogazione = dataErogazione;
        this.tipoVisita = tipoVisita;
        this.nomeVisita = nomeVisita;
        this.costo = costo;
    }

    public Timestamp getDataErogazione() {
        return dataErogazione;
    }

    public void setDataErogazione(Timestamp dataErogazione) {
        this.dataErogazione = dataErogazione;
    }

    public String getPrettyDataErogazione() {
        return Utils.parseTimestamp(dataErogazione);

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
