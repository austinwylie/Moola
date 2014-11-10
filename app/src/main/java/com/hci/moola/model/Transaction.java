package com.hci.moola.model;


public class Transaction {
    private boolean owesMe;
    private double amount;
    private String description;

    public boolean isOwesMe() {
        return owesMe;
    }

    public void setOwesMe(boolean owesMe) {
        this.owesMe = owesMe;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Transaction(boolean owesMe, double amount, String description) {
        this.owesMe = owesMe;
        this.amount = amount;
        this.description = description;

    }
}
