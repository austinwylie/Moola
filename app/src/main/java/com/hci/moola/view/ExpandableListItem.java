package com.hci.moola.view;

public class ExpandableListItem<T extends Comparable<? super T>> implements Comparable<ExpandableListItem<T>> {
	private T mItem;
	private Object mTag;
	private boolean mIsExpanded;
	private int mCollapsedHeight;

	public ExpandableListItem(T item) {
		this.mItem = item;
		mIsExpanded = false;
	}

	public boolean isExpanded() {
		return mIsExpanded;
	}

	public void toggleExpanded() {
		mIsExpanded = !mIsExpanded;
	}

	public void setExpanded(boolean isExpanded) {
		mIsExpanded = isExpanded;
	}

	public T getItem() {
		return mItem;
	}

	public void setTag(Object o) {
		mTag = o;
	}

	public Object getTag() {
		return mTag;
	}

	public void setCollapsedHeight(int height) {
		mCollapsedHeight = height;
	}

	public int getCollapsedHeight() {
		return mCollapsedHeight;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ExpandableListItem)
			return mItem.equals(((ExpandableListItem) o).mItem);
		if (o instanceof Comparable<?>)
			return mItem.equals((T) o);
		return false;
	}

	@Override
	public int hashCode() {
		return mItem.hashCode();
	}

	@Override
	public int compareTo(ExpandableListItem<T> another) {
		return mItem.compareTo(another.mItem);
	}
}
