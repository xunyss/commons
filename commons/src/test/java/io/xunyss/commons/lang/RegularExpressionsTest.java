package io.xunyss.commons.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class RegularExpressionsTest {
	
	@Test
	public void delimiterCommaSpace() {
		String string = "item1, item2   item3 ,item4";
		String[] array = string.split(RegularExpressions.DELIMITER_COMMA_SPACE);
		
		Assert.assertEquals(array.length, 4);
		Assert.assertEquals(array[0], "item1");
		Assert.assertEquals(array[1], "item2");
		Assert.assertEquals(array[2], "item3");
		Assert.assertEquals(array[3], "item4");
	}
}
