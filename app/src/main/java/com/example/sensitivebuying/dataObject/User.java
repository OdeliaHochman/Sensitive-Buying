package com.example.sensitivebuying.dataObject;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String mail;
    private boolean rep; // true if the user is represntative, defult false


    public User() {
    }

    public User(String name, String mail, boolean rep) {
        this.name = name;
        this.mail = mail;
        this.rep = rep;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean getRep() {
        return rep;
    }

    public void setRep(boolean rep) {
        this.rep = rep;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }


}