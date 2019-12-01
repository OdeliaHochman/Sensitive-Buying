package com.example.sensitivebuying;

import java.util.ArrayList;
import java.util.Vector;

public class RepresentativeUser extends User {

    public ArrayList<Products> historyList = new ArrayList<>();
    public String companyName;

    public RepresentativeUser() {
        this.historyList = new ArrayList<>();
    }


    public RepresentativeUser(String name, String mail, ArrayList<Products> historyList , String companyName) {
        super(name, mail);
        this.companyName = companyName;
        for ( int i=0; i<historyList.size();i++)
        {
            this.historyList.add(historyList.get(i));
        }
    }

    public ArrayList<Products> getHistoryList() {
        return historyList;
    }

    public String getCompanyName() {
        return companyName;
    }
}
