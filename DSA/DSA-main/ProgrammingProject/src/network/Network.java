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

public class Network {

	private static ArrayList<Object> ourNetwork;	// Stores friends and relationships (which is why we generalize type Object)
	private Stack<Friend> friendStack;
	
	

	
	public Network() {
		ourNetwork = new ArrayList<Object>();
		friendStack = new Stack<Friend>();
	}
	
	
	
	
	/**
	 * Network getter
	 * @return
	 */
	public static ArrayList<Object> getOurNetwork() {
		return ourNetwork;
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
		ourNetwork.add(user);
	}
	
	
	
	
	
	
	/**
	 * Adds relationship to the network with given parameters
	 * @param friend1
	 * @param friend2
	 */
	public void addShip(String friend1, String friend2) {
		Relationships rel = new Relationships(friend1, friend2);
		ourNetwork.add(rel);
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
		
		for(Object user : ourNetwork)	// Casts objects depending on instance and prints them accordingly
			if(user instanceof Friend) 
				output.println(((Friend) user).print());
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
			Friend f1 = new Friend(input[0]);
			Friend f2 = new Friend(input[1]);
			boolean isF1InNetwork = false;
			boolean isF2InNetwork = false;
			int i = 0;
			while (i < ourNetwork.size() && (!isF1InNetwork || !isF2InNetwork)) {
				if (ourNetwork.get(i) instanceof Friend) {
					if (((Friend) ourNetwork.get(i)).equals(f1))
						isF1InNetwork = true;
					else if (((Friend) ourNetwork.get(i)).equals(f2))
						isF2InNetwork = true;
				}
				i++;
			}
			
			if (isF1InNetwork && isF2InNetwork)		
				addShip(input[0], input[1]);
		}
		scnr.close();
	}
	
		
	
	
	
	/**
	 * Retrieves friend ID and last name by their string. Useful for working with Relationships-type objects
	 * @param f
	 * @return
	 */
	private String retrieveRelInfo(String f) {
		for (int i = 0; i < ourNetwork.size(); i++) 
			if (ourNetwork.get(i) instanceof Friend)
				if (f.equals(((Friend) ourNetwork.get(i)).getId())) 
					return ((Friend) ourNetwork.get(i)).getId() + " " + ((Friend) ourNetwork.get(i)).getLastname();
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
        for(int i = 0; i < ourNetwork.size(); i++) {
            if(ourNetwork.get(i) instanceof Friend) {
                if(((Friend) ourNetwork.get(i)).getLastname().equals(lastname)) {
                    friendStack.push((Friend) ourNetwork.get(i));
                }
            }
        }
        
        String writePath = "cliquesDSA2021/friendsdata_" +  lastname + ".txt";
        File file = new File (writePath);
        PrintWriter output = new PrintWriter (file);
        
        while(!friendStack.isEmpty()) {
        	f1 = friendStack.pop();
        	boolean newf = true;
        	for(int j = 0; j < ourNetwork.size(); j++) {
        		if(ourNetwork.get(j) instanceof Relationships) {
                    if(select == 1) { // Print to console
                    	if (newf) {
                    		System.out.println("\n  Friends of " + f1.getName() + " " + f1.getLastname() + ": ");
                    		newf = false;
                    	}
                    		
                        if(((Relationships) ourNetwork.get(j)).getFriend1().equals(f1.getId())) {
                        	System.out.println("    — " + retrieveRelInfo(((Relationships) ourNetwork.get(j)).getFriend2()));
                        } else if(((Relationships) ourNetwork.get(j)).getFriend2().equals(f1.getId())){
                        	System.out.println("    — " + retrieveRelInfo(((Relationships) ourNetwork.get(j)).getFriend1()));
                        }
                    } else if(select == 0) { // Export to text file
                        if(((Relationships) ourNetwork.get(j)).getFriend1().equals(f1.getId())) {
                            output.println(retrieveRelInfo(((Relationships) ourNetwork.get(j)).getFriend2()));
                        }else if(((Relationships) ourNetwork.get(j)).getFriend2().equals(f1.getId())){
                            output.println(retrieveRelInfo(((Relationships) ourNetwork.get(j)).getFriend1()));
                        }
                    }
                }
        	}
        } 
        
        output.close(); //TODO Hacer para que si se selecciona por consola, no cree un archivo de texto vacio sin usar
    }
	
	
	
	
	
	
	/**
	 * Prints all people from network in console that were born on the given city
	 * @param city
	 */
	public void printByCity(String city) {
		System.out.println("\n  People living in " + city + ": ");
		
		for(int i = 0; i < ourNetwork.size(); i++) {
			if(ourNetwork.get(i) instanceof Friend) {
				if(((Friend) ourNetwork.get(i)).getBirthPlace().equals(city)) {
					System.out.print("    — ");
					System.out.print(((Friend) ourNetwork.get(i)).getId() + " ");
					System.out.print(((Friend) ourNetwork.get(i)).getLastname() + "\n");
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
		
		for(int i = 0; i < ourNetwork.size(); i++) {
			if(ourNetwork.get(i) instanceof Friend) {
				bd = ((Friend) ourNetwork.get(i)).getBirthDate().split("-");
				if(Integer.parseInt(bd[2]) >= year1 && Integer.parseInt(bd[2]) <= year2) {
					friendList.add((Friend) ourNetwork.get(i));
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
			for(int i = 0; i < ourNetwork.size(); i++) {
				if(ourNetwork.get(i) instanceof Friend) {
					if(((Friend) ourNetwork.get(i)).getId().equals(line)) {
						friendStack.push((Friend) ourNetwork.get(i)); // their hometown will be used later
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
        	
        	for(int i = 0; i < ourNetwork.size(); i++) {
        		if(ourNetwork.get(i) instanceof Friend) {
        			if(f1.getBirthPlace().equals(((Friend) ourNetwork.get(i)).getResidence())) {
        				String[] stdats = ((Friend)ourNetwork.get(i)).getStudiedAt().split(";");
        				System.out.print("    — " + ((Friend)ourNetwork.get(i)).getName() + " " + 
        										((Friend)ourNetwork.get(i)).getLastname() + 
        								   "  —  Born in " + ((Friend)ourNetwork.get(i)).getBirthPlace() + 
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
		for (int i = 0; i < ourNetwork.size(); i++) {
			if(ourNetwork.get(i) instanceof Friend) {
				films = ((Friend) ourNetwork.get(i)).getFilms();
				if(filmGroups.size() == 0) {
					filmGroups.add(films);
					classes.add(((Friend) ourNetwork.get(i)).getName() + " " + ((Friend) ourNetwork.get(i)).getLastname());
				} else if(!filmGroups.contains(films)){
					filmGroups.add(films);
					classes.add(((Friend) ourNetwork.get(i)).getName() + " " + ((Friend) ourNetwork.get(i)).getLastname());
				} else {
					String existingUsers = classes.get(filmGroups.indexOf(films)); // All users in the same class are in one string split by commas
					classes.set(filmGroups.indexOf(films), existingUsers + ", " + ((Friend) ourNetwork.get(i)).getName() + " " + ((Friend) ourNetwork.get(i)).getLastname());
				}
			}
		}
		for (int i = 0; i < classes.size(); i++) {
			System.out.println("\n  Films of interest: " + filmGroups.get(i));
			System.out.println("  Members: " + classes.get(i));
		}
	}
	
}
