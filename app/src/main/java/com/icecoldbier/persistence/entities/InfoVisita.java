package com.icecoldbier.persistence.entities;

public class InfoVisita {
    private String nome;
    private String descrizione;
    private Report report;

    public InfoVisita() {
    }

    public InfoVisita(String nome, String descrizione, Report report) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.report = report;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
