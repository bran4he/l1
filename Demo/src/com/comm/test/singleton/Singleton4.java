package com.comm.test.singleton;

public class Singleton4 {

	private static class Singleton4Instance{
		private static final Singleton4 instance = new Singleton4();
	}
	
	private Singleton4(){
		
	}
	
	public Singleton4 getInstance(){
		return Singleton4Instance.instance;
	}
}
