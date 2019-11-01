package com.icecoldbier.persistence.entities;

import java.sql.Date;
import java.util.Calendar;

public class Paziente {
    private int id_user;
    private Date data_nascita;
    private String luogo_nascita;
    private String codice_fiscale;
    private char sesso;
    private String foto;
    private int id_medico;
    private String email;

    public Paziente(int id_user, Date data_nascita, String luogo_nascita, String codice_fiscale, char sesso, String foto, int id_medico, String email) {
        this.id_user = id_user;
        this.data_nascita = data_nascita;
        this.luogo_nascita = luogo_nascita;
        this.codice_fiscale = codice_fiscale;
        this.sesso = sesso;
        this.foto = foto;
        this.id_medico = id_medico;
        this.email = email;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    @Override
    public String toString() {
        return "Paziente{" +
                "id_user=" + id_user +
                ", data_nascita=" + data_nascita +
                ", luogo_nascita='" + luogo_nascita + '\'' +
                ", codice_fiscale='" + codice_fiscale + '\'' +
                ", sesso=" + sesso +
                ", foto='" + foto + '\'' +
                ", id_medico=" + id_medico +
                ", email='" + email + '\'' +
                '}';
    }
}
