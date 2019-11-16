package com.icecoldbier.persistence.entities;

public class User {
    public enum UserType{
        medico_base,
        medico_specialista,
        paziente,
        ssp;
    }

    private Integer id;
    private UserType typ;
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String provinciaAppartenenza;
    private String locale;

    public User(Integer id, UserType typ, String username, String password, String nome, String cognome, String provinciaAppartenenza, String locale) {
        this.id = id;
        this.typ = typ;
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.provinciaAppartenenza = provinciaAppartenenza;
        this.locale = locale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserType getTyp() {
        return typ;
    }

    public void setTyp(UserType typ) {
        this.typ = typ;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", typ=" + typ +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", provinciaAppartenenza='" + provinciaAppartenenza + '\'' +
                ", locale='" + locale + '\'' +
                '}';
    }
}
