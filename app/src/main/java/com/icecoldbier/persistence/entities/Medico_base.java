package com.icecoldbier.persistence.entities;

public class Medico_base {
    private Integer id;
    private String nome;
    private String cognome;
    private String provincia_appartenenza;

    public Medico_base(Integer id, String nome, String cognome, String provincia_appartenenza) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.provincia_appartenenza = provincia_appartenenza;
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

    public String getProvincia_appartenenza() {
        return provincia_appartenenza;
    }

    public void setProvincia_appartenenza(String provincia_appartenenza) {
        this.provincia_appartenenza = provincia_appartenenza;
    }
}