package challenge7;

import java.io.Console;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides a Command Line Interface for using the {@link Chat} class
 * <p>On the command line, type <code>/help</code> for list of commands</p>
 * @see Chat
 * @see WindowLogin
 */
public class CLI {
	public static void main (String[] args) {
		//Initialization
		Chat chat = new Chat();
		Scanner scanner = new Scanner(System.in);
		Console console = System.console();
		
		//Welcome
		System.out.println("*** Welcome to the console version of this chat client! ***");
		System.out.println("Whenever you want to quit type /quit or /exit");
		
		//Login
		System.out.println("Please log in. (Automatic registration is on)");
		System.out.print("Username: ");
		String username = scanner.nextLine();
		String password;
			if (console != null)
				password = new String(console.readPassword("Password: "));
			else
			{
				System.out.print("Password: ");
				password = scanner.nextLine();
			}
			////password = hash(password);
		
		//Check if credentials are correct
		if (!chat.login(username, password))
		{
			System.out.println("Sorry, incorrect username or password!");
			System.out.println("Exiting...");
			System.exit(0);
		}
			
		
		//Handle user input
		String recipient = null;
		while (true)
		{
			System.out.print(chat.getUser() + (recipient==null ? "" : " to "+recipient) + " > ");
			String input = scanner.nextLine();
			
			//Handle messages
			if (!input.matches("^\\s*/"))
			{
				//Send message
				if (recipient == null)
				{
					//Prompt for Recipient selection
					System.out.print("Select contact: ");
					recipient = scanner.nextLine();
				}
				
				chat.sendMessage(recipient, input);
			}
			
			
			//Handle the help command
			String regexHelpCmd = "^\\s*/(help|h|?).*";
			if (input.matches(regexHelpCmd))
			{
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
				
				continue;
			}
			
			
			//Handle the add command
			String regexAddCmd = "^\\s*/(add).*";
			if (input.matches(regexAddCmd))
			{
				if (recipient == null)
				{
					System.out.println("You are not chatting with anyone at the moment!");
					System.out.println("Please use: /select [contact]");
					continue;
				}
				
				chat.addContact(recipient);
				continue;
			}
			
			
			//Handle the search command
			String regexSearchCmd = "^\\s*/(search)\\s*(.*)$";
			if (input.matches(regexSearchCmd))
			{
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
				
				continue;
			}
			
			
			//Handle the select command
			String regexSelectCmd = "^\\s*/(to|show|select)\\s*(.*)$";
			if (input.matches(regexSelectCmd))
			{
				Matcher matcher = Pattern.compile(regexSelectCmd).matcher(input);
				matcher.find();
				
				if (matcher.groupCount() > 1)
					recipient = matcher.group(2);
				else
				{
					System.out.print("Select contact: ");
					recipient = scanner.nextLine();
				}
				
				if (!chat.connect(recipient))
				{
					recipient = null;
					System.out.println("Contact was not found!");
				}
				
				continue;
			}
			
			
			//Exit command
			String regexExitCmd = "^\\s*/(exit|quit|e|q).*";
			if (input.matches(regexExitCmd))
				break;
		}

		//Close resources
		scanner.close();
		chat.close();
		System.out.println("Bye!");
	}
}
