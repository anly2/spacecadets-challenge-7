package challenge7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class Server {
	////CHANGE
	private static final String serverIP = "192.168.99.1";
	private static final int serverPort = 80;
	private static final String serverAddress = "http://"+serverIP+":"+serverPort + "/Chat/"; 
	
	//Server () {}
	
	/** @deprecated Use login(String, String, int) instead*/
	public boolean login (String username, String password) {
		String response = sendGet (serverAddress + "/?login&" +"user="+username +"&"+ "pass="+password);
			
		return (response.toLowerCase().contains("success"));
	}

	public boolean login (String username, String password, int port) {
		String response = sendGet (serverAddress + "/?login&" +"user="+username +"&"+ "pass="+password +"&"+ "port="+port);
			
		return (response.toLowerCase().contains("success"));
	}

	public Socket query (String contact) {
		String response = sendGet (serverAddress + "/?query&" +"user="+contact);
		
		if (response.toLowerCase().contains("fail"))
			return null; //Exception
		
		String[] splitResponse = response.split(":");
		Socket socket = null;
		
		try {
			socket = new Socket (splitResponse[0], (int) new Integer(splitResponse[1]));
		}
		catch (NumberFormatException e) {}
		catch (UnknownHostException e) {}
		catch (IOException e) {}
		
		return socket;
	}
	
	public String getContacts (String username) {
		return sendGet (serverAddress + "/?contacts&" +"user="+username);
	}

	public boolean addContact (String username, String contact) {
		String response = sendGet (serverAddress + "/?add&" +"user="+username +"&"+ "contact="+contact);
			
		return (response.toLowerCase().contains("success"));
	}
	
	
	public String sendGet (String url) {
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
		}
		catch (MalformedURLException e) {}
		catch (IOException e) {}
		
		try {
			con.setRequestMethod("GET"); //optional, default is "get"
		} catch (ProtocolException e3) {}
		
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
}