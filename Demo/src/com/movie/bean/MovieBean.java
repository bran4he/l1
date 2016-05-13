package com.movie.bean;

public class MovieBean {
	private String url;
	private String name;
	private String img;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public MovieBean(String url, String name) {
		super();
		this.url = url;
		this.name = name;
	}
	
	
	
}
