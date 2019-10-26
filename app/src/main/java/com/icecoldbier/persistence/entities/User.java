package com.icecoldbier.persistence.entities;

public class User {
    private Integer id;
    private String typ;
    private String username;
    private String password;

    public User(Integer id, String typ, String username, String password) {
        this.id = id;
        this.typ = typ;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", typ='" + typ + '\'' +
                ", username='" + username + '\'' +
                ", psw_salt='" + password + '\'' +
                '}';
    }
}
