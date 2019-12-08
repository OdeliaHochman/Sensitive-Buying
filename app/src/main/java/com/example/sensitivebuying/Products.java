package com.example.sensitivebuying;

import java.util.ArrayList;

public class Products {



    public String companyName;
    public String productName;
    public String barcode;
    public String weight;
    public String typeWeight;
    public String productDescription;
    public String urlImage;
    public ArrayList<Sensitive> sensitiveList = new ArrayList<>();



    public Products() {

        this.sensitiveList = new ArrayList<>();
    }

    public Products(String companyName, String productName, String barcode,String weight, String typeWeight, String productDescription, String urlImage, ArrayList<Sensitive> sensitiveList)
    {
        this.companyName = companyName;
        this.productName = productName;
        this.barcode = barcode;
        this.weight = weight;
        this.typeWeight = typeWeight;
        this.productDescription = productDescription;
        this.urlImage = urlImage;
        for ( int i=0; i<sensitiveList.size();i++)
        {
            this.sensitiveList.add(sensitiveList.get(i));
        }
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

    public String getWeight()
    {
        return weight;
    }

    public String getTypeWeight()
    {
        return typeWeight;
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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setTypeWeight(String typeWeight) {
        this.typeWeight = typeWeight;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setSensitiveList(ArrayList<Sensitive> sensitiveList) {
        this.sensitiveList = sensitiveList;
    }

}
