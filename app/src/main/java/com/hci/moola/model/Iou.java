package com.hci.moola.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Iou implements Parcelable {
    private List<Transaction> transactionList = new ArrayList<Transaction>();

    public Iou(Transaction trans) {
        transactionList.add(trans);
    }

    public void addTransaction(Transaction trans) {
        transactionList.add(trans);
    }

    public String getPerson() {
        return transactionList.get(0).getPerson();
    }

    public String getTotalAmountText() {
        double amount = getTotalAmount();
        if (amount != 0)
            return CurrencyFormatter.getInstance().format(amount);
        for (Transaction txn : transactionList) {
            if (txn.getDescription() != null && !txn.getDescription().isEmpty())
                return txn.getDescription();
        }
        return "";
    }

    private double getTotalAmount() {
        double total = 0;

        for (Transaction txn : transactionList) {
            total += txn.getFormattedAmount();
        }

        return total;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public Iou(Parcel in) {
        int count = in.readInt();
        for (int i = 0; i < count; i++) {
            Transaction t = in.readParcelable(Transaction.class.getClassLoader());
            transactionList.add(t);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(transactionList.size());
        for (Transaction t : transactionList) {
            dest.writeParcelable(t, 0);
        }
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Iou createFromParcel(Parcel in) {
            return new Iou(in);
        }

        public Iou[] newArray(int size) {
            return new Iou[size];
        }
    };
}
