package com.hci.moola.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byoung2 on 11/16/14.
 */
public class ColorPicker {
    private static ColorPicker mInstance;
    private List<Integer> mColors;
    private int mCurrentIndex;

    public static ColorPicker getInstance() {
        if (mInstance == null)
            mInstance = new ColorPicker();
        return mInstance;
    }

    private ColorPicker() {
        mCurrentIndex = 0;
        mColors = new ArrayList<Integer>();
        mColors.add(0xFF556270);
        mColors.add(0xFF4ECDC4);
        mColors.add(0xFFC7F464);
        mColors.add(0xFFFF6B6B);
        mColors.add(0xFFC44D58);
    }

    public int next() {
        int color = mColors.get(mCurrentIndex);
        mCurrentIndex = (mCurrentIndex + 1) % mColors.size();
        return color;
    }

    public static boolean isDarkColor(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        double luma = 0.2126 * r + 0.7152 * g + 0.0722 * b;
        return luma < 105;
    }
}
