package com.chat.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Reciver implements Runnable{

	private Socket client;
	private DataInputStream dis;
	private boolean isRunning = true;
	
	
	
	public Reciver(Socket client) {
		this.client = client;
		try {
			dis = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			isRunning = false;
			CloseUtil.colseAll(dis, client);
		}
	}

	private String getMessage(){
		String msg = "";
		try {
			msg = dis.readUTF();
		} catch (IOException e) {
			isRunning = false;
			CloseUtil.colseAll(dis, client);
		}
		return msg;
	}


	@Override
	public void run() {
		while(isRunning){
			System.out.println(getMessage());
		}
	}
}
