package com.movie.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {

	public static void main(String[] args) {
		Timer timer = new Timer();
		
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				System.out.println("run my timer1");
//			}
//		}, 1000, 2000);	//delay 1000ms, period 2000ms
		
		
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				System.out.println("run my timer2");
//			}
//		}, new Date(new Date().getTime()+5000));	//在Date运行一次
		
//		timer.scheduleAtFixedRate(new TimerTask() {
//			@Override
//			public void run() {
//				System.out.println("run my timer2");
//			}
//		}, new Date(new Date().getTime()+5000), 10000);	//first time Date, period 10000ms
		
		
		//每天中午12点，晚上12点运行，即first time运行在此后12点，period为12 hours
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND,59);
		Date firstDate = c.getTime();	//今天的23:59:59开始
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println("timer tasl test run at " + new Date() );
			}
		}, firstDate, 12*60*60*1000);	//每一个小时执行一次
		
	}
}
