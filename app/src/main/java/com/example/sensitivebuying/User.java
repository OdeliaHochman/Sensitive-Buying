package com.example.sensitivebuying;

import java.io.Serializable;

abstract public class User implements Serializable {

    private String name;
    private String mail;
    private boolean isRep; // true if the user is represntative, defult false


    public User() {
    }

    public User(String name, String mail, boolean isRep) {
        this.name = name;
        this.mail = mail;
        this.isRep = isRep;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setRep(boolean rep) {
        isRep = rep;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }


    public boolean isRep() {
        return isRep;
    }
}