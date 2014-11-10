package com.hci.moola;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hci.moola.model.Iou;

import java.util.List;


public class IouAdapter extends BaseAdapter{
    Context context;
    List<Iou> ious;

    public IouAdapter(Context context, List<Iou> ious) {
        this.context = context;
        this.ious = ious;
    }

    @Override
    public int getCount() {
        return this.ious.size();
    }

    @Override
    public Object getItem(int i) {
        return this.ious.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        IouView iouView = null;

        if (view == null) {
            iouView = new IouView(context, this.ious.get(i));
        }
        else {
            iouView = (IouView) view;
        }

        iouView.setIou(this.ious.get(i));
        return iouView;
    }
}
