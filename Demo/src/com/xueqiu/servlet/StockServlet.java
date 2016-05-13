package com.xueqiu.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@WebServlet("/StockServlet")
public class StockServlet extends HttpServlet {
	private static final long serialVersionUID = 1822052925052805820L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
	}
	
	public static void main(String[] args){
		
		String urlbase = "http://182.92.48.186/sac/login/login_lb.htm";
		String loginUrl ="";
		String codeUrl="http://182.92.48.186/sac/manage/yzm.jsp";
		try {
			Document doc = Jsoup.connect(urlbase).timeout(3000).get();
//			System.out.println(doc.html());
			Elements eles = doc.select("a");
//			for(Element ele :eles){
//				System.out.println(ele.toString());
//				System.out.println(ele.absUrl("href"));
////				http://182.92.48.186/sac/login/login_qt.htm?jsessionid=4f492d398ed5d896&kslb=1
////				http://182.92.48.186/sac/login/login_qt.htm?jsessionid=e4cd65d3ab5a8934&kslb=1
////				http://182.92.48.186/sac/login/login_qt.htm?jsessionid=0c1e7694b641acb5&kslb=4
//			}
			
			loginUrl = eles.get(1).absUrl("href");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
        URL url;
        URL codeu;
        InputStream is = null;
        FileOutputStream fos = null;
		try {
			url = new URL(loginUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			
	        connection.setDoOutput(true);  
	        connection.setDoInput(true);  
	        connection.setRequestMethod("GET");  
	        connection.setUseCaches(false);  
	        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
	        connection.setRequestProperty("Accept-Encoding","gzip, deflate");  
	        connection.setRequestProperty("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3");  
	        connection.setRequestProperty("Connection","keep-alive");  
	        connection.setRequestProperty("Cookie",
	        		"Hm_lvt_ca4c14789976af32ed421eba13cd0981=1457158214,1457704867,1457963263;"
	        		+ "Hm_lpvt_ca4c14789976af32ed421eba13cd0981=1457964698;"
	        		+ "JSESSIONID=3C8F1EAC91951B059E00D85920C5B93C;"
	        		+ "Hm_lvt_ca4c14789976af32ed421eba13cd0981=1457158214;"
	        		+ " Hm_lpvt_ca4c14789976af32ed421eba13cd0981=1457963679");
	        
	        connection.setRequestProperty("Host","182.92.48.186");  
	        connection.setRequestProperty("Referer",urlbase);  
	        connection.setRequestProperty("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:39.0) Gecko/20100101 Firefox/39.0");  
	        
	        codeu = new URL(codeUrl);
	        HttpURLConnection connu = (HttpURLConnection) codeu.openConnection(); 
	        
	        connu.setDoOutput(true);  
	        connu.setDoInput(true);  
	        connu.setRequestMethod("GET");  
	        connu.setUseCaches(false);  
	        connu.setRequestProperty("Accept", "image/png,image/*;q=0.8,*/*;q=0.5");  
	        connu.setRequestProperty("Accept-Encoding","gzip, deflate");  
	        connu.setRequestProperty("Accept-Language","zh-CN,en-US;q=0.7,en;q=0.3");  
	        connu.setRequestProperty("Connection","keep-alive");  
	        connu.setRequestProperty("Cookie",
	        		"JSESSIONID=3C8F1EAC91951B059E00D85920C5B93C;"
	        		+ "Hm_lvt_ca4c14789976af32ed421eba13cd0981=1457158214;"
	        		+ "Hm_lpvt_ca4c14789976af32ed421eba13cd0981=1457963679");
	        
	        connu.setRequestProperty("Host","182.92.48.186");  
	        connu.setRequestProperty("Referer",loginUrl);  
	        connu.setRequestProperty("User-Agent","Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:39.0) Gecko/20100101 Firefox/39.0");  
	        
	        is = connu.getInputStream();
	        
	        fos = new FileOutputStream(new File("/home/bran/code.jpeg"));
	        
	        int len =0;
	        byte[] buffer = new byte[1024];
	        while((len = is.read(buffer)) != -1){
	        	fos.write(buffer, 0, len);
	        }
	        
	        
//	        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
//	        
//	        
//	        // 其中的memberName和password可通过fiddler来抓取  
//	        out.write("msg=&tip=&flag=&jsessionid=e4cd65d3ab5a8934&kslb=1&username=hangxiaowei&password=hw19891027&yzm=6293");   
//	        out.flush();  
//	        out.close();  
//	  
//	        connection.connect();  
//	          
//	        InputStream in = connection.getInputStream();  
//	  
//	        StringBuilder retStr = new StringBuilder();  
//	        BufferedReader br = new BufferedReader(new InputStreamReader(in));  
//	        String temp = br.readLine();  
//	        while (temp != null) {  
//	            retStr.append(temp);  
//	            temp = br.readLine();  
//	        }  
//	        br.close();  
//	        in.close();  
//	  
//	        System.out.println(retStr);  
//	        for(Entry<String, List<String>> header: connection.getHeaderFields().entrySet()){
//	             System.out.println(header.getKey() +" " + header.getValue());    
//	        } 
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			if(is != null){
				try {
					is.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			if(fos != null){
				try {
					fos.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		}
  

	}

}
