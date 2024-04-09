package com.example.phong_assignment.Model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("_id")
    private String productId;
    private String productName,productImage, description;
    private double productPrice;
    @SerializedName("categoryID")
    private String categoryID;
    private Boolean isFavorite, isCart;

    public Product(String productId, String productName, String productImage, String description, double productPrice, String categoryID, Boolean isFavorite, Boolean isCart) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.description = description;
        this.productPrice = productPrice;
        this.categoryID = categoryID;
        this.isFavorite = isFavorite;
        this.isCart = isCart;
    }

    public Product() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Boolean getCart() {
        return isCart;
    }

    public void setCart(Boolean cart) {
        isCart = cart;
    }
}
