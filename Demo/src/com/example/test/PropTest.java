package com.example.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import com.common.util.PropertiesUtil;

public class PropTest {

	public static void main(String[] args) throws IOException {
		//read();
		String path=System.getProperty("user.dir");
		System.out.println(path);
		
		PropertiesUtil.write(path+"/src/p2p_token.properties", "sdfs", "323");
		
		
	}

	public static void read() throws IOException{
	       Properties properties = new Properties();
	      
	       
	       InputStream ins = PropTest.class.getResourceAsStream("/demo.properties");
	      
	       properties.load(ins);
	      
	       Set<Object> keySet = properties.keySet();
	      
	       for (Object object : keySet) {
	           System.out.println(object.toString()+":"+properties.getProperty(object.toString()));
	       }
	    }
}
