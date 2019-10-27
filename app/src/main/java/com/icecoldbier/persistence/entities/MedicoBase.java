package com.icecoldbier.persistence.entities;

public class MedicoBase {
    private Integer id;
    private String nome;
    private String cognome;
    private String provinciaAppartenenza;

    public MedicoBase(Integer id, String nome, String cognome, String provinciaAppartenenza) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.provinciaAppartenenza = provinciaAppartenenza;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getProvinciaAppartenenza() {
        return provinciaAppartenenza;
    }

    public void setProvinciaAppartenenza(String provinciaAppartenenza) {
        this.provinciaAppartenenza = provinciaAppartenenza;
    }
}