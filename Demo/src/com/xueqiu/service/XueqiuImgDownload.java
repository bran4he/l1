package com.xueqiu.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XueqiuImgDownload implements Runnable {

	private String userid;
	private String orgPhoto;
	
	public String getOrgPhoto() {
		return orgPhoto;
	}

	public void setOrgPhoto(String orgPhoto) {
		this.orgPhoto = orgPhoto;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public XueqiuImgDownload(String userid, String orgPhoto) {
		super();
		this.userid = userid;
		this.orgPhoto = orgPhoto;
	}

	@Override
	public void run() {
		File fold = new File("/home/bran/xueqiu");
		if(!fold.exists()){
			fold.mkdirs();
		}
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYY-MM-dd");
		String prefix = sdf.format(date);
		
		File file = new File(fold +File.separator+prefix+"-"+userid);
		if(!file.exists()){
			
			URL photoUrl;
			try {
				photoUrl = new URL(orgPhoto);
				URLConnection conn = photoUrl.openConnection();
				conn.setConnectTimeout(5000);
				InputStream ins = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[10 * 1024];
				int len =0;
				while((len = ins.read(buffer)) != -1){
					fos.write(buffer, 0, len);
				}
			} catch (MalformedURLException e) {
				System.out.println("url出错　："+orgPhoto);
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("下载图片出错："+ file.getName());
				e.printStackTrace();
			}
		}
	}

}
