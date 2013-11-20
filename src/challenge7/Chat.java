package challenge7;
import java.util.*;
import java.net.Socket;
public class Chat {
	private HashMap<String, Socket> contacts;
	private String username;
	Server server;
	Connection connection = new Connection();
	private static final String CONTACT_DELIMETER = ",";
	
	public Chat()
	{
		server = new Server();
		contacts = new HashMap<String, Socket>();
	}
	
	public boolean login(String username, String password)
	{
		boolean loged = server.login(username, password);
		if(!loged)
			return false;
		
		this.username = username;
		
		String csl = server.getContacts();
		String[] contactList = csl.split(CONTACT_DELIMETER);
		
		for (String n : contactList)
			contacts.put(n,  null);
		
		return true;
	}
	public String getUser()
	{
		return username;
	}
	public void sendMessage(String recipient, String message)
	{
		connect(recipient);
		
	}
	public void addContact(String recipient)
	{
		server.addContact(getUser(), recipient);
	}
	public boolean connect(String recipient)
	{
		if ((!contacts.containsKey(recipient)) || (contacts.get(recipient)==null))
			contacts.put(recipient, server.query(recipient));
		
		String[] connect = contacts.get(recipient).toString().split(":");
		connection.createClientSocket(connect[0], new Integer(connect[1]));
		
		return contacts.get(recipient) == null;
	}
	public String[] getContacts()
	{
		return contacts.keySet().toArray(new String[0]);
	}
	
	

}
