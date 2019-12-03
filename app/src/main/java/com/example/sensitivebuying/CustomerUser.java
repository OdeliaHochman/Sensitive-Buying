package com.example.sensitivebuying;

import java.util.ArrayList;
import java.util.Vector;

public class CustomerUser extends User {

    public ArrayList<Sensitive> sensitiveList =  new ArrayList<>();
    public ArrayList<Products> favoriteList =  new ArrayList<>();

    public CustomerUser()
    {
        this.sensitiveList =new ArrayList<>();
        this.favoriteList =new ArrayList<>();
    }


    public CustomerUser(String name, String mail, ArrayList<Sensitive> sensitiveList, ArrayList<Products> favoriteList)
    {
        super(name, mail);
        for ( int i=0; i<sensitiveList.size();i++)
        {
            this.sensitiveList.add(sensitiveList.get(i));
        }
        for ( int i=0; i<favoriteList.size();i++)
        {
            this.favoriteList.add(favoriteList.get(i));
        }

    }

    public ArrayList<Sensitive> getSensitiveList()
    {
        return sensitiveList;
    }

    public void setSensitiveList(ArrayList<Sensitive> sensitiveList)
    {
        for ( int i=0; i<sensitiveList.size();i++)
        {
            this.sensitiveList.add(sensitiveList.get(i));
        }
    }

    public ArrayList<Products> getFavoriteList()
    {
        return favoriteList;
    }

    public void setFavoriteList(Vector<Products> favoriteList)
    {
        for ( int i=0; i<favoriteList.size();i++)
        {
            this.favoriteList.add(favoriteList.get(i));
        }
    }
}
