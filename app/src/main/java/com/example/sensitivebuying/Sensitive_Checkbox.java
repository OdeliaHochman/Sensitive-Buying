package com.example.sensitivebuying;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Sensitive_Checkbox implements Serializable {

    private String sensitiveKeybox;
    private String sensitiveTypebox;
    private boolean selectedbox;


    public Sensitive_Checkbox()
    {
    }

    public Sensitive_Checkbox(String sensitiveType, String sensitiveKey)
    {
        this.sensitiveTypebox = sensitiveType;
        this.sensitiveKeybox=sensitiveKey;
    }

    public Sensitive_Checkbox(String sensitiveType, boolean selected)
    {
        this.sensitiveTypebox = sensitiveType;
        this.selectedbox=selected;
    }

    public String getKeybox() {
        return sensitiveKeybox;
    }

    public void setKeybox(String sensitiveKey) {
        this.sensitiveKeybox = sensitiveKey;
    }

    public String getSensitiveTypebox()
    {
        return sensitiveTypebox;
    }

    public void setSensitiveTypebox(String sensitiveType) {
        this.sensitiveTypebox = sensitiveType;
    }

    public boolean getSelectedbox() {
        return selectedbox;
    }

    public void setSelectedbox(Boolean selected) {
        this.selectedbox = selected;
    }


    @NonNull
    @Override
    public String toString() {
        return sensitiveTypebox;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }
        Sensitive_Checkbox s = (Sensitive_Checkbox) obj;

        if (this.sensitiveTypebox.equals(s.sensitiveTypebox)){
            return true;
        }


        return false;
    }


}
