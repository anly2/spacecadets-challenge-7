package challenge7;

public class Main {
	public static void main (String[] args){
		
		//Test the http requests
		Server server = new Server();
		String response = null;
		try {
			response = server.sendPost("http://users.ecs.soton.ac.uk/aa5u12/Chat/index.php?login", "user=halit");
			
			if (server.login("a", "b", 27327))
				System.out.println("Success");
			else
				System.out.println("Failure");
			
		}catch (Exception e) {}
		
		System.out.println(response);
	}

}
