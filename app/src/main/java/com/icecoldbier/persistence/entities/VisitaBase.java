package com.icecoldbier.persistence.entities;

import java.util.Date;

public class VisitaBase {
    private Integer id;
    private Integer idMedicoBase;
    private Integer idPaziente;
    private Date dataErogazione;

    public VisitaBase(Integer id, Integer idMedicoBase, Integer idPaziente, Date dataErogazione) {
        this.id = id;
        this.idMedicoBase = idMedicoBase;
        this.idPaziente = idPaziente;
        this.dataErogazione = dataErogazione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdMedicoBase() {
        return idMedicoBase;
    }

    public void setIdMedicoBase(Integer idMedicoBase) {
        this.idMedicoBase = idMedicoBase;
    }

    public Integer getIdPaziente() {
        return idPaziente;
    }

    public void setIdPaziente(Integer idPaziente) {
        this.idPaziente = idPaziente;
    }

    public Date getDataErogazione() {
        return dataErogazione;
    }

    public void setDataErogazione(Date dataErogazione) {
        this.dataErogazione = dataErogazione;
    }
}
