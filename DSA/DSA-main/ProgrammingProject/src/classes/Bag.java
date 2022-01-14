package classes;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Bag class used in Graph
 *
 * @param <T>
 */
public class Bag<T> implements Iterable<T>{

	private int count;		// number of total nodes in bag
	private Node first;		// pointer to first node (acts like a linked list of nodes)
	
	/**
	 * Link node used for bag (linked list)
	 */
	private class Node {
		private T content;
		private Node next;
		
		/**
		 * Constructor
		 */
		private Node() {
		}
	}
	
	/**
	 * Constructor
	 */
	public Bag() {
		first = null;
		count = 0;
	}
	
	/**
	 * Checks if bag is empty (has no nodes)
	 * @return true if empty
	 */
	public boolean isEmpty() {
		return count == 0;
	}
	
	/**
	 * Checks amount of nodes in bag
	 * @return total
	 */
	public int size() {
		return count;
	}
	
	/**
	 * Adds a node to the bag by shifting all existent bags and placing the new node as first in the linked list that composes the bag
	 * @param content
	 */
	public void add(T content) {
		Node aux = first;
		first = new Node();
		first.content = content;
		first.next = aux;
		count++;
	}
	
	/**
	 * Iterates through the nodes of the bag
	 */
	public Iterator<T> iterator() {
		return new ListIterator();
	}
	
	/**
	 * Iterator of linked-list type
	 *
	 */
	private class ListIterator implements Iterator<T> {
		private Node current = first;
		
		/**
		 * Returns true if iterator pointer finds that there are still unexplored nodes (next node exists)
		 */
		public boolean hasNext() {
			return current != null;
		}
		
	
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		/**
		 * Returns content of next iterated node
		 */
		public T next() {
			if (!hasNext()) 
				throw new NoSuchElementException();
			T out = current.content;
			current = current.next;
			return out;
		}
	}
}
