package com.comm.test;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ApacheCommonsConfigurationTest {

	private static Configurations configs = null;
	
	static{
		configs = new Configurations();
	}
	
	public static void test2(){
		try {
			
			PropertiesConfiguration builder = configs.properties(new File("/test.properties"));
			
			
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static void test1() {
		
		
		try {
			Configuration config = configs.properties(new File("/test.properties"));
			
			String host = config.getString("Host");
			System.out.println(host);
			
			config.setProperty("Host", "test");
			config.addProperty("test", "hello");
			
			host = config.getString("Host");
			System.out.println(host);
			
			
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

}
