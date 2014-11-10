package com.hci.moola.model;

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("serial")
public class SortedList<T extends Comparable<? super T>> extends ArrayList<T> {
	@Override
	public boolean add(T object) {
		int index = Collections.binarySearch(this, object);
		if (index < 0)
			index = ~index;
		super.add(index, object);
		return true;
	}
	
	public boolean addWithoutSort(T object) {
		return super.add(object);
	}
}
