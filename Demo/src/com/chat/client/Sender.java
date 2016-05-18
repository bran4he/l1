package com.chat.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Sender implements Runnable {

	private Socket client;
	private DataOutputStream dos;
	private BufferedReader bis;
	private boolean isRunning = true;
	
	public Sender(Socket client) {
		this.client = client;
		try {
			dos = new DataOutputStream(client.getOutputStream());
			bis = new BufferedReader(new InputStreamReader(System.in));
		} catch (IOException e) {
			isRunning = false;
			CloseUtil.colseAll(dos, bis, client);
		}
	}
	
	private String getMessage(){
		String msg = "";
		try {
			msg =  bis.readLine();
		} catch (IOException e) {
			isRunning = false;
			CloseUtil.colseAll(dos, bis, client);
		}
		return msg;
	}
	
	private void send(){
		try {
			dos.writeUTF(getMessage());
			dos.flush();
		} catch (IOException e) {
			isRunning = false;
			CloseUtil.colseAll(dos, bis, client);
		}
	}
	
	@Override
	public void run() {
		while(isRunning){
			send();
		}
	}

}
