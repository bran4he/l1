package com.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.xueqiu.service.CommonDownload;
import com.xueqiu.service.SuffixFileFilter;

public class FileWalk extends TimerTask{
	
	private static Logger logger = Logger.getLogger(FileWalk.class);

	public void walk() throws IOException {
		String savePath =System.getProperty("user.home") +"/jack/jack";
		
	    
		File root = new File(savePath);
		File[] folders = root.listFiles();
		
		if(folders != null){
			// 建立一个容量为10的固定尺寸的线程池
			ExecutorService executor = Executors.newFixedThreadPool(10);
			
			for(File folder : folders){
				//System.out.println(folder.getName());
				//遍历一个视频目录
				if(folder.isDirectory()){
					String[] readmeFiles = folder.list(new SuffixFileFilter("readme")); 
					
					//folder里没有一个readme文件
					if(readmeFiles == null || (readmeFiles != null && readmeFiles.length == 0)){
						logger.info(folder.getName()+"没有readme文件，取消下载");
						continue; 	//退出此次循环，进行下一个folder
					}
					
					//是否存在mp4文件
					String[] mp4Files = folder.list(new SuffixFileFilter("mp4"));
					
					if(mp4Files != null && mp4Files.length ==1){
						logger.info(folder.getName()+"遍历文件时有MP4文件，取消下载");
						
						//删除文件, 返回的是文件名
						String[] tsFiles = folder.list(new SuffixFileFilter("ts"));
						for(String ts:tsFiles){
							File temp = new File(folder.getCanonicalPath()+File.separator+ts);
							if(temp.exists()){
								logger.info(temp.getName()+"删除ts文件！");
								temp.delete();
							}
						}
						
						continue; 	//退出此次循环，进行下一个folder
					}
					
					
					logger.info(folder.getName()+"存在readme文件，开始下载");
					
					File[] targets = folder.listFiles();
					
					if(targets != null){
						for(File target : targets){
							
							//获得m3u8文件
							if(target.getName().endsWith("m3u8")){
								//System.out.println(target.getName());
								
								//读取文件解析
								BufferedReader br = null;
								String temp = null;
								try {
									br = new BufferedReader(new FileReader(target));
									while((temp = br.readLine()) != null){
										//如果行不是以#开头，则为下载地址
										if(!temp.startsWith("#")){
											//拼接下载地址送给commondownload下载
											String mvUrl=readmeFiles[0].substring(0, readmeFiles[0].length()-7).replace("&", "/")+temp;
											logger.info(folder.getName()+"mvUrl:"+mvUrl);
											
											CommonDownload mvdownload = new CommonDownload(
													temp, 
													mvUrl, 
													folder.getCanonicalPath());
											mvdownload.setBufferSize(500);//设置buffer500kb
											executor.execute(mvdownload);
										}
									}
								} catch (FileNotFoundException e) {
									logger.error("文件不存在"+target.getName());
									e.printStackTrace();
								} catch (IOException e) {
									logger.error("读取文件出错"+target.getName());
									e.printStackTrace();
								} finally{
									if(br != null){
										try {
											br.close();
										} catch (Exception e2) {
											e2.printStackTrace();
										}
									}
								}
								
								
							}
						}
						//保存下载了多少ts文件
					}
					
				}
			}
			
			
			executor.shutdown();
		}
		
	}

	@Override
	public void run() {
		logger.info("start FileWalk Task @" + new Date());
		try {
			walk();
		} catch (IOException e) {
			logger.info("删除ts文件时出错");
			e.printStackTrace();
		}
	}
}
