package com.icecoldbier.persistence.entities;

public class User {
    private static final Integer medicoBase = 0;
    private static final Integer medicoSpecialista = 1;
    private static final Integer paziente = 2;
    private static final Integer ssp = 3;

    private Integer id;
    private String typ;
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String provinciaAppartenenza;

    public User(Integer id, String typ, String username, String password, String nome, String cognome, String provinciaAppartenenza) {
        this.id = id;
        this.typ = typ;
        this.username = username;
        this.password = password;
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

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", typ='" + typ + '\'' +
                ", username='" + username + '\'' +
                ", psw_salt='" + password + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", provincia di appartenenza='" + provinciaAppartenenza + '\'' +
                '}';
    }
}
