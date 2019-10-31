package com.icecoldbier.persistence.entities;

import java.util.Date;

public class VisitaBase {
    private Integer serial;
    private Integer idMedicoBase;
    private Integer idPaziente;
    private Date dataErogazione;

    public VisitaBase(Integer serial, Integer idMedicoBase, Integer idPaziente, Date dataErogazione) {
        this.serial = serial;
        this.idMedicoBase = idMedicoBase;
        this.idPaziente = idPaziente;
        this.dataErogazione = dataErogazione;
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

    public Date getDataErogazione() {
        return dataErogazione;
    }

    public void setDataErogazione(Date dataErogazione) {
        this.dataErogazione = dataErogazione;
    }

    @Override
    public String toString(){
        return "";
    }
}
