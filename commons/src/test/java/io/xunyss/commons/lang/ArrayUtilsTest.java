package io.xunyss.commons.lang;

import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class ArrayUtilsTest {
	
	@Test
	public void getComponentType() {
		Class<?> clazz = ArrayUtilsTest.class.getComponentType();
		System.out.println(clazz);
		
		String string = new String();
		System.out.println(string.getClass());
		
		String[] array = new String[] {null, ""};
		System.out.println(array.getClass());
		System.out.println(array.getClass().getComponentType());
	}
	
	@Test
	public void isArray() {
		String[] array = {"a", "b", "c", "d"};
		int[] array2 = {1, 2, 3, 4, 5};
		System.out.println((Object)array2 instanceof int[]);
		System.out.println(ArrayUtils.isArray(array2));
	}
	
	@Test
	public void add() {
		String[] array = {"a", "b", "c", "d"};
		String[] newArray = ArrayUtils.add("e", array);
		System.out.println(ArrayUtils.toString(newArray));
	}
	
	@Test
	public void toArrayString() {
		String[] array = {"a", "b", "c", "d"};
		String string = ArrayUtils.toString(array);
		System.out.println(string);
	}
}
