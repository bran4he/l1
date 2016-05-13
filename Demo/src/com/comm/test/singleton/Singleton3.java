package com.comm.test.singleton;

public class Singleton3 {

	private static Singleton3 instance = null;
	
	private Singleton3(){
	}
	
	public static Singleton3 getInstance(){
		if(instance == null){
			Singleton3 sc;
			
			synchronized (Singleton3.class) {
				sc = instance;
				if(sc == null){
					
					synchronized (Singleton3.class) {
						if(sc == null){
							sc = new Singleton3();
						}
					}
					instance = sc;
				}
			}
		}
		return instance;
	}

}
