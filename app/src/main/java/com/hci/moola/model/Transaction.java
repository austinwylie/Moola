package com.hci.moola.model;


public class Transaction {
    private String person;
    private boolean owesMe;
    private double amount;
    private String description;

    public Transaction(String person, boolean owesMe, double amount, String description) {
        this.person = person;
        this.owesMe = owesMe;
        this.amount = amount;
        this.description = description;
    }

    public double getFormattedAmount() {
        return owesMe ? amount : -amount;
    }

    public String getPerson() {
        return person;
    }

    public boolean isOwesMe() {
        return owesMe;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

}
