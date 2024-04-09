package com.example.phong_assignment.Model;

import com.google.gson.annotations.SerializedName;

public class Bill {
    @SerializedName("_id")
    private String billId;
    @SerializedName("userId")
    private String userId;
    private double total;
    private String address, createAt, updateAt;

    public Bill(String billId, String userId, double total, String address, String createAt, String updateAt) {
        this.billId = billId;
        this.userId = userId;
        this.total = total;
        this.address = address;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Bill() {
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

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
