package com.icecoldbier.persistence.entities;

public class SSP {
    private Integer id;
    private String username;
    private String provinciaAppartenenza;

    public SSP(Integer id, String username, String provinciaAppartenenza) {
        this.id = id;
        this.username = username;
        this.provinciaAppartenenza = provinciaAppartenenza;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProvinciaAppartenenza() {
        return provinciaAppartenenza;
    }

    public void setProvinciaAppartenenza(String provinciaAppartenenza) {
        this.provinciaAppartenenza = provinciaAppartenenza;
    }

    @Override
    public String toString() {
        return "SSP di " + getProvinciaAppartenenza();
    }
}
