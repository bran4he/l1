package com.xueqiu.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserBean {
	private String id;
	private String photo_domain;
	private String profile_image_url;
	private String province;
	private String city;
	private String followers_count; //fans
	
	public String getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(String followers_count) {
		this.followers_count = followers_count;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhoto_domain() {
		return photo_domain;
	}
	public void setPhoto_domain(String photo_domain) {
		this.photo_domain = photo_domain;
	}
	public String getProfile_image_url() {
		return profile_image_url;
	}
	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "UserBean [id=" + id + ", photo_domain=" + photo_domain + ", profile_image_url=" + profile_image_url
				+ ", province=" + province + ", city=" + city + ", followers_count=" + followers_count + "]";
	}
	
	
	
}
