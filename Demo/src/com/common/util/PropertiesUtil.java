package com.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.example.test.PropTest;

public class PropertiesUtil {

	public static Map<String, String> read(String fileName){
       Properties properties = new Properties();
      
       Map<String, String> headerMap = new HashMap<String, String>();
       
       //InputStream ins = PropTest.class.getResourceAsStream("/demo.properties");
       InputStream ins = PropTest.class.getResourceAsStream(fileName);
      
       try {
		properties.load(ins);
		} catch (IOException e) {
			System.out.println("加载properties文件流错误");
			e.printStackTrace();
		}
       Set<Object> keySet = properties.keySet();
       System.out.println("===================加载properties");
       for (Object object : keySet) {
           System.out.println(object.toString()+":"+properties.getProperty(object.toString()));
    	   headerMap.put(object.toString(), properties.getProperty(object.toString()));
       }
       System.out.println("===================end");
       return headerMap;
	 }
	
	public static String getValue(String key, String fileName){
		 Properties props = new Properties();
	        try {
	        	InputStream ins = PropTest.class.getResourceAsStream(fileName);
	            props.load(ins);
	            String value = props.getProperty(key);
	            System.out.println(key +"键的值是："+ value);
	            return value;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	}
	
	public static Map<String, String> readIgnoreConetentLength(String fileName){
		Properties properties = new Properties();
		
		Map<String, String> headerMap = new HashMap<String, String>();
		
		InputStream ins = PropTest.class.getResourceAsStream(fileName);
		
		try {
			properties.load(ins);
		} catch (IOException e) {
			System.out.println("加载properties文件流错误");
			e.printStackTrace();
		}
		Set<Object> keySet = properties.keySet();
		
		System.out.println("===================加载properties");
		for (Object object : keySet) {
			System.out.println(object.toString()+":"+properties.getProperty(object.toString()));
			if(!"Content-Length".equals(object.toString())){
				headerMap.put(object.toString(), properties.getProperty(object.toString()));
			}
		}
		System.out.println("===================end");
		return headerMap;
	}
	
	public static void write(String filePath, String key, String value) throws FileNotFoundException{
		Properties prop = new Properties();
		File file = new File(PropertiesUtil.class.getClassLoader().getResource("").getPath()+filePath);
		try {
			System.out.println("文件地址：" + file.getCanonicalPath());
			if(!file.exists()){
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		InputStream ins = new FileInputStream(file);
		InputStream ins = PropTest.class.getResourceAsStream("/"+filePath);
		try {
			prop.load(ins);
			
			OutputStream out = new FileOutputStream(file);
			prop.setProperty(key, value);
			prop.store(out, "update");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
