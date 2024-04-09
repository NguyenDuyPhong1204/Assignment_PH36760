package com.example.phong_assignment.Model;

public class BillDetails {
    private String billId, productId;
    private int quantity;

    public BillDetails(String billId, String productId, int quantity) {
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public BillDetails() {
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
