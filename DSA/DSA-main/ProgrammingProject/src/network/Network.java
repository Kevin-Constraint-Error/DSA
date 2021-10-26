package network;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;
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
	 * Outputs network friends into a new text file
	 * @param lastname
	 * @param press
	 * @throws FileNotFoundException
	 */
	
	public void exportRelationships(String lastname, int select) throws FileNotFoundException {
		
		Friend f1;      
        
        // Push found friends onto friend stack
        for(int i = 0; i < ourNetwork.size(); i++) {
            if(ourNetwork.get(i) instanceof Friend) {
                if(((Friend) ourNetwork.get(i)).getLastname().equals(lastname)) {
                    friendStack.push((Friend) ourNetwork.get(i));
                }
            }
        }
        
        String writePath = "cliquesDSA2021/NetworkReldata.txt";
        File file = new File (writePath);
        PrintWriter output = new PrintWriter (file);
        
        while(!friendStack.isEmpty()) {
        	f1 = friendStack.pop();
        	for(int j = 0; j < ourNetwork.size(); j++) {
        		if(ourNetwork.get(j) instanceof Relationships) {
                    if(select == 1) { // Print to console
                        if(((Relationships) ourNetwork.get(j)).getFriend1().equals(f1.getId())) {
                            System.out.println(((Relationships) ourNetwork.get(j)).getFriend2());
                        }else if(((Relationships) ourNetwork.get(j)).getFriend2().equals(f1.getId())){
                            System.out.println(((Relationships) ourNetwork.get(j)).getFriend1());
                        }
                    }else if(select == 0) { // Export to text file
                        if(((Relationships) ourNetwork.get(j)).getFriend1().equals(f1.getId())) {
                            output.println(((Relationships) ourNetwork.get(j)).getFriend2());
                        }else if(((Relationships) ourNetwork.get(j)).getFriend2().equals(f1.getId())){
                            output.println(((Relationships) ourNetwork.get(j)).getFriend1());
                        }
                    }
                }
        	}
        } 
        
        output.close();
    }
	
	/**
	 * Prints all people from network in console that live on the given city
	 * @param city
	 */
	public void printByCity(String city) {
		
		for(int i=0; i < ourNetwork.size(); i++) {
			if(ourNetwork.get(i) instanceof Friend) {
				if(((Friend) ourNetwork.get(i)).getBirthPlace().equals(city)) {
					System.out.println(((Friend) ourNetwork.get(i)).getId());
					System.out.println(((Friend) ourNetwork.get(i)).getLastname());
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
		String[] years = new String[2];
		
		for(int i = 0; i < ourNetwork.size(); i++) {
			if(ourNetwork.get(i) instanceof Friend) {
				years = ((Friend) ourNetwork.get(i)).getBirthDate().split("-");
				if(Integer.parseInt(years[0]) > year1 && Integer.parseInt(years[1]) < year2) {
					friendList.add((Friend) ourNetwork.get(i));
				}
			}
		}
		Collections.sort(friendList);
		for(Friend f : friendList) {
			System.out.println(f.getName() + " " + f.getLastname() + " was born in " + f.getBirthDate());
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
        
        // print full list of users in residential record
		System.out.println("\nUsers in residential record: " );
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
		
		while(!friendStack.isEmpty()) {
        	f1 = friendStack.pop();
        	for(int i = 0; i < ourNetwork.size(); i++) {
        		if(ourNetwork.get(i) instanceof Friend) {
        			if(f1.getResidence().equals(((Friend) ourNetwork.get(i)).getResidence()))
        				System.out.println("\n Name: " + ((Friend)ourNetwork.get(i)).getName() + "\n Surname: " + ((Friend)ourNetwork.get(i)).getLastname() + "\n Birthplace: " + ((Friend)ourNetwork.get(i)).getBirthPlace() + "\n Studied at: " + ((Friend)ourNetwork.get(i)).getStudiedAt() + "\n");
        		}
        	}
        } //TODO check this ^
	}
	
	
	/**
	 * Prints all different classes into console. A class is a group of users that like exactly the same films.
	 */
	public void printFilmClasses() {
		String films = "";
		ArrayList<String> filmGroups = new ArrayList<String>();
		ArrayList<String> classes = new ArrayList<String>();
		for(int i = 0; i < ourNetwork.size(); i++) {
			if(ourNetwork.get(i) instanceof Friend) {
				films = ((Friend) ourNetwork.get(i)).getFilms();
				if(filmGroups.size() == 0) {
					filmGroups.add(films);
					classes.add(((Friend) ourNetwork.get(i)).getName() + " " + ((Friend) ourNetwork.get(i)).getLastname());
				}else if(!filmGroups.contains(films)){
					filmGroups.add(films);
					classes.add(((Friend) ourNetwork.get(i)).getName() + " " + ((Friend) ourNetwork.get(i)).getLastname());
				}else {
					String existingUsers = classes.get(filmGroups.indexOf(films)); // All users in the same class are in one string split by commas
					classes.set(filmGroups.indexOf(films), existingUsers + ", " + ((Friend) ourNetwork.get(i)).getName() + " " + ((Friend) ourNetwork.get(i)).getLastname());
				}
			}
		}
		for(int i = 0; i < classes.size(); i++) {
			System.out.println("\nFilms of interest: " + filmGroups.get(i));
			System.out.println("Members: " + classes.get(i));
		}
	}
	
	
	
	
	public static void main(String args[]) {
		  
		try {  
			Network Network = new Network();
			
			System.out.println("=-=-=-=-=-=");
			System.out.println(" MAIN MENU:");
			System.out.println("1. Import people into network");
			System.out.println("2. Import relationships into network");
			System.out.println("3. Export network userdata to file...");
			System.out.println("4. Search and update information");
			System.out.println("5. Calculate chain of people");
			System.out.println("0. Exit program...");


			System.out.print("Select your option > ");
			int number;
			Scanner keyboard = new Scanner(System.in);
			number = keyboard.nextInt();
			String path;
			boolean done=false;
			while (number != 0) {
			
				switch(number) {
				case 1:
					System.out.print("\nChoose file to import... > ");
					path = keyboard.next();
							try
							{
								Network.importUsers(path);
								System.out.println("People imported successfully");
							}
							catch(FileNotFoundException e)
							{
								System.out.println("Incorrect file name");
							}
					break;
				case 2:
					System.out.print("\nChoose file to import... > ");
					path = keyboard.next();
					try
					{
						Network.importRelationships(path);
						System.out.println("Relationships imported successfully");
					}
					catch(FileNotFoundException e)
					{
						System.out.println("Incorrect file name");
					}
					break;
				case 3:
					System.out.print("\nChoose file to export to... > ");
					path = keyboard.next();
					Network.exportUserdata(path);
					System.out.println("\nData successfully saved in " + path);
					break;
				case 4:
					System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					System.out.println(" SEARCH AND UPDATE INFORMATION OF USERS: ");
					System.out.println("1. Filter by lastname (friendships of users with lastname)");
					System.out.println("2. Filter by city");
					System.out.println("3. Filter by time frame");
					System.out.println("4. Filter by residents matched in residential.txt");
					System.out.println("5. Filter by identical film classes");
					System.out.print("Select your option > ");
					int search = keyboard.nextInt();
					switch(search) {
					case 1 :
						// 6
						System.out.println("\nEnter lastname: ");
						String lastname = keyboard.next();
						System.out.println("\nSelect printing method: ");
						System.out.println("0. Export to text file...");
						System.out.println("1. Print to console");
						System.out.print("> ");


						int select = keyboard.nextInt();
						Network.exportRelationships(lastname, select);
						break;
					case 2 :
						//7
						System.out.print("\nEnter city > ");
						String city = keyboard.next();
						Network.printByCity(city);
						break;
					case 3 :
						//8
						System.out.println("\nEnter year of time frame beginning > ");
						int date1 = keyboard.nextInt();
						System.out.print("Enter year of time frame end > ");

						int date2 = keyboard.nextInt();
						Network.printbyTimeFrame(date1, date2);
						break;
					case 4 :
						//9
						Network.loadResidential();
						break;
					case 5 :
						//10
						Network.printFilmClasses();
						break;
					default:
						
					}
					break;
					
				// case 5 not done yet
					
				default:	
					
				}
				
				System.out.println("\n\n=-=-=-=-=-=");
				System.out.println(" MAIN MENU:");
				System.out.println("1. Import people into network");
				System.out.println("2. Import relationships into network");
				System.out.println("3. Export network userdata to file...");
				System.out.println("4. Search and update information");
				System.out.println("5. Calculate chain of people");
				System.out.println("0. Exit program...");


				System.out.print("Select your option > ");
				number = keyboard.nextInt();
			}
			keyboard.close();
		} catch (IOException e)  {  
			e.printStackTrace();  
		}
		
	}
}
