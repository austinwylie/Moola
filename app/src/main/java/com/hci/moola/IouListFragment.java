package com.hci.moola;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hci.moola.model.Iou;
import com.hci.moola.model.Transaction;

import java.util.ArrayList;


public class IouListFragment extends Fragment {

    private IouAdapter adapter;

    public static IouListFragment newInstance() {
        IouListFragment fragment = new IouListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public IouListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ArrayList<Iou> ious = new ArrayList<Iou>();
        ious.add(new Iou(new Transaction("Ben", true, 10, "lunch")));
        ious.add(new Iou(new Transaction("Braden", false, 30, "hi")));
        ious.add(new Iou(new Transaction("Lana", true, 15.60, "idk")));
        // TODO: Change Adapter to display your content
        adapter = new IouAdapter(this.getActivity(), ious);
//        this.setListAdapter(adapter);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
