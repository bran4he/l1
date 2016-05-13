package com.example.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.common.util.CharacterUtil;
import com.common.util.PostUtil;
import com.common.util.PropertiesUtil;

public class PostTest {
	public static void main(String[] args) throws ClientProtocolException, IOException {
//		send1("15692132432");
//		send2("15692132432");
//		send3("15692132432");
//		send4("15692132432");
//		send5("15692132432");
//		send5("15692132432");
		send6("15692132432");
	}
	
	public static void send4(String phone){
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("phone", phone);
		String result = PostUtil.simplePost(
				"http://vpfinance.cn/registration/sendSms12","/vpfinance.properties", postData);
		System.out.println("测试返回结果：" +result);
	}
	
	public static void send1(String phone){
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("phone", phone);
		String result = PostUtil.simplePost(
				"http://www.changanweidai.com/user/phoneacces.html","/demo2.properties", postData);
		System.out.println("测试返回结果：" +result);
	}
	
	public static void send2(String phone){
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("cellphone", phone);
		String result = PostUtil.simplePost(
				"http://www.jianshuncf.com/Member/common/sendphone/","/jianshuncf.properties", postData);
		System.out.println("测试返回结果：" +result);
	}
	
	public static void send3(String phone){
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("u", phone);
		String result = PostUtil.simplePost(
				"http://www.mzcf88.com/user/common/dogetpass/","/mzcf88.properties", postData);
		System.out.println("测试返回结果：" +result);
	}
	
	public static void send5(String phone) throws ParseException, IOException{

		 //(1) 创建HttpGet实例  
	    HttpGet get = new HttpGet("http://www.hewand.com/regist.html");  
	    
	    //默认不让重定向 这样就能拿到Location头了
	    HttpParams params = new BasicHttpParams();
       params.setParameter("http.protocol.handle-redirects", false); 
       get.setParams(params);
	    
	    //(2) 使用HttpClient发送get请求
	    HttpClient http = new DefaultHttpClient();
	    
	    //(3)添加请求头信息
	    Map<String, String> headerMap = PropertiesUtil.readIgnoreConetentLength("/hwand_register_get.properties");
	    Set<String> keySet = headerMap.keySet();
	    for(String key : keySet){
	    	get.setHeader(key,headerMap.get(key));
	    }
	    
	    //发送请求
	    HttpResponse response = http.execute(get);  
	    
	    //（4）获取返回头
	    System.out.println("======================================response headers=====================================");
	    Header[] headers = response.getAllHeaders();
	    for(Header header : headers){
	    	System.out.println(header.getName()+"," + header.getValue());
	    	if("Location".equals(header.getName()) 
	    			|| "Set-Cookie".equals(header.getName())){
	    		PropertiesUtil.write("hwand_register_location&cookie.properties",header.getName(), header.getValue());
	    	}
	    	
	    }
	    System.out.println("======================================response headers end=====================================");
	    
	    //(5) 读取返回体  
	    System.out.println("======================================response body=====================================");
	    if (response.getStatusLine().getStatusCode() == 200) {
	        HttpEntity entity = response.getEntity();  
//	        System.out.println(EntityUtils.toString(entity));
	        System.out.println(CharacterUtil.decodeUnicode(EntityUtils.toString(entity)));
	    }  
	    System.out.println("======================================response body end=====================================");
		
	    //获得cookie和posturl
	    String cookies = PropertiesUtil.getValue("Set-Cookie", "/hwand_register_location&cookie.properties");
	    PropertiesUtil.getValue("Location", "/hwand_register_location&cookie.properties");
	    String cookie = "";
	    String[] array = cookies.split(";");
	    for(String a : array){
	    	System.out.println("++++++++++++++"+a);
	    	if(a.indexOf("JSESSIONID") != -1){
	    		cookie = a;
	    		System.out.println("解析得到的cookies" + cookie);
	    	}
	    }
	    
	    /*
	    form_hf_0=&
	    isInvite=0&
	    txtUserPhone=15692132432&
	    passwordStrengthPanel%3AtxtPassword=test&
	    txtVerifycode=&
	    isSuccessInput=1&
	    txtIntroducer=&
	    lnkChange=1
	     */
	    
	    PropertiesUtil.write("hwand_register_send.properties", "Cookie", cookie);
	    
	    Map<String, String> postData = new HashMap<String, String>();
		postData.put("form_hf_0", "");
		postData.put("isInvite", "0");
		postData.put("txtUserPhone", phone);
		postData.put("passwordStrengthPanel%3AtxtPassword", "test");
		postData.put("txtVerifycode", "");
		postData.put("isSuccessInput", "1");
		postData.put("txtIntroducer", "");
		postData.put("lnkChange", "1");
		PostUtil.simplePost(
				"http://www.hewand.com/regist.html?0-1.IBehaviorListener.0-body-form-lnkChange",
				"/hwand_register_send.properties", 
				postData);
		//System.out.println("测试返回结果：" +result);
	
	}
	
	/**
	 * 无需设置cookie，无限次数
	 * @param phone
	 */
	public static void send6(String phone){

		//构造post对象
		HttpClient http = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.huangjd.com/core/phone_ajax.php");  
		
		//构造post数据
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//nub=15692132432&phoneajax=&valicode=
		params.add(new BasicNameValuePair("nub", phone));
		params.add(new BasicNameValuePair("phoneajax", ""));
		params.add(new BasicNameValuePair("valicode", ""));
		try {
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//添加请求头信息
		Map<String, String> headerMap = PropertiesUtil.readIgnoreConetentLength("/huangjd.properties");
		Set<String> keySet = headerMap.keySet();
		for(String key : keySet){
			post.setHeader(key,headerMap.get(key));
		}
		
		HttpResponse response;
		try {
			response = http.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {  
				HttpEntity entity = response.getEntity();  
				//获得返回
				System.out.println("原始返回结果：" + EntityUtils.toString(entity));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	
	}
	
	
	
	/**
	 * 每天三次
	 * @param phone
	 */
	public static void sendx(String phone){
		Map<String, String> postData = new HashMap<String, String>();
		postData.put("tel", phone);
		String result = PostUtil.simplePost(
				"http://www.xinyitianxia.com/public/SendSms","/xinyitianxia.properties", postData);
		System.out.println("测试返回结果：" +result);
	}
	
}
