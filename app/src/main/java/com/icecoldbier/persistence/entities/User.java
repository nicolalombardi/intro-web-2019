package com.icecoldbier.persistence.entities;

public class User {
    private Integer id;
    private String typ;
    private String username;
    private String psw_salt;
    private String psw_hash;

    public User(Integer id, String typ, String username, String psw_salt, String psw_hash) {
        this.id = id;
        this.typ = typ;
        this.username = username;
        this.psw_salt = psw_salt;
        this.psw_hash = psw_hash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsw_salt() {
        return psw_salt;
    }

    public void setPsw_salt(String psw_salt) {
        this.psw_salt = psw_salt;
    }

    public String getPsw_hash() {
        return psw_hash;
    }

    public void setPsw_hash(String psw_hash) {
        this.psw_hash = psw_hash;
    }
}
