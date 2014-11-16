package com.hci.moola.model;

import android.app.Activity;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Created by byoung2 on 11/10/14.
 */
public class DataCollector {
    private static DataCollector mInstance;
    private PrintWriter mWriter;
    private int mTouchCount;

    public static DataCollector getInstance() {
        if (mInstance == null)
            mInstance = new DataCollector();
        return mInstance;
    }

    private DataCollector() {
        mTouchCount = 0;
        Calendar mCal = Calendar.getInstance();

        String filename = "/moola_log_" + mCal.get(Calendar.MONTH) + mCal.get(Calendar.DAY_OF_MONTH)
                + mCal.get(Calendar.HOUR_OF_DAY) + mCal.get(Calendar.MINUTE) + ".txt";
        try {
            mWriter = new PrintWriter(Environment.getExternalStorageDirectory() + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToLogFile() {
        String content = "NumberOfTouches: " + mTouchCount;
        if (mWriter != null) {
            mWriter.write(content);
            mWriter.flush();
        }
    }

    private void touchOccurred() {
        mTouchCount++;
    }

    public void attachTouchCounterToActivity(Activity activity) {
        View overlay = new View(activity);
        overlay.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        overlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    touchOccurred();
                }
                return false;
            }
        });
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(overlay);
    }
}
