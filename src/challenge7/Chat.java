package challenge7;

import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * The Chat object contains information about the currently logged user and his contacts.
 * <br /> Provides methods for establishing connections between users as well as to the public server for queries
 * @see #login(String, String)
 * @see #sendMessage(String, String)
 * @see ListenerThread
 * @see #contacts
 * @see #messages
 */
public class Chat {
	/**
	 * The username of the currently logged user
	 * @see #login(String, String)
	 */
	protected String username;
	/**
	 * An instance of {@link Server}
	 */
	protected Server server;
	/**
	 * An instance of {@link ListenerThread}
	 */
	protected Thread listener;
	/**
	 * The interface that the client sees and interacts with.
	 * @see ClientInterface#update(String, String)
	 * @see CLI
	 */
	protected ClientInterface clientInterface;
	
	/**
	 * A {@link HashMap} with usernames as keys and a socket to the user as value
	 * <p> Filled at {@link #login(String, String) login} </p>
	 * @see #addContact(String)
	 * @see #login(String, String) 
	 * @see #connect(String)
	 */
	protected HashMap<String, Socket> contacts;
	/**
	 * A {@link HashMap} with an ArrayList of messages for each user
	 * <p> Initialized in {@link #connect(String)} </p>
	 * @see #addMessage(String, String)
	 */
	protected HashMap<String, ArrayList<String>> messages;

	
	/* Constants */
	
	/**
	 * The delimiter which separates Contact entries in the response from the server
	 * @see Server#getContacts(String)
	 */
	protected static final String CONTACT_DELIMETER = ",";
	/**
	 * The port on which to listen for incoming connections
	 * <br /> Default is <code>27327</code
	 */
	protected static final int port = 27327;
	
	
	/* Constructors */
	
	/**
	 * A default constructor to initialize the instance variables
	 */
	public Chat()
	{
		username = null;
		server = new Server();
		contacts = new HashMap<>();
		messages = new HashMap<>();
	}

	/**
	 * Initializes the instance variables and stores a reference to the client interface
	 * @param intf the client interface running this
	 */
	public Chat (ClientInterface intf) {
		this ();
		clientInterface = intf;
	}
	
	
	/* Accessors */
	
	/**
	 * @return the username of the currently logged user.
	 * <br /> Will be <code>null</code> if not logged in yet
	 */
	public String getUser () {
		return username;
	}
	
	/**
	 * Gets a message from a specified user
	 * <br />
	 * <br /> Note that negative messageIndex values ARE allowed.
	 * <br /> If the index is negative it gets calculated to <code>(messages.size() - i)</code>
	 * 
	 * <dl>
	 * 	<dt>Example:</dt>
	 * 		<dd> messages from "wife": &nbsp; "Buy" "eggs" "this" "time!"
	 * 		<dd> getMessage("wife", 0) returns "Buy" </dd>
	 *  	<dd> getMessage("wife", -1) returns "time!" </dd>
	 * </dl>
	 *  
	 * @param userFrom the username of the contact from whom the message is
	 * @param messageIndex the index of the message. Note that it can be a negative number!
	 * @return Returns the <b>nth message</b> from the specified user
	 * <br /> or the <b>last nth message</b> if the index was negative 
	 * @throws RuntimeException if there is no message with that index
	 * @throws RuntimeException if there are no messages from that user (or if the user does not exist)
	 * @see #messages
	 * @see #addMessage(String, String)
	 */
	public String getMessage (String userFrom, int messageIndex) throws RuntimeException {
		ArrayList<String> messages = this.messages.get(userFrom);
		
		if (messages == null)
			throw new RuntimeException ("No messages found from that user! (User may not exists)");
		
		int i = (messageIndex < 0) ? (messages.size() - messageIndex) : messageIndex;
		
		if (i < 0 || i >= messages.size())
			throw new RuntimeException ("Message index out of bounds!");
		
		return messages.get(i);
	}
	
	/* Main methods */
	
	/**
	 * Attempt to sign in as the specified user.
	 * <br />
	 * <br /> After a successful login
	 * <br /> assigns a {@link ListenerThread} to listen for incoming connections on the chosen {@link #port}
	 * <br /> Then gets the contacts for this user
	 * <br/>If a GUI is used, this will also send you to the next window which is {@link WindowContacts}
	 * 
	 * <p>
	 * 	Note that no hashing or encryption is applied to the password currently
	 *  <i>(Needs changing!)</i>
	 * </p>
	 * @param username the username with which to sign in
	 * @param password the password for the given username
	 * @return <b>true</b> if {@link Server#login(String, String, int)} was successful
	 * <br /> or <b>false</b> otherwise.
	 * @see Server#login(String, String, int)
	 */
	public boolean login(String username, String password)
	{
		//password = hash(password+salt)
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
	
	/**
	 * Sends a <code>message</code> from the currently logged user
	 * <br />
	 * <br />Attempts to {@link #connect(String) connect} to the recipient
	 * <br />If the recipient is known, creates a socket and writes to its Input Stream 
	 * @param recipient the username of the user to whom to send the message
	 * @param message the actual message
	 * @throws RuntimeException if {@link #connect(String)} failed
	 * @see #connect(String)
	 * @see #username
	 */
	public void sendMessage(String recipient, String message) throws RuntimeException
	{
		if (!connect(recipient))
			throw new RuntimeException("Failed to find user \""+recipient+"\"");

		Socket socket = contacts.get(recipient);
		PrintWriter sender;
		
		try {
			sender = new PrintWriter(socket.getOutputStream(), true);
			sender.println(message);
			sender.close();
		}
		catch (IOException e) {}
	}
	
	/**
	 * Attempts to add an user to the contact list of the currently logged user
	 * <p>This is basically a call to {@link Server#addContact(String, String)}</p>
	 * @param recipient the contact to add to the list
	 * @see #username
	 */
	public void addContact(String recipient)
	{
		server.addContact(getUser(), recipient);
	}

	/**
	 * Fetches the details for connecting to the recipient
	 * <br />
	 * <br /> Looks through the already known details
	 * <br /> If the details for this recipient were found, stop with success
	 * <br /> Otherwise, {@link Server#query(String) query} the server for details
	 * <p><i>"details"</i> basically means <i>"IP and Port"</i></p>  
	 * @param recipient the user for whose details to look for
	 * @return <b>true</b> if the details for connecting to the user were found
	 * <br /> or <b>false</b> otherwise
	 * @see Server#query(String)
	 * @see #contacts
	 */
	public boolean connect(String recipient)
	{
		if ((!contacts.containsKey(recipient)) || (contacts.get(recipient)==null))
			contacts.put(recipient, server.query(recipient));
		
		if (contacts.get(recipient) == null)
			return false;
		
		messages.put(recipient, new ArrayList<String>());
		
		return true;
	}

	/**
	 * Gets the currently loaded contacts
	 * @return Returns a String array containing the usernames of the loaded contacts
	 * @see #contacts
	 */
	public String[] getContacts(){
		return contacts.keySet().toArray(new String[0]);
	}

	/**
	 * Register a received message
	 * <br />
	 * <br />Adds a String element to the corresponding entry in {@link #messages}
	 * <br />
	 * <br /><i>Should</i> update the view of the user if he is currently chatting with this user 
	 * @param contact the user who sent the message
	 * @param message the message as String
	 * @see #messages
	 */
	public void addMessage(String contact, String message) {
		messages.get(contact).add(message);
		
		//clientInterface.update("message", contact);
	}

	/**
	 * Finds the username that corresponds to a given IP and Port
	 * <br /> Loops through {@link #contacts}  
	 * @param IP the IP of the user
	 * @param port2 the Port the user is using
	 * @return Returns the <b>username</b> of the matched contact
	 * <br /> or <b>null</b> if no user had such details 
	 */
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

	/**
	 * Stops the {@link ListenerThread}
	 */
	public void close(){
		listener.interrupt();
	}
	
	/**
	 * Alias of {@link #close()}
	 */
	public void stop() {
		this.close();
	}
	
}
