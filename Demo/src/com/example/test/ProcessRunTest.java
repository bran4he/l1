package com.example.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessRunTest {

	public static void main(String[] args) throws IOException {
//		File folder = new File("/home/bran/");
//		String[] mp4Files = folder.list(new SuffixFileFilter("t"));
//		for(String f: mp4Files){
//			System.out.println(folder.getCanonicalPath()+File.separator+f);
//		}
		
		File file = new File("/home/bran/download/pass/xac");
		InputStreamReader ins = new InputStreamReader(
					new FileInputStream(file),
					"GB18030");
		
		BufferedReader br = new BufferedReader(ins);
		
		String line = null;
		String temp = null;		//缓存前一行字符串
		String result = null;
		boolean flag = true;	//继续读行标记
		while((line = br.readLine()) != null){
			
			if(flag){				
				if(!line.endsWith("}")){
					temp = line;
					flag = false;
				}
			}else{
				line += temp;
				flag = true;
				temp = null;
			}
			
			if(flag){
				String[] arr = line.split(",");
				if(arr.length != 19){
					System.out.println(arr.length+":"+line);
				}	
			}

		}
		
	}

}
