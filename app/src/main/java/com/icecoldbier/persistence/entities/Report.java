package com.icecoldbier.persistence.entities;

public class Report {
    private int id;
    private String esito;
    private int id_ricetta;

    public Report(int id, String esito, int id_ricetta) {
        this.id = id;
        this.esito = esito;
        this.id_ricetta = id_ricetta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }

    public int getId_ricetta() {
        return id_ricetta;
    }

    public void setId_ricetta(int id_ricetta) {
        this.id_ricetta = id_ricetta;
    }
}
