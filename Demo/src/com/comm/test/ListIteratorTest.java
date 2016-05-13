package com.comm.test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.Test;

public class ListIteratorTest {

	public static void main(String[] args) {
		int a = 0;
		 a = a++;
//		a = ++a;
		System.out.println(a);
		int b;
		System.out.println(b = a++);//返回副本的值
		System.out.println(a);

		int j = 0;
		for (int i = 0; i < 100; i++) {
			j = j++;
		}
		System.out.println(j);

	}

	@Test
	public void test() {
		List list = new ArrayList<String>();

		list.add("jack");
		list.add("tom");
		list.add("ttt");

		ListIterator it = list.listIterator();

		if (it.hasNext()) {
			System.out.println(it.next());
			System.out.println(it.previous());
			it.set("kaka"); // replace the next/previous ele
			System.out.println(list.toString());

			it.add("dada");
			it.add("haha");
			System.out.println(it.next());
			System.out.println(list.toString());
		}

	}
}
