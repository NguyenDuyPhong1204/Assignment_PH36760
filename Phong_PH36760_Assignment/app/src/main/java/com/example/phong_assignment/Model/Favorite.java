package com.example.phong_assignment.Model;

import com.google.gson.annotations.SerializedName;

public class Favorite {
    @SerializedName("_id")
    private String favoriteId;
    @SerializedName("userId")
    private String userId;
    @SerializedName("productId")
    private String productId;

    public Favorite(String favoriteId, String userId, String productId) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.productId = productId;
    }

    public Favorite() {
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
