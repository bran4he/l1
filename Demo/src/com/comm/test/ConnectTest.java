package com.comm.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class ConnectTest {

	public static void main(String[] args) throws IOException {
//		isAddressAvailable("www.baidu.com");
//		isAddressAvailable("127.0.0.1");
		isReachable("www.baidu.com");
		isReachable("192.168.200.8");
	}

	static void isAddressAvailable(String ip) throws IOException {
		InetAddress address;
		try {
			address = InetAddress.getByName(ip);
			System.out.println("Name: " + address.getHostName());
			System.out.println("Addr: " + address.getHostAddress());
			System.out.println("Reach: " + address.isReachable(3000)); // 是否能通信
																		// 返回true或false
			System.out.println(address.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void isReachable(String ip) {
		// 方法一 最常用的 PING 方法
		Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
		Process process = null; // 声明处理类对象
		String line = null; // 返回行信息
		InputStream is = null; // 输入流
		InputStreamReader isr = null; // 字节流
		BufferedReader br = null;
		boolean res = false;// 结果
		try {
			process = runtime.exec("ping " + ip); // PING
			is = process.getInputStream(); // 实例化输入流
			isr = new InputStreamReader(is);// 把输入流转换成字节流
			br = new BufferedReader(isr);// 从字节中读取文本
			while ((line = br.readLine()) != null) {
				if (line.contains("ttl") || line.contains("TTL")) {
					res = true;
					break;
				}
			}
			is.close();
			isr.close();
			br.close();
			if (res) {
				System.out.println("ping 通  ...");

			} else {
				System.out.println("ping 不通...");
			}
		} catch (IOException e) {
			System.out.println(e);
			runtime.exit(1);
		}
	}

}
