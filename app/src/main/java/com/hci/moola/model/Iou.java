package com.hci.moola.model;

import java.math.BigDecimal;

public class Iou {
    private String title;
    private boolean owesMe;
    private BigDecimal amount;
    private String description;

    public Iou(String title, boolean owesMe, BigDecimal amount, String description) {
        this.title = title;
        this.owesMe = owesMe;
        this.amount = amount;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public boolean isOwesMe() {
        return owesMe;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
