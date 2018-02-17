package io.xunyss.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Unit tests for the IOUtils class.
 *
 * @author XUNYSS
 */
public class IOUtilsTest {
	
	@Rule
	public final TemporaryFolder tmpDir = new TemporaryFolder();
	
	private File tmpRoot = null;
	
	
	@Before
	public void setup() {
		// always tmpRoot is 'true'
		if (tmpRoot == null) {
			tmpRoot = tmpDir.getRoot();
		}
	}
	
	@Test
	public void copyFromReaderToWriter() throws IOException {
		String content = "이것은 한글 입니다";
		Reader src = new StringReader(content);
		Writer dst = new StringWriter();
		
		int size = IOUtils.copy(src, dst);
		Assert.assertEquals(content.length(), size);
	}
}
