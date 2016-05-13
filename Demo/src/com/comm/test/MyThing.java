package com.comm.test;

import java.util.Date;

public class MyThing {

	private String name;
	private Date date;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public MyThing(String name, Date date) {
		super();
		this.name = name;
		this.date = date;
	}

	public MyThing() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MyThing [name=" + name + ", date=" + date + "]";
	}

}
