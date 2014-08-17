package test.com.thesis.helpers;

import com.thesis.helpers.ValidationHelper;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class ValidationHelperTestClass extends junit.framework.TestCase {

	@org.junit.Before
	public void setUp() throws Exception {

	}

	@org.junit.After
	public void tearDown() throws Exception {

	}

	@org.junit.Test
	public void testIsFilePathValid() throws Exception {
		assertTrue(ValidationHelper.isFilePathValid("master.md"));
	}
}