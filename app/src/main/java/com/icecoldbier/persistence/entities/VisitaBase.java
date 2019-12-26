package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class VisitaBase extends Visita{
    private Integer idMedicoBase;
    private String nomeMedico;
    private String cognomeMedico;

    public VisitaBase(Integer id, Integer idMedicoBase, Integer idPaziente, Date dataErogazione) {
        super(id, idPaziente, dataErogazione);
        this.idMedicoBase = idMedicoBase;
    }

    public VisitaBase(int id, int id_paziente, Date dataErogazione, Integer idMedicoBase, String nomeMedico, String cognomeMedico) {
        super(id, id_paziente, dataErogazione);
        this.idMedicoBase = idMedicoBase;
        this.nomeMedico = nomeMedico;
        this.cognomeMedico = cognomeMedico;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getCognomeMedico() {
        return cognomeMedico;
    }

    public void setCognomeMedico(String cognomeMedico) {
        this.cognomeMedico = cognomeMedico;
    }

    public Integer getIdMedicoBase() {
        return idMedicoBase;
    }



    public void setIdMedicoBase(Integer idMedicoBase) {
        this.idMedicoBase = idMedicoBase;
    }
}
