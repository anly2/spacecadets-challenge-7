package challenge7;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;

/**
 *  An Object through which you can communicate with the public server and get realtime data
 */ 
public class Server {
	/* Constants */
	private static final String serverAddress = "http://"+ "users.ecs.soton.ac.uk/aa5u12" + "/Chat/"; 

	
	/* Constructors */
	
	//Server () {}
	
	
	/* Main methods */

	/**
	 * @deprecated Use {@link #login(String, String, int)} instead
	 */
	public boolean login (String username, String password) {
		String response = "";
		try {
			response = sendPost (serverAddress + "?login", "user="+username +"&"+ "pass="+password);
		}
		catch (Exception e)  {
			System.err.println("Something went bad when logging in");
		}

		return (response.toLowerCase().contains("success"));
	}

	/**
	 * Attempts to log you in at the chat server.
	 * <br />This methods sends the username and password vie HTTP POST to the server.
	 * <br />The server then checks if they are valid and responds.
	 * <br />
	 * <br />On the server side,
	 * <br />if the username/password pair was not found and automatic_registraion is enabled such pair is registered.
	 * <br />Upon successful login (or registration) the IP and Port are recorded on the server.
	 * @param username the username as String
	 * @param password the password as String.
	 * <br />Nothing further is done to this value (no hashing) so it should directly correspond to what the server has.
	 * <br /><i>(Very Bad, ye)</i>
	 * @param port the port which should be stored on the server. This is the port to which other users will connect to you on.
	 * @return <b>true</b> if login was successful or registration was sucessful
	 * <br /><b>false</b> if the username/password were not found and automatic_registraion is not enabled
	 * @see #query(String)
	 */
	public boolean login (String username, String password, int port) {
		String response = "";
		try {
			response = sendPost (serverAddress + "?login", "user="+username +"&"+ "pass="+password +"&"+ "port="+port);
		}
		catch (Exception e)  {
			System.err.println("Something went bad when logging in");
		}

		return (response.toLowerCase().contains("success"));
	}

	/**
	 * Asks for a socket to the given contact.
	 * <br />
	 * <br /> Asks the server for the IP and Port of the given user.
	 * <br /> If the user is not logged in, or in other words, no IP/Port were recorded on the server
	 * <br /> or if there was no such user at all
	 * <br /> the response will contain "fail"
	 * <br />
	 * <br /> Uses the received IP and Port to create a socket and returns it. 
	 * @param contact the username of the contact to whom the socket will be
	 * @return Socket pointing to the IP of the <code>contact</code> and the specified Port
	 * @see Chat#contacts 
	 */
	public Socket query (String contact) {
		String response = "fail";
		
		try {
			response = sendGet (serverAddress + "?query&" +"user="+contact);
		}
		catch (Exception e) {
			System.err.println("Something went bad when querying for a socket to user "+contact);
		}

		if (response.toLowerCase().contains("fail"))
			return null; //Exception

		String[] splitResponse = response.split(":");
		Socket socket = null;

		try {
			socket = new Socket (splitResponse[0], (int) new Integer(splitResponse[1]));
		}
		catch (Exception e) {}

		return socket;
	}

	/**
	 * Asks the server for a comma-separated list of contacts.
	 * @param username the user for whose contacts to ask
	 * @return a comma-separated list of Usernames
	 * <br /> or an empty string if something wasn't right
	 * @see #addContact(String, String)
	 */	
	public String getContacts (String username) {
		try {
			return sendGet (serverAddress + "?contacts&" +"user="+username);
		}
		catch (Exception e) {
			System.err.println("Something went bad when getting contacts for user "+username);
		}

		return null;
	}

	/**
	 * Adds the given user to the list of contacts of the first user
	 * @param username the user to whose contact list the contact will be added
	 * @param contact the user who is added to the contact list
	 * @return <b>true</b> upon success or <b>false</b> otherwise
	 * @see Server#getContacts(String)
	 */
	public boolean addContact (String username, String contact) {
		String response = "";
		try {
			response = sendGet (serverAddress + "?add&" +"user="+username +"&"+ "contact="+contact);
		}
		catch (Exception e) {
			System.err.println("Something went bad when adding "+contact+" as contact of user "+username);
		}

		return (response.toLowerCase().contains("success"));
	}


	/* Tool methods */
	
	/**
	 * Sends an HTTP GET request to the given url and returns the response
	 * @param url the destination of the request
	 * @return the response of the other side as String
	 * @throws MalformedURLException
	 * @throws IOException
	 * @see #sendPost(String, String)
	 * @see #query(String)
	 * @see #getContacts(String)
	 * @see #addContact(String, String)
	 */
	public String sendGet (String url) throws MalformedURLException, IOException
	{
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

		try {
			con.setRequestMethod("GET"); //optional, default is "get"
		} catch (ProtocolException e) {}

		con.setRequestProperty("User-Agent", "Mozilla/5.0"); //add user agent header

		BufferedReader bfr;
		StringBuffer response = new StringBuffer();

		try {
			bfr = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String ln;
			while ((ln = bfr.readLine()) != null) 
				response.append(ln);

			bfr.close();
		}
		catch (IOException e) {}

		return response.toString();
	}

	/**
	 * Sends an HTTP POST request to the given url and returns the response
	 * <p>Note that it is not httpS</p>
	 * @param url the destination of the request
	 * @param urlParameters a list of parameters formated as if for use in an URL
	 * @return the response of the other side as String
	 * @throws MalformedURLException
	 * @throws IOException
	 * @see #sendGet(String)
	 * @see #login(String, String, int)
	 */
	public String sendPost(String url, String urlParameters) throws MalformedURLException, IOException
	{	
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();       
		
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setInstanceFollowRedirects(false); 
		con.setRequestMethod("POST"); 
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		con.setRequestProperty("charset", "utf-8");
		con.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
		con.setUseCaches (false);


		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		BufferedReader bfr;
		StringBuffer response = new StringBuffer();

		try {
			bfr = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String ln;
			while ((ln = bfr.readLine()) != null) 
				response.append(ln);

			bfr.close();
		}
		catch (IOException e) {}

		return response.toString();
	}
}