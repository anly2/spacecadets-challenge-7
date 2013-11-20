package challenge7;
import java.util.*;
import java.net.Socket;
public class Chat {
	HashMap<String, Socket> contacts = new HashMap<String, Socket>();
	public Chat() throws Exception
	{
		Connection connection = new Connection();
		Socket client = connection.getClientSocket();
		String user = null;
		contacts.put(user, client);
	}
	
	

}
