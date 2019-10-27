package com.icecoldbier.persistence.entities;

public class VisitaSpecialistica {
    private Integer idMedico;
    private Integer idPaziente;
    private Integer idReport;
    private Integer ticket;

    public VisitaSpecialistica(Integer idMedico, Integer idPaziente, Integer idReport, Integer ticket) {
        this.idMedico = idMedico;
        this.idPaziente = idPaziente;
        this.idReport = idReport;
        this.ticket = ticket;
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

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }
}
