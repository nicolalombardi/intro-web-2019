package com.icecoldbier.persistence.entities;

public class SSP {
    private Integer id;
    private String provinciaAppartenenza;
    
    public SSP(Integer id, String provinciaAppartenenza){
        this.id = id;
        this.provinciaAppartenenza = provinciaAppartenenza;
    }
    
    public Integer getId(){
        return this.id;
    }
    
    public String getProvinciaAppartenenza(){
        return this.provinciaAppartenenza;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
    public void setProvinciaAppartenenza(String provinciaAppartenenza){
        this.provinciaAppartenenza = provinciaAppartenenza;
    }
    
    @Override
    public String toString(){
        return "Provincia{"
                + "id= }" + this.id +
                ", provincia= " + this.provinciaAppartenenza;
    }
}
