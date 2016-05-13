package com.jack.service.failed;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.task.FileMerge;
import com.task.FileWalk;
import com.task.JackAnalysis;

/**
 * 失败的例子，这个任务timer存活在servlet的生命周期里，servlet销毁时timer就被销毁了，我们不能保证servlet一直存活。
 * 
 * 最好的作法是将timer放在web项目容器的存活时期或者application的存活时期，这样能保证任务一直存在。
 * 
 * @author bran
 *
 */
//@WebServlet(name="timerServlet", urlPatterns="/TimerServlet", loadOnStartup=5)
public class TimerServlet extends HttpServlet {
/*	
	private static final long serialVersionUID = -1554019401422844174L;
	private static Logger logger = Logger.getLogger(TimerServlet.class);
	
	private static final int MAX_PAGE = 10;
	
	
	JobInterface jobAnalysis = null;
	JobInterface jobMv = null;
	JobInterface jobMerge = null;
	//servlet销毁时取消定时任务
	@Override
	public void destroy() {
		jobAnalysis.cancleJob();
		jobMv.cancleJob();
		jobMerge.cancleJob();
	}

	//servlet初始化时开始任务
	@Override
	public void init() throws ServletException {
		
		logger.info("TimerServlet start...");
		
		//分析网页
		//启动后30秒后运行，每隔6小时运行一次
		jobAnalysis = new JobInterface(0,1,0,1*60*60*1000,"JackAnalysis"){
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
		jobMv = new JobInterface(0,5,0,1*60*60*1000,"FileWalker"){
			@Override
			public void runJob() {
				FileWalk fw = new FileWalk();
				fw.walk();	
			}
		};
		jobMv.runJob();	
		
		//合并文件
		//启动后10分钟后运行，每隔6小时运行一次
		jobMerge = new JobInterface(0,10,0,1*60*60*1000,"FileMerge"){
			@Override
			public void runJob() {
				FileMerge fm = new FileMerge();
				fm.merge();	
			}
		};
		jobMerge.runJob();			

		
	}
*/
	
}
