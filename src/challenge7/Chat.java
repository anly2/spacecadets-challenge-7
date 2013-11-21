package challenge7;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
public class Chat {
	protected String username;
	protected Server server;
	protected Thread listener;
	protected HashMap<String, Socket> contacts;
	protected HashMap<String, ArrayList<String>> messages;
	
	private static final String CONTACT_DELIMETER = ",";
	private static final int port = 27327;
	
	public Chat()
	{
		server = new Server();
		contacts = new HashMap<>();
		messages = new HashMap<>();
	}
	
	public String getUser() {
		return username;
	}
	
	public boolean login(String username, String password)
	{
		boolean logged = server.login(username, password, port);
		
		if(!logged)
			return false;
		
		this.username = username;
		listener = new ListenerThread(this);
		listener.start();
		
		String csl = server.getContacts(this.username);
		String[] contactList = csl.split(CONTACT_DELIMETER);
		
		for (String n : contactList)
			contacts.put(n,  null);
		
		return true;
	}
	
	public void sendMessage(String recipient, String message)
	{
		if (!connect(recipient))
			return; ////Exception!

		Socket socket = contacts.get(recipient);
		PrintWriter sender;
		
		try {
			sender = new PrintWriter(socket.getOutputStream(), true);
			sender.println(message);
			sender.close();
		}
		catch (IOException e) {}
	}
	
	public void addContact(String recipient)
	{
		server.addContact(getUser(), recipient);
	}

	public boolean connect(String recipient)
	{
		if ((!contacts.containsKey(recipient)) || (contacts.get(recipient)==null))
			contacts.put(recipient, server.query(recipient));
		
		if (contacts.get(recipient) == null)
			return false;
		
		messages.put(recipient, new ArrayList<String>());
		
		return true;
	}

	public String[] getContacts(){
		return contacts.keySet().toArray(new String[0]);
	}

	public void addMessage(String contact, String message) {
		messages.get(contact).add(message);
	}

	public String recognizeContact(InetAddress IP, int port2) {
		String[] keys = contacts.keySet().toArray(new String[0]);
		for (String n : keys)
		{
			if (!contacts.get(n).getInetAddress().equals(IP))
				continue;
			
			if(contacts.get(n).getPort() != port2)
				continue;
			
			return n;
		}
		return null;
	}
	
	public void close(){
		listener.interrupt();
	}
	
	public void stop() {
		this.close();
	}
}
