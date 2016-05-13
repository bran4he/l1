package com.comm.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class XSSFTest {

	     
	     public static Object obj = new Object();
	     
	     //标示图片下载功能是否结束
	     public static boolean isFinish = false;

	     public static void main(String[] args) {
	           //下载线程
	           final Thread download = new Thread(){
	               public void run(){
	                    //下载图片
	                   
	                   System. out.println( "download: image start");
	                    for( int i=1; i<=100; i++){
	                        System. out.println( "download:image "+ i+ "%");
	                         try {
	                             Thread. sleep(50);
	                        } catch (InterruptedException e) {
	                              e.printStackTrace();
	                        }
	                   }
	                   System. out.println( "download: image + finished");
	                   
	                    //图片下载功能结束
	                    isFinish= true;
	                    /*
	                    * 当图片下载完毕就应当通知显示图片的线程开始显示图片
	                    */
	                    synchronized( obj){
	                         obj.notify();       //notify() & wait() 是Object下的本地方法
	                   }
	                   
	                   
	                    //下载附件
	                   System. out.println( "download: start download attachment");
	                    for( int i=1; i<=100; i++){
	                        System. out.println( "download:attachment "+i+"%" );
	                         try {
	                             Thread. sleep(50);
	                        } catch (InterruptedException e) {
	                              e.printStackTrace();
	                        }
	                   }
	                   System. out.println( "download: attachment + finished");
	                   
	                   
	              }
	              
	          };
	          
	          Thread show = new Thread(){
	               public void run(){
	                   System. out.println( " the image is showing...");
	                   
//	                 download.join();        //不适用join()
	                   
	                    try {
	                         synchronized( obj){
	                              obj.wait();
	                        }
	                        
	                   } catch (InterruptedException e) {
	                         e.printStackTrace();
	                   }
	                   
	                    if(! isFinish){
	                         throw new RuntimeException( "the image has not been downloaded");                     
	                   }
	                   System. out.println( "the image has been downloaded");
	              }
	          };
	          
	          
	           download.start();
	           show.start();
	          

	     }
	
	@Test
	public void NewWorkbookTest(){
		Workbook wb = new XSSFWorkbook();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("/home/bran/doc/book.xlsx");
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	
	

}
