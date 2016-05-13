package com.comm.test;

import java.util.Comparator;
import java.util.Date;


public class MyComparator implements Comparator<MyThing> {

	@Override
	public int compare(MyThing o1, MyThing o2) {
		
		Date date1 = o1.getDate();
		Date date2 = o2.getDate();
		
		if(date1.after(date2)){
			return 1;
		}
		
		return -1;
	}


}
