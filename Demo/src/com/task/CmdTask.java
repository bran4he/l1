package com.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.example.test.PostTest;



public class CmdTask extends TimerTask {

	private static Logger logger = Logger.getLogger(CmdTask.class);
	private static String DEFAULT_PHONE = "15692132432";
	
	private String cmd;
	private String regEx;
	private String phone;
	
	public CmdTask(String cmd, String regEx,String phone) {
		super();
		this.cmd = cmd;
		this.regEx = regEx;
		this.phone = phone;
	}
	
	public CmdTask(String cmd, String regEx) {
		super();
		this.cmd = cmd;
		this.regEx = regEx;
		this.phone = DEFAULT_PHONE;
	}


	@Override
	public void run() {
		logger.info("checking login record...");
		Pattern pat = Pattern.compile(regEx);
		
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		InputStream is = null;
		BufferedReader br = null;
		String line = null;
		boolean online = false;
		Matcher matcher = null;
		
		int idx = 0;
		
		try {
			process = runtime.exec(cmd);
			is = process.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			while((line = br.readLine()) != null){
				
				if(idx == 0){
					idx++;
					if(line.contains("yuanyuan")){
						System.out.println("someone ever online");
						PostTest.send5(phone);
						System.out.println("ever:" + line);
					}
					
				}
				matcher = pat.matcher(line);
				
				if(matcher.find()){
					System.out.println("logged in :" + line);
					online = true;
				}
			}
			
			is.close();
			br.close();
			
			if(online){
				System.out.println("send message...");
				PostTest.send6(phone);
				
				System.out.println("someone is online");
			}else{
				System.out.println("all are offline");
			}
			
		} catch (IOException e) {
			logger.error("run cmd error...");
			e.printStackTrace();
			runtime.exit(-1);
		}
		
		
	}
	
	
	public static void main(String[] args) {
		CmdTask task = new CmdTask("last" ,"(bran)+.*(still)+");
		task.run();
	}	

}
