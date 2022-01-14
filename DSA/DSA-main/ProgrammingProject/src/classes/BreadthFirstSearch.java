package classes;
import java.util.Stack;

/**
 * Breadth-first searching algorithm
 * BFS class done from scratch. Taking studied algorithm structure as reference
 */
public class BreadthFirstSearch {

	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] visited;	// stores if nodes have been visited when searching
	private int[] edgeTo;		// stores edges between two nodes
	private int[] distTo;		// stores distances between two nodes
	
	
	
	/**
	 * Search initializer (one source)
	 * @param gr Graph
	 * @param s Source node (starting place)
	 */
	public BreadthFirstSearch(Graph gr, int s) {
		visited = new boolean[gr.getVertices()];
		edgeTo = new int[gr.getVertices()];
		distTo = new int[gr.getVertices()];
		bfs(gr, s);
	}

	
	
	/**
	 * Search initializer (multiple sources)
	 * @param gr Graph
	 * @param sources Multiple source nodes
	 */
	public BreadthFirstSearch(Graph gr, Iterable<Integer> sources) {
		visited = new boolean[gr.getVertices()];
		edgeTo = new int[gr.getVertices()];
		distTo = new int[gr.getVertices()];
		for(int i = 0; i < gr.getVertices(); i++) 
			distTo[i] = INFINITY;
		bfs(gr, sources);
	}
	
	
	
	/**
	 * BFS algorithm for one source
	 * @param gr
	 * @param s
	 */
	private void bfs(Graph gr, int s) {
		Queue<Integer> q = new Queue<Integer>();
		
		for (int i = 0; i < gr.getVertices(); i++)
			distTo[i] = INFINITY;	// set all distances to source as infinity so when ending search, unconnected nodes will remain with current value
		
		distTo[s] = 0;
		visited[s] = true;
		q.enqueue(s);
		
		while(!q.isEmpty()) {	
			int v = q.dequeue();
			
			for(int w : gr.adj(v)) { // check adjacent nodes to v
				if(!visited[w]) {	 // process them if they have not been visited yet  (searched)
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
					visited[w] = true;
					q.enqueue(w);
				}
			}
		}
	}
	
	
	
	/**
	 * BFS algorithm for multiple sources
	 * @param gr
	 * @param sources
	 */
	private void bfs(Graph gr, Iterable<Integer> sources) {
		Queue<Integer> q = new Queue<Integer>();
		for(int s: sources) {
			distTo[s] = 0;
			visited[s] = true;
			q.enqueue(s);
		}
		
		while(!q.isEmpty()) {
			int v = q.dequeue();
			
			for(int w : gr.adj(v)) {
				if(!visited[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
					visited[w] = true;
					q.enqueue(w);
				}
			}
		}
	}
	
	
	
	
	/**
	 * Returns true if there is a valid path from the source node s to given node v.
	 * BFS from source node must have already been executed.
	 * @param v
	 * @return
	 */
	public boolean hasPathTo(int v) {
		return visited[v];
	}
	
	
	/**
	 * Returns distance from source node s to given node v
	 * BFS from source node must have already been executed.
	 * @param v
	 * @return
	 */
	public int distTo(int v) {
		return distTo[v];
	}
	
	
	
	
	/**
	 * Piles a stack of nodes that construct the path between source node s to given node v by backtracking with edgeTo[] variable
	 * @param v
	 * @return
	 */
	public Stack<Integer> pathTo(int v) {
		if(!hasPathTo(v)) 
			return null;
		Stack<Integer> path = new Stack<Integer>();

		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x]) // backtracks using edgeTo[] and pushes nodes that compose the path
			path.push(x);

		path.push(x); // path.push(s); // pushes source
		return path;
	}
}
