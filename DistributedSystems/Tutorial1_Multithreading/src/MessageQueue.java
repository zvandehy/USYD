import java.util.ArrayList;
import java.util.Vector;

public class MessageQueue<E> implements Channel<E> {
	/*
	MessageQueue implements a Vector because a Vector is thread safe
	it is thread safe in 2 ways:
	1. It's size is dynamic and changes as elements are added to it
	2. It's iterators are "fail-fast" which means that if the Vector is being manipulated in any way
	other than the .add or .remove method (see queue.add and queue.remove below) then a ConcurrentModificationException will throw

	ArrayLists are not fail-fast, and so concurrent manipulation would not be caught and our threads would not be safe
	*/
	private Vector<E> queue;
	
	public MessageQueue() {
		queue = new Vector<E>();
	}

	/*
	Synchronized locks this object when it is being accessed.
	TODO: Play around with this by using a count variable instead of a date
	- Try with Vector without synchronized
	- Try with ArrayList with synchronized
	- Try with ArrayList without synchronized
	 */

	// This implements a synchronized send
	public synchronized void send(E item) {
		queue.add(item);
	}
	
	// This implements a synchronized receive
	public synchronized E receive() {
		if (queue.size() == 0)
			return null;
		else return queue.remove(0);
	}
}