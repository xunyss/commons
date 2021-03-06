package io.xunyss.commons.lang;

import java.lang.reflect.Array;

/**
 * Array utilities.
 *
 * @author XUNYSS
 */
public final class ArrayUtils {
	
	/**
	 * An empty immutable {@code Object} array.
	 */
	public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	
	/**
	 * An empty immutable {@code String} array.
	 */
	public static final String[] EMPTY_STRING_ARRAY = new String[0];
	
	
	/**
	 * Constructor.
	 */
	private ArrayUtils() {
		// cannot create instance
	}
	
	/**
	 * Determine whether the given object is an array.
	 *
	 * @param object object
	 * @return {@code true} if object is array, {@code false} otherwise
	 */
	public static boolean isArray(final Object object) {
		return object != null && object.getClass().isArray();
	}
	
	/**
	 * Create a type-safe generic array.
	 *
	 * @param array the varargs array items, null allowed
	 * @param <T> the array's element type
	 * @return the array, not null unless a null array is passed in
	 */
	@SafeVarargs
	public static <T> T[] toArray(final T... array) {
		return array;
	}
	
	/**
	 *
	 * @param array
	 * @return
	 */
	public static Object[] nullToEmpty(final Object[] array) {
		if (getLength(array) == 0) {
			return EMPTY_OBJECT_ARRAY;
		}
		return array;
	}
	
	/**
	 *
	 * @param array
	 * @return
	 */
	public static String[] nullToEmpty(final String[] array) {
		if (getLength(array) == 0) {
			return EMPTY_STRING_ARRAY;
		}
		return array;
	}
	
	/**
	 * Add the given element at the end of the new array.
	 *
	 * @param array array
	 * @param element the object to add
	 * @param <T> the component type of the array
	 * @return a new array
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
	 * Add the elements of the given arrays into a new array.
	 *
	 * @param array1 array1
	 * @param array2 array2
	 * @param <T> the component type of the array
	 * @return a new array
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
	 * Add the elements of the given arrays into a new array.
	 *
	 * @param element the object to add
	 * @param array array
	 * @param <T> the component type of the array
	 * @return a new array
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
	 * Return the length of the specified array.
	 *
	 * @param array the array to retrieve the length from
	 * @return the length of the array, or {@code 0} if the array is {@code null}
	 */
	public static int getLength(final Object array) {
		if (array == null) {
			return 0;
		}
		return Array.getLength(array);
	}
	
//	/**
//	 * Output an array as a String.
//	 *
//	 * @param array array
//	 * @param <T> the component type of the array
//	 * @return a string representation of the array
//	 */
//	public static <T> String toString(final T[] array) {
//		if (array != null) {
//			int arrayLength = array.length;
//			StringBuilder arrayString = new StringBuilder();
//
//			arrayString.append('[');
//			for (int index = 0; index < arrayLength; index++) {
//				if (index > 0) {
//					arrayString.append(',').append(' ');
//				}
//				arrayString.append(array[index]);
//			}
//			return arrayString.append(']')
//					.toString();
//		}
//		return null;
//	}
	
	/**
	 * Output an array as a String.
	 *
	 * @param array object array
	 * @param <T> the component type of the array
	 * @return a string representation of the array
	 */
	public static <T> String toString(final T[] array) {
		return toStringInternal(array);
	}
	
	/**
	 * Output an array as a String.
	 *
	 * @param array byte array
	 * @return a string representation of the array
	 */
	public static String toString(final byte[] array) {
		return toStringInternal(array);
	}
	
	/**
	 * Output an array as a String.
	 *
	 * @param array short array
	 * @return a string representation of the array
	 */
	public static String toString(final short[] array) {
		return toStringInternal(array);
	}
	
	/**
	 * Output an array as a String.
	 *
	 * @param array int array
	 * @return a string representation of the array
	 */
	public static String toString(final int[] array) {
		return toStringInternal(array);
	}
	
	/**
	 * Output an array as a String.
	 *
	 * @param array long array
	 * @return a string representation of the array
	 */
	public static String toString(final long[] array) {
		return toStringInternal(array);
	}
	
	/**
	 * Output an array as a String.
	 *
	 * @param array float array
	 * @return a string representation of the array
	 */
	public static String toString(final float[] array) {
		return toStringInternal(array);
	}
	
	/**
	 * Output an array as a String.
	 *
	 * @param array double array
	 * @return a string representation of the array
	 */
	public static String toString(final double[] array) {
		return toStringInternal(array);
	}
	
	/**
	 * Output an array as a String.
	 *
	 * @param array char array
	 * @return a string representation of the array
	 */
	public static String toString(final char[] array) {
		return toStringInternal(array);
	}
	
	/**
	 * Output an array as a String.
	 *
	 * @param array boolean array
	 * @return a string representation of the array
	 */
	public static String toString(final boolean[] array) {
		return toStringInternal(array);
	}
	
	/**
	 * Output an array as a String using reflection.
	 *
	 * @param object array object
	 * @return a string representation of the array
	 */
	private static String toStringInternal(final Object object) {
//		if (isArray(object)) {
			int arrayLength = Array.getLength(object);
			StringBuilder arrayString = new StringBuilder();
			
			arrayString.append('[');
			for (int index = 0; index < arrayLength; index++) {
				if (index > 0) {
					arrayString.append(',').append(' ');
				}
				arrayString.append(Array.get(object, index));
			}
			return arrayString.append(']')
					.toString();
//		}
//		return null;
	}
}
