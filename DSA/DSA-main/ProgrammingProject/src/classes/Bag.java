package classes;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Bag class used in Graph
 *
 * @param <T>
 */
public class Bag<T> implements Iterable<T>{

	private int count;
	private Node first;
	
	private class Node {
		private T content;
		private Node next;
		
		private Node() {
			
		}
	}
	
	public Bag() {
		first = null;
		count = 0;
	}
	
	public boolean isEmpty() {
		return count == 0;
	}
	
	public int size() {
		return count;
	}
	
	public void add(T content) {
		Node aux = first;
		first = new Node();
		first.content = content;
		first.next = aux;
		count++;
	}
	
	public Iterator<T> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<T> {
		private Node current = first;
		
		public boolean hasNext() {
			return current != null;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public T next() {
			if (!hasNext()) 
				throw new NoSuchElementException();
			T out = current.content;
			current = current.next;
			return out;
		}
	}
}
