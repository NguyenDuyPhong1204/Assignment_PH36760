package com.example.phong_assignment.Model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("_id")
    private String categoryId;
    private String title, image;

    public Category(String categoryId, String title, String image) {
        this.categoryId = categoryId;
        this.title = title;
        this.image = image;
    }

    public Category(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public Category() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
