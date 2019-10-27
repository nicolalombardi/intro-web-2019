package com.icecoldbier.persistence.entities;

public class VisitaBase {
    private Integer serial;
    private Integer idMedicoBase;
    private Integer idPaziente;
    
    public VisitaBase(Integer serial, Integer idMedicoBase, Integer idPaziente){
        this.serial = serial;
        this.idMedicoBase = idMedicoBase;
        this.idPaziente = idPaziente;
    }
    
    public Integer getSerial(){
        return this.serial;
    }
    
    public Integer getIdMedicoBase(){
        return this.idMedicoBase;
    }
    
    public Integer getIdPaziente(){
        return this.idPaziente;
    }
    
    public void setSerial(Integer serial){
        this.serial = serial;
    }
    
    public void setIdMedicoBase(Integer idMedicoBase){
        this.idMedicoBase = idMedicoBase;
    }
    
    public void setIdPaziente(Integer idPaziente){
        this.idPaziente = idPaziente;
    }
    
    @Override
    public String toString(){
        return "";
    }
}
