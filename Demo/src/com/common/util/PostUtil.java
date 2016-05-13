package com.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class PostUtil {

	/**
	 * 
	 * @param url "http://www.hujingfax.com/weixin/HjfaxService.asmx/sendihuyisms"
	 * @param jsonType true
	 * @param postData "{mobile:\'18217185596\',content:\'短信校验服务,您的注册验证码:1234\'}"
	 * @param propPath "/demo.properties"
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String post(String url, boolean jsonType, String postData, String propPath) throws ClientProtocolException, IOException{
		
//		if(!jsonType){
//			return "";
//		}
		
		//构造post对象
		HttpClient http = new DefaultHttpClient();
		//HttpPost post = new HttpPost("http://www.hujingfax.com/weixin/HjfaxService.asmx/sendihuyisms");  
		HttpPost post = new HttpPost(url);  
		
		if(jsonType){
			//添加请求参数
			//String data = "{mobile:\'18217185596\',content:\'短信校验服务,您的注册验证码:1234\'}";// 构造请求数据
			StringEntity myEntity = new StringEntity(postData,ContentType.APPLICATION_JSON);//请求数据类型为json
			post.setEntity(myEntity);// 设置请求体
		}else{
			List<NameValuePair> params = new ArrayList<NameValuePair>();  
			params.add(new BasicNameValuePair("phone", "15692132432"));
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		}
		
		//添加请求头信息
		//Map<String, String> headerMap = PropertiesUtil.readIgnoreConetentLength("/demo.properties");
		Map<String, String> headerMap = PropertiesUtil.readIgnoreConetentLength(propPath);
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
				System.out.println("第三次访问输出内容");
				return CharacterUtil.decodeUnicode(EntityUtils.toString(entity));
			}
			return "";
	}
	
	public static String readStream(InputStream in){
        StringBuffer line = new StringBuffer();
        String temp = null;
        BufferedReader br = null;
        try {
        	br = new BufferedReader(new InputStreamReader(in));
			while((temp = br.readLine())!= null){
				//System.out.println(temp);
				line.append(temp);
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
        return line.toString();
	}
	

	/**
	 * 
	 * @param url "http://www.changanweidai.com/user/phoneacces.html"
	 * @param propPath "/demo2.properties"
	 * @param postData <"phone", "15692132432">
	 * @return	response
	 */
	public static String simplePost(String url, String propPath, Map<String, String>postData){
		
		//构造post对象
		HttpClient http = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);  
		
		//构造post数据
		List<NameValuePair> params = new ArrayList<NameValuePair>(); 
		Set<String> dataKey = postData.keySet();
		for(String key : dataKey){
			params.add(new BasicNameValuePair(key, postData.get(key)));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//添加请求头信息
		Map<String, String> headerMap = PropertiesUtil.readIgnoreConetentLength(propPath);
		Set<String> keySet = headerMap.keySet();
		for(String key : keySet){
			post.setHeader(key,headerMap.get(key));
		}
		
		HttpResponse response;
		try {
			
//			InetSocketAddress socksaddr = new InetSocketAddress("104.160.36.48", 13140);
//	        HttpClientContext context = HttpClientContext.create();
//	        context.setAttribute("socks.address", socksaddr);
//	        response = http.execute(post, context);
			
			response = http.execute(post);
			//遍历返回头 
			Header[] headers = response.getAllHeaders();   
			System.out.println("====================response headers==================");  
			for(Header h : headers){  
				System.out.println(h.getName() + " : " + h.getValue());   
			}  
			System.out.println("======================================");  
			
			if (response.getStatusLine().getStatusCode() == 200) {  
				HttpEntity entity = response.getEntity();  
				
				//从输入流读取返回信息  
				System.out.println("getContentType+++++++++"+entity.getContentType());  
				System.out.println("getContentEncoding+++++++++"+entity.getContentEncoding());  
				System.out.println("getContentLength+++++++++"+entity.getContentLength()); 
				//获得返回
				System.out.println("返回结果======");
				//System.out.println("原始返回结果：" + EntityUtils.toString(entity));
				return CharacterUtil.decodeUnicode(EntityUtils.toString(entity));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	
	}
}
