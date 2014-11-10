package com.hci.moola;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hci.moola.model.Iou;


public class IouView extends RelativeLayout{
    private TextView person;
    private TextView amount;
    private Iou iou;

    public IouView(Context context, Iou iou) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.list_item_iou, this, true);

        person = (TextView) findViewById(R.id.iouPerson);
        amount = (TextView) findViewById(R.id.iouAmount);

        setIou(iou);
        requestLayout();
    }

    public void setIou(Iou iou) {
        this.iou = iou;

        person.setText(iou.getPerson());
        amount.setText(iou.getTotalAmount() + "");

    }
}
