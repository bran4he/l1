package com.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.chat.client.CloseUtil;

public class Server {
//	private List<MyChannel> list = new ArrayList<MyChannel>();

	private Map<String, MyChannel> map = new HashMap<String, MyChannel>();
	
//	private LinkedList<String> MessageList = new LinkedList<String>();
	
	public static void main(String[] args) throws IOException{
			new Server().start();
	}
	
	
	public void start() throws IOException{
		ServerSocket server = new ServerSocket(9999);
		int i=1;
		while(true){
			Socket client = server.accept();
			MyChannel c = new MyChannel(client);
			
			Thread t = new Thread(c);
			t.start();
			map.put(t.getName(), c);
			
		}
	}
	
	private class MyChannel implements Runnable{
		
		private Socket client;
		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean isRunning = true;
		private String name;
		
		
		public MyChannel(Socket client) {
			this.client = client;
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
				
				
			} catch (IOException e) {
				isRunning = false;
				map.remove(this.name);
				CloseUtil.colseAll(dis, dos, client);
			}
		}
		
		public MyChannel(Socket client, String name) {
			this.client = client;
			this.name = name;
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
			} catch (IOException e) {
				isRunning = false;
				map.remove(this.name);
				CloseUtil.colseAll(dis, dos, client);
			}
		}
		
		
		private String readMessage(){
			String msg = "";
			try {
				msg = dis.readUTF();
			} catch (IOException e) {
				isRunning = false;
				map.remove(this.name);
				CloseUtil.colseAll(dis, dos, client);
			}
			return msg;
		}
		
		private void sendMessageTo(MyChannel chanel, String msg){
			DataOutputStream toDos = chanel.getDos();
			try {
				toDos.writeUTF(msg);
			} catch (IOException e) {
				chanel.setRunning(false);
				CloseUtil.colseAll(chanel.getDis(), toDos, chanel.getClient());
			}
		}
		
		private void sendMessage(MyChannel chanel, String msg){
			DataOutputStream toDos = chanel.getDos();
			try {
				toDos.writeUTF("server say:"+msg);
			} catch (IOException e) {
				chanel.setRunning(false);
				CloseUtil.colseAll(chanel.getDis(), toDos, chanel.getClient());
			}
		}
		
		
		private void sendMessage(){
			String msg = readMessage();
			
			//#开头，想某人发消息
			if(msg.startsWith("#")){
				String name = msg.substring(1, msg.indexOf(":"));
				String message = msg.substring(msg.indexOf(":")+1);
				MyChannel toClient = map.get(name);
				sendMessageTo(toClient, message);
				
			//$开头，向全部人发送消息	
			}else if(msg.startsWith("$")){
				Collection<MyChannel> list = map.values();
				String message = msg.substring(1);
				for(MyChannel chanel : list){
					sendMessageTo(chanel, message);
				}
				
			//其他开头，向自己返回消息
			}else{
				try {
					dos.writeUTF(msg);
					dos.flush();
				} catch (IOException e) {
					isRunning = false;
					map.remove(this.name);
					CloseUtil.colseAll(dis, dos, client);
				}
			}
			
		}
		
		private void welcome(){
			try {
				dos.writeUTF("welcome: " + this.name);
				dos.flush();
			} catch (IOException e) {
				isRunning = false;
				map.remove(this.name);
				CloseUtil.colseAll(dis, dos, client);
			}
		
		}
		
		
		@Override
		public void run() {
			//welcome();
			
			sendMessage(this, "please input your nick name:");
			String name = readMessage();
			this.setName(name);
			
			map.remove(Thread.currentThread().getName());
			map.put(name, this);
			System.out.println(name +" has logged in");
			
			while(isRunning){
				sendMessage();
			}
		}


		public Socket getClient() {
			return client;
		}


		public void setClient(Socket client) {
			this.client = client;
		}


		public DataInputStream getDis() {
			return dis;
		}


		public void setDis(DataInputStream dis) {
			this.dis = dis;
		}


		public DataOutputStream getDos() {
			return dos;
		}


		public void setDos(DataOutputStream dos) {
			this.dos = dos;
		}


		public boolean isRunning() {
			return isRunning;
		}


		public void setRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}
		
		
	}
}


