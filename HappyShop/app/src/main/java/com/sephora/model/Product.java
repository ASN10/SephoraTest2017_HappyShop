package com.sephora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by nivedhitha.a on 26-Apr-16.
 */
public class Product implements Serializable {

    private String id;
    private String name;
    private String category;
    private String price;

    public boolean isUnderSale() {
        return isUnderSale;
    }

    public void setIsUnderSale(boolean isUnderSale) {
        this.isUnderSale = isUnderSale;
    }

    @Expose
    @SerializedName("under_sale")
    private boolean isUnderSale = false;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Expose
    @SerializedName("img_url")
    private String imageUrl;
}
