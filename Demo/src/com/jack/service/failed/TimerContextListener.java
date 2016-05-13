package com.jack.service.failed;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.jack.task.FileMerge;
import com.jack.task.FileWalk;
import com.jack.task.JackAnalysis;

//又一个失败的例子，原因是没有搞清楚timertask的工作机制
//@WebListener
public class TimerContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	/*
	private static Logger logger = Logger.getLogger(TimerContextListener.class);
	
	private static final int MAX_PAGE = 10;
	
	
	JobInterface jobAnalysis = null;
	JobInterface jobMv = null;
	JobInterface jobMerge = null;
	
    *//**
     * Default constructor. 
     *//*
    public TimerContextListener() {
    }

	*//**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     *//*
    public void contextInitialized(ServletContextEvent evt)  { 

		
		logger.info("TimerConetxtListener start...");
		
		//分析网页
		//启动后30秒后运行，每隔6小时运行一次
		jobAnalysis = new JobInterface(0,1,0,3*60*1000,"JackAnalysis"){
			@Override
			public void runJob() {
				for(int i=0; i< MAX_PAGE; i++){
					JackAnalysis ja = new JackAnalysis(i);
					ja.startAnalysis();
				}
			}
		};
		jobAnalysis.runJob();	
		
		//下载ts视频
		//启动后5分钟后运行，每隔6小时运行一次
		jobMv = new JobInterface(0,5,0,3*60*1000,"FileWalker"){
			@Override
			public void runJob() {
				FileWalk fw = new FileWalk();
				fw.walk();	
			}
		};
		jobMv.runJob();	
		
		//合并文件
		//启动后10分钟后运行，每隔6小时运行一次
		jobMerge = new JobInterface(0,10,0,3*60*1000,"FileMerge"){
			@Override
			public void runJob() {
				FileMerge fm = new FileMerge();
				fm.merge();	
			}
		};
		jobMerge.runJob();			
	
    }

	*//**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     *//*
    public void contextDestroyed(ServletContextEvent evt)  {
    	jobAnalysis.cancleJob();
		jobMv.cancleJob();
		jobMerge.cancleJob();
    }
	*/
}
