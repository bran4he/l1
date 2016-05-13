package com.comm.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class MyComparartorTest {

	@Test
	public void test(){
		List<MyThing> list = new ArrayList<MyThing>();
		list.add(new MyThing("1", new Date(1000)));
		list.add(new MyThing("2", new Date(6000)));
		list.add(new MyThing("3", new Date(2000)));
		list.add(new MyThing("4", new Date(4000)));
		
		System.out.println(list.toString());
		
		MyComparator comparator = new MyComparator();
		Collections.sort(list, comparator);
		System.out.println(list.toString());
		
	}
}
