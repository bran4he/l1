package com.example.test;

import org.apache.log4j.Logger;

public class TestExample {
	private static Logger logger = Logger.getLogger(TestExample.class);

	public static void main(String[] args) {
		String usrHome = System.getProperty("user.home");
		System.out.println(usrHome);
		
		String str = "1243";
		System.out.println(str.charAt(0));
		
		String projectPath = TestExample.class.getResource("/").getPath();
		System.out.println(projectPath);
		
		System.setProperty("projectPath", projectPath.substring(0, projectPath.lastIndexOf("classes")-1));
		
		logger.info("this is a log test");
		logger.error("this is a log test");
		logger.debug("this is a log test");
		
//		int page;
//		System.out.println(page);
		
		
	}

}
