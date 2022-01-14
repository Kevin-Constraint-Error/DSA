package network;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

import classes.Graph;
import classes.BreadthFirstSearch;
import classes.DepthFirstSearch;

/**
 * Class for main program / network console menu
 */
public class Main {

	/**
	 * Main program
	 * @param args
	 */
	public static void main(String[] args) {
		try {  
			Network net = new Network();

			System.out.println("=-=-=-=-=-=");
			System.out.println(" MAIN MENU:");
			System.out.println("1. Import people into network");
			System.out.println("2. Import friend connections into network");
			System.out.println("3. Export network userdata to file...");
			System.out.println("4. Search for user information");
			System.out.println("5. Search for chain of people");
			System.out.println("0. Exit program...");


			System.out.print("\nSelect your option > ");


			boolean executed = false;
			Scanner console = new Scanner(System.in);
			int number = console.nextInt();

			String path;
			while (number != 0) {

				switch(number) {

				case 1:													// import people into network
					System.out.print("\nChoose file to import... > ");
					executed = false;
					while(!executed) {
						try {
							path = console.next();
							net.importUsers(path);
							System.out.println("People imported successfully.");
							executed = true;

						} catch(FileNotFoundException e) {
							System.out.print("Incorrect file name, introduce a valid file name (don't forget to add .txt) > ");

						} catch(ArrayIndexOutOfBoundsException e) {
							System.out.print("The contents of the file are incorrect, correct them and try again, or try with another file > ");
						}

					}
					break;

				case 2:														// import relationships into network
					System.out.print("\nChoose file to import... > ");
					executed = false;
					while(!executed) {
						try {
							path = console.next();
							net.importRelationships(path);
							System.out.println("Friend connections imported successfully"); 
							executed=true;

						} catch(FileNotFoundException e) {
							System.out.print("Incorrect file name, introduce a valid file name (don't forget to add .txt) > ");

						} catch(ArrayIndexOutOfBoundsException e) {
							System.out.print("The contents of the file are incorrect, correct them and try again, or try with another file > ");
						}
					}
					break;

				case 3:														// export users to a text file
					System.out.print("\nChoose file to export to... > ");
					path = console.next();
					net.exportUserdata(path);
					System.out.println("\nData successfully saved in " + path);
					break;

				case 4:														// filter information
					System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
					System.out.println(" SEARCH INFORMATION OF USERS: ");
					System.out.println("1. Filter by lastname to search for their friend connections");
					System.out.println("2. Filter by birthplace");
					System.out.println("3. Filter by time frame");
					System.out.println("4. Filter by residents living in birthplaces matched in \"residential.txt\"");
					System.out.println("5. Filter by identical film classes");
					System.out.print("\nSelect your option > ");
					int search = console.nextInt();
					switch(search) {
					case 1 :
						// 6
						System.out.print("\nEnter lastname > ");
						String lastname = console.next();
						System.out.println("\nSelect printing method: ");
						System.out.println("0. Export to text file...");
						System.out.println("1. Print to console");
						System.out.print("> ");
						int select = console.nextInt();
						net.printConnections(lastname, select);
						break;
					case 2 :
						//7
						System.out.print("\nEnter city > ");
						String city = console.next();
						net.printByCity(city);
						break;
					case 3 :
						//8
						System.out.print("\nEnter year of time frame beginning > ");
						int date1 = console.nextInt();
						System.out.print("Enter year of time frame end > ");
						int date2 = console.nextInt();
						net.printbyTimeFrame(date1, date2);
						break;
					case 4 :
						//9
						net.loadResidential();
						break;
					case 5 :
						//10
						net.printFilmClasses();
						break;
					default:

					}
					break;

				case 5:
					System.out.print("\nNote: A backup of the network will be generated and exported as a file in order to compute calculations."
							+ "\nDo you wish to continue? (Y/N)   > ");

					boolean validCompl = false;		// detects valid response to previous question (yes or no)
					while (!validCompl) {
						String compl = console.next();

						if (compl.equalsIgnoreCase("y") || compl.equalsIgnoreCase("yes")) {

							validCompl = true;
							System.out.println("\nExporting data...");
							net.backupNetwork();

							Graph gr = new Graph(net.getUserCount(), "fNetwork.txt", "relNetwork.txt");

							System.out.println("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
							System.out.println("\nWhat would you like to search for? ");
							System.out.println("1. Shortest chain between two given users");
							System.out.println("2. Largest chain between two given users");
							System.out.println("3. Search for groups with at least 4 members");

							int sel = console.nextInt();
							switch(sel) {
							case 1:
								//11
								try {
									System.out.print("Enter the id of the first user > ");
									String sourceUser = console.next();

									System.out.print("Enter the id of the second user > ");
									String destUser = console.next();

									BreadthFirstSearch bfs = new BreadthFirstSearch(gr, gr.getHash(sourceUser));

									Stack<Integer> minChain = new Stack<Integer>();
									minChain = bfs.pathTo(gr.getHash(destUser));

									String ch = "\n";
									while(!minChain.isEmpty())
										ch += gr.getID(minChain.pop()) + " -> ";

									System.out.println(ch.substring(0, ch.length() - 4));

								} catch (NullPointerException e) {
									e.printStackTrace();
									System.out.println("\nError: One or both of the given IDs could not be found on the network.");
								}
								break;


							case 2:
								//12
								try {
									System.out.print("Enter the id of the first user > ");
									String sourceUser = console.next();
									System.out.print("Enter the id of the second user > ");
									String destUser = console.next();

									DepthFirstSearch dfs = new DepthFirstSearch(gr, gr.getHash(sourceUser));
									Stack<Integer> maxChain = new Stack<Integer>();
									maxChain = dfs.pathTo(gr.getHash(destUser));


									String ch = "\n";
									while(!maxChain.isEmpty())
										ch += gr.getID(maxChain.pop()) + " -> ";

									System.out.println(ch.substring(0, ch.length() - 4));
								} catch (NullPointerException e) {
									e.printStackTrace();
									System.out.println("\nError: One or both of the given IDs could not be found on the network.");

								}


								break;

							case 3:
								//13
								net.computeMaximalClique();		// Maximal clique calculator algorithm (directly from Network class).
																// Backup is not needed for this one.
								break;
							}
						}

						else if (compl.equalsIgnoreCase("n") || compl.equalsIgnoreCase("no"))
							validCompl = true;

						else 
							System.out.print("Invalid input. Please try again > ");
					}

					break;




				default:	
					System.out.println("Invalid option. Please try again > ");
					break;
				}

				System.out.println("\n\n=-=-=-=-=-=");
				System.out.println(" MAIN MENU:");
				System.out.println("1. Import people into network");
				System.out.println("2. Import friend connections into network");
				System.out.println("3. Export network userdata to file...");
				System.out.println("4. Search for user information");
				System.out.println("5. Calculate chain of people");
				System.out.println("0. Exit program...");


				System.out.print("\nSelect your option > ");
				number = console.nextInt();
			}

			console.close();

		} catch (IOException e)  { // files not found  
			e.printStackTrace();  
		}
	}

}
