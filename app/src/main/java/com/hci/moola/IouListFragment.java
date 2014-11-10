package com.hci.moola;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.hci.moola.model.Iou;
import com.hci.moola.model.Transaction;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;

public class IouListFragment extends Fragment implements AdapterView.OnItemClickListener {


    private IouAdapter adapter;
    private DynamicListView iouLayout;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_iou_list, container, false);

        iouLayout = (DynamicListView) rootView.findViewById(R.id.iouListView);

        ArrayList<Iou> ious = new ArrayList<Iou>();

        ious.add(new Iou(new Transaction("Ben", true, 10, "lunch")));
        ious.add(new Iou(new Transaction("Braden", false, 30, "hi")));
        ious.add(new Iou(new Transaction("Lana", true, 15.60, "idk")));

        adapter = new IouAdapter(this.getActivity(), ious);
        iouLayout.setAdapter(adapter);


        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
