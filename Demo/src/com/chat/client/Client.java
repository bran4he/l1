package com.chat.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client{

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("localhost", 9999);
		
		new Thread(new Sender(client)).start();
		
		new Thread(new Reciver(client)).start();
		
	}
	
}
