package challenge7;

public class Main {
	public static void main (String[] args){
		Server server = new Server();
		String respons = server.sendGet("http://users.ecs.soton.ac.uk/aa5u12/Chat/?contacts&user=halit");
		System.out.println(respons);
	}

}
