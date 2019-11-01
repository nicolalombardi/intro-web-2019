package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class VisitaSpecialistica {
    private int id;
    private int id_visita;
    private boolean erogata;
    private Date dataPrescrizione;
    private Date dataErogazione;
    private Integer idMedico;
    private Integer idPaziente;
    private Integer idReport;

    public VisitaSpecialistica(int id, int id_visita, boolean erogata, Date dataPrescrizione, Date dataErogazione, Integer idMedico, Integer idPaziente, Integer idReport) {
        this.id = id;
        this.id_visita = id_visita;
        this.erogata = erogata;
        this.dataPrescrizione = dataPrescrizione;
        this.dataErogazione = dataErogazione;
        this.idMedico = idMedico;
        this.idPaziente = idPaziente;
        this.idReport = idReport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDataErogazione() {
        return dataErogazione;
    }

    public void setDataErogazione(Date dataErogazione) {
        this.dataErogazione = dataErogazione;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public Integer getIdPaziente() {
        return idPaziente;
    }

    public void setIdPaziente(Integer idPaziente) {
        this.idPaziente = idPaziente;
    }

    public Integer getIdReport() {
        return idReport;
    }

    public void setIdReport(Integer idReport) {
        this.idReport = idReport;
    }
}
