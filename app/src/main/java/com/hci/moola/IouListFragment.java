package com.hci.moola;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hci.moola.model.Iou;
import com.hci.moola.model.Transaction;
import com.hci.moola.view.ExpandableLayout;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
        mAdapter.collapseExpanded();

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

        if (savedInstanceState == null) {
            mIous = getArguments().getParcelableArrayList(TAG_ARGS);
        }
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
                            mIous.remove(position);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
        );

        return rootView;
    }

    private static class IouAdapter extends BaseAdapter {
        private List<Iou> mItems;
        private Locale mLocale;
        private LayoutInflater mInflater;
        private boolean[] mExpanded;

        public IouAdapter(Activity activity, List<Iou> items) {
            mItems = items;
            mInflater = activity.getLayoutInflater();
            mLocale = activity.getResources().getConfiguration().locale;
            mExpanded = new boolean[items.size()];
        }

        public void collapseExpanded() {
            Arrays.fill(mExpanded, false);
        }

        private boolean isExpanded(int index) {
            return mExpanded[index];
        }

        @Override
        public void notifyDataSetChanged() {
            if (mExpanded.length < mItems.size()) {
                mExpanded = new boolean[mItems.size() + 4];
            }
            super.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int pos) {
            return mItems.get(pos);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final TitleViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_iou, parent, false);

                holder = new TitleViewHolder();
                holder.person = (TextView) convertView.findViewById(R.id.list_item_iou_person);
                holder.amount = (TextView) convertView.findViewById(R.id.list_item_iou_amount);
                holder.expandableLayout = (ExpandableLayout) convertView.findViewById(R.id.list_item_iou_expandablelayout);
                holder.summaryLayout = (ViewGroup) convertView.findViewById(R.id.list_item_iou_summary_layout);
                convertView.setTag(holder);
            } else
                holder = (TitleViewHolder) convertView.getTag();

            holder.summaryLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isExpanded(position)) {
                        holder.expandableLayout.collapse();
                    } else {
                        holder.expandableLayout.expand();
                    }
                    mExpanded[position] = !mExpanded[position];
                }
            });

            Iou item = (Iou) getItem(position);
            if (item != null) {
                holder.person.setText(item.getPerson());
                holder.amount.setText(item.getTotalAmountText());

                List<Transaction> txns = item.getTransactionList();
                for (int i = 0; i < txns.size(); i++) {
                    View tRow;

                    if (holder.expandableLayout.getChildCount() <= i) {
                        tRow = mInflater.inflate(R.layout.list_item_iou_expanded, null);
                        holder.expandableLayout.addView(tRow);
                    } else
                        tRow = holder.expandableLayout.getChildAt(i);

                    TextView description = (TextView) tRow.findViewById(R.id.list_item_iou_expanded_description);
                    TextView date = (TextView) tRow.findViewById(R.id.list_item_iou_expanded_date);
                    Transaction t = txns.get(i);
                    description.setText(t.getFormattedDescription());
                    date.setText(t.getDate(mLocale));
                }

                while (holder.expandableLayout.getChildCount() > txns.size())
                    holder.expandableLayout.removeViewAt(holder.expandableLayout.getChildCount() - 1);

                holder.expandableLayout.setVisibility(isExpanded(position) ? View.VISIBLE : View.GONE);
            }

            return convertView;
        }

        private static class TitleViewHolder {
            ViewGroup summaryLayout;
            TextView person;
            TextView amount;
            ExpandableLayout expandableLayout;
        }
    }
}
