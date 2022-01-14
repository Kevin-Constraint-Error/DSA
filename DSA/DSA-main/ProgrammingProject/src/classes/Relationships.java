package classes;

/**
 * Class where connections between users is stored
 * It makes use of their IDs
 */
public class Relationships {

	private String friend1;
	private String friend2;
	
	/**
	 * Constructor with both users' IDs
	 * @param friend1
	 * @param friend2
	 */
	public Relationships(String friend1, String friend2) {
		this.friend1 = friend1;
		this.friend2 = friend2;
	}
	
	/**
	 * Get first of the friends
	 * They are stored as two fixed values like left/right
	 * @return
	 */
	public String getFriend1() {
		return friend1;
	}
	
	/**
	 * Modify first of the friends
	 * They are stored as two fixed values like left/right
	 * @return
	 */
	public void setFriend1(String friend1) {
		this.friend1 = friend1;
	}

	/**
	 * Get second of the friends
	 * They are stored as two fixed values like left/right
	 * @return
	 */
	public String getFriend2() {
		return friend2;
	}

	/**
	 * Modify second of the friends
	 * They are stored as two fixed values like left/right
	 * @return
	 */
	public void setFriend2(String friend2) {
		this.friend2 = friend2;
	}
	
	/**	
	 * Prints connection information. Formatted cleanly for console output use
	 * @return
	 */
	public String print() {
		return friend1 + " and " + friend2 + " are friends.";
	}
	
	/**
	 * Prints connection information. Formatted as readable text file format for I/O use ("__,__")
	 * @return
	 */
	public String printToFile() {
		return friend1 + "," + friend2;
	}
}
