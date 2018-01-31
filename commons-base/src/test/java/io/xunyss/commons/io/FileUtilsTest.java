package io.xunyss.commons.io;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the FileUtils class.
 *
 * @author XUNYSS
 */
public class FileUtilsTest {

	@Test
	public void getTempDirectory() {
		File tempDir = FileUtils.getTempDirectory();
		Assert.assertTrue(tempDir.isDirectory());
	}
}
