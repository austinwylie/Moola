package com.hci.moola.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Transaction implements Parcelable {
    private String person;
    private boolean owesMe;
    private double amount;
    private String description;
    private Calendar date;

    public Transaction(String person, boolean owesMe, double amount, String description, Calendar date) {
        this.person = person;
        this.owesMe = owesMe;
        this.amount = amount;
        this.description = description;
        this.date = date;
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

    public String getFormattedDescription() {
        if (amount != 0) {
            String amountText = CurrencyFormatter.getInstance().format(getFormattedAmount());
            if (!description.isEmpty())
                return amountText + " for " + description;
            return amountText;
        }
        return description;
    }

    public String getDate(Locale locale) {
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, locale).format(date.getTime());
    }

    public boolean belongsToIou(Iou iou) {
        return this.person.equals(iou.getPerson());
    }

    public Transaction(Parcel in){
        person = in.readString();
        owesMe = in.readByte() != 0;
        amount = in.readDouble();
        description = in.readString();
        date = (Calendar) in.readSerializable();
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(person);
        dest.writeByte((byte) (owesMe ? 1 : 0));
        dest.writeDouble(amount);
        dest.writeString(description);
        dest.writeSerializable(date);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
