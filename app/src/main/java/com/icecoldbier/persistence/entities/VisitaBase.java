package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class VisitaBase extends Visita{
    private Integer idMedicoBase;

    public VisitaBase(Integer id, Integer idMedicoBase, Integer idPaziente, Date dataErogazione) {
        super(id, idPaziente, dataErogazione, VisitaType.BASE);
        this.idMedicoBase = idMedicoBase;
    }

    public Integer getIdMedicoBase() {
        return idMedicoBase;
    }

    public void setIdMedicoBase(Integer idMedicoBase) {
        this.idMedicoBase = idMedicoBase;
    }
}
