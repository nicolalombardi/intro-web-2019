package com.icecoldbier.persistence.entities;

import java.sql.Date;

public class InfoRicetta {
    private Date data;
    private String farmaco;
    private Integer idm;
    private Integer idp;

    public InfoRicetta(Date data, String farmaco, Integer idm, Integer idp) {
        this.data = data;
        this.farmaco = farmaco;
        this.idm = idm;
        this.idp = idp;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getFarmaco() {
        return farmaco;
    }

    public void setFarmaco(String farmaco) {
        this.farmaco = farmaco;
    }

    public Integer getIdm() {
        return idm;
    }

    public void setIdm(Integer idm) {
        this.idm = idm;
    }

    public Integer getIdp() {
        return idp;
    }

    public void setIdp(Integer idp) {
        this.idp = idp;
    }
}
