package com.jack.service;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;

import com.task.CmdTask;
import com.task.FileMerge;
import com.task.FileWalk;
import com.task.JackAnalysis;

@WebListener
public class MyTimerContextListener implements ServletContextListener {
	
	private static Logger logger = Logger.getLogger(MyTimerContextListener.class);
	private static final long PERIOD = 5*60*1000;
	public static final String PROJECT_PATH = "projectPath";
	
	private Timer timer = new Timer();
	
    public MyTimerContextListener() {
    }

    public void contextInitialized(ServletContextEvent evt)  { 
    	logger.info("TimerConetxtListener initialized...");
    	
    	String regEx = "(yuanyuan)+.*(still)+";	//(bran)+.*(still)+
    	//last | grep ppp  -> username * still在线
    	TimerTask cmdTask = new CmdTask("last", regEx);
    	
    	this.timer.schedule(cmdTask, 60000L, PERIOD);
    	
    	//设置系统属性给log4j使用，用于生成日志到web-inf目录
//    	String log4jdir = evt.getServletContext().getRealPath("/");
//    	System.setProperty(PROJECT_PATH, log4jdir);
//    	System.out.println(System.getProperty(PROJECT_PATH));
    	//log4j.properties在listener之前加载，导致系统属性设置失败
/*    	
    	TimerTask jackAnalysis = new JackAnalysis();
    	this.timer.scheduleAtFixedRate(jackAnalysis, 30000, PERIOD);
    	
    	TimerTask fileWalk = new FileWalk();
    	this.timer.scheduleAtFixedRate(fileWalk, 60000, PERIOD);
    	
    	
    	TimerTask fileMerge = new FileMerge();
    	this.timer.scheduleAtFixedRate(fileMerge, 90000, PERIOD);
    	
    	*/
    }

    public void contextDestroyed(ServletContextEvent evt)  {
    	logger.info("TimerConetxtListener destroyed...");
    	this.timer.cancel();
//    	System.getProperties().remove(PROJECT_PATH);	//移除系统属性
    }
    
}
