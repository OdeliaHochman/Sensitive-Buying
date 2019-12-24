package com.example.sensitivebuying;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Sensitive implements Serializable {

    private String sensitiveKey;
    private String sensitiveType;
    private boolean selected;


    public Sensitive()
    {
    }

    public Sensitive(String sensitiveType, String sensitiveKey)
    {
        this.sensitiveType = sensitiveType;
        this.sensitiveKey=sensitiveKey;
    }

    public Sensitive(String sensitiveType, boolean selected)
    {
        this.sensitiveType = sensitiveType;
        this.selected=selected;
    }

    public String getKey() {
        return sensitiveKey;
    }

    public void setKey(String sensitiveKey) {
        this.sensitiveKey = sensitiveKey;
    }

    public String getSensitiveType()
    {
        return sensitiveType;
    }

    public void setSensitiveType(String sensitiveType) {
        this.sensitiveType = sensitiveType;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
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
