package io.xunyss.commons.lang;

import java.lang.reflect.Array;

/**
 *
 * @author XUNYSS
 */
public class ArrayUtils {
	
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	public static final String[] EMPTY_STRING_ARRAY = new String[0];
	
	
	/**
	 *
	 */
	private ArrayUtils() {
		/* cannot create instance */
	}
	
	/**
	 *
	 * @param object object
	 * @return {@code true} if object is array, {@code false} otherwise
	 */
	public static boolean isArray(final Object object) {
		return object != null && object.getClass().isArray();
	}
	
	/**
	 *
	 * @param array1 array1
	 * @param array2 array2
	 * @param <T> the component type of the array
	 * @return A new array
	 */
	@SafeVarargs
	public static <T> T[] add(final T[] array1, final T... array2) {
		int newArrayLength = array1.length + array2.length;
		@SuppressWarnings("unchecked")
		final T[] newArray = (T[]) Array.newInstance(array1.getClass().getComponentType(), newArrayLength);
		
		System.arraycopy(array1, 0, newArray, 0, array1.length);
		System.arraycopy(array2, 0, newArray, array1.length, array2.length);
		
		return newArray;
	}
	
	/**
	 *
	 * @param array array
	 * @param element the object to add
	 * @param <T> the component type of the array
	 * @return A new array
	 */
	public static <T> T[] add(final T[] array, final T element) {
		int arrayLength = array.length;
		@SuppressWarnings("unchecked")
		final T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
		
		System.arraycopy(array, 0, newArray, 0, arrayLength);
		newArray[arrayLength] = element;
		
		return newArray;
	}
	
	/**
	 *
	 * @param element the object to add
	 * @param array array
	 * @param <T> the component type of the array
	 * @return A new array
	 */
	@SafeVarargs
	public static <T> T[] add(final T element, final T... array) {
		int arrayLength = array.length;
		@SuppressWarnings("unchecked")
		final T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
		
		newArray[0] = element;
		System.arraycopy(array, 0, newArray, 1, arrayLength);
		
		return newArray;
	}
	
	/**
	 *
	 * @param array array
	 * @param <T> the component type of the array
	 * @return string
	 */
	public static <T> String toString(final T[] array) {
		if (array != null) {
			int arrayLength = array.length;
			StringBuilder arrayString = new StringBuilder();
			
			arrayString.append('[');
			for (int index = 0; index < arrayLength; index++) {
				if (index > 0) {
					arrayString.append(',').append(' ');
				}
				arrayString.append(array[index]);
			}
			return arrayString.append(']')
					.toString();
		}
		
		return null;
	}
}
