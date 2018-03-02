package io.xunyss.commons.io;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Unit tests for the FileUtils class.
 *
 * @author XUNYSS
 */
public class FileUtilsTest {

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
	public void getTempDirectory() {
		File tempDir = FileUtils.getTempDirectory();
		Assert.assertTrue(tempDir.isDirectory());
	}
	
	@Test
	public void copyFromURLToFile() throws IOException {
		URL src = ResourceUtils.getResource("/io/xunyss/commons/io/FileUtilsTestData.txt");
		File dst = new File(tmpRoot, "FileUtils_URL_Out.txt");
		
		int size = FileUtils.copy(src, dst);
		Assert.assertTrue("Destination file is not created", dst.isFile());
		Assert.assertEquals(new File(src.getPath()).length(), size);
		Assert.assertEquals(size, dst.length());
	}
	
	@Test
	public void readToString() throws IOException {
		File file;
		FileUtils.copy(
				ResourceUtils.getResource("/io/xunyss/commons/io/FileUtilsTestData.txt"),
				file = new File(tmpRoot, "FileUtils_URL_Out.txt")
		);
		
		String data = FileUtils.readToString(file);
		Assert.assertEquals(file.length(), data.length());
	}
	
	@Test
	public void writeString() throws IOException {
		String data = "This string will be saved as a file";
		File file = new File(tmpRoot, "FileUtils_String_Out.txt");
		
		FileUtils.writeString(file, data);
		Assert.assertTrue("Destination file is not created", file.isFile());
		Assert.assertEquals(data.length(), file.length());
	}
}
