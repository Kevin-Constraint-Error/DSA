package network;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.lang.Comparable;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileInputStream.*;

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
     
        Scanner scnr = new Scanner(new File("C:\\Users\\Kevin\\Desktop\\cliquesDSA2021\\" + path));
        
        
		while (scnr.hasNextLine()) {
			line = scnr.nextLine();
			String[] input = line.split(splitBy); // Comma used as separator in input files 
			if (input[0] != "idperson") // Avoids adding info template as an actual user
				addUser(input[0], input[1], input[2], input[3], input[4], input[5], input[6], input[7], input[8], input[9], input[10]);
		}
		scnr.close();
	}
	
	
	/**
	 * Outputs network data into a new text file
	 * @throws FileNotFoundException
	 */
	
	public void exportData() throws FileNotFoundException {
		// TODO maybe refers to just users and not friendships (ask)
		String writePath = "C:\\Users\\Kevin\\Desktop\\NetworkUserdata.txt";
		
		File file = new File (writePath);
		PrintWriter output = new PrintWriter (file);
		
		for(Object user : ourNetwork) {	// Casts objects depending on instance and prints them accordingly
			if(user instanceof Friend) {
				output.println(((Friend) user).print());
			}else {
				output.println(((Relationships) user).print());
			}
		}
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
		Scanner scnr = new Scanner(new File("C:\\Users\\Kevin\\Desktop\\" + path));
        
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
        
        String writePath = "C:\\Users\\Kevin\\Desktop\\NetworkReldata.txt";
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
     
        Scanner scnr = new Scanner(new File("C:\\Users\\Kevin\\Desktop\\residential.txt"));
        
		System.out.println("\nUsers in residence: " );
		while (scnr.hasNextLine()) {  
			line = scnr.nextLine();
			for(int i = 0; i < ourNetwork.size(); i++) {
				if(ourNetwork.get(i) instanceof Friend) {
					if(((Friend) ourNetwork.get(i)).getId().equals(line)) {
						friendStack.push((Friend) ourNetwork.get(i)); // their hometown will be used later
						System.out.println("\n Name: " + ((Friend)ourNetwork.get(i)).getName() + "\n Surname: " + ((Friend)ourNetwork.get(i)).getLastname() + "\n Birthplace: " + ((Friend)ourNetwork.get(i)).getBirthPlace() + "\n Studied at: " + ((Friend)ourNetwork.get(i)).getStudiedAt() + "\n");
					}
				}
			}
		}
		scnr.close();
		
		while(!friendStack.isEmpty()) {
        	f1 = friendStack.pop();
        	for(int j = 0; j < ourNetwork.size(); j++) {
        		if(ourNetwork.get(j) instanceof Friend) {
        			if(f1.getHome().equals(((Friend) ourNetwork.get(j)).getHome()))
            			System.out.println(((Friend) ourNetwork.get(j)).getId());
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
			System.out.println("3. Export network data to file...");
			System.out.println("4. Search and update information");
			System.out.println("5. Calculate chain of people");
			System.out.println("0. Exit program...");


			System.out.println("Select your option:");
			int number;
			Scanner keyboard = new Scanner(System.in);
			number = keyboard.nextInt();
			String path;
			
			while (number != 0) {
			
				switch(number) {
				case 1:
					path = keyboard.next();
					Network.importUsers(path);
					break;
				case 2:
					path = keyboard.next();
					Network.importRelationships(path);
					break;
				case 3:
					Network.exportData();
					break;
				case 4:
					System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					System.out.println(" SEARCH AND UPDATE INFORMATION OF USERS: ");
					System.out.println("1. Filter by lastname (friendships of users with lastname)");
					System.out.println("2. Filter by city");
					System.out.println("3. Filter by time frame");
					System.out.println("4. Filter by residents matched in residential.txt");
					System.out.println("5. Filter by identical film classes");
					System.out.println("Select your option > ");
					int search = keyboard.nextInt();
					switch(search) {
					case 1 :
						// 6
						System.out.println("\nEnter lastname: ");
						String lastname = keyboard.next();
						System.out.println("\nSelect printing method: ");
						System.out.println("0. Export to text file...");
						System.out.println("1. Print to console");
						System.out.println("> ");


						int select = keyboard.nextInt();
						Network.exportRelationships(lastname, select);
						break;
					case 2 :
						//7
						System.out.println("\nEnter city > ");
						String city = keyboard.next();
						Network.printByCity(city);
						break;
					case 3 :
						//8
						System.out.println("\nEnter year of time frame beginning > ");
						int date1 = keyboard.nextInt();
						System.out.println("Enter year of time frame end > ");

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
				case 5 :
					Graph graph = new Graph(8,19, "C:\\Users\\Kevin\\Desktop\\graph8.txt", "C:\\Users\\Kevin\\Desktop\\graph88.txt","Jon232");
					
					System.out.println("\nSelect chain: ");
					System.out.println("1. Shortest chain between two given users");
					System.out.println("2. Largest chain between two given users");
					System.out.println("3. Search for groups with at least 4 members");
				
					int x = keyboard.nextInt();
					switch(x) {
					case 1:
						//11
						System.out.println("Enter the id of the first user");
						String idUserBreadth = keyboard.next();
						
						System.out.println("Enter the id of the second user");
						String idUserLastBreadth = keyboard.next();
						
						BreadthFirstSearch minChain = new BreadthFirstSearch(graph, graph.returnHashtableValue(idUserBreadth));//graphUsers,graph.returnHashtableValue(idUserLast)
						
						Stack<Integer> shortChain = new Stack<Integer>();
						shortChain = minChain.pathTo(graph.returnHashtableValue(idUserLastBreadth));
						
						while(!shortChain.isEmpty()) {
							System.out.println(shortChain.pop());
						}
						
						break;
					case 2:
						//12 DepthFirstSearch
						System.out.println("Enter the id of the first user");
						String idUserDepth = keyboard.next();
						System.out.println("Enter the id of the second user");
						String idUser2Depth = keyboard.next();
						
						DepthFirstSearch maxChain = new DepthFirstSearch(graph, graph.returnHashtableValue(idUserDepth));
						Stack<Integer> largeChain = new Stack<Integer>();
						largeChain = maxChain.pathTo(graph.returnHashtableValue(idUser2Depth));
						
						while(!largeChain.isEmpty()) {
							System.out.println(largeChain.pop());
						}
						
						break;
					case 3:
						//13
						
						break;
					default:
						
					}
				default:	
					
				}
				number = keyboard.nextInt();
			}
			keyboard.close();
		} catch (IOException e)  {  
			e.printStackTrace();  
		}
		
	}
}
