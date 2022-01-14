package classes;
import java.lang.Comparable;
import java.util.ArrayList;

/**
 * Network user class
 */
public class Friend implements Comparable<Object> {
	private String id;
	private String name;
	private String lastname;
	private String birthDate;
	private String gender;
	private String birthPlace;
	private String residence;
	private String studiedAt;
	private String workPlaces;
	private String films;
	private String groupCode;
	private ArrayList<Friend> relationships; // list of friends they have


	/**
	 * Constructor with id
	 * @param id
	 */
	public Friend(String id) {
		this.id = id;
		relationships = new ArrayList<Friend>();
	}

	/**
	 * Constructor with all parameters (except relationships)
	 * @param id
	 * @param name
	 * @param lastname
	 * @param birthdate
	 * @param gender
	 * @param birthPlace
	 * @param residence
	 * @param studiedAt
	 * @param workPlaces
	 * @param films
	 * @param groupCode
	 */
	public Friend(String id, String name, String lastname, String birthdate, String gender, String birthPlace, String residence, String studiedAt, String workPlaces, String films, String groupCode) {
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.birthDate = birthdate;
		this.gender = gender;
		this.birthPlace = birthPlace;
		this.residence = residence;
		this.studiedAt = studiedAt;
		this.workPlaces = workPlaces;
		this.films = films;
		this.groupCode = groupCode;
		relationships = new ArrayList<Friend>();
	}

	/**
	 * Returns user ID
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Modifies user ID
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns user's legal name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Modifies user's legal name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns user's legal surname
	 * @return
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Modifies user's legal surname
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Returns user's birthday
	 * @return
	 */
	public String getBirthDate() {
		return birthDate;
	}

	/**
	 * Modifies user's birthday
	 * @param birthDate
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Returns user's gender
	 * @return
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Modifies user's gender
	 * @param gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * Returns user's birth place
	 * @return
	 */
	public String getBirthPlace() {
		return birthPlace;
	}

	/**
	 * Modifies user's birth place
	 * @param birthPlace
	 */
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	/**
	 * Returns user's residence
	 * @return
	 */
	public String getResidence() {
		return residence;
	}

	/**
	 * Modifies user's residence
	 * @param residence
	 */
	public void setResidence(String residence) {
		this.residence = residence;
	}

	/**
	 * Returns places where user has studied at
	 * @return
	 */
	public String getStudiedAt() {
		return studiedAt;
	}

	/**
	 * Modifies places where user has studied at
	 * @param studiedAt
	 */
	public void setStudiedAt(String studiedAt) {
		this.studiedAt = studiedAt;
	}

	/**
	 * Returns places where user has worked at
	 * @return
	 */
	public String getWorkPlaces() {
		return workPlaces;
	}

	/**
	 * Modifies places where user has worked at
	 * @param workPlaces
	 */
	public void setWorkPlaces(String workPlaces) {
		this.workPlaces = workPlaces;
	}

	/**
	 * Returns user's favorite films
	 * @return
	 */
	public String getFilms() {
		return films;
	}

	/**
	 * Modifies user's favorite films
	 * @param films
	 */
	public void setFilms(String films) {
		this.films = films;
	}

	/**
	 * Returns group code user is part of
	 * @return
	 */
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * Modifies group code user is part of
	 * @param groupCode
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * Returns list of other users this user is friends with on the network
	 * @return
	 */
	public ArrayList<Friend> getRelationships() {
		return relationships;
	}

	/**
	 * Adds new friend to list of user's friends
	 * @param r
	 */
	public void addRelationship(Friend r) {
		relationships.add(r);
	}

	/**	
	 * Prints user information. Formatted cleanly for console output use
	 * @return
	 */
	public String print() {
		String in = "User's Id: " + id + ", Name: " + name + ", Lastname: " + lastname + ", Birthdate: " + birthDate + ", Gender: " + gender + ", Birthplace: " + birthPlace + ", Home: " + residence + ", Studied at: " + studiedAt + ", Work places: " + workPlaces + ", Films: " + films + ", Groupcode: " + groupCode;
		return in;
	}


	/**
	 * Prints user information. Formatted as readable text file format for I/O use ("__,__,__,__,__,__,__,__,__,__,__")
	 * @return
	 */
	public String printToFileRaw() {
		return id + "," + name + "," + lastname + "," + birthDate + "," + gender + "," + birthPlace + "," + residence + "," + studiedAt + "," + workPlaces + "," + films + "," + groupCode + ",";
	}




	// OVERWRITTEN EQUALS METHOD
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Friend))
			return false;

		Friend f1 = (Friend) o;
		if (this.getId().equals(f1.getId()))	// two friend instances are equal if their IDs are the same (regardless of other attributes)
			return true;
		else
			return false;
	}




	// FRIEND COMPARISON METHOD FOR SORTING ALGORITHM
	@Override
	public int compareTo(Object o) {
		Friend f1 = (Friend) o;								// Object casting to Friend type

		// Sorting order: birthplace, lastname, name (using the first character of the strings)

		int charbp1 = this.getBirthPlace().charAt(0);
		int charbp2 = f1.getBirthPlace().charAt(0);
		if(charbp1 < charbp2) {
			return -1;
		}else if(charbp1 == charbp2) {
			int charln1 = this.getLastname().charAt(0);
			int charln2 = f1.getLastname().charAt(0);
			if(charln1 < charln2) {
				return -1;
			}else if(charln1 == charln2) {
				int charn1 = this.getName().charAt(0);
				int charn2 = f1.getName().charAt(0);
				if(charn1 < charn2) {
					return -1;
				}else if(charn1 == charn2) {
					return 0;
				}else {
					return 1;
				}
			}else {
				return 1;
			}
		}else {
			return 1;
		}
	}
}
