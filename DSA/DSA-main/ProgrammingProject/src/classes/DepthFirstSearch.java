package classes;
import java.util.Stack;

public class DepthFirstSearch {

	private boolean[] marked;	//marked[v] 
	private int[] edgeTo;		//
	private final int s;		//source vertex

	public DepthFirstSearch(Graph gr, int s) {
		this.s = s;
		edgeTo = new int[gr.getVertices()];
		marked = new boolean[gr.getVertices()];
		dfs(gr, s);
	}

	private void dfs(Graph gr, int v) {
		marked[v] = true;
		for (int w : gr.adj(v)) {
			if(!marked[w]) {
				edgeTo[w] = v;
				dfs(gr, w);
			}
		}
	}
	
	public boolean hasPathTo(int v) {
		return marked[v];
	}
	
	public Stack<Integer> pathTo(int v){
		if(!hasPathTo(v)) 
			return null;
		
		Stack<Integer> path = new Stack<Integer>();
		for (int x = v; x != s; x = edgeTo[x]) 
			path.push(x);
		path.push(s);
		return path;
	}
	
}
