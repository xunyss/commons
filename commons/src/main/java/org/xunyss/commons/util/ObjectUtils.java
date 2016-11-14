package org.xunyss.commons.util;

import java.lang.reflect.Array;

/**
 * 
 * @author XUNYSS
 */
public class ObjectUtils {

	private ObjectUtils() {
		/* cannot create instance */
	}

	/**
	 * 
	 * @param type
	 * @param len
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<T> type, int len) {
		return (T[]) Array.newInstance(type, len);
	}

	/**
	 * 
	 * @param array1
	 * @param array2
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] add(T[] array1, T[] array2) {
		int newlen = array1.length + array2.length;
		T[] newArray = (T[]) newArray(array1.getClass().getComponentType(), newlen);

		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);
		
		return newArray;
	}

	/**
	 * 
	 * @param array
	 * @param item
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] add(T[] array, T item) {
		int len = array != null ? array.length : 0;
		T[] newArray = (T[]) newArray(array.getClass().getComponentType(), len + 1);
		
		System.arraycopy(array, 0, newArray, 0, len);
		newArray[len] = item;

		return newArray;
	}

	/**
	 * 
	 * @param item
	 * @param array
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] add(T item, T[] array) {
		int len = array != null ? array.length : 0;
		T[] newArray = (T[]) newArray(array.getClass().getComponentType(), len + 1);

		newArray[0] = item;
		System.arraycopy(array, 0, newArray, 1, len);

		return newArray;
	}
}
