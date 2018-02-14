package io.xunyss.commons.lang;

public class ClassLoadingTestClass {
	
	static String string;
	
	static {
		// occur NullPointerException
		string.length();
	}
}
