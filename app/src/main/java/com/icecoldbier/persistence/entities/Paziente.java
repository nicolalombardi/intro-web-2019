package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class Paziente extends User{
    private Date dataNascita;
    private String luogoNascita;
    private String codiceFiscale;
    private char sesso;
    private String foto;
    private int idMedico;
    private String email;

    public Paziente(Integer id, UserType typ, String username, String password, String nome, String cognome, String provinciaAppartenenza, Date dataNascita, String luogoNascita, String codiceFiscale, char sesso, String foto, int idMedico, String email) {
        super(id, typ, username, password, nome, cognome, provinciaAppartenenza);
        this.dataNascita = dataNascita;
        this.luogoNascita = luogoNascita;
        this.codiceFiscale = codiceFiscale;
        this.sesso = sesso;
        this.foto = foto;
        this.idMedico = idMedico;
        this.email = email;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
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

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
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
                "data_nascita=" + dataNascita +
                ", luogo_nascita='" + luogoNascita + '\'' +
                ", codice_fiscale='" + codiceFiscale + '\'' +
                ", sesso=" + sesso +
                ", foto='" + foto + '\'' +
                ", id_medico=" + idMedico +
                ", email='" + email + '\'' +
                "} " + super.toString();
    }
}
