package com.example.sensitivebuying;

import androidx.annotation.Nullable;

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

    @Override
    public boolean equals(@Nullable Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }
        Sensitive s = (Sensitive) obj;

        if (this.sensitiveType==s.sensitiveType){
            return true;
        }
        return false;
    }


}
