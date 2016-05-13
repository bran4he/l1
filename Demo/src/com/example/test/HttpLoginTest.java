package com.example.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.common.util.CharacterUtil;
import com.common.util.PropertiesUtil;

public class HttpLoginTest {
	private static String cookies = "";
	private static String p2p_token = "";
	
	//先登陆获得cookie，存储为prop，只要session未过期可以重复使用
	
	
	//使用前cookie，再发送sms，可重复发送不需要登陆
	
	/**
	 * 
	 * get登陆页面，post登陆请求，保存登陆cookies
	 * 
	 * @param loginGetUrl	http://www.chuangjietz.com/login.shtml
	 * @param loginHeaders	chuangjietz_login.properties
	 * @param loginPostUrl	http://www.chuangjietz.com/login.shtml
	 * @param loginPostHeaders chuangjietz_login_post.properties
	 * @param loginSuccessCookieProp chuangjietz_cookie.properties
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void login(String loginGetUrl, 
								String loginGetHeadersProp,
								String loginPostUrl,
								String loginPostHeadersProp,
								String loginSuccessCookieProp) throws ClientProtocolException, IOException {
	    
		HttpGet get = new HttpGet(loginGetUrl);  
	      
	    HttpClient http = new DefaultHttpClient();
	    
	    //请求头
	    Map<String, String> headerMap = PropertiesUtil.readIgnoreConetentLength("/"+loginGetHeadersProp);
		Set<String> keySet = headerMap.keySet();
		for(String key : keySet){
			get.setHeader(key,headerMap.get(key));
		}
	    
	    HttpResponse response = http.execute(get);  
	    
	    //遍历返回头, 拿到cookies用于后面使用
	    System.out.println("================== get cookies====================");
	    Header[] headerCookie = response.getHeaders("Set-Cookie");
	    String cc ="";
	    for(Header h : headerCookie){
  			System.out.println(h.getName() + " : " + h.getValue());
  			cc += h.getValue().split(";")[0];
  			cc += ";";
	    }
	    cookies = cc.substring(0, cc.length()-1);
	    System.out.println(cookies);
  		System.out.println("===================end===================");
	    
  		
  		Header[] headerss = response.getAllHeaders();
  		for(Header h : headerss){
  			System.out.println(h.getName() +", " + h.getValue());
  		}
  		
  		
	    if (response.getStatusLine().getStatusCode() == 200) { 
	    	
	    	HttpEntity entity = response.getEntity();
//	    	 String result = CharacterUtil.decodeUnicode(EntityUtils.toString(entity, "utf-8"));
//	    	 String result = CharacterUtil.decodeUnicode(EntityUtils.toString(entity));
//	    	 String result = EntityUtils.toString(entity, "utf-8");
//	    	 String result = EntityUtils.toString(entity);
	        InputStream in = entity.getContent();
//	        System.out.println("请求登陆get返回内容");
	        String result = readStreamReturn(in);
	        System.out.println("result的内容："+result);
	        Document doc =Jsoup.parse(result);
	        p2p_token = doc.select("#p2p").val();
	        System.out.println("===============p2p_token===============:"+p2p_token);
	    }
	    
	    HttpClient http2 = new DefaultHttpClient();
	    
	    //登陆 
		HttpPost post = new HttpPost(loginPostUrl);  
		// 设置请求体
/*		
		nickname			test
		password			16D7A4FCA7442DDA3AD93C9A726597E4
		ucpasword			test1234
		chkboxautologin			true
		uri			user
		p2p			7698d5b24e36ae809f00adc08976a105
*/
		List<NameValuePair> params = new ArrayList<NameValuePair>();  
		params.add(new BasicNameValuePair("nickname", "test"));
		params.add(new BasicNameValuePair("password", "16D7A4FCA7442DDA3AD93C9A726597E4"));//md5计算
		params.add(new BasicNameValuePair("ucpasword", "test1234"));
		params.add(new BasicNameValuePair("uri", "user"));
		params.add(new BasicNameValuePair("chkboxautologin", "true"));
		params.add(new BasicNameValuePair("p2p", p2p_token));
		//在页面上id=p2p, name=p2p的value字段上，动态令牌
		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
		
		Map<String, String> headerMapLogin = PropertiesUtil.readIgnoreConetentLength("/"+loginPostHeadersProp);
		//设置登陆请求头
		Set<String> keySetLogin = headerMapLogin.keySet();
		for(String key : keySetLogin){
			post.setHeader(key,headerMapLogin.get(key));
		}
		//cookies += ";p_at_username=czo0OiJ0ZXN0Ijs%3D";
		post.setHeader("Cookie",cookies);
		
		HttpResponse responseLogin = http2.execute(post);  
		
		System.out.println("==================登陆后 返回的headers====================");
		//遍历返回头  
		Header[] headers = responseLogin.getAllHeaders();   
		for(Header h : headers){
			if("Set-Cookie".endsWith(h.getName())){
				System.out.println(h.getName() + " : " + h.getValue());
				String temp = h.getValue().split(";")[0];	//p_at_username\=czo1OiJ0ZXN0MiI7
				cookies += (";"+temp);
			}
		}  
		System.out.println("登陆后的cookies：" + cookies);
		//最终的cookie/token写入文件
		String path=System.getProperty("user.dir");
		PropertiesUtil.write(path+"/src/"+loginSuccessCookieProp, "Cookie", cookies);
		
		System.out.println("===================end===================");  
		
		if (responseLogin.getStatusLine().getStatusCode() == 200) {  
			HttpEntity entity = responseLogin.getEntity();  
			//获得返回流
			InputStream in = entity.getContent();
			System.out.println("登陆后输出内容");
			System.out.println(CharacterUtil.decodeUnicode(EntityUtils.toString(entity)));
			//readStream(in);
		}
	
	}
	
	public static void send(String loginSuccessCookieProp,
					String sendGetUrl,
					String sendGetHeadersProp,
					String sendPostUrl,
					String sendPostHeadersProp,
					String sendCookieAppend
			
			) throws ClientProtocolException, IOException {

		HttpGet get = new HttpGet(sendGetUrl);
		
		HttpClient http2 = new DefaultHttpClient();
		
		//已cookies访问发送短信页面
		
		
		//请求头
	    Map<String, String> headerMap = PropertiesUtil.readIgnoreConetentLength("/"+sendGetHeadersProp);
		Set<String> keySet = headerMap.keySet();
		for(String key : keySet){
			get.setHeader(key,headerMap.get(key));
		}
		
		String cookie = PropertiesUtil.getValue("Cookie", "/"+loginSuccessCookieProp);
		get.setHeader("Cookie", cookie);
		
		HttpResponse response = http2.execute(get);
		
		if (response.getStatusLine().getStatusCode() == 200) { 
	    	
	    	HttpEntity entity = response.getEntity();  
	        InputStream in = entity.getContent();
	        System.out.println("请求短信页面get返回内容:");
	        String result = readStreamReturn(in);
	        Document doc =Jsoup.parse(result);
	        p2p_token = doc.select("#p2p").val();
	        System.out.println("===============p2p_token:"+p2p_token);
	    }
		
		//请求发送短信页面没有set-cookie
		
		//post 发送短信请求
		
		HttpPost post = new HttpPost(sendPostUrl);  
		// 设置请求体
		List<NameValuePair> params = new ArrayList<NameValuePair>();  
		params.add(new BasicNameValuePair("newmobile", "18217185596"));
		params.add(new BasicNameValuePair("randCode", ""));
		params.add(new BasicNameValuePair("mobile", "15692132432"));
		params.add(new BasicNameValuePair("p2p", p2p_token));//令牌可以变动来实现无限发送
		//在页面上id=p2p, name=p2p的value字段上，动态令牌
		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
		
		Map<String, String> headerMapLogin = PropertiesUtil.readIgnoreConetentLength("/"+sendPostHeadersProp);
		Set<String> keySetLogin = headerMapLogin.keySet();
		for(String key : keySetLogin){
			post.setHeader(key,headerMapLogin.get(key));
		}
		
		
		//特殊cookie，如js加载的或者需要跳过验证的
		//cck_lasttime=1010888552019; cck_count=0; unix时间戳，用来表示两者间隔小于如60s
		//此处两个cookie是通过页面js设置的，不是服务器返回头设置
		
		long time = System.currentTimeMillis();
		String unixFlag = PropertiesUtil.getValue("cck_lasttime", "/"+sendCookieAppend);
		if(null == unixFlag){
			PropertiesUtil.write("/"+sendCookieAppend, "cck_lasttime", (time+100000)+"");
		}else{
			unixFlag = String.valueOf(Long.valueOf(unixFlag)+100000);
			String path=System.getProperty("user.dir");
			PropertiesUtil.write(path+"/src/"+sendCookieAppend, "cck_lasttime", unixFlag);
		}
		
		cookie += (";cck_lasttime=" + unixFlag);
		cookie += ";cck_count=0";
		
		System.out.println("==================请求短信post的cookies："+ cookie);
		
		post.setHeader("Cookie", cookie);
		
		HttpResponse responseLogin = http2.execute(post);  
		
		System.out.println("==================发送短信post 请求 返回的headers====================");
		//遍历返回头  
		Header[] headers = responseLogin.getAllHeaders();   
		for(Header h : headers){
			System.out.println(h.getName() + " : " + h.getValue());
		}  
		System.out.println("===================end===================");  
		
		if (responseLogin.getStatusLine().getStatusCode() == 200) {  
			HttpEntity entity = responseLogin.getEntity();  
			//获得返回流
			System.out.println("发送短信post后返回内容===================");
			System.out.println(CharacterUtil.decodeUnicode(EntityUtils.toString(entity)));
			System.out.println("end===================");
		}
	
	}
	
	
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
	    
		HttpGet get = new HttpGet("http://www.hn95tz.com/my/login.aspx");  
	      
	    HttpClient http = new DefaultHttpClient();
	    
	    Map<String, String> headerMap = PropertiesUtil.readIgnoreConetentLength("/hn95z.properties");
		Set<String> keySet = headerMap.keySet();
		for(String key : keySet){
			get.setHeader(key,headerMap.get(key));
		}
	    
	    HttpResponse response = http.execute(get);  
	    
	    //遍历返回头, 拿到cookies用于后面使用
	    System.out.println("================== get cookies====================");
//  		Header[] headers = response.getAllHeaders();
//  		for(Header h : headers){  
//  		} 
	    Header[] headerCookie = response.getHeaders("Set-Cookie");
	    String cc ="";
	    for(Header h : headerCookie){
  			System.out.println(h.getName() + " : " + h.getValue());
  			cc += h.getValue().split(";")[0];
  			cc += ";";
	    }
	    cookies = cc.substring(0, cc.length()-1);
	    System.out.println(cookies);
  		System.out.println("===================end===================");
	    
	    if (response.getStatusLine().getStatusCode() == 200) { 
	    	
	    	HttpEntity entity = response.getEntity();  
	    	
	    	ContentType contentType = ContentType.getOrDefault(entity);
	        Charset charset = contentType.getCharset();
	    	System.out.println("charset :" + charset);
	    	System.out.println(entity.getContentEncoding());
	    	System.out.println(entity.getContentType());
	        InputStream in = entity.getContent();
	        System.out.println("第一次访问输出内容");
	        //System.out.println(EntityUtils.toString(entity));
	        String result = readStreamReturn(in);
	        Document doc =Jsoup.parse(result);
	        p2p_token = doc.select("#p2p").val();
	        System.out.println("===============p2p_token===============:"+p2p_token);
	    }
	    
	    HttpClient http2 = new DefaultHttpClient();
	    
	    //登陆 POST /my/login.aspx HTTP/1.1
//	    nickname=test&
//	    password=CC03E747A6AFBBCBF8BE7668ACFEBEE5&
//		ucpasword=test123&
//		chkboxautologin=true&
//		uri=user&
//		p2p=b5c15ba0ad3442571cbbbc0ef7d38e36
	    
		HttpPost post = new HttpPost("http://www.hn95tz.com/my/login.aspx");  
//		String data = "nickname=test&password=CC03E747A6AFBBCBF8BE7668ACFEBEE5&ucpasword=test123&chkboxautologin=true&uri=user&p2p=b5c15ba0ad3442571cbbbc0ef7d38e36";// 构造请求数据
//		StringEntity myEntity = new StringEntity(data);
//		post.setEntity(myEntity);
		// 设置请求体
		List<NameValuePair> params = new ArrayList<NameValuePair>();  
		params.add(new BasicNameValuePair("nickname", "test2"));
		params.add(new BasicNameValuePair("password", "CC03E747A6AFBBCBF8BE7668ACFEBEE5"));//md5计算
		params.add(new BasicNameValuePair("ucpasword", "test123"));
		params.add(new BasicNameValuePair("chkboxautologin", "true"));
		params.add(new BasicNameValuePair("uri", "user"));
		params.add(new BasicNameValuePair("chkboxautologin", "true"));
		params.add(new BasicNameValuePair("p2p", p2p_token));
		//在页面上id=p2p, name=p2p的value字段上，动态令牌
		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
		
		Map<String, String> headerMapLogin = PropertiesUtil.readIgnoreConetentLength("/hn95z_login.properties");
		Set<String> keySetLogin = headerMapLogin.keySet();
		for(String key : keySetLogin){
			post.setHeader(key,headerMapLogin.get(key));
		}
		//cookies += ";p_at_username=czo0OiJ0ZXN0Ijs%3D";
		post.setHeader("Cookie",cookies);
		
		HttpResponse responseLogin = http2.execute(post);  
		
		System.out.println("第二次访问cookies:"+cookies);
		System.out.println("==================第二次访问 返回的headers====================");
		//遍历返回头  
		Header[] headers = responseLogin.getAllHeaders();   
		for(Header h : headers){
			if("Set-Cookie".equals(h.getName())){
				System.out.println(h.getName() + " : " + h.getValue());
				String temp = h.getValue().split(";")[0];	//p_at_username\=czo1OiJ0ZXN0MiI7
				cookies += (";"+temp);
			}
		}  
		//最终的cookie/token写入文件
		String path=System.getProperty("user.dir");
		PropertiesUtil.write(path+"/src/hn95z_send.properties", "Cookie", cookies);
		PropertiesUtil.write(path+"/src/p2p_token.properties", "p2p", p2p_token);
		
		
		System.out.println("===================end===================");  
		
		if (responseLogin.getStatusLine().getStatusCode() == 200) {  
			HttpEntity entity = responseLogin.getEntity();  
			//获得返回流
			InputStream in = entity.getContent();
			System.out.println("第二次访问输出内容");
			System.out.println(EntityUtils.toString(entity));
			//readStream(in);
		}
	}
	
	public static void readStream(InputStream in){
		System.out.println("++++++++++++++++response+++++++++++++++++++");
        String line = null;
        BufferedReader br = null;
        try {
        	br = new BufferedReader(new InputStreamReader(in,"utf-8"));
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
        System.out.println("+++++++++++++++++++++++++++++++++++");
	}
	
	public static String readStreamReturn(InputStream in){
        String line = null;
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
        	br = new BufferedReader(new InputStreamReader(in,"utf-8"));
			while((line = br.readLine())!= null){
				sb.append(line);
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
		return sb.toString();
	}
}
