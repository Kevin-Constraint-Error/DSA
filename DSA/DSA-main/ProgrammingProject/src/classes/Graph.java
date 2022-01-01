package classes;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.String;
	
public class Graph {
	
	private final int vertices;
	private int edges;
	private Bag<Integer>[] adj;
	private Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
	private ArrayList<Friend> users = new ArrayList<Friend>();
	
	
	
	
	@SuppressWarnings("unchecked")
	public Graph(int V) {
		if (V < 0) 
			throw new IllegalArgumentException("Number of vertices must be positive");
		
		this.vertices = V;
		this.edges = 0;
		adj = (Bag<Integer>[]) new Bag[V];
		for (int i = 0; i < V; i++) {
			adj[i] = new Bag<Integer>();
		}
	}
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public Graph(int V, int E, String path1, String path2) throws FileNotFoundException {
		this.vertices = V;
		
		if (E < 0)
			throw new IllegalArgumentException("Number of edges must be positive");
		
		adj = (Bag<Integer>[]) new Bag[V];
		for (int i = 0; i < V; i++) {
			adj[i] = new Bag<Integer>();
		}
		for (int i = 0; i < E; i++) {
			this.convertData(path1, path2);
		}
	}
	
	
	
	
	
	public int getVertices() {
		return vertices;
	}
	
	
	public int getEdges() {
		return edges;
	}
	
	
	
	
	
	public Bag<Integer>[] getAdj(){
		return adj;
	}
	
	
	
	
	
	public void addEdge(int v, int w) {
		if (v < 0 || v >= vertices) 
			throw new IndexOutOfBoundsException();
		if (w < 0 || w >= vertices)
			throw new IndexOutOfBoundsException();
		
		edges++;
		adj[v].add(w);
		adj[w].add(v);
	}

	
	
	
	
	public Iterable<Integer> adj(int v) {
		if (v < 0 || v >= vertices) 
			throw new IndexOutOfBoundsException();
		
		return adj[v];
	}
	
	
	
	
	
	public void convertData(String path, String path2) throws FileNotFoundException {
		
		String line = "";  

		int i = 0;
        //Creating Scanner instance to read File in Java
        Scanner input = new Scanner(new File(path));
        
		while (input.hasNextLine()) {  
			line = input.nextLine();
			String[] info = line.split(","); // use comma as separator
			Friend user = new Friend(info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8], info[9], info[10]);
			users.add(user);
			ht.put(info[0], i);
			i++;
		}
		
		input.close();

		input = new Scanner(new File(path2)); 
		
		while (input.hasNextLine()) {  
			line = input.nextLine();
			String[] rel = line.split(","); // use comma as separator
			if (ht.get(rel[0]) != null && ht.get(rel[1]) != null)
				addEdge(ht.get(rel[0]), ht.get(rel[1]));
		}
		
		input.close();
	}
	
	
	
	
	
	
	public int getHash(String idUser) {
		return ht.get(idUser);
	}
	
}
