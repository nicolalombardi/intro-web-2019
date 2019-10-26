package com.icecoldbier.persistence.entities;

import java.util.Date;

public class Paziente {
    private Integer id;
    private String nome;
    private String cognome;
    private Date data_nascita;
    private String luogo_nascita;
    private String codice_fiscale;
    private char sesso;
    //foto
    private int id_medico;
    private String email;
    private String provincia_appartenenza;

    public Paziente(Integer id, String nome, String cognome, Date data_nascita, String luogo_nascita, String codice_fiscale, char sesso, int id_medico, String email, String provincia_appartenenza) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.data_nascita = data_nascita;
        this.luogo_nascita = luogo_nascita;
        this.codice_fiscale = codice_fiscale;
        this.sesso = sesso;
        this.id_medico = id_medico;
        this.email = email;
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

    public Date getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(Date data_nascita) {
        this.data_nascita = data_nascita;
    }

    public String getLuogo_nascita() {
        return luogo_nascita;
    }

    public void setLuogo_nascita(String luogo_nascita) {
        this.luogo_nascita = luogo_nascita;
    }

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    public void setCodice_fiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    public char getSesso() {
        return sesso;
    }

    public void setSesso(char sesso) {
        this.sesso = sesso;
    }

    public int getId_medico() {
        return id_medico;
    }

    public void setId_medico(int id_medico) {
        this.id_medico = id_medico;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvincia_appartenenza() {
        return provincia_appartenenza;
    }

    public void setProvincia_appartenenza(String provincia_appartenenza) {
        this.provincia_appartenenza = provincia_appartenenza;
    }
}
