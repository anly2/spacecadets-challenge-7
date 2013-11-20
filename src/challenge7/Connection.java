package challenge7;
import java.net.*;
import java.io.*;

public class Connection {
	//private String user;
	//private String localIP;
	private int localPort;
	//private String remoteIP;
	//private int remotePort;
	ServerSocket server;
	Socket client;
	public Connection() throws Exception
	{
		this.createServerSocket();
		this.createClientSocket();
	}
	public void setLocalData(String localIP, int localPort)
	{
		//this.localIP = localIP;
		this.localPort = localPort;
	}
	public void createServerSocket() throws Exception
	{
		server = new ServerSocket(localPort);
	}
	/*public void setRemoteData(String remoteIP, int remotePort)
	{
		this.remoteIP = remoteIP;
		this.remotePort = remotePort;
	}*/
	public void setUser(String user)
	{
		//this.user = user;
	}
	public void createClientSocket() throws Exception
	{
		client = server.accept();
	}
	public void send(String content) throws Exception
	{
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		out.println(content);
		out.close();
	}
	public Socket getClientSocket()
	{
		return client;
	}
	public void onReceive() throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		System.out.println(in.readLine());
		in.close();
	}

}
