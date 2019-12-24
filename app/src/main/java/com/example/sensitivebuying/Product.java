package com.example.sensitivebuying;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable  {

    private String companyName;
    private String productName;
    private String barcode;
    private String weightAndType;
    private String productDescription;
    private String urlImage;
    private ArrayList<Sensitive> sensitiveList;



    public Product() {

    }

    public Product(String companyName, String productName, String barcode, String weightAndType, String productDescription, String urlImage, ArrayList<Sensitive> sensitiveList)
    {
        this.companyName = companyName;
        this.productName = productName;
        this.barcode = barcode;
        this.weightAndType = weightAndType;
        this.productDescription = productDescription;
        this.urlImage = urlImage;
        this.sensitiveList=sensitiveList ;

    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }


    public void setWeightAndType(String weightAndType) {
        this.weightAndType = weightAndType;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setSensitiveList(ArrayList<Sensitive> sensitiveList) {
        this.sensitiveList = new ArrayList<>(sensitiveList);
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public String getProductName()
    {
        return productName;
    }

    public String getBarcode()
    {
        return barcode;
    }


    public String getWeightAndType()
    {
        return weightAndType;
    }

    public String getProductDescription()
    {
        return productDescription;
    }

    public String getUrlImage()
    {
        return urlImage;
    }

    public ArrayList<Sensitive> getSensitiveList()
    {
        return sensitiveList;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }
        Product p = (Product) obj;

        if (this.barcode.equals(p.barcode)){
            return true;
        }


        return false;
    }




}
