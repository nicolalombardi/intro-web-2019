package com.icecoldbier.persistence.entities;

public class Report {
    private int id;
    private String esito;
    private Ricetta ricetta;

    public Report(int id, String esito, Ricetta ricetta) {
        this.id = id;
        this.esito = esito;
        this.ricetta = ricetta;
    }
    
    public Report(String esito, Ricetta ricetta){
        this.esito = esito;
        this.ricetta = ricetta;
    }
    
    public Report(String esito){
        this.esito = esito;
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

    public Ricetta getRicetta() {
        return ricetta;
    }

    public void setRicetta(Ricetta ricetta) {
        this.ricetta = ricetta;
    }
}
