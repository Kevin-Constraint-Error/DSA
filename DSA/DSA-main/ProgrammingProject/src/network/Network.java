package network;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

import classes.Friend;
import classes.Relationships;

import java.util.Collections;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

/**
 * Main network class
 */
public class Network {

	private static ArrayList<Object> net;	// Stores friends and relationships (which is why we generalize type Object)
	private Stack<Friend> friendStack;		// used as auxiliary 
	private int usercount;					// total number of users
	private int relcount;					// total number of connections

	// Lists used for clique computation
	private ArrayList<ArrayList<Friend>> cliqueRoster;	// list of all processed cliques
	private ArrayList<Friend> users;					// list of users in network



	/**
	 * Constructor
	 */
	public Network() {
		net = new ArrayList<Object>(); 		// Stores ALL object-types (Friend and Relationships) into one single instance (list).
											// This will be the representation of the network, where all data will be stored
		friendStack = new Stack<Friend>();	// used as auxiliary for the future
		usercount = relcount = 0;
	}




	/**
	 * Network getter
	 * @return
	 */
	public static ArrayList<Object> getOurNetwork() {
		return net;
	}



	/**
	 * Returns user count
	 * @return
	 */
	public int getUserCount() {
		return usercount;
	}

	
	
	
	/**
	 * Returns relationship count
	 * @return
	 */
	public int getRelCount() {
		return relcount;
	}


	
	
	/**
	 * Adds user to the network with given parameters
	 * @param id
	 * @param name
	 * @param lastname
	 * @param birthdate
	 * @param gender
	 * @param birthplace
	 * @param residence
	 * @param studiedAt
	 * @param workedAt
	 * @param films
	 * @param gpc
	 */
	public void addUser(String id, String name, String lastname, String birthdate, String gender, String birthplace, String residence, String studiedAt, String workedAt, String films, String gpc) {
		Friend user = new Friend(id, name, lastname, birthdate, gender, birthplace, residence, studiedAt, workedAt, films, gpc);
		net.add(user);
		usercount++;
	}






	/**
	 * Adds relationship to the network with given parameters
	 * @param friend1
	 * @param friend2
	 */
	public void addShip(Friend friend1, Friend friend2) {
		Relationships rel = new Relationships(friend1.getId(), friend2.getId());

		Friend f1 = (Friend) net.get(net.indexOf(friend1));
		Friend f2 = (Friend) net.get(net.indexOf(friend2));

		net.remove(friend1);		// Necessary to update friends in the network with the new relationship data
		net.remove(friend2);

		f1.addRelationship(f2);
		f2.addRelationship(f1);

		net.add(f1);
		net.add(f2);
		net.add(rel);

		relcount++;
	}






	/**
	 * Scans and reads people's information from file input and stores it in the network
	 * @param path Filename
	 * @throws IOException
	 */

	public void importUsers(String path) throws IOException {
		String line = "";  
		String splitBy = ",";

		Scanner scnr = new Scanner(new File("cliquesDSA2021/" + path));


		while (scnr.hasNextLine()) {
			line = scnr.nextLine();
			String[] input = line.split(splitBy); // Comma used as separator in input files 
			if (!input[0].equals("idperson")) // Avoids adding info template as an actual user
				addUser(input[0], input[1], input[2], input[3], input[4], input[5], input[6], input[7], input[8], input[9], input[10]);
			else {
				String[] template = input;

				String id = "";
				String name = "";
				String lastname = "";
				String birthDate = "";
				String gender = "";
				String birthPlace = "";
				String residence = "";
				String studiedAt = "";
				String workPlaces = "";
				String films = "";
				String groupCode = "";

				while (scnr.hasNextLine()) {
					line = scnr.nextLine();
					input = line.split(splitBy);

					for (int i = 0; i < input.length; i++) {
						if (template[i].equals("idperson"))
							id = input[i];
						else if (template[i].equals("name"))
							name = input[i];
						else if (template[i].equals("lastname"))
							lastname = input[i];
						else if (template[i].equals("birthdate"))
							birthDate = input[i];
						else if (template[i].equals("gender"))
							gender = input[i];
						else if (template[i].equals("birthplace"))
							birthPlace = input[i];
						else if (template[i].equals("home"))
							residence = input[i];
						else if (template[i].equals("studiedat"))
							studiedAt = input[i];
						else if (template[i].equals("workplaces"))
							workPlaces = input[i];
						else if (template[i].equals("films"))
							films = input[i];
						else if (template[i].equals("groupcode"))
							groupCode = input[i];
					}
					addUser(id, name, lastname, birthDate, gender, birthPlace, residence, studiedAt, workPlaces, films, groupCode);
				}
			}
		}
		scnr.close();
	}






	/**
	 * Outputs network data into a new text file
	 * @throws FileNotFoundException
	 */

	public void exportUserdata(String path) throws FileNotFoundException {
		String writePath = "cliquesDSA2021/" + path;

		File file = new File (writePath);
		PrintWriter output = new PrintWriter (file);

		for(Object user : net) 	// Casts objects depending on instance and prints them accordingly
			if(user instanceof Friend) 
				output.println(((Friend) user).print());

		output.close();
	}





	/**
	 * Outputs network data into a new text file
	 * @throws FileNotFoundException
	 */

	public void backupNetwork() throws FileNotFoundException {
		exportRawUserdata("fNetwork.txt");
		exportReldata("relNetwork.txt");
	}






	/**
	 * Exports network users into raw original text format for network backup
	 * @throws FileNotFoundException
	 */

	private void exportRawUserdata(String path) throws FileNotFoundException {
		String writePath = "cliquesDSA2021/" + path;

		File file = new File (writePath);
		PrintWriter output = new PrintWriter (file);

		for(Object user : net)	// Casts objects depending on instance and prints them accordingly
			if(user instanceof Friend) 
				output.println(((Friend) user).printToFileRaw());
		output.close();
	}



	/**
	 * Exports network relationships into raw original text format for network backup
	 * @param path
	 * @throws FileNotFoundException
	 */
	private void exportReldata(String path) throws FileNotFoundException {
		String writePath = "cliquesDSA2021/" + path;

		File file = new File (writePath);
		PrintWriter output = new PrintWriter (file);

		for(Object rel : net)	// Casts objects depending on instance and prints them accordingly
			if(rel instanceof Relationships) 
				output.println(((Relationships) rel).printToFile());
		output.close();
	}






	/**
	 * Scans and reads people's mutual friendships from file input and stores it in the network
	 * @param path Filename
	 * @throws IOException
	 */

	public void importRelationships(String path) throws IOException {
		String line = "";  
		String splitBy = ",";

		//Creating Scanner 
		Scanner scnr = new Scanner(new File("cliquesDSA2021/" + path));

		line = scnr.nextLine();

		while (scnr.hasNextLine()) {  
			line = scnr.nextLine();
			String[] input = line.split(splitBy); // Comma used as separator in input files 

			if (input.length > 2)
				throw new ArrayIndexOutOfBoundsException();

			Friend f1 = new Friend(input[0]);	  // new Friend instances from retrieved id on file
			Friend f2 = new Friend(input[1]);

			if (net.contains(f1))
				f1 = (Friend) net.get(net.indexOf(f1));      // retrieves full Friend data from network corresponding to the previous ID
			if (net.contains(f2))
				f2 = (Friend) net.get(net.indexOf(f2));

			boolean isF1InNetwork = false;
			boolean isF2InNetwork = false;
			int i = 0;
			while (i < net.size() && (!isF1InNetwork || !isF2InNetwork)) {
				if (net.get(i) instanceof Friend) {
					if (((Friend) net.get(i)).equals(f1))
						isF1InNetwork = true;
					else if (((Friend) net.get(i)).equals(f2))
						isF2InNetwork = true;
				}
				i++;
			}

			if (isF1InNetwork && isF2InNetwork)				// Discards adding relationships of members that don't exist
				addShip(f1, f2);							
		}
		scnr.close();
	}





	/**
	 * Retrieves friend ID and last name by their string. Useful for working with Relationships-type objects
	 * @param f
	 * @return
	 */
	private String retrieveRelInfo(String f) {
		for (int i = 0; i < net.size(); i++) 
			if (net.get(i) instanceof Friend)
				if (f.equals(((Friend) net.get(i)).getId())) 
					return ((Friend) net.get(i)).getId() + " " + ((Friend) net.get(i)).getLastname();
		return "";
	}






	/**
	 * Outputs network friends into a new text file
	 * @param lastname
	 * @param press
	 * @throws FileNotFoundException
	 */

	public void printConnections(String lastname, int select) throws FileNotFoundException {

		Friend f1;      

		// Push found friends onto friend stack
		for(int i = 0; i < net.size(); i++) {
			if(net.get(i) instanceof Friend) {
				if(((Friend) net.get(i)).getLastname().equals(lastname)) {
					friendStack.push((Friend) net.get(i));
				}
			}
		}

		String writePath = "cliquesDSA2021/friendsdata_" +  lastname + ".txt";
		File file = new File (writePath);
		PrintWriter output = new PrintWriter (file);

		while(!friendStack.isEmpty()) {
			f1 = friendStack.pop();
			boolean newf = true;
			for(int j = 0; j < net.size(); j++) {
				if(net.get(j) instanceof Relationships) {
					if(select == 1) { // Print to console
						if (newf) {
							System.out.println("\n  Friends of " + f1.getName() + " " + f1.getLastname() + ": ");
							newf = false;
						}

						if(((Relationships) net.get(j)).getFriend1().equals(f1.getId())) {
							System.out.println("    — " + retrieveRelInfo(((Relationships) net.get(j)).getFriend2()));
						} else if(((Relationships) net.get(j)).getFriend2().equals(f1.getId())){
							System.out.println("    — " + retrieveRelInfo(((Relationships) net.get(j)).getFriend1()));
						}
					} else if(select == 0) { // Export to text file
						if(((Relationships) net.get(j)).getFriend1().equals(f1.getId())) {
							output.println(retrieveRelInfo(((Relationships) net.get(j)).getFriend2()));
						}else if(((Relationships) net.get(j)).getFriend2().equals(f1.getId())){
							output.println(retrieveRelInfo(((Relationships) net.get(j)).getFriend1()));
						}
					}
				}
			}
		} 

		output.close();
	}






	/**
	 * Prints all people from network in console that were born on the given city
	 * @param city
	 */
	public void printByCity(String city) {
		System.out.println("\n  People living in " + city + ": ");

		for(int i = 0; i < net.size(); i++) {
			if(net.get(i) instanceof Friend) {
				if(((Friend) net.get(i)).getBirthPlace().equals(city)) {
					System.out.print("    — ");
					System.out.print(((Friend) net.get(i)).getId() + " ");
					System.out.print(((Friend) net.get(i)).getLastname() + "\n");
				}
			}
		}		
	}






	/**
	 * First gathers all people from network that belong to the year range given, sorts them in a new array and prints them in console
	 * @param year1
	 * @param year2
	 */
	public void printbyTimeFrame(int year1, int year2) {
		//the first year has to be the oldest year
		ArrayList<Friend> friendList = new ArrayList<Friend>();
		String[] bd = new String[2];

		for(int i = 0; i < net.size(); i++) {
			if(net.get(i) instanceof Friend) {
				bd = ((Friend) net.get(i)).getBirthDate().split("-");
				if(Integer.parseInt(bd[2]) >= year1 && Integer.parseInt(bd[2]) <= year2) {
					friendList.add((Friend) net.get(i));
				}
			}
		}
		Collections.sort(friendList);
		System.out.println("\n");

		for(Friend f : friendList) {
			System.out.println("    — " + f.getName() + " " + f.getLastname() + " was born in " + f.getBirthDate());
		}
	}







	/**
	 * Prints personal information from people located at residences matched in residential.txt
	 * @throws IOException
	 */
	public void loadResidential() throws IOException {
		String line = "";  
		Friend f1;

		Scanner scnr = new Scanner(new File("cliquesDSA2021/residential.txt"));


		while (scnr.hasNextLine()) {  
			line = scnr.nextLine();
			for(int i = 0; i < net.size(); i++) {
				if(net.get(i) instanceof Friend) {
					if(((Friend) net.get(i)).getId().equals(line)) {
						friendStack.push((Friend) net.get(i)); // their hometown will be used later
					}
				}
			}
		}
		scnr.close();

		// print full list of users in residential record
		System.out.println("\nSearching for users whose residences match the birthplaces of the users in the record..." );

		while(!friendStack.isEmpty()) {
			f1 = friendStack.pop();
			System.out.println("\n  People living in " + f1.getBirthPlace() + ": ");

			for(int i = 0; i < net.size(); i++) {
				if(net.get(i) instanceof Friend) {
					if(f1.getBirthPlace().equals(((Friend) net.get(i)).getResidence())) {
						String[] stdats = ((Friend)net.get(i)).getStudiedAt().split(";");
						System.out.print("    — " + ((Friend)net.get(i)).getName() + " " + 
								((Friend)net.get(i)).getLastname() + 
								"  —  Born in " + ((Friend)net.get(i)).getBirthPlace() + 
								"  —  Studied at ");
						int j = 0;
						for(String pl : stdats) {
							System.out.print(pl);
							if(j == stdats.length-1)
								System.out.print("\n");
							else
								System.out.print(", ");
							j++;
						}

					}
				}
			}
		} 
	}







	/**
	 * Prints all different classes into console. A class is a group of users that like exactly the same films.
	 */
	public void printFilmClasses() {
		String films = "";
		ArrayList<String> filmGroups = new ArrayList<String>();
		ArrayList<String> classes = new ArrayList<String>();
		for (int i = 0; i < net.size(); i++) {
			if(net.get(i) instanceof Friend) {
				films = ((Friend) net.get(i)).getFilms();
				if(filmGroups.size() == 0) {
					filmGroups.add(films);
					classes.add(((Friend) net.get(i)).getName() + " " + ((Friend) net.get(i)).getLastname());
				} else if(!filmGroups.contains(films)){
					filmGroups.add(films);
					classes.add(((Friend) net.get(i)).getName() + " " + ((Friend) net.get(i)).getLastname());
				} else {
					String existingUsers = classes.get(filmGroups.indexOf(films)); // All users in the same class are in one string split by commas
					classes.set(filmGroups.indexOf(films), existingUsers + ", " + ((Friend) net.get(i)).getName() + " " + ((Friend) net.get(i)).getLastname());
				}
			}
		}
		for (int i = 0; i < classes.size(); i++) {
			System.out.println("\n  Films of interest: " + filmGroups.get(i));
			System.out.println("  Members: " + classes.get(i));
		}
	}






	/**
	 * Performs maximal clique calculations on the network and prints those who are bigger than 4 members
	 */
	public void computeMaximalClique() {
		int count = 0;
		boolean found;

		cliqueRoster = new ArrayList<ArrayList<Friend>>();		// Collection of all retrieved cliques
		users = new ArrayList<Friend>();						

		for (int i = 0; i < net.size(); i++)					// Creating a copy of the network that only contains users
			if(net.get(i) instanceof Friend)
				users.add((Friend) net.get(i));

		// Searching for cliques for every user
		for (Friend f : users) {
			found = false;
			ArrayList<Friend> connections = new ArrayList<Friend>();   // To store links between users
			ArrayList<Friend> visited = new ArrayList<Friend>();	   // Search marking

			ArrayList<Friend> clique = cliqueSearch(f, connections, visited);

			// Discard duplicated clique
			for (int i = 0; i < cliqueRoster.size(); i++)
				if(cliqueRoster.get(i).containsAll(clique)) {
					found = true;
					break;
				}

			// Print clique bigger than 4 members
			if(!found && clique.size() >= 4) {
				count++;

				String output = "\nClique " + count + ": ";
				for (Friend current : connections) 
					output += current.getName() + ", ";

				System.out.println(output.substring(0, output.length() - 2));
			}
			cliqueRoster.add(clique);
		}
	}





	/**
	 * Performs search to build entire clique of people. Recursive method. 
	 * @param f
	 * @param connections
	 * @param visited
	 * @param usercopy
	 * @return
	 */
	public ArrayList<Friend> cliqueSearch(Friend f, ArrayList<Friend> connections, ArrayList<Friend> visited) {
		ArrayList<Friend> rels = f.getRelationships();
		ArrayList<Friend> clique = new ArrayList<Friend>();

		connections.add(f);	 // Add link

		// Return clique if all users have been visited or the amount of user's friends is smaller than the total connections
		if(rels.size() <= connections.size() || visited.size() == users.size()) 
			return connections;

		// Otherwise, analyze friend list
		for(Friend r : rels) {
			
			// If friend isn't connected nor visited, but the friend's friends are already connected
			if(!connections.contains(r) && !visited.contains(r) && r.getRelationships().containsAll(connections)) {
				visited.add(r);									 // visit friend
				clique = cliqueSearch(r, connections, visited);	 // keep searching
			} else 
				visited.add(r);    								 // otherwise, just visit.     						  
		} 

		return clique;

	}







}
