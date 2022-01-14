package classes;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.String;

/**
 * Graph class used to represent network for chain calculations
 */
public class Graph {

	private final int vertices;													// total number of users in the network
	private int edges;															// total number of connections in the network
	private Bag<Integer>[] adj;													// array of bags where user connections are stored
																				// 		(one array entry for each user)
	private Hashtable<String, Integer> ht = new Hashtable<String, Integer>();	// Hashtable for mapping user ID with corresponding vertex nodes
	private ArrayList<Friend> users = new ArrayList<Friend>();					// list of users in the network




	@SuppressWarnings("unchecked") // for bag casting
	/**
	 * Constructor with total number of users
	 * @param V
	 */
	public Graph(int V) {
		if (V < 0) 
			throw new IllegalArgumentException("Number of vertices must be positive");

		vertices = V;
		edges = 0;
		adj = (Bag<Integer>[]) new Bag[vertices];
		for (int i = 0; i < vertices; i++) {
			adj[i] = new Bag<Integer>();
		}
	}





	@SuppressWarnings("unchecked") // for bag casting
	/**
	 * Constructor with data text file inputs
	 * @param V
	 * @param path1
	 * @param path2
	 * @throws FileNotFoundException
	 */
	public Graph(int V, String path1, String path2) throws FileNotFoundException {
		vertices = V;

		if (edges < 0)
			throw new IllegalArgumentException("Number of edges must be positive");

		adj = (Bag<Integer>[]) new Bag[vertices];
		for (int i = 0; i < vertices; i++) {
			adj[i] = new Bag<Integer>();
		}
		this.convertData(path1, path2);	// converts input data into a graph
	}




	/**
	 * Returns number of total users
	 * @return
	 */
	public int getVertices() {
		return vertices;
	}

	/**
	 * Returns number of total connections
	 * @return
	 */
	public int getEdges() {
		return edges;
	}




	/**
	 * Returns complete array of user's connections
	 * @return
	 */
	public Bag<Integer>[] getAdj(){
		return adj;
	}




	/**
	 * Adds connection between two given users
	 * @param v
	 * @param w
	 */
	public void addEdge(int v, int w) {
		if (v < 0 || v >= vertices) 
			throw new IndexOutOfBoundsException();
		if (w < 0 || w >= vertices)
			throw new IndexOutOfBoundsException();

		edges++;
		adj[v].add(w);
		adj[w].add(v);
	}




	/**
	 * Iterates through friends of given user
	 * @param v
	 * @return
	 */
	public Iterable<Integer> adj(int v) {
		if (v < 0 || v >= vertices) 
			throw new IndexOutOfBoundsException();

		return adj[v];
	}




	/**
	 * Loads network backup and transforms data into a graph
	 * @param fpath
	 * @param relpath
	 * @throws FileNotFoundException
	 */
	public void convertData(String fpath, String relpath) throws FileNotFoundException {

		String line = "";  

		int i = 0;

		// Creating Scanner instance to read user file in Java
		Scanner input = new Scanner(new File("cliquesDSA2021/" + fpath));

		while (input.hasNextLine()) {  
			line = input.nextLine();
			String[] info = line.split(","); // use comma as separator
			Friend user = new Friend(info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8], info[9], info[10]);
			users.add(user);

			String id = info[0];
			ht.put(id, i);	// Using user id's to map the network
			i++;
		}

		input.close();

		// Reusing Scanner instance to read relationship file in Java
		input = new Scanner(new File("cliquesDSA2021/" + relpath)); 

		while (input.hasNextLine()) {  
			line = input.nextLine();
			String[] rel = line.split(","); // use comma as separator
			if (ht.get(rel[0]) != null && ht.get(rel[1]) != null)	// Discard format from text file to only operate with existent users
				addEdge(ht.get(rel[0]), ht.get(rel[1]));
		}

		input.close();
	}



	// Retrieve vertex of given user
	/**
	 * Retrieves node vertex of given user represented in the graph
	 * @param idUser
	 * @return
	 */
	public int getHash(String idUser) {		
		return ht.get(idUser);
	}



	/**
	 * Retrieves user ID given by their node vertex represented in the graph
	 * @param vertex
	 * @return
	 */
	public String getID(int vertex) {
		for (Map.Entry<String, Integer> entry : ht.entrySet())	// iterates through entire set of vertices
			if (vertex == entry.getValue())
				return entry.getKey();
		return null;
	}
}
