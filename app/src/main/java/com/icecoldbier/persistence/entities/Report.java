package com.icecoldbier.persistence.entities;

public class Report {
    private Integer id;
    private String esito;

    public Report(Integer id, String esito) {
        this.id = id;
        this.esito = esito;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEsito() {
        return esito;
    }

    public void setEsito(String esito) {
        this.esito = esito;
    }
}
