package com.chat.client;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {

	public static void colseAll(Closeable ...closes){
		for(Closeable c: closes){
			try {
				if(null != c){
					c.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
