package com.comm.test;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleTest {

	public static void main(String[] args) {
		
		Locale loc = Locale.getDefault();
		ResourceBundle rb = ResourceBundle.getBundle("myres", loc);
		System.out.println(rb.getString("msg"));	
		
		Locale loc1 = new Locale("zh", "CN");
		ResourceBundle rb1 = ResourceBundle.getBundle("myres", loc1);
		System.out.println(rb1.getString("msg"));
		
		Locale loc2 = new Locale("en", "US");
		ResourceBundle rb2 = ResourceBundle.getBundle("myres", loc2);
		System.out.println(rb2.getString("msg"));
		
	}
}
