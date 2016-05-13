package com.comm.msg.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.common.util.CharacterUtil;
import com.common.util.PostUtil;
import com.common.util.PropertiesUtil;

/**
 * 在注册时使用
 * 1.第一次无需输入验证码，可以发送短信
 * 2.后面发送需要传入验证码内容，验证码从第二次发送post的response中获取，为一个图片
 * 3.验证码一次读取后填入可以无限次使用
 * 
 * 可以在发送页面上使用ajax将验证码传递过来，然后手动填入实现>1次短信炸弹
 * 
 * @author bran
 *
 */
public class sendWithCaptchaTest {

	public static void main(String[] args) throws ParseException, IOException {
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
		postData.put("txtUserPhone", "15692132432");
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

}
