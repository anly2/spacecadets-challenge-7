package challenge7;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
public class Chat {
	protected String username;
	protected Server server;
	protected HashMap<String, Socket> contacts;
	
	private static final String CONTACT_DELIMETER = ",";
	private static final int port = 27327;
	
	public Chat()
	{
		server = new Server();
		contacts = new HashMap<String, Socket>();
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
		//start ListenerThread
		
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
		
		return contacts.get(recipient) == null;
	}

	public String[] getContacts()
	{
		return contacts.keySet().toArray(new String[0]);
	}
}
