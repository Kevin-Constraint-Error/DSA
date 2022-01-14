package classes;
import java.util.EmptyStackException;

/**
 * Custom queue class used for implemented breadth-first searching algorithm 
 *
 * @param <T>
 */
public class Queue<T> {

	private T[] queue;					// queue of items
	private final int CAPACITY;
	private int front, back, count;
	
	
	
	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked") // for item casting
	public Queue() {
		CAPACITY = 50;
		queue = (T[]) new Object[CAPACITY];
		front = back = count = 0;
	}
	
	
	/**
	 * Adds given element to queue
	 * @param element
	 */
	public void enqueue(T element) {
		if(size() == queue.length)
			expand();
		
		queue[back] = element;
		back = (back + 1) % queue.length;
		count++;
	}
	
	
	/**
	 * Removes and returns element from the front of the queue
	 * @return
	 * @throws EmptyStackException
	 */
	public T dequeue() throws EmptyStackException {
		if(isEmpty()) throw new EmptyStackException();
		
		T result = queue[front];
		queue[front] = null;
		front = (front + 1) % queue.length;
		count--;
		return result;
	}
	
	
	/**
	 * Returns element from the front of the queue without deleting it
	 * @return
	 */
	public T first() {
		if(isEmpty()) throw new EmptyStackException();
		
		return queue[front];
	}
	
	
	/**
	 * Checks if queue is empty (has no items)
	 * @return true if empty
	 */
	public boolean isEmpty() {
		return (count == 0);
	}
	
	
	/**
	 * Checks amount of items in queue
	 * @return total
	 */
	public int size() {
		return count;
	}
	
	
	/**
	 * Expands queue capacity if queue runs out of space.
	 * Necessary since regular arrays are used and they require a determined size to operate
	 */
	@SuppressWarnings("unchecked") // for item casting
	public void expand() {
		T[] expanded = (T[]) new Object[CAPACITY * 2];
		for(int i = 0; i < count; i++) {
			expanded[i] = queue[front];
			front = (front + 1) % queue.length;
		}
		
		front = 0;
		back = count;
		queue = expanded;
	}
}
