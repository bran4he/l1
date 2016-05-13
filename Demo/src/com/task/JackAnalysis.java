package com.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.movie.bean.Jack;
import com.xueqiu.service.CommonDownload;
import com.xueqiu.service.SuffixFileFilter;

public class JackAnalysis extends TimerTask{
	
	private static Logger logger = Logger.getLogger(JackAnalysis.class);
	private static final String JACK_SAVE_PATH ="/home/bran/jack/jack";
	private static final String URL_BASE = "http://18jack.cc/free2-";
	
	private int page;	//从0开始
	
	public JackAnalysis() {
		super();
		this.page =0;	//默认为第一页
	}

	public JackAnalysis(int page) {
		super();
		this.page = page;
	}

	public void startAnalysis() {
		
		String URL_SITE = URL_BASE+page+".html";
		
		
		try {
			Document doc = Jsoup.connect(URL_SITE).timeout(5000).get();
			//System.out.println(doc.html());
			Element ulEele = doc.select(".mvlist_con").first();
			//System.out.println(ulEele.html());
			Elements lis = ulEele.select("li");
			//System.out.println(lis.size());
			int id =0;
			for(Element li : lis){
				id++;
				
				List<String> urlList = new ArrayList<String>();
				
				String urlPhoto = li.select("img").first().absUrl("src");
				String namePhoto = li.select("img").first().attr("alt")+".jpg";	//增加后缀
				
				Elements fancyboxs = li.select(".fancybox");
				for(Element ele : fancyboxs){
					urlList.add(ele.absUrl("href"));
				}
				//
				Jack jack = new Jack(id,namePhoto, urlPhoto, urlList);
				
				String fileName="";
				List<String> fileList = new ArrayList<String>();
				
				//先分析视频url得到的script
				for(String url : urlList){
					Document mvDOc = Jsoup.connect(url).timeout(6000).get();
					//System.out.println(mvDOc.html());
					String script = mvDOc.select("script").first().html();
					//file: "http://wpc.1D5EC.phicdn.net/031D5EC/jackcc//20160316/RCT-803_01.mp4.m3u8",
					int start = script.indexOf("http");
					int end = script.indexOf("m3u8");
					String file = script.substring(start, end+4);
					//System.out.println(file);
					fileList.add(file);
					if("".equals(fileName)){
						//如果有-分隔符
						if(file.indexOf("_") != -1){
							fileName = file.substring(file.lastIndexOf("/")+1, file.lastIndexOf("_"));
						}else{
							fileName = file.substring(file.lastIndexOf("/")+1, file.lastIndexOf("-")+4);
						}
						jack.setFileName(fileName);
						//System.out.println(fileName);
					}
				}
				jack.setFileList(fileList);
				//System.out.println(jack.toString());
				
				File folder = new File(JACK_SAVE_PATH + File.separator+ jack.getFileName());
				
				//是否存在mp4文件
				String[] mp4Files = folder.list(new SuffixFileFilter("mp4"));
				if(mp4Files != null && mp4Files.length ==1){
					logger.info(folder.getName()+"分析时有MP4文件，取消下载");
					continue; 	//退出此次循环，进行下一个folder
				}else{
					//下载图片和m3u8文件方法
					downloadAll(jack);
				}
				
				
				//测试只下一个
//				if(id >= 1){
//					break;
//				}
			}
			
			
		} catch (IOException e) {
			logger.error("connect to url error");
			e.printStackTrace();
		}
		
	}
	
	public static void downloadAll(Jack jack){
		
		//文件不能出现斜线	http://wpc.1D5EC.phicdn.net/031D5EC/jackcc//20160309/
		//http://wpc.1D5EC.phicdn.net/031D5EC/jackcc//20160309/MIDE-277_02.mp4.m3u8
		String str = jack.getFileList().get(0);
		int end = str.lastIndexOf("/");
		String urlPrefix = str.substring(0, end+1).replace("/", "&"); //斜线替换为&
		
		//保存jack信息
		CommonDownload jackDownload = new CommonDownload(
				urlPrefix+".readme", 
				null,
				JACK_SAVE_PATH + File.separator+ jack.getFileName());
		jackDownload.setSaveToFile(jack.toString());
		Thread j = new Thread(jackDownload);
		j.start();
		
		
		CommonDownload imgDownload = new CommonDownload(
				jack.getName(), 
				jack.getUrlPhoto(),
				JACK_SAVE_PATH + File.separator+ jack.getFileName());
		Thread t = new Thread(imgDownload);
		t.start();
		
		ExecutorService executor = Executors.newFixedThreadPool(4);
		
		for(int i =1; i<=jack.getFileList().size(); i++){
			String fileUrl = jack.getFileList().get(i-1);
			CommonDownload m3u8Download = new CommonDownload(
					fileUrl.substring(fileUrl.lastIndexOf("/")+1),
					fileUrl,
					JACK_SAVE_PATH + File.separator+ jack.getFileName()
					);
			Thread tt = new Thread(m3u8Download);
			executor.execute(tt);
		}
		
		executor.shutdown();
		
	}

	@Override
	public void run() {
		logger.info("start JackAnalysis Task @" + new Date());
		startAnalysis();
	}
}
