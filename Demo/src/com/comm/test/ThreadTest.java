package com.comm.test;

public class ThreadTest extends Thread{

	private int i;
	
	public static void main(String[] args) {
		for(int i=0; i<1000; i++){
			System.out.println(Thread.currentThread().getName()+" "+i);
			if(i == 20){
				new ThreadTest().start();
				new ThreadTest().start();
			}
		}
	}

	@Override
	public void run() {
		for(; i< 100; i++){
			System.out.println(getName() + " " + i);
		}
	}

}
