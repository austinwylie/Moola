package com.hci.moola.model;

import java.util.Collections;

@SuppressWarnings("serial")
public class ObservableSortedList<T extends Comparable<? super T>> extends SortedList<T> {
	private ObservableListListener<T> mCallback;

	public void setObservableListListener(ObservableListListener<T> listener) {
		mCallback = listener;
	}

	@Override
	public boolean add(T object) {
		boolean result = super.add(object);
		if (mCallback != null)
			mCallback.onAdd(object);
		return result;
	}

	@Override
	public boolean remove(Object object) {
		int index = Collections.binarySearch(this, (T) object);
		if (index < 0)
			index = ~index;

		super.remove(index);
		if (mCallback != null)
			mCallback.onRemove((T) object);
		return true;
	}

	@Override
	public T set(int index, T object) {
		T result = super.set(index, object);
		if (mCallback != null)
			mCallback.onSet(index, result, object);
		return result;
	}

	public static interface ObservableListListener<T> {
		/**
		 * Item has been added to this list. Do something about it son!
		 * 
		 * @param itemAdded
		 */
		public void onAdd(T itemAdded);

		public void onRemove(T itemRemoved);

		public void onSet(int index, T oldItem, T newItem);
	}
}
