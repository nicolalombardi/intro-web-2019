package com.icecoldbier.persistence.entities;

public class VisitaPossibile {
    private int id;
    private User.UserType praticante;
    private String nome;
    private String descrizione;
    private int costo_ticket;

    public VisitaPossibile(int id, User.UserType praticante, String nome, String descrizione, int costo_ticket) {
        this.id = id;
        this.praticante = praticante;
        this.nome = nome;
        this.descrizione = descrizione;
        this.costo_ticket = costo_ticket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User.UserType getPraticante() {
        return praticante;
    }

    public void setPraticante(User.UserType praticante) {
        this.praticante = praticante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getCosto_ticket() {
        return costo_ticket;
    }

    public void setCosto_ticket(int costo_ticket) {
        this.costo_ticket = costo_ticket;
    }

    @Override
    public String toString() {
        return "VisitaPossibile{" +
                "id=" + id +
                ", praticante=" + praticante +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", costo_ticket=" + costo_ticket +
                '}';
    }
}

