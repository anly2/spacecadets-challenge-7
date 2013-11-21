package challenge7;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
public class ListenerThread implements Runnable{
	Thread listener;
	ServerSocket serverSocket;
	Socket clientSocket;
	ListenerThread() {
	listener = new Thread(this);
	listener.start(); // Start the thread
	}
	   
	// This is the entry point for the second thread.
	public void run() {
		try {
			
				serverSocket = new ServerSocket(27327);
				clientSocket = serverSocket.accept();
				BufferedReader buff = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				addMessage(buff.readLine());
				buff.close();
	    	} 
		catch (Exception e) {
	    	System.out.println("Child interrupted.");
	    }
		System.out.println("Exiting child thread.");
	}
	public void addMessage(String message)
	{
		
	}
}

