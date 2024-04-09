package com.example.phong_assignment.Model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    private String userId;
    private String emailUser, username, password, fullname, imageUser, addressUser, phoneNumber;
    private String createAt, updateAt;

    public User() {
    }

    public User(String userId, String emailUser, String username, String password, String fullname, String imageUser, String addressUser, String phoneNumber, String createAt, String updateAt) {
        this.userId = userId;
        this.emailUser = emailUser;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.imageUser = imageUser;
        this.addressUser = addressUser;
        this.phoneNumber = phoneNumber;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImageUser() {
        return imageUser;
    }

    public void setImageUser(String imageUser) {
        this.imageUser = imageUser;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
