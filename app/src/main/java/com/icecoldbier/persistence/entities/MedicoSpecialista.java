package com.icecoldbier.persistence.entities;

/**
 *
 * @author Aurelio
 */
public class MedicoSpecialista {
    private Integer id;
    private String nome;
    private String cognome;
    
    public MedicoSpecialista(Integer id, String nome, String cognome){
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
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
    
    @Override
    public String toString(){
        return "Medico specialista{" +
                "id: " + this.id +
                ", nome: " + this.nome + 
                ", cognome: " + this.cognome;
    }
}
