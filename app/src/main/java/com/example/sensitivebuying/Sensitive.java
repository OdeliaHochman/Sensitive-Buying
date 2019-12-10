package com.example.sensitivebuying;

import java.io.Serializable;

public class Sensitive implements Serializable {

    private String sensitiveType;

    public Sensitive()
    {
    }

    public Sensitive(String sensitiveType)
    {
        this.sensitiveType = sensitiveType;
    }

    public String getSensitiveType()
    {
        return sensitiveType;
    }

    public void setSensitiveType(String sensitiveType) {
        this.sensitiveType = sensitiveType;
    }

}
