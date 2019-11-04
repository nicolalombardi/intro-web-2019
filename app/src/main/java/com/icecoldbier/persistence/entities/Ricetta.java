package com.icecoldbier.persistence.entities;

public class Ricetta {
    private Integer id;
    private String nome;
    private Integer id_visita_base;
    private boolean prescritta;

    public Ricetta(Integer id, String nome, Integer id_visita_base, boolean prescritta) {
        this.id = id;
        this.nome = nome;
        this.id_visita_base = id_visita_base;
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

    public Integer getId_visita_base() {
        return id_visita_base;
    }

    public void setId_visita_base(Integer id_visita_base) {
        this.id_visita_base = id_visita_base;
    }
}
