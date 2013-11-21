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
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

public class Server {
	////CHANGE
	private static final String serverAddress = "http://"+ "users.ecs.soton.ac.uk/aa5u12" + "/Chat/"; 

	//Server () {}

	/** @deprecated Use login(String, String, int) instead*/
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

	public String getContacts (String username) {
		try {
			return sendGet (serverAddress + "?contacts&" +"user="+username);
		}
		catch (Exception e) {
			System.err.println("Something went bad when getting contacts for user "+username);
		}

		return null;
	}

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