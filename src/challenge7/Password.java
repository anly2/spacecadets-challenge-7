package challenge7;

import java.io.Console;
import java.util.Scanner;

public class Password {
	private String password;
	private static String format = "[^a-zA-Z0-9_\\-\\$!@#\\?]*";

	/**
	 * Creates a new Password object with no password stored
	 * <p>Note that most of the methods will throw an exception if no password was loaded when they were called</p>
	 * @see #read(String)
	 */
	public Password () {
		password = null;
	}
	
	/**
	 * Creates a new Password object with the given string stored inside after {@link #normalize(String) normalization}
	 * @param password the password to store in this object
	 * @see #normalize(String)
	 * @see #normalize()
	 */
	public Password (String password) {
		this.password = normalize(password);
	}
	
	
	/**
	 * Normalizes the password stored
	 * <br />
	 * <br /> "Trims" the password to comply with the set format
	 * @return Returns the Password object after the normalization
	 * @throws RuntimeException if no password was loaded in this object
	 * @see #normalize(String)
	 * @see #setFormat(String)
	 * @see #getFormat()
	 */
	public Password normalize () throws RuntimeException {
		if (password == null)
			throw new RuntimeException ("No password loaded in this object.");
		
		password = normalize (password);
		return this;
	}
	
	/**
	 * Normalizes the given String
	 * <br />
	 * <br /> Views the given String as a password and "trims" it to comply with the set format
	 * @param password the String to be normalized into a password
	 * @return Returns a normalized password.
	 * @see #normalize()
	 * @see #setFormat(String)
	 * @see #getFormat()
	 */
	public String normalize (String password) {
		return password.replaceAll(getFormat(), "");
	}
	
	
	/**
	 * Gets the format used for normalization
	 * @return Returns the format as a regex String
	 * @see #setFormat(String)
	 * @see #normalize()
	 * @see #normalize(String)
	 */
	public String getFormat () {
		return format;
	}
	
	/**
	 * Sets the format used for normalization
	 * @param newFormat the new format regex
	 * @see #getFormat()
	 * @see #normalize()
	 * @see #normalize(String)
	 */
	public void setFormat (String newFormat) {
		format = newFormat;
	}
	
	
	/**
	 * Reads the standard input for a password.
	 * <br /> The read line is #
	 * @param prompt the prompt that is printed before the program starts listening for input
	 * @return Returns this Password object
	 * 
	 */
	public Password read (String prompt) {
		Console console = System.console();
		
		if (console != null)
			password = new String(console.readPassword("Password: "));
		else
		{
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("Password: ");
			password = scanner.nextLine();
			
			scanner.close();
		}
		
		normalize ();		
		return this; //allows chain calls
	}
	
	/**
	 * Loads the given String in this object as password
	 * <br />
	 * <br /> Basically stores the string in this object for further use.
	 * <p> The given is String is {@link #normalize() normalized}! </p>
	 * 
	 * @param password the string to load in this Password object
	 * @return Returns this Password object
	 * @see #read(String)
	 * @see #normalize()
	 * @see #Password(String)
	 */
	public Password load (String password) {
		this.password = normalize (password);
		return this; //allows chain calls
	}
	
	/**
	 * Alias of {@link #load(String)}
	 */
	public Password store (String password) {
		return load (password);
	}
	

	/**
	 * Hashes the password in this object with some salt added
	 * 
	 * @param salt the salt String that gets added to the password for hashing
	 * @return Returns a hash string of the password combined with the provided salt
	 * @throws RuntimeException if no password was loaded in this object
	 * @see #hash()
	 */
	public String hash (String salt) throws RuntimeException {
		if (password == null)
			throw new RuntimeException ("No password loaded in this object.");
		
		//hashed = besthash(salt+password+salt);
		String hashed = password;
		
		return hashed;
	}
	
	/**
	 * Hashes the password with no salt added
	 * @return the hash string of the password
	 * @throws RuntimeException if no password was loaded in this object
	 * @see #hash(String)
	 */
	public String hash () throws RuntimeException {
		if (password == null)
			throw new RuntimeException ("No password loaded in this object.");
		
		//hashed = besthash(password);
		String hashed = password;
		
		return hashed;
	}
	
	/**
	 * CAREFUL!
	 * <br /> WHY would you want the actual password again?
	 * <br /> This just gives you back the password
	 * 
	 * @return Returns the actual password without any sort of ecryption!!!
	 */
	public String toString () {
		return password;
	}
}
