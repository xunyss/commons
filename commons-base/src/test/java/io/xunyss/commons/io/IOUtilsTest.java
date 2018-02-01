package io.xunyss.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

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
	
	@Test
	public void copyFromStringToFile() throws IOException {
		String src = "This string will be saved as a file";
		File dst = new File(tmpRoot, "IOUtils_Test_Out.txt");
		
		int size = IOUtils.copy(src, dst);
		Assert.assertTrue("Destination file is not created", dst.isFile());
		Assert.assertEquals(src.length(), size);
		Assert.assertEquals(size, dst.length());
	}
	
	@Test
	public void copyFromURLToFile() throws IOException {
		URL src = getClass().getResource("/io/xunyss/commons/io/IOUtilsTestDataFile.dat");
		File dst = new File(tmpRoot, "IOUtils_Test_Out.dat");
		
		int size = IOUtils.copy(src, dst);
		Assert.assertTrue("Destination file is not created", dst.isFile());
		Assert.assertEquals(new File(src.getPath()).length(), size);
		Assert.assertEquals(size, dst.length());
	}
}
