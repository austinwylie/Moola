package com.hci.moola.model;

import java.util.HashMap;

public class PostOffice {

	private static HashMap<String, Object> mMessageMap = new HashMap<String, Object>();

	/**
	 * Grabs a message for the given activity/fragment if any were stored for it. Called from the destination described 
	 * in putMessage. The object reference is removed upon retrieval.
	 * 
	 * @param recipient
	 *            should be current activity/fragment
	 * @return null if no message
	 */
	public static Object getMessage(Class<?> recipient) {
		return mMessageMap.remove(recipient.getName());
	}

	/**
	 * Stores a message for the destination activity/fragment to grab.
	 * 
	 * @param destination
	 *            the activity/fragment that you are sending the message to
	 * @param message
	 */
	public static void putMessage(Class<?> destination, Object message) {
		mMessageMap.put(destination.getName(), message);
	}
}
