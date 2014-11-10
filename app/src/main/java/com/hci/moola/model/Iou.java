package com.hci.moola.model;

import java.math.BigDecimal;
import java.util.List;

public class Iou {
    private String person;
    private double totalAmount;
    private List<Transaction> transactionList;

    public Iou(String person, List<Transaction> transactionList) {
        this.person = person;
        this.transactionList = transactionList;

        totalAmount = 0;
        for (Transaction txn : transactionList) {
           totalAmount += txn.getAmount();
        }
    }

    public Iou(String person, double amount) {
        this.person = person;
        this.totalAmount = amount;

    }


    public String getPerson() {
        return person;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}
