package com.comm.test.apache.collections;

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.IterableMap;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.OrderedMap;
import org.apache.commons.collections4.bag.HashBag;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.junit.Test;

public class CollectionsTest {

	public static void main(String[] args) {
		
	}

	@Test
	public void MapIteratorTest(){
		IterableMap<String, String> map = new HashedMap<String, String>(0);
		map.put("name", "jack");
		map.put("age", "12");
		map.put("price", "222");
		
		MapIterator it = map.mapIterator();
		while(it.hasNext()){
			//1
//			System.out.println(it.next()+"\t\t\t" + it.getValue());
			
			//2 
			it.next();
			System.out.println(it.getKey()+"\t\t\t" + it.getValue());
			it.setValue("replace");	//set value of current key
		}
		
		MapIterator it2 = map.mapIterator();
		while(it2.hasNext()){
			//1 it.next() return current key
			System.out.println(it2.next()+"\t\t\t" + it2.getValue()); 
		}
	}
	
	@Test
	public void OrderedMapTest(){
		OrderedMap map = new LinkedMap<String, String>();	//linked
		map.put("a", "5");
		map.put("b", "6");
		map.put("c", "7");
		map.put("d", "8");
		System.out.println(map.firstKey());
		System.out.println(map.nextKey("a"));	//get the next key of key "a"
		System.out.println(map.previousKey("d"));
		System.out.println(map.lastKey());
		
		System.out.println("--------------------");

		OrderedMap map2 = new ListOrderedMap<String, String>();	//list HashMap
		map.put("a", "5");
		map.put("b", "6");
		map.put("c", "7");
		map.put("d", "8");
		System.out.println(map.firstKey());
		System.out.println(map.nextKey("a"));	//get the next key of key "a"
		System.out.println(map.previousKey("d"));
		System.out.println(map.lastKey());
		
	}
	
	@Test
	public void BiodirectionalMapTest(){
		BidiMap bidi = new TreeBidiMap<String, String>();
		bidi.put("a", "5");
		bidi.put("b", "6");
		bidi.put("c", "7");
		bidi.put("d", "8");
		
		System.out.println(bidi.get("b"));	//get value from key
		System.out.println(bidi.getKey("7"));	//get key from value
		bidi.remove("a");	//remove key related
		bidi.removeValue("6"); //remove value related
		
		System.out.println("--------------------");
		BidiMap inverse = bidi.inverseBidiMap();	//swap key-value
		System.out.println(inverse.getKey("c") +"\t\t" + inverse.get("8"));
		
		System.out.println("--------------------");
		MapIterator it = inverse.mapIterator();
		while(it.hasNext()){
			System.out.println(it.next() +"\t\t" + it.getValue());
		}
		
	}
	
	@Test
	public void BagTest(){
		Bag bag = new HashBag();	//like a bag put things and count numbers
		//bag.add(object, nCopies);
		
		bag.add("apple", 7);
		System.out.println(bag.getCount("apple"));
		bag.remove("apple", 3);
		System.out.println(bag.getCount("apple"));
		
	}
}
