package com.hci.moola.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by byoung2 on 11/10/14.
 */
public class CurrencyFormatter {
    private static CurrencyFormatter instance;
    private DecimalFormat amountFormatter;

    public static CurrencyFormatter getInstance() {
        if (instance == null)
            instance = new CurrencyFormatter();
        return instance;
    }

    public String format(double value) {
        return amountFormatter.format(value);
    }

    private CurrencyFormatter() {
        amountFormatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
        String symbol = amountFormatter.getCurrency().getSymbol();
        amountFormatter.setNegativePrefix("-" + symbol);
        amountFormatter.setNegativeSuffix("");
    }
}
