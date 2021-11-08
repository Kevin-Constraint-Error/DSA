package network;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//is it working?
public class Main {

	public static void main(String[] args) {
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
			int number=99;//random value to start from for the next try/catch
			boolean done=false;
			Scanner keyboard = new Scanner(System.in);
			while(number==99) {
			try
			{
			number = keyboard.nextInt();
			}
			catch (Exception e) {
	            System.out.print("That is not a number, type a number:");
	           keyboard.next();// Move to next other wise exception
	        }
			}
			String path;
			while (number != 0) {
			
				switch(number) {
				case 1:
					System.out.print("\nChoose file to import... > ");
					while(!done) {
							try
							{
								path = keyboard.next();
								Network.importUsers(path);
								System.out.println("People imported successfully");
								done=true;
							}
							catch(FileNotFoundException e)
							{
								System.out.print("Incorrect file name, introduce a valid file name (don't forget to add .txt):");
							}
							catch(ArrayIndexOutOfBoundsException e)
							{
								System.out.print("The contents of the file are incorrect, correct them and try again, or try with another file:");
							}
							
								}
					break;
				case 2:
					System.out.print("\nChoose file to import... > ");
					done=false;
					while(!done) {
					try
					{
						path = keyboard.next();
						Network.importRelationships(path);
						System.out.println("Relationships imported successfully"); //TODO it can load a friends file too, should we prevent that?
						done=true;
					}
					catch(FileNotFoundException e)
					{
						System.out.println("Incorrect file name");
					}
					catch(ArrayIndexOutOfBoundsException e)
					{
						System.out.print("The contents of the file are incorrect, correct them and try again, or try with another file:");
					}
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
