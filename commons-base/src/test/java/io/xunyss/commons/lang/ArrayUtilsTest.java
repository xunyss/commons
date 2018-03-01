package io.xunyss.commons.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the ArrayUtils class.
 *
 * @author XUNYSS
 */
public class ArrayUtilsTest {
	
	@Test
	public void getComponentType() {
		Assert.assertNull(getClass().getComponentType());
		
		String[] array = new String[] {null, ""};
		Assert.assertEquals(String.class, array.getClass().getComponentType());
	}
	
	@Test
	public void isArray() {
		String[] stringArray = {"a", "b", "c", "d"};
		//noinspection ConstantConditions
		Assert.assertTrue(stringArray instanceof String[]);
		Assert.assertTrue(ArrayUtils.isArray(stringArray));
		
		int[] intArray = {1, 2, 3, 4, 5};
		//noinspection ConstantConditions
		Assert.assertTrue(intArray instanceof int[]);
		Assert.assertTrue(ArrayUtils.isArray(intArray));
	}
	
	@Test
	public void add() {
		Assert.assertArrayEquals(
				new String[] {"a", "b", "c"},
				ArrayUtils.add(new String[] {"a", "b"}, "c")
		);
		
		//noinspection RedundantArrayCreation
		Assert.assertArrayEquals(
				new String[] {"a", "b", "c", "d"},
				ArrayUtils.add(new String[] {"a", "b"}, new String[] {"c", "d"})
		);
		
		//noinspection RedundantArrayCreation
		Assert.assertArrayEquals(
				new String[] {"a", "b", "c"},
				ArrayUtils.add("a", new String[] {"b", "c"})
		);
	}
	
	@Test
	public void toObjectArrayString() {
		String[] stringArray = {"a", "b", "c", "d"};
		Assert.assertEquals("[a, b, c, d]", ArrayUtils.toString(stringArray));
	}
	
	@Test
	public void toPrimitiveArrayString() {
		byte[] byteArray = {1, 2};
		Assert.assertEquals("[1, 2]", ArrayUtils.toString(byteArray));
		
		short[] shortArray = {1, 2, 3};
		Assert.assertEquals("[1, 2, 3]", ArrayUtils.toString(shortArray));
		
		int[] intArray = {1, 2, 3, 4};
		Assert.assertEquals("[1, 2, 3, 4]", ArrayUtils.toString(intArray));
		
		long[] longArray = {1, 2, 3, 4, 5};
		Assert.assertEquals("[1, 2, 3, 4, 5]", ArrayUtils.toString(longArray));
		
		float[] floatArray = {1.1F, 2.2F, 3.3F};
		Assert.assertEquals("[1.1, 2.2, 3.3]", ArrayUtils.toString(floatArray));
		
		double[] doubleArray = {6.6D, 7.7D, 8.8D};
		Assert.assertEquals("[6.6, 7.7, 8.8]", ArrayUtils.toString(doubleArray));
		
		char[] charArray = {'a', 'b'};
		Assert.assertEquals("[a, b]", ArrayUtils.toString(charArray));
		
		boolean[] booleanArray = {true, false};
		Assert.assertEquals("[true, false]", ArrayUtils.toString(booleanArray));
	}
	
	@Test
	public void toEmptyArrayString() {
		Object[] array = ArrayUtils.EMPTY_OBJECT_ARRAY;
		Assert.assertEquals("[]", ArrayUtils.toString(array));
	}
}
