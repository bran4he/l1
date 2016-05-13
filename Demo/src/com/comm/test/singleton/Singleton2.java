package com.comm.test.singleton;

public class Singleton2 {

	private static Singleton2 instance;
	
	private Singleton2(){
	}
	
	public static Singleton2 getInstance(){
		if(null == instance){
			return new Singleton2();
		}else{
			return instance;
		}
	}
}
