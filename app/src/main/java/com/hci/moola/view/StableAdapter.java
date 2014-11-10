package com.hci.moola.view;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public abstract class StableAdapter<T> extends BaseAdapter {
	protected List<T> mItems;
	private final Object mLock = new Object();
	private Context mContext;
	private HashMap<T, Integer> mIdMap = new HashMap<T, Integer>();

	public StableAdapter(Context context, List<T> items) {
		mContext = context;
		mItems = items;

		for (int i = 0; i < mItems.size(); i++)
			mIdMap.put(mItems.get(i), i);
	}

	public Context getContext() {
		return mContext;
	}

	/**
	 * Adds the specified object at the end of the array.
	 * 
	 * @param object
	 *            The object to add at the end of the array.
	 */
	public void add(T object) {
		synchronized (mLock) {
			mItems.add(object);
			mIdMap.put(object, mIdMap.size() - 1);
		}
		notifyDataSetChanged();
	}

	/**
	 * Adds the specified Collection at the end of the array.
	 * 
	 * @param collection
	 *            The Collection to add at the end of the array.
	 */
	public void addAll(Collection<? extends T> collection) {
		synchronized (mLock) {
			mItems.addAll(collection);
			for (T item : collection)
				mIdMap.put(item, mIdMap.size() - 1);
		}
		notifyDataSetChanged();
	}

	/**
	 * Adds the specified items at the end of the array.
	 * 
	 * @param items
	 *            The items to add at the end of the array.
	 */
	public void addAll(T... items) {
		synchronized (mLock) {
			Collections.addAll(mItems, items);
			for (T item : items)
				mIdMap.put(item, mIdMap.size() - 1);
		}
		notifyDataSetChanged();
	}

	/**
	 * Removes the specified object from the array.
	 * 
	 * @param item
	 *            The object to remove.
	 */
	public void remove(T item) {
		synchronized (mLock) {
			mItems.remove(item);
			mIdMap.remove(item);
		}
		notifyDataSetChanged();
	}
	
	public void set(int index, T object) {
		synchronized (mLock) {
			mIdMap.put(object, index);
			mItems.set(index, object);
		}
		notifyDataSetChanged();
	}
	
	public void set(Object oldObject, T newObject) {
		synchronized (mLock) {
			int index = -1;
			for (int i = 0; i < mItems.size(); i++) {
				T item = mItems.get(i);
				if (item.equals(oldObject)) {
					index = i;
					break;
				}
			}
			
			mIdMap.put(newObject, index);
			mItems.set(index, newObject);
		}
		notifyDataSetChanged();
	}

	/**
	 * Returns the position of the specified item in the array.
	 * 
	 * @param item
	 *            The item to retrieve the position of.
	 * 
	 * @return The position of the specified item.
	 */
	public int getPosition(T item) {
		return mItems.indexOf(item);
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mIdMap.get(getItem(position));
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
}
