package com.icecoldbier.persistence.entities;

import com.icecoldbier.utils.Utils;

import java.sql.Date;

public class Paziente extends User{
    private Date dataNascita;
    private String luogoNascita;
    private String codiceFiscale;
    private char sesso;
    private String foto;
    private String fotoThumb;
    private User medico;


    public Paziente(Integer id, UserType typ, String username, String password, String nome, String cognome, String provinciaAppartenenza, Date dataNascita, String luogoNascita, String codiceFiscale, char sesso, String foto, User medico) {
        super(id, typ, username, password, nome, cognome, provinciaAppartenenza);
        this.dataNascita = dataNascita;
        this.luogoNascita = luogoNascita;
        this.codiceFiscale = codiceFiscale;
        this.sesso = sesso;
        this.foto = (foto == null || foto.equals("")) ? "/photos/" : foto;
        this.fotoThumb = (foto == null || foto.equals("")) ? "/photos/" : foto + "?size=thumbnail";
        this.medico = medico;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getPrettyDataNascita() {
        return Utils.parseDate(dataNascita);
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

    public String getFotoThumb() {
        return fotoThumb;
    }

    public void setFotoThumb(String fotoThumb) {
        this.fotoThumb = fotoThumb;
    }

    public User getMedico() {
        return medico;
    }

    public void setMedico(User medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        return "Paziente{" +
                "data_nascita=" + dataNascita +
                ", luogo_nascita='" + luogoNascita + '\'' +
                ", codice_fiscale='" + codiceFiscale + '\'' +
                ", sesso=" + sesso +
                ", foto='" + foto + '\'' +
                ", id_medico=" + medico.getId() +
                "} " + super.toString();
    }
}
