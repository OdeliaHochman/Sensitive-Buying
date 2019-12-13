package com.example.sensitivebuying;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Sensitive implements Serializable {

    private String key;
    private String sensitiveType;

    public Sensitive()
    {
    }

    public Sensitive(String sensitiveType, String key)
    {
        this.sensitiveType = sensitiveType;
        this.key=key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSensitiveType()
    {
        return sensitiveType;
    }

    public void setSensitiveType(String sensitiveType) {
        this.sensitiveType = sensitiveType;
    }

    @NonNull
    @Override
    public String toString() {
        return sensitiveType;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }
        Sensitive s = (Sensitive) obj;

        if (this.sensitiveType.equals(s.sensitiveType)){
            return true;
        }


        return false;
    }


}
