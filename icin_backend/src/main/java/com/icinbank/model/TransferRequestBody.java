package com.icinbank.model;

public class TransferRequestBody {

    private int userIdFrom;

    private String firstnameTo;

    private String lastnameTo;

    private double amount;

    private boolean fromSaving;

    private boolean toSaving;

    public int getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(int userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public String getFirstnameTo() {
        return firstnameTo;
    }

    public void setFirstnameTo(String firstnameTo) {
        this.firstnameTo = firstnameTo;
    }

    public String getLastnameTo() {
        return lastnameTo;
    }

    public void setLastnameTo(String lastnameTo) {
        this.lastnameTo = lastnameTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isFromSaving() {
        return fromSaving;
    }

    public void setFromSaving(boolean fromSaving) {
        this.fromSaving = fromSaving;
    }

    public boolean isToSaving() {
        return toSaving;
    }

    public void setToSaving(boolean toSaving) {
        this.toSaving = toSaving;
    }
}
