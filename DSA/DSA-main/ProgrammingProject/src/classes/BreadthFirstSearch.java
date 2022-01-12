package classes;
import java.util.Stack;

/**
 * BFS class done from scratch. Taking studied algorithm structure as reference
 *
 */
public class BreadthFirstSearch {

	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] visited;
	private int[] edgeTo;
	private int[] distTo;
	
	
	
	
	public BreadthFirstSearch(Graph gr, int s) {
		visited = new boolean[gr.getVertices()];
		edgeTo = new int[gr.getVertices()];
		distTo = new int[gr.getVertices()];
		bfs(gr, s);
	}

	
	
	
	public BreadthFirstSearch(Graph gr, Iterable<Integer> sources) {
		visited = new boolean[gr.getVertices()];
		edgeTo = new int[gr.getVertices()];
		distTo = new int[gr.getVertices()];
		for(int i = 0; i < gr.getVertices(); i++) 
			distTo[i] = INFINITY;
		bfs(gr, sources);
	}
	
	
	
	
	private void bfs(Graph gr, int s) {
		Queue<Integer> q = new Queue<Integer>();
		
		for (int i = 0; i < gr.getVertices(); i++)
			distTo[i] = INFINITY;
		
		distTo[s] = 0;
		visited[s] = true;
		q.enqueue(s);
		
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
	
	
	
	
	
	public boolean hasPathTo(int v) {
		return visited[v];
	}
	
	
	
	public int distTo(int v) {
		return distTo[v];
	}
	
	
	
	
	
	public Stack<Integer> pathTo(int v) {
		if(!hasPathTo(v)) 
			return null;
		Stack<Integer> path = new Stack<Integer>();

		int x;
		for (x = v; distTo[x] != 0; x = edgeTo[x])
			path.push(x);

		path.push(x);
		return path;
	}
}
