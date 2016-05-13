package com.example.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.common.util.CharacterUtil;
import com.common.util.PropertiesUtil;

public class HttpSendMsgTest {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClient http2 = new DefaultHttpClient();
		
		//记载token
		String p2p = PropertiesUtil.getValue("p2p", "/p2p_token.properties");
		
		HttpPost post = new HttpPost("http://www.hn95tz.com/my/security/sendsms.aspx");  
		// 设置请求体
		//newmobile=&randCode=&mobile=15692132432&p2p=7538d575aff9458804bbc8af1747542d
		List<NameValuePair> params = new ArrayList<NameValuePair>();  
		params.add(new BasicNameValuePair("newmobile", ""));
		params.add(new BasicNameValuePair("randCode", ""));
		params.add(new BasicNameValuePair("mobile", "18217185596"));
		//产生随机数来变为随机token
//		int num = new Random().nextInt(100);
//		String p2p_ran = String.valueOf(new Random().nextInt(1000000));
//		int cut = new Random().nextInt(3);
//		System.out.println("===================原始token:" + p2p);
//		p2p = p2p.substring(0, p2p.length()-cut) + String.valueOf(num);
//		System.out.println("===================随机后token: " + p2p);
		params.add(new BasicNameValuePair("p2p", p2p));//令牌可以变动来实现无限发送
		//在页面上id=p2p, name=p2p的value字段上，动态令牌
		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
		
		Map<String, String> headerMapLogin = PropertiesUtil.readIgnoreConetentLength("/hn95z_send.properties");
		Set<String> keySetLogin = headerMapLogin.keySet();
		for(String key : keySetLogin){
			post.setHeader(key,headerMapLogin.get(key));
		}
		
		HttpResponse responseLogin = http2.execute(post);  
		
		System.out.println("==================第三次访问 返回的headers====================");
		//遍历返回头  
		Header[] headers = responseLogin.getAllHeaders();   
		for(Header h : headers){
			System.out.println(h.getName() + " : " + h.getValue());
		}  
		System.out.println("===================end===================");  
		
		if (responseLogin.getStatusLine().getStatusCode() == 200) {  
			HttpEntity entity = responseLogin.getEntity();  
			//获得返回流
			InputStream in = entity.getContent();
			System.out.println("第三次访问输出内容");
			System.out.println(CharacterUtil.decodeUnicode(EntityUtils.toString(entity)));
			//readStream(in);
		}
	}
}
