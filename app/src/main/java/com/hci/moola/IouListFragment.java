package com.hci.moola;

import android.animation.Animator;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hci.moola.model.Iou;
import com.hci.moola.model.ObservableSortedList;
import com.hci.moola.model.Transaction;
import com.hci.moola.view.ExpandableListItem;
import com.hci.moola.view.ExpandableMultiSelectAdapter;
import com.hci.moola.view.NaturalListView;
import com.hci.moola.view.SwipeDismissTouchListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class IouListFragment extends Fragment {
    private ObservableSortedList<Iou> mIous;
    private IouAdapter mAdapter;
    private NaturalListView mIouListView;
    private IouListFragmentCallback mCallback;

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

    public void updateTransaction(Transaction txn) {
        for (Iou iou : mIous) {
            if (txn.belongsToIou(iou)) {
                iou.updateTransaction(txn);
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public interface IouListFragmentCallback {
        public void onTransactionClicked(Iou iou, Transaction txn);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (IouListFragmentCallback) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            ArrayList<Iou> items = getArguments().getParcelableArrayList(TAG_ARGS);
            mIous = new ObservableSortedList<Iou>();
            for (Iou i : items)
                mIous.add(i);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_iou_list, container, false);

        mIouListView = (NaturalListView) rootView.findViewById(R.id.iouListView);
        mAdapter = new IouAdapter(this.getActivity(), mIous, mIouListView, mCallback);
        mIouListView.setAdapter(mAdapter);
        mIouListView.setNaturalListViewListener(mAdapter);

        return rootView;
    }

    private static class IouAdapter extends ExpandableMultiSelectAdapter<Iou> implements NaturalListView.NaturalListViewListener {
        private Locale mLocale;
        private LayoutInflater mInflater;
        private NaturalListView mListView;
        private IouListFragmentCallback mFragmentCallback;

        public IouAdapter(Activity activity, ObservableSortedList<Iou> items, NaturalListView listView, IouListFragmentCallback callback) {
            super(activity, items);
            mInflater = activity.getLayoutInflater();
            mLocale = activity.getResources().getConfiguration().locale;
            mListView = listView;
            mFragmentCallback = callback;
        }

        public void collapseAllItems() {
            for (ExpandableListItem<Iou> item : mItems) {
                item.setExpanded(false);
            }
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final TitleViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_iou, parent, false);

                holder = new TitleViewHolder();
                holder.person = (TextView) convertView.findViewById(R.id.list_item_iou_person);
                holder.amount = (TextView) convertView.findViewById(R.id.list_item_iou_amount);
                holder.expandableLayout = (LinearLayout) convertView.findViewById(R.id.list_item_iou_expandablelayout);
                holder.summaryLayout = (ViewGroup) convertView.findViewById(R.id.list_item_iou_summary_layout);
                convertView.setTag(holder);
            } else
                holder = (TitleViewHolder) convertView.getTag();

            convertView.setOnTouchListener(new SwipeDismissTouchListener(convertView, null, new SwipeDismissTouchListener.DismissCallbacks() {
                @Override
                public boolean canDismiss(Object token, float x, float y) {
                    return y >= holder.summaryLayout.getTop() && y <= holder.summaryLayout.getBottom();
                }

                @Override
                public void onDismiss(View view, Object token) {
                    IouAdapter.this.mItems.remove(position);
                    IouAdapter.this.notifyDataSetChanged();
                }

                @Override
                public void onClick(View view, Object token, float x, float y) {
                    mListView.onItemClick(view, position);
                }
            }));

            final ExpandableListItem<Iou> expandedItem = (ExpandableListItem<Iou>) getItem(position);
            final Iou item = expandedItem.getItem();
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

                    final Transaction t = txns.get(i);
                    tRow.setOnTouchListener(new SwipeDismissTouchListener(tRow, null, new SwipeDismissTouchListener.DismissCallbacks() {
                        @Override
                        public boolean canDismiss(Object token, float x, float y) {
                            return true;
                        }

                        @Override
                        public void onDismiss(View view, Object token) {
                            item.removeTransaction(t);
                            if (item.getTransactionList().isEmpty()) {
                                IouAdapter.this.mItems.remove(position);
//                                IouAdapter.this.collapseAllItems();
                            }
                            IouAdapter.this.notifyDataSetChanged();
                        }

                        @Override
                        public void onClick(View view, Object token, float x, float y) {
                            mFragmentCallback.onTransactionClicked(item, t);
                        }
                    }));

                    TextView description = (TextView) tRow.findViewById(R.id.list_item_iou_expanded_description);
                    TextView date = (TextView) tRow.findViewById(R.id.list_item_iou_expanded_date);
                    description.setText(t.getFormattedDescription());
                    date.setText(t.getDate(mLocale));
                }

                while (holder.expandableLayout.getChildCount() > txns.size())
                    holder.expandableLayout.removeViewAt(holder.expandableLayout.getChildCount() - 1);

                if (!expandedItem.isExpanded())
                    holder.expandableLayout.setVisibility(View.GONE);
            }

            return convertView;
        }

        @Override
        public boolean onExpandStart(View v, ExpandableListItem viewObject) {
            TitleViewHolder holder = (TitleViewHolder) v.getTag();
            holder.expandableLayout.setVisibility(View.VISIBLE);
            return true;
        }

        @Override
        public void onExpandEnd(View v, ExpandableListItem viewObject) {

        }

        @Override
        public void onCollapseStart(View v, ExpandableListItem viewObject) {

        }

        @Override
        public void onCollapseEnd(View v, ExpandableListItem viewObject) {
            TitleViewHolder holder = (TitleViewHolder) v.getTag();
            holder.expandableLayout.setVisibility(View.GONE);
        }

        @Override
        public Collection<Animator> addExpandAnimations(View v, ExpandableListItem viewObject) {
            return null;
        }

        @Override
        public Collection<Animator> addCollapseAnimations(View v, ExpandableListItem viewObject) {
            return null;
        }

        private static class TitleViewHolder {
            ViewGroup summaryLayout;
            TextView person;
            TextView amount;
            LinearLayout expandableLayout;
        }
    }
}
