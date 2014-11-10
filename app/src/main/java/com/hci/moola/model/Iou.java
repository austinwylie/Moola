package com.hci.moola.model;

import java.util.ArrayList;
import java.util.List;

public class Iou {
    private List<Transaction> transactionList;

    public Iou(Transaction trans) {
        transactionList = new ArrayList<Transaction>();
        transactionList.add(trans);
    }

    public void addTransaction(Transaction trans) {
        transactionList.add(trans);
    }

    public String getPerson() {
        return transactionList.get(0).getPerson();
    }

    public double getTotalAmount() {
        return 0;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }
}
