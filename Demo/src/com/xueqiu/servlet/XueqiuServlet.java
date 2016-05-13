package com.xueqiu.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xueqiu.bean.UserBean;
import com.xueqiu.service.XueqiuImgDownload;

@WebServlet(name="xueqiu", urlPatterns="/xueqiu")
public class XueqiuServlet extends HttpServlet {
	
	private static final long serialVersionUID = -2960056553570935392L;
	private static final String SITE = "http://xueqiu.com/";
	private static Logger logger = Logger.getLogger(XueqiuServlet.class);
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException {
		
		String userid = req.getParameter("userid");
		String url = SITE + userid;
		String photo ="";
		String orgPhoto ="";
		try {
			Document doc = Jsoup.connect(url).timeout(4000).get();
			String str = doc.getElementsByTag("script").get(10).html();
			
			//System.out.println(str);
			
			String jsonStr = str.substring(str.indexOf("{"), str.length());
			
			//System.out.println(jsonStr);
			
			ObjectMapper mapper = new ObjectMapper();  
			UserBean user = mapper.readValue(jsonStr, UserBean.class);
			
			String temp = user.getPhoto_domain();
			if(!temp.startsWith("http")){
				user.setPhoto_domain("http:"+temp);
			}
			logger.info(user.toString());
			//System.out.println(user.toString());
			
			photo = user.getPhoto_domain()+user.getProfile_image_url();
			//http://www.xx.com/ + community/20130/1358525438701-1358525563542.png!50x50.png
			orgPhoto = photo.substring(0, photo.indexOf("!"));
			
		} catch (IOException e) {
			System.out.println("获取图片地址错误，userid :" + userid);
			e.printStackTrace();
		}
		
		XueqiuImgDownload down = new XueqiuImgDownload(userid, orgPhoto);
		Thread thread = new Thread(down);
		thread.start();
		
		XueqiuImgDownload down2 = new XueqiuImgDownload(userid+"!50", photo);
		Thread thread2 = new Thread(down2);
		thread2.start();
		
		try {
			res.getWriter().println(photo+";"+orgPhoto);
		} catch (IOException e) {
			System.out.println("response写出数据出错");
			e.printStackTrace();
		}
		
	}
    
	public static void main(String[] args) {
		String site = "http://xueqiu.com/";
		String id ="8879830473";
		String url = site + id;
		try {
			Document doc = Jsoup.connect(url).timeout(4000).get();
			//System.out.println(doc.getElementsByTag("script").get(10).html());
			String str = doc.getElementsByTag("script").get(10).html();
			String jsonStr = str.substring(str.indexOf("{"), str.length());
			//System.out.println(jsonStr);
			ObjectMapper mapper = new ObjectMapper();  
			UserBean user = mapper.readValue(jsonStr, UserBean.class);
			//System.out.println(user.toString());
			/*			
			UserBean [id=8879830473, 
			photo_domain=http://xavatar.imedao.com/, 
			profile_image_url=community/20130/1358525438701-1358525563542.png!50x50.png, 
			province=广东, 
			city=广州]
			*/
			String userid = "8879830473";
			String orgPhoto = "http://xavatar.imedao.com/community/20130/1358525438701-1358525563542.png";
			File fold = new File("/home/bran/xueqiu");
			if(!fold.exists()){
				fold.mkdirs();
			}
			File file = new File(fold +File.separator+userid);
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
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
