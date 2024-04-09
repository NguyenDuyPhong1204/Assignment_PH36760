package com.example.phong_assignment.Model;

import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("_id")
    private String cartId;
    @SerializedName("productId")
    private String productId;
    @SerializedName("userId")
    private String userId;
    private String productName, productImage;
    private int quantity;
    private double productPrice;
    private String createAt, updateAt;

    public Cart() {
    }

    public Cart(String cartId, String productId, String userId, String productName, String productImage, int quantity, double productPrice, String createAt, String updateAt) {
        this.cartId = cartId;
        this.productId = productId;
        this.userId = userId;
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
