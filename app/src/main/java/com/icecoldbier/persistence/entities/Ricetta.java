package com.icecoldbier.persistence.entities;

public class Ricetta {
    private Integer id;
    private String nome;
    
    public Ricetta(Integer id, String nome){
        this.id = id;
        this.nome = nome;
    }
    
    public Integer getId(){
        return this.id;
    }
    
    public String getNome(){
        return this.nome;
    }
    
    public void setId(Integer id){
        this.id = id;
    } 
    
    public void setNome(String nome){
        this.nome = nome;
    }
}
