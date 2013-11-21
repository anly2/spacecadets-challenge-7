package challenge7;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
public class ListenerThread extends Thread implements Runnable{
	Thread listener;
	ServerSocket serverSocket;
	Socket clientSocket;
	Chat chat;
	
	ListenerThread(Chat chat) {
		this.chat = chat;
		listener = new Thread(this);
		listener.start(); // Start the thread
	}
	   
	// This is the entry point for the second thread.
	public void run() {
		
		while(true)
		{
			try {
				serverSocket = new ServerSocket(27327);
				clientSocket = serverSocket.accept();
				String contact = chat.recognizeContact(clientSocket.getInetAddress(), clientSocket.getPort());
				BufferedReader buff = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				chat.addMessage(contact, buff.readLine());
				buff.close();
			} 
			catch (Exception e) {}
		}
	}
}

