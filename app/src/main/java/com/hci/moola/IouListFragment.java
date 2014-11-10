package com.hci.moola;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hci.moola.model.Iou;
import com.hci.moola.model.Transaction;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem.ExpandableListItemAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IouListFragment extends Fragment {
    private List<Iou> mIous;
    private IouAdapter mAdapter;
    private DynamicListView iouListView;

    private static final String TAG_ARGS = "model";

    public static IouListFragment newInstance(ArrayList<Iou> ious) {
        IouListFragment fragment = new IouListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(TAG_ARGS, ious);
        fragment.setArguments(args);
        return fragment;
    }

    public void addTransaction(Transaction txn) {
        for (Iou iou : mIous) {
            if (txn.belongsToIou(iou)) {
                iou.addTransaction(txn);
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
        mIous.add(new Iou(txn));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mIous = args.getParcelableArrayList(TAG_ARGS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_iou_list, container, false);

        iouListView = (DynamicListView) rootView.findViewById(R.id.iouListView);
        mAdapter = new IouAdapter(this.getActivity(), mIous);
        iouListView.setAdapter(mAdapter);
        iouListView.enableSwipeToDismiss(
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(final ViewGroup listView, final int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            mAdapter.collapse(position);
                            mAdapter.remove(position);
                        }
                    }
                }
        );

        return rootView;
    }

    private static class IouAdapter extends ExpandableListItemAdapter<Iou> {
        private Activity mActivity;
        private List<View> mRecycledViews;

        public IouAdapter(Activity activity, List<Iou> items) {
            super(activity, items);
            mActivity = activity;
            mRecycledViews = new ArrayList<View>();
        }

        @Override
        public View getTitleView(int position, View convertView, ViewGroup parent) {
            TitleViewHolder holder;

            if (convertView == null) {
                convertView = mActivity.getLayoutInflater().inflate(R.layout.list_item_iou, parent, false);

                holder = new TitleViewHolder();
                holder.person = (TextView) convertView.findViewById(R.id.list_item_iou_person);
                holder.amount = (TextView) convertView.findViewById(R.id.list_item_iou_amount);
                convertView.setTag(holder);
            } else
                holder = (TitleViewHolder) convertView.getTag();

            Iou item = getItem(position);
            holder.person.setText(item.getPerson());
            holder.amount.setText(item.getTotalAmountText());

            return convertView;
        }

        private static class TitleViewHolder {
            TextView person;
            TextView amount;
        }

        @Override
        public View getContentView(int position, View convertView, ViewGroup parent) {
            LinearLayout layoutView = (LinearLayout) convertView;

            if (layoutView == null) {
                layoutView = new LinearLayout(mActivity);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutView.setLayoutParams(params);
            }

            List<Transaction> transactions = getItem(position).getTransactionList();
            while (layoutView.getChildCount() > transactions.size()) {
                int index = layoutView.getChildCount() - 1;
                mRecycledViews.add(layoutView.getChildAt(index));
                layoutView.removeViewAt(index);
            }

            while (layoutView.getChildCount() < transactions.size()) {
                View tRow = null;
                if (!mRecycledViews.isEmpty())
                    tRow = mRecycledViews.remove(mRecycledViews.size() - 1);

                if (tRow == null) {
                    tRow = mActivity.getLayoutInflater().inflate(R.layout.list_item_iou_expanded, layoutView, false);
                    ContentViewHolder holder = new ContentViewHolder();
                    holder.description = (TextView) tRow.findViewById(R.id.list_item_iou_expanded_description);
                    holder.date = (TextView) tRow.findViewById(R.id.list_item_iou_expanded_date);
                    tRow.setTag(holder);
                }

                layoutView.addView(tRow);
            }

            for (int i = 0; i < layoutView.getChildCount(); i++) {
                View tRow = layoutView.getChildAt(i);
                ContentViewHolder holder = (ContentViewHolder) tRow.getTag();
                Transaction txn = transactions.get(i);
                holder.description.setText(txn.getFormattedDescription());
                holder.date.setText(txn.getDate(mActivity.getResources().getConfiguration().locale));
            }

            return layoutView;
        }

        private static class ContentViewHolder {
            TextView description;
            TextView date;
        }
    }
}
