package com.example.test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

	public static void main(String[] args) throws InterruptedException {
		//一个timer可以运行多个定时任务
		Timer t = new Timer();
		
		t.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("1秒");
			}
		}, 0, 1000);
		
		t.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("3秒");
			}
		}, 0, 3000);
		
		for(int i=0; i<10; i++){
			Thread.sleep(1000);
			System.out.println((i+1)+"time");
		}
		t.cancel();
	}

}
