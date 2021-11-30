package network;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//is it working?
public class Main {

	public static void main(String[] args) {
		try {  
			Network net = new Network();

			System.out.println("=-=-=-=-=-=");
			System.out.println(" MAIN MENU:");
			System.out.println("1. Import people into network");
			System.out.println("2. Import friend connections into network");
			System.out.println("3. Export network userdata to file...");
			System.out.println("4. Search for user information");
			System.out.println("5. Calculate chain of people");
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
							System.out.println("Friend connections imported successfully"); //TODO it can load a friends file too, should we prevent that?
							executed=true;
							
						} catch(FileNotFoundException e) {
							System.out.println("Incorrect file name");
							
						} catch(ArrayIndexOutOfBoundsException e) {
							System.out.print("The contents of the file are incorrect, correct them and try again, or try with another file:");
						}
					}
					break;
					
				case 3:
					System.out.print("\nChoose file to export to... > ");
					path = console.next();
					net.exportUserdata(path);
					System.out.println("\nData successfully saved in " + path);
					break;
					
				case 4:
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

					// case 5 not done yet

				default:	
					System.out.println("Invalid option. Please try again.");
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

		} catch (IOException e)  {  
			e.printStackTrace();  
		}
	}

}
