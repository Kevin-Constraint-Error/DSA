package classes;

/**
 * Class where relationship data is stored, using friend ID's
 *
 */
public class Relationships {

	private String friend1;
	private String friend2;
	
	public Relationships(String friend1, String friend2) {
		this.friend1 = friend1;
		this.friend2 = friend2;
	}
	
	public String getFriend1() {
		return friend1;
	}

	public void setFriend1(String friend1) {
		this.friend1 = friend1;
	}

	public String getFriend2() {
		return friend2;
	}

	public void setFriend2(String friend2) {
		this.friend2 = friend2;
	}
	
	public String print() {
		return friend1 + " and " + friend2 + " are friends.";
	}
	
	// Print relationship as raw text format ("__,__")
	public String printToFile() {
		return friend1 + "," + friend2;
	}
}
