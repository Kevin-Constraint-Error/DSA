package classes;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item>{

	private int cnt;
	private Node first;
	
	private class Node {
		private Item item;
		private Node next;
		
		private Node() {
			
		}
	}
	
	public Bag() {
		first = null;
		cnt = 0;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public int size() {
		return cnt;
	}
	
	public void add(Item item) {
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
		cnt++;
	}
	
	public Iterator<Item> iterator() {
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item> {
		private Node current = first;
		
		public boolean hasNext() {
			return current != null;
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		public Item next() {
			if (!hasNext()) 
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}
