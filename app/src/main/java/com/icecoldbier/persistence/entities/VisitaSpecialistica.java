package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class VisitaSpecialistica extends Visita{
    private int id_visita;
    private boolean erogata;
    private Date dataPrescrizione;
    private Integer idMedico;
    private Integer idReport;
    private Integer idMedicoBase;
    private Integer idPaziente;
    private String nomeMedico;
    private String cognomeMedico;

    public VisitaSpecialistica(int id, int id_visita, boolean erogata, Date dataPrescrizione, Date dataErogazione, Integer idMedico, Integer idPaziente, Integer idReport) {
        super(id, idPaziente, dataErogazione);
        this.id_visita = id_visita;
        this.erogata = erogata;
        this.dataPrescrizione = dataPrescrizione;
        this.idMedico = idMedico;
        this.idReport = idReport;
    }

    public VisitaSpecialistica(int id, int id_paziente, Date dataErogazione, int id_visita, boolean erogata, Date dataPrescrizione, Integer idMedico, Integer idReport, String nomeMedico, String cognomeMedico) {
        super(id, id_paziente, dataErogazione);
        this.id_visita = id_visita;
        this.erogata = erogata;
        this.dataPrescrizione = dataPrescrizione;
        this.idMedico = idMedico;
        this.idReport = idReport;
        this.nomeMedico = nomeMedico;
        this.cognomeMedico = cognomeMedico;
    }
    
    public VisitaSpecialistica(int id, int id_visita, boolean erogata, Date dataPrescrizione, Date dataErogazione, Integer idMedico, Integer idPaziente, Integer idMedicoBase,Integer idReport){
        super(id, idPaziente, dataErogazione);
        this.id_visita = id_visita;
        this.erogata = erogata;
        this.dataPrescrizione = dataPrescrizione;
        this.idMedico = idMedico;
        this.idReport = idReport;
        this.idMedicoBase = idMedicoBase;
        this.idPaziente = idPaziente;
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

    public int getId_visita() {
        return id_visita;
    }

    public void setId_visita(int id_visita) {
        this.id_visita = id_visita;
    }

    public boolean isErogata() {
        return erogata;
    }

    public void setErogata(boolean erogata) {
        this.erogata = erogata;
    }

    public Date getDataPrescrizione() {
        return dataPrescrizione;
    }

    public void setDataPrescrizione(Date dataPrescrizione) {
        this.dataPrescrizione = dataPrescrizione;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public Integer getIdReport() {
        return idReport;
    }

    public void setIdReport(Integer idReport) {
        this.idReport = idReport;
    }
    
    public void setIdPaziente(Integer idPaziente){
        this.idPaziente = idPaziente;
    }
    
    public Integer getIdPaziente(){
        return this.idPaziente;
    }

}
