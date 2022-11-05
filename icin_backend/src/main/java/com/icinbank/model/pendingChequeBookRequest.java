package com.icinbank.model;

public class pendingChequeBookRequest {

    private int requestId;

    private String customerName;

    private String account;

    private int quantity;

    private String status;

    public pendingChequeBookRequest(int requestId, String customerName, String account, int quantity, String status) {
        this.requestId = requestId;
        this.customerName = customerName;
        this.account = account;
        this.quantity = quantity;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
