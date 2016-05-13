package com.jack.service.failed;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;


public abstract class JobInterface {
	
	private static Logger logger = Logger.getLogger(JobInterface.class);
	
	private int hourp;
	private int minutesp;
	private int secondsp;
	private long periodMillionSecond;
	private String jobName;
	
	private Timer timer = null;
	
	/**
	 * 定时任务构造器
	 * @param hourp
	 * @param minutesp
	 * @param secondsp
	 * @param periodMillionSecond
	 * @param jobName
	 */
	public JobInterface(int hourp, int minutesp, int secondsp, long periodMillionSecond, String jobName) {
		super();
		this.hourp = hourp;
		this.minutesp = minutesp;
		this.secondsp = secondsp;
		this.periodMillionSecond = periodMillionSecond;
		this.jobName = jobName;
	}

	
	
	public JobInterface() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化timer
	 */
	public void init(){
		timer = new Timer();
	}

	/**
	 * 取消job
	 */
	public void cancleJob(){
		timer.cancel();
	}

	/**
	 * 初始化定时job，启动job
	 */
	public void setJob(){
		
		init();
		
		Calendar c = Calendar.getInstance();
		
		
		int hour = c.get(Calendar.HOUR_OF_DAY);
		c.set(Calendar.HOUR_OF_DAY, hour+hourp);
		
		int minutes = c.get(Calendar.MINUTE);
		c.set(Calendar.MINUTE,minutes+minutesp);
		
		int seconds = c.get(Calendar.SECOND);
		c.set(Calendar.SECOND,seconds+secondsp);
		
		Date firstDate = c.getTime();		//注意是服务器所在时间
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				logger.info(jobName +": timer task run at " + new Date() );
				
				runJob();
				
			}
		}, firstDate, periodMillionSecond);	//6小时执行一次
	}
	
	/**
	 * 需要重写的定时任务
	 */
	public abstract void runJob();
	
	
	public int getHourp() {
		return hourp;
	}

	public void setHourp(int hourp) {
		this.hourp = hourp;
	}

	public int getMinutesp() {
		return minutesp;
	}

	public void setMinutesp(int minutesp) {
		this.minutesp = minutesp;
	}

	public int getSecondsp() {
		return secondsp;
	}

	public void setSecondsp(int secondsp) {
		this.secondsp = secondsp;
	}

	public long getPeriodMillionSecond() {
		return periodMillionSecond;
	}

	public void setPeriodMillionSecond(long periodMillionSecond) {
		this.periodMillionSecond = periodMillionSecond;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
}
