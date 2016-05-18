package com.comm.test;

public class RunnableTest implements Runnable{

	private int i;
	
	public static void main(String[] args) {

		for(int i=0; i<1000; i++){
			System.out.println(Thread.currentThread().getName() + "," + i);
			if(i == 20){
				RunnableTest rt = new RunnableTest();
				new Thread(rt).start();
				new Thread(rt).start();
			}
		}
	}

	@Override
	public void run() {
		for(; i< 100; i++){
			System.out.println(Thread.currentThread().getName()+ " " + i);
		}
	}

}
