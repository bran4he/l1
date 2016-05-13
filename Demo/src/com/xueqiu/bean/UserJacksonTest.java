package com.xueqiu.bean;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//反序列化未知的属性时不会抛出异常
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserJacksonTest {

	private String name;  
    private Integer age;  
    private Date birthday;  
    private String email;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	} 
    
	
	
    @Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", birthday=" + birthday + ", email=" + email + "]";
	}
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
    	String json = "{\"name\":\"小民\",\"age\":20,\"birthday\":844099200000,\"email\":\"xiaomin@sina.com\",\"xx\":\"yy\"}";  
        
        /** 
         * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。 
         */  
        ObjectMapper mapper = new ObjectMapper();  
        UserJacksonTest user = mapper.readValue(json, UserJacksonTest.class);  
        System.out.println(user);  
	}
}
