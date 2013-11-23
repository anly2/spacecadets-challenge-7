package challenge7;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
=======
import java.util.Scanner;
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides a Command Line Interface for using the {@link Chat} class
 * <p>On the command line, type <code>/help</code> for list of commands</p>
 * @see #run()
 * @see Chat
 * @see WindowLogin
 */
public class CLI implements ClientInterface, Runnable {
	/* Instance variables */
	protected Chat chat;
	protected Scanner scanner;
	protected String recipient;
	
	/* Constants */
	protected static String regexHelpCmd = "^\\s*/(help|h|\\?).*";
	protected static String regexAddCmd  = "^\\s*/(add).*";
	protected static String regexSearchCmd = "^\\s*/(search)\\s*(.*)$";
<<<<<<< HEAD
	protected static String regexSelectCmd = "^\\s*/(to|show|select)\\s*(|.+)$";
	protected static String regexExitCmd = "^\\s*/(exit|quit|e|q).*";
	
	
	/* Constructors */
	
=======
	protected static String regexSelectCmd = "^\\s*/(to|show|select)\\s*(.*)$";
	protected static String regexExitCmd = "^\\s*/(exit|quit|e|q).*";
	
	
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
	/**
	 * Constructor that initializes the instance variables
	 * <br />
	 * <br /> Creates a new {@link Chat} object
	 * <br /> Creates a common {@link Scanner} object for {@link System#in}
	 * <br /> Initializes {@link #recipient} to <code>null</code>
	 */	
	CLI () {
		chat = new Chat(this);
		scanner = new Scanner(System.in);
		recipient = null;
	}
	
<<<<<<< HEAD
	
	/* Running methods */
=======
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061

	/**
	 * Starts a {@link CLI Command Line Interface}
	 */
	public static void main (String[] args) {
		ClientInterface intf = new CLI();
		intf.run();
	}
	
	/**
	 * Runs a Command Line Interface
	 * <p> Logs in and then listens for input and performs the appropriate command </p>
	 * @see #login()
	 * @see #message(String)
	 * @see #helpCommand()
	 * @see #addCommand()
	 * @see #searchCommand(String)
	 * @see #selectCommand(String)
	 * @see #exitCommand()
	 */
	public void run () {		
		//Welcome
		System.out.println("*** Welcome to the console version of this chat client! ***");
		System.out.println("Whenever you want to quit type /quit or /exit");
		
		//Login
		login();
		
		//Handle user input
		while (true)
<<<<<<< HEAD
		{			
			//Get input (and print the prefix)
			String input = prompt(chat.getUser() + (recipient==null ? "" : " to "+recipient) + " > ");
=======
		{
			//Print the prefix
			System.out.print(chat.getUser() + (recipient==null ? "" : " to "+recipient) + " > ");
			
			//Get the input
			String input = scanner.nextLine();
			
			
			//Handle messages
			if (!input.matches("^\\s*/"))
				message (input);
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
			
			
			//Handle the help command
			if (input.matches(regexHelpCmd))
				helpCommand();
<<<<<<< HEAD
			else
=======
			
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
			
				
			//Handle the add command
			if (input.matches(regexAddCmd))
				addCommand();
<<<<<<< HEAD
			else
=======
			
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
			
				
			//Handle the search command
			if (input.matches(regexSearchCmd))
				searchCommand (input);
<<<<<<< HEAD
			else
				
=======
			
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
			
			//Handle the select command
			if (input.matches(regexSelectCmd))
				selectCommand (input);
<<<<<<< HEAD
			else
				
=======
			
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
			
			//Exit command
			if (input.matches(regexExitCmd))
				exitCommand ();
<<<<<<< HEAD
			else
			
			//Handle messages
				message (input);
		}
	}
	
	
	/* Command methods */
	
	/**
	 * Asks for an username and password and attempts to {@link Chat#login(String, String) log in} at the server
	 * @return <b>true</b> on successful {@link Chat#login(String, String) login} or <b>false</b> otherwise
	 * @see #run()
	 * @see Chat#login(String, String)
	 */
	protected boolean login () {
		System.out.println("Please log in. (Automatic registration is on)");
		String username = prompt("Username: ");
		String password = new Password().read("Password: ").hash(username);
		
		//Check if credentials are correct
		if (!chat.login(username, password))
		{
			System.out.println("Sorry, incorrect username or password!");
			System.out.println("Exiting...");
			System.exit(0);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Sends a message to the currently selected contact.
	 * <p> Prompts for a selection of recipient if none is selected</p>
	 * @param message the message to be sent
	 * @return <b>true</b> if everything went all right or <b>false</b> otherwise
	 * @see #selectCommand(String)
	 * @see #run()
	 */
	protected boolean message (String message) {
		//Send message
		if (recipient == null && !selectCommand("/to"))
			return false;
		
		try {
			chat.sendMessage(recipient, message);
			return true;
		}
		catch (Exception e) {
			System.out.println("Could not send the message to \""+recipient+"\".");
			recipient = null;
		}
		
		return false;
	}
	
	/**
	 * Prints a list of the available commands and a brief description 	
	 * @return true always
	 * @see #run()
	 * @see #selectCommand(String)
	 * @see #searchCommand(String)
	 * @see #addCommand()
	 * @see #exitCommand()
	 */
	protected boolean helpCommand () {
		System.out.println("Manual:\n\n");

		System.out.println();
		System.out.println("/to [contact]\n /show [contact]\n /select [contact]\n"
				+ "          Selects the recipient for the next message and shows your chat history with that person for the current session");

		System.out.println();
		System.out.println("/search [keyword]\n" 
				+ "          Prints all the contacts in your contact list that contain the keyword entered.");

		System.out.println();
		System.out.println("/add\n" 
				+ "          Adds the contact you are currently chatting with to your contact list.");
		
		System.out.println();				
		System.out.println("/exit\n /quit\n /e\n /q\n" 
				+ "          Stops this Chat program");
		
		return true;
	} 

	/**
	 * Attempts to add the currently {@link #selectCommand(String) selected} user to the logged in user's contact list
	 * @return <b>true</b> if attempt was successful
	 * <br /> or <b>false</b> if no user is {@link #selectCommand(String) selected}
	 * @see #run()
	 * @see #selectCommand(String)
	 * @see #login()
	 */
	protected boolean addCommand () {
		if (recipient == null)
		{
			System.out.println("You are not chatting with anyone at the moment!");
			System.out.println("Please use: /select [contact]");
			return false;
		}
=======
		}
	}
	
	
	/* Command methods */
	
	/**
	 * Asks for an username and password and attempts to {@link Chat#login(String, String) log in} at the server
	 * @return <b>true</b> on successful {@link Chat#login(String, String) login} or <b>false</b> otherwise
	 * @see #run()
	 * @see Chat#login(String, String)
	 */
	protected boolean login () {
		System.out.println("Please log in. (Automatic registration is on)");
		System.out.print("Username: ");
		String username = scanner.nextLine();
		String password = new Password().read("Password: ").hash(username);
		
		//Check if credentials are correct
		if (!chat.login(username, password))
		{
			System.out.println("Sorry, incorrect username or password!");
			System.out.println("Exiting...");
			System.exit(0);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Sends a message to the currently selected contact.
	 * <p> Prompts for a selection of recipient if none is selected</p>
	 * @param message the message to be sent
	 * @return <b>true</b> if everything went all right or <b>false</b> otherwise
	 * @see #selectCommand(String)
	 * @see #run()
	 */
	protected boolean message (String message) {
		//Send message
		if (recipient == null)
			//selectCommand()
		{
			//Prompt for Recipient selection
			System.out.print("Select contact: ");
			recipient = scanner.nextLine();
			
			//Not among known contacts?
			if (!chat.contacts.containsKey(recipient))
				System.out.println("(\""+recipient+"\" is not in your contact list.)");
		}
		
		try {
			chat.sendMessage(recipient, message);
		}
		catch (RuntimeException e) {
			System.out.println("Could not connect to \""+recipient+"\".");
			recipient = null;
			return false;
		}
		
		return true;
	}
	
	/**
	 * Prints a list of the available commands and a brief description 	
	 * @return true always
	 * @see #run()
	 * @see #selectCommand(String)
	 * @see #searchCommand(String)
	 * @see #addCommand()
	 * @see #exitCommand()
	 */
	protected boolean helpCommand () {
		System.out.println("Manual:\n\n");

		System.out.println();
		System.out.println("/to [contact]\n /show [contact]\n /select [contact]\n"
				+ "          Selects the recipient for the next message and shows your chat history with that person for the current session");

		System.out.println();
		System.out.println("/search [keyword]\n" 
				+ "          Prints all the contacts in your contact list that contain the keyword entered.");

		System.out.println();
		System.out.println("/add\n" 
				+ "          Adds the contact you are currently chatting with to your contact list.");
		
		System.out.println();				
		System.out.println("/exit\n /quit\n /e\n /q\n" 
				+ "          Stops this Chat program");
		
		return true;
	} 

	/**
	 * Attempts to add the currently {@link #selectCommand(String) selected} user to the logged in user's contact list
	 * @return <b>true</b> if attempt was successful
	 * <br /> or <b>false</b> if no user is {@link #selectCommand(String) selected}
	 * @see #run()
	 * @see #selectCommand(String)
	 * @see #login()
	 */
	protected boolean addCommand () {
		if (recipient == null)
		{
			System.out.println("You are not chatting with anyone at the moment!");
			System.out.println("Please use: /select [contact]");
			return false;
		}
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
		
		chat.addContact(recipient);
		return true;
	}
<<<<<<< HEAD

	/**
	 * Searches in the contact list of the logged user for a keyword and prints any matches
	 * @param input the input line as it was entered by the user
	 * <br /> Example: "/search wife"
	 * @return true always
	 * @see #run()
	 * @see #selectCommand(String)
	 */
	protected boolean searchCommand (String input) {
		Matcher matcher = Pattern.compile(regexSearchCmd).matcher(input);
		matcher.find();
		
		String keyword;
		if (matcher.groupCount() > 1)
			keyword = matcher.group(2);
		else
			keyword = prompt ("Enter keyword: ");
		
		String[] contacts = chat.getContacts();
		
		for (String contact: contacts)
			if (contact.toLowerCase().contains(keyword))
				System.out.print("\n" + contact);
		
		return true;
	}

	/**
=======

	/**
	 * Searches in the contact list of the logged user for a keyword and prints any matches
	 * @param input the input line as it was entered by the user
	 * <br /> Example: "/search wife"
	 * @return true always
	 * @see #run()
	 * @see #selectCommand(String)
	 */
	protected boolean searchCommand (String input) {
		Matcher matcher = Pattern.compile(regexSearchCmd).matcher(input);
		matcher.find();
		
		String keyword;
		if (matcher.groupCount() > 1)
			keyword = matcher.group(2);
		else
		{
			System.out.print("Enter keyword: ");
			keyword = scanner.nextLine();
		}
		
		String[] contacts = chat.getContacts();
		
		for (String contact: contacts)
			if (contact.toLowerCase().contains(keyword))
				System.out.print("\n" + contact);
		
		return true;
	}

	/**
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
	 * Selects a contact as a recipient (target) for your subsequent questions and commands
	 * @param input the input line as it was entered by the user
	 * <br /> Example: "/search wife"
	 * @return <b>true</b> if successful
	 * <br /> or <b>false</b> the contact was unreachable / non-existant
	 * @see #run()
	 * @see Chat#connect(String)
	 */
	protected boolean selectCommand (String input) {
		Matcher matcher = Pattern.compile(regexSelectCmd).matcher(input);
		matcher.find();
		
<<<<<<< HEAD
		if (matcher.groupCount() <= 1 || matcher.group(2).equals("") || matcher.group(2) == null)
			recipient = prompt ("Select contact: ");
		else
			recipient = matcher.group(2);
		
		if (recipient.equalsIgnoreCase(chat.getUser()))
			//throw new RuntimeException ("Cannot chat with yourself!");
			return false;
=======
		if (matcher.groupCount() > 1)
			recipient = matcher.group(2);
		else
		{
			System.out.print("Select contact: ");
			recipient = scanner.nextLine();
		}
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
		
		if (!chat.connect(recipient))
		{
			recipient = null;
			System.out.println("Contact was not found!");
			return false;
		}
<<<<<<< HEAD
		else if (!chat.contacts.containsKey(recipient))
			System.out.println("(\""+recipient+"\" is not in your contact list.)");
=======
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
		
		return true;
	}
	
	/**
	 * Closes the opened resources and stops the program
	 * @see #run()
	 */
	protected boolean exitCommand () {
		//Close resources
		chat.close();
		
		System.out.println("Bye!");
		System.exit(0);
		
		return true;
	}

	
	/* Update methods */
	
	/**
 	 * Updates something on the ClientInterface
	 * @param args
	 * <br />the first argument says what changed, or in other words, what needs updating
	 * <br />the rest of the arguments are used in the actual process of updating
	 * @see ClientInterface
	 * @see CLI
	 * @see Chat#addMessage(String, String)
	 */
	public void update(String... args) {
		// What changed that needs updating
		String updateType = args[0];

		// We've received a message, display it
		if (updateType.equalsIgnoreCase("messages"))
			updateMessages (args[1]);
	}
	
	/**
	 * We've received a message, display it
	 * <br />
	 * <br /> Prints the last message if you have currently {@link #selectCommand(String) selected} that user to chat
	 * @param userFrom the user who sent the message we just received
	 * @see #update(String...)
	 * @see #selectCommand(String)
	 */
	public void updateMessages (String userFrom) {
		// Stop if the current chat is not with that person
		if (!recipient.equalsIgnoreCase(userFrom))
			return;
		
		System.out.println(recipient + ": "+chat.getMessage(recipient, -1));
<<<<<<< HEAD
	}


	/* Helper methods */
	
	/** 
	 * Alias of {@link #prompt(String, String) prompt(message, "")}
	 */
	public static String prompt (String message) {
		return prompt (message, "");
	}

	public static String prompt (String message, String dfault)
	{
		//print the prompt message
		System.out.print(message);

		//read a line from the standard input
		try {
			return (new BufferedReader(new InputStreamReader(System.in))).readLine();
		} catch (IOException ioe) {
			System.err.println(ioe.toString());
		}

		//return the provided default value
		return dfault;
=======
>>>>>>> b93ec9d584729e98a7a5e6cd219a7920dd8a3061
	}
}
