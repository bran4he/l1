package com.example.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.common.util.PropertiesUtil;

public class HttpTest {

	public static void main(String[] args) throws ClientProtocolException, IOException, URISyntaxException {
		/*
		
		testGet();
		testGetParam();
		testPost();
		testGetParam2();
		*/
		testPostParam();
	}
	
	public static void testPost() throws ClientProtocolException, IOException{
	    HttpPost get = new HttpPost("http://104.160.36.48:8080/Demo/hello");  
	      
	    HttpClient http = new DefaultHttpClient();  
	    HttpResponse response = http.execute(get);  
	      
	    if (response.getStatusLine().getStatusCode() == 200) {  
	        HttpEntity entity = response.getEntity();  
	      
	        InputStream in = entity.getContent();  
	        readStream(in);
	        
	    }  
	}
	public static void testPostParam() throws ClientProtocolException, IOException{
		
		//http://www.hujingfax.com/HjfaxServicePc.asmx/GetSumCountByBigTypeId 
		//post json : {BigTypeId:'3'}
//		HttpPost post = new HttpPost("http://www.hujingfax.com/HjfaxServicePc.asmx/GetSumCountByBigTypeId");
		
		//构造post对象
		HttpClient http = new DefaultHttpClient();  
		HttpPost post = new HttpPost("http://www.hujingfax.com/weixin/HjfaxService.asmx/sendihuyisms");  
		
		
//		POST /weixin/HjfaxService.asmx/sendihuyisms HTTP/1.1
//		Host: www.hujingfax.com
//		Proxy-Connection: keep-alive
//		Content-Length: 78
//		Accept: application/json, text/javascript, */*; q=0.01
//		Origin: http://www.hujingfax.com
//		X-Requested-With: XMLHttpRequest
//		User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/45.0.2454.101 Chrome/45.0.2454.101 Safari/537.36
//		Content-Type: application/json;charset=UTF-8
//		Referer: http://www.hujingfax.com/weixin/zhuce.html
//		Accept-Encoding: gzip, deflate
//		Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4
//		
//		{mobile:'15692132432',content:''}18217185596
		//添加请求参数
//		String data = "{mobile:\'15692132432\',content:\'短信校验服务,您的注册验证码:12345123451234512345\'}";// 构造请求数据 有长度限制，可用作上线通知自定义内容， 6个中文,20个字母
		String data = "{mobile:\'18317108150\',content:\'短信校验服务,您的注册验证码:呵呵\'}";// 构造请求数据 有长度限制，可用作上线通知自定义内容， 6个中文,20个字母
		StringEntity myEntity = new StringEntity(data,ContentType.APPLICATION_JSON);//请求数据类型为json
		post.setEntity(myEntity);// 设置请求体
		
		//添加请求头信息
//		post.setHeader("Host", "www.hujingfax.com");
//		post.setHeader("Proxy-Connection", "keep-alive");
//		//post.setHeader("Content-Length", "78"); 去掉让服务器自己计算
//		//post.setHeader("Content-Length", ""+myEntity.getContentLength());
//		post.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
//		post.setHeader("Origin", "http://www.hujingfax.com");
//		post.setHeader("X-Requested-With", "XMLHttpRequest");
//		post.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/45.0.2454.101 Chrome/45.0.2454.101 Safari/537.36");
//		post.setHeader("Content-Type", "application/json;charset=UTF-8");
//		post.setHeader("Referer", "http://www.hujingfax.com/weixin/zhuce.html");
//		post.setHeader("Accept-Encoding", "gzip, deflate");
//		post.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4");
		
		Map<String, String> headerMap = PropertiesUtil.readIgnoreConetentLength("/demo.properties");
		Set<String> keySet = headerMap.keySet();
		for(String key : keySet){
			post.setHeader(key,headerMap.get(key));
		}
		
		HttpResponse response = http.execute(post);  
		
		//遍历返回头  
		Header[] headers = response.getAllHeaders();   
		for(Header h : headers){  
			System.out.println(h.getName() + " : " + h.getValue());   
		}  
		System.out.println("======================================");  
		
		if (response.getStatusLine().getStatusCode() == 200) {  
			HttpEntity entity = response.getEntity();  
			
			//从输入流读取返回信息  
			System.out.println("+++++++++"+entity.getContentType());  
			System.out.println("+++++++++"+entity.getContentEncoding());  
			System.out.println("+++++++++"+entity.getContentLength()); 
			//获得返回流
			InputStream in = entity.getContent();  
			readStream(in);
		}  
	}
	
	public static void testGetParam() throws ClientProtocolException, IOException{
		 // (1) 创建HttpGet实例  
	    HttpGet get = new HttpGet("http://104.160.36.48:8080/Demo/hello?name=1");  
	      
	    // (2) 使用HttpClient发送get请求，获得返回结果HttpResponse  
	    HttpClient http = new DefaultHttpClient();  
	    HttpResponse response = http.execute(get);  
	      
	    // (3) 读取返回结果  
	    if (response.getStatusLine().getStatusCode() == 200) {  
	        HttpEntity entity = response.getEntity();  
	      
	        InputStream in = entity.getContent();  
	        readStream(in);
	        
	    }  
	}
	public static void testGetParam2() throws ClientProtocolException, IOException, URISyntaxException{
		
		 List<NameValuePair> params = new ArrayList<NameValuePair>();  
		 params.add(new BasicNameValuePair("name", "jack"));  
		 String queryString = URLEncodedUtils.format(params, "utf-8"); 
		 
		 // (1) 创建HttpGet实例
		 URI uri = URIUtils.createURI("http", "104.160.36.48", 8080, "/Demo/hello", queryString, null);  
		 HttpGet get = new HttpGet(uri); 
		 
		// (2) 使用HttpClient发送get请求，获得返回结果HttpResponse  
		HttpClient http = new DefaultHttpClient();  
		HttpResponse response = http.execute(get);  
		
		// (3) 读取返回结果  
		if (response.getStatusLine().getStatusCode() == 200) {  
			HttpEntity entity = response.getEntity();  
			
			InputStream in = entity.getContent();  
			readStream(in);
			
		}  
	}
	
	public static void testGet() throws ClientProtocolException, IOException{
		 // (1) 创建HttpGet实例  
	    HttpGet get = new HttpGet("http://121.43.145.89/");  
	      
	    // (2) 使用HttpClient发送get请求，获得返回结果HttpResponse  
	    HttpClient http = new DefaultHttpClient();  
	    HttpResponse response = http.execute(get);  
	      
	    // (3) 读取返回结果  
	    if (response.getStatusLine().getStatusCode() == 200) {  
	        HttpEntity entity = response.getEntity();  
	      
	        InputStream in = entity.getContent();  
	        readStream(in);
	        
	    }  
	}
	
	public static void readStream(InputStream in){
        String line = null;
        BufferedReader br = null;
        try {
        	br = new BufferedReader(new InputStreamReader(in));
			while((line = br.readLine())!= null){
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
