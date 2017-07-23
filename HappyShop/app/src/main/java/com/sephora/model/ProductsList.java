package com.sephora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nivedhitha.a on 26-Apr-16.
 */
public class ProductsList {

    public ArrayList<Product> getProductArrayList() {
        return productArrayList;
    }

    public void setProductArrayList(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    @Expose
    @SerializedName("products")
    private ArrayList<Product> productArrayList;
}
