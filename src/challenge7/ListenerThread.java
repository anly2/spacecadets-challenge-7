package challenge7;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

/**
 * Creates a separate {@link Thread} and uses it to listen on a specified port for incoming connections
 * <br />Calls {@link Chat#addMessage(String, String)} when a connection is received and starts listening again
 * @see #run() 
 */
public class ListenerThread extends Thread implements Runnable {
	/**
	 * A reference to the {@link Chat} object that created this Thread
	 */
	Chat chat;
	
	/**
	 * Binds chat and immediately starts this Thread
	 * @param chat the {@link Chat} object that created this Thread
	 */
	ListenerThread(Chat chat) {
		this.chat = chat;		
		this.start(); // Start the thread
	}
	   
	/**
	 * The listening process that is running on this Thread
	 * <br />
	 * <br /> Creates a {@link ServerSocket} and waits for a connection on it.
	 * <br /> Handles an incoming message by calling {@link Chat#addMessage(String, String) Chat.addMessage}
	 * <br /> Starts listening again
	 */
	public void run() {
		//Continuously handle incoming connections
		while(true)
		{
			try {
				//Open a socket and listen
				ServerSocket serverSocket = new ServerSocket(Chat.port);
				Socket clientSocket = serverSocket.accept(); //wait for a connection
				
				//Incoming connection
				
				//Recognize the sender of the incoming message and also get the message as String
				String contact = chat.recognizeContact(clientSocket.getInetAddress(), clientSocket.getPort());
				BufferedReader buff = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				//Register the message
				chat.addMessage(contact, buff.readLine());
				
				//Close resourses
				buff.close();
				serverSocket.close();
				clientSocket.close();
			} 
			catch (Exception e) {} //Ignore errors
		}
		//Loops until is interrupted {@see Thread#interrupt()}
	}
}

