package classes;
import java.util.Stack;

/**
 * Depth-first searching algorithm.
 * DFS class done from scratch. Taking studied algorithm structure as reference
 */
public class DepthFirstSearch {

	private boolean[] visited;	// stores if nodes have been visited when searching
	private int[] edgeTo;		// stores edges between two nodes
	private final int s;		// source vertex (stored as global)

	/**
	 * Search initializer
	 * @param gr Graph
	 * @param s Source node
	 */
	public DepthFirstSearch(Graph gr, int s) {
		this.s = s;
		edgeTo = new int[gr.getVertices()];
		visited = new boolean[gr.getVertices()];
		dfs(gr, s); // start recursion
	}

	
	/**
	 * DFS algorithm (recursive function)
	 * @param gr
	 * @param v
	 */
	private void dfs(Graph gr, int v) {
		visited[v] = true;
		for (int w : gr.adj(v)) {
			if(!visited[w]) {
				edgeTo[w] = v;
				dfs(gr, w);
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
	 * Piles a stack of nodes that construct the path between source node s to given node v by backtracking with edgeTo[] variable
	 * @param v
	 * @return
	 */
	public Stack<Integer> pathTo(int v) {
		if(!hasPathTo(v)) 
			return null;
		
		Stack<Integer> path = new Stack<Integer>();
		for (int x = v; x != s; x = edgeTo[x]) 
			path.push(x);
		path.push(s);
		return path;
	}
	
}
