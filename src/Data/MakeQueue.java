package Data;

import java.util.*;

/*Å¥ »ý¼º*/
public class MakeQueue <T> {
	Vector<T> Queue;
	
	public MakeQueue(){
		Queue=new Vector<T>();
	}
	
	public void enqueue(T data) {
		Queue.add(data);
	}
	
	public T dequeue() {
		return Queue.remove(0);
	}
	
	public T peek() {
		return Queue.elementAt(0);
	}
	
	public boolean isEmpty() {
		return Queue.isEmpty();
	}
	public int size() {
		return Queue.size();
	}
}
