package io.xunyss.commons.lang;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
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
		Assert.assertTrue(stringArray instanceof String[]);
		Assert.assertTrue(ArrayUtils.isArray(stringArray));
		
		int[] intArray = {1, 2, 3, 4, 5};
		Assert.assertTrue(intArray instanceof int[]);
		Assert.assertTrue(ArrayUtils.isArray(intArray));
	}
	
	@Test
	public void add() {
		Assert.assertArrayEquals(
				new String[] {"a", "b", "c"},
				ArrayUtils.add(new String[] {"a", "b"}, "c")
		);
		
		Assert.assertArrayEquals(
				new String[] {"a", "b", "c"},
				ArrayUtils.add("a", new String[] {"b", "c"})
		);
		
		Assert.assertArrayEquals(
				new String[] {"a", "b", "c", "d"},
				ArrayUtils.add(new String[] {"a", "b"}, new String[] {"c", "d"})
		);
	}
	
	@Test
	public void toArrayString() {
		String[] array = {"a", "b", "c", "d"};
		Assert.assertEquals("[a, b, c, d]", ArrayUtils.toString(array));
	}
	
	
	@Test
	public void aaa() {
		List<String> list1 = new ArrayList<>();  list1.add("hello"); list1.add("world");
		List<Integer> list2 = new ArrayList<>(); list2.add(1);       list2.add(2);
	}
}
