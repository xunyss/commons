package io.xunyss.commons.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class RegularExpressionsTest {
	
	@Test
	public void delimiterSpace() {
		String string = "hello world  xunyss";
		String[] array = string.split(RegularExpressions.DELIMITER_SPACE);
		
		Assert.assertEquals(4, array.length);
		Assert.assertTrue(StringUtils.isEmpty(array[2]));
	}
	
	@Test
	public void delimiterSpaces() {
		String string = "hello world  xunyss";
		String[] array = string.split(RegularExpressions.DELIMITER_SPACES);
		
		Assert.assertEquals(3, array.length);
	}
	
	@Test
	public void delimiterCommaSpace() {
		String string = "item1, item2   item3 ,item4 ,,item5";
		String[] array = string.split(RegularExpressions.DELIMITER_COMMA_SPACE);
		
		Assert.assertEquals(5, array.length);
		Assert.assertEquals("item1", array[0]);
		Assert.assertEquals("item2", array[1]);
		Assert.assertEquals("item3", array[2]);
		Assert.assertEquals("item4", array[3]);
		Assert.assertEquals("item5", array[4]);
	}
}
