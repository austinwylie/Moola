package com.hci.moola.view;

import android.content.Context;

import com.hci.moola.model.ObservableSortedList;

import java.util.Arrays;
import java.util.List;

public abstract class ExpandableMultiSelectAdapter<T extends Comparable<? super T>> extends StableAdapter<ExpandableListItem<T>> implements
        ObservableSortedList.ObservableListListener<T> {
	private boolean[] mCheckedItems;
	private int mCheckedItemCount;
	private boolean mIsEditModeEnabled;

	public ExpandableMultiSelectAdapter(Context context, ObservableSortedList<T> items) {
		super(context, buildExpandableListItems(items));

		items.setObservableListListener(this);
		mCheckedItems = new boolean[items.size() + 4];
		mCheckedItemCount = 0;
		mIsEditModeEnabled = false;
	}

	/**
	 * T list items has been updated. We got to mirror this in our List of ExpandableListItems
	 */
	@Override
	public void onAdd(T itemAdded) {
		super.add(new ExpandableListItem<T>(itemAdded));
	}

	@Override
	public void onRemove(T itemRemoved) {
		super.remove(new ExpandableListItem<T>(itemRemoved));
	}

	@Override
	public void onSet(int index, T oldItem, T newItem) {
		super.set(index, new ExpandableListItem<T>(newItem));
	}
	
	private static <T extends Comparable<? super T>> List<ExpandableListItem<T>> buildExpandableListItems(ObservableSortedList<T> items) {

		ObservableSortedList<ExpandableListItem<T>> listItems = new ObservableSortedList<ExpandableListItem<T>>();
		for (T t : items)
			listItems.add(new ExpandableListItem<T>(t));
		return listItems;
	}

	public boolean isEditModeEnabled() {
		return mIsEditModeEnabled;
	}

	public int[] getCheckedIndicies() {
		int[] vals = new int[mCheckedItemCount];
		int count = 0;
		for (int i = 0; i < mCheckedItems.length; i++) {
			if (mCheckedItems[i])
				vals[count++] = i;
		}

		return vals;
	}

	public int getCheckedCount() {
		return mCheckedItemCount;
	}

	public void setEditModeEnabled(boolean isEdit) {
		if (!isEdit) {
			for (int i = 0; i < mCheckedItems.length; i++)
				mCheckedItems[i] = false;
			mCheckedItemCount = 0;
		}

		mIsEditModeEnabled = isEdit;
	}

	@Override
	public void notifyDataSetChanged() {
		if (mCheckedItems.length < getCount())
			mCheckedItems = Arrays.copyOf(mCheckedItems, getCount() + 4);
		super.notifyDataSetChanged();
	}

	public void toggleChecked(int index) {
		if (index < 0)
			return;

		if (mCheckedItems[index] = !mCheckedItems[index])
			mCheckedItemCount++;
		else
			mCheckedItemCount--;
	}

	protected boolean isChecked(int index) {
		return mCheckedItems[index];
	}
}
