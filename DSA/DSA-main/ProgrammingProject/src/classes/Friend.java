package classes;
import java.lang.Comparable;
import java.util.ArrayList;

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
	private ArrayList<Friend> relationships;
	
	
	
	public Friend(String id) {
		this.id = id;
		relationships = new ArrayList<Friend>();
	}
	
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getStudiedAt() {
		return studiedAt;
	}

	public void setStudiedAt(String studiedAt) {
		this.studiedAt = studiedAt;
	}

	public String getWorkPlaces() {
		return workPlaces;
	}

	public void setWorkPlaces(String workPlaces) {
		this.workPlaces = workPlaces;
	}

	public String getFilms() {
		return films;
	}

	public void setFilms(String films) {
		this.films = films;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	public ArrayList<Friend> getRelationships() {
		return relationships;
	}

	public void addRelationship(Friend r) {
		relationships.add(r);
	}

	
	public String print() {
		String in = "User's Id: " + id + ", Name: " + name + ", Lastname: " + lastname + ", Birthdate: " + birthDate + ", Gender: " + gender + ", Birthplace: " + birthPlace + ", Home: " + residence + ", Studied at: " + studiedAt + ", Work places: " + workPlaces + ", Films: " + films + ", Groupcode: " + groupCode;
		return in;
	}
	
	public String printToFileRaw() {
		return id + "," + name + "," + lastname + "," + birthDate + "," + gender + "," + birthPlace + "," + residence + "," + studiedAt + "," + workPlaces + "," + films + "," + groupCode + ",";
	}
	
	
	public boolean equals(Friend f1) {
		if (this.getId().equals(f1.getId()))
			return true;
		else
			return false;
	}
	
	
	
	
	// FRIEND COMPARISON METHOD FOR SORTING ALGORITHM
	@Override
	public int compareTo(Object o) {
		Friend f1 = (Friend) o;								// Object casting to Friend type
		
		// Sorting order: birthplace, lastname, name
		
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
