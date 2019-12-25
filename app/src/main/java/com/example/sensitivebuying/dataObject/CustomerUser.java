package com.example.sensitivebuying.dataObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerUser extends User implements Serializable {

    private ArrayList<Sensitive> sensitiveList;
    private ArrayList<Product> favoriteList;

    public CustomerUser()
    {
        super();
        this.sensitiveList =new ArrayList<>();
        this.favoriteList =new ArrayList<>();
    }


    public CustomerUser(String name, String mail, ArrayList<Sensitive> sensitiveList, ArrayList<Product> favoriteList)
    {
        super(name, mail, false);
        this.favoriteList=favoriteList;
        this.sensitiveList =sensitiveList;

    }
    public ArrayList<Product> getFavoriteList()
    {
        return favoriteList;
    }


    public void setFavoriteList(ArrayList<Product> favoriteList) {
        this.favoriteList=favoriteList;
    }

    public ArrayList<Sensitive> getSensitiveList()
    {
        return sensitiveList;
    }

    public void setSensitiveList(ArrayList<Sensitive> sensitiveList) {
        this.sensitiveList = new ArrayList<>(sensitiveList);

}



}
