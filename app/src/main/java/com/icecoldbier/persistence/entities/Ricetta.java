package com.icecoldbier.persistence.entities;

public class Ricetta {
    private Integer id;
    private String nome;
    private boolean prescritta;

    public Ricetta(Integer id, String nome, boolean prescritta) {
        this.id = id;
        this.nome = nome;
        this.prescritta = prescritta;
    }
    
    public Ricetta(String nome, boolean prescritta) {
        this.nome = nome;
        this.prescritta = prescritta;
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

    public boolean isPrescritta() {
        return prescritta;
    }

    public void setPrescritta(boolean prescritta) {
        this.prescritta = prescritta;
    }
}
