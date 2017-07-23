package com.sephora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nivedhitha.a on 27-Apr-16.
 */
public class ProductDetail implements Serializable {

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Expose
    @SerializedName("product")
    private Product product;
}
