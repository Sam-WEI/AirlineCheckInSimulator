
public class Queue<T> {
	
	private Node<T> head;
	private Node<T> tail;
	
	private int size = 0;
	private int maxSize = -1;
	
	public int servedNum = 0;
	
	public Queue(){
		head = null;
		tail = null;				
	}
	
	public synchronized void enqueue(T data){
		Node<T> newNode = new Node<T>(data);
		if(isEmpty()){
			head = newNode;
			head.next = null;
			tail = head;
			notifyAll();
		} else {
			tail.next = newNode;
			tail = newNode;
			tail.next = null;
		}
		size++;
		maxSize = (size > maxSize ? size : maxSize);
	}
	
	public synchronized T dequeue(){
		while(isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		T h = head.data;
		head = head.next;
		size--;
		servedNum++;
		return h;
	}
	
	public synchronized boolean isEmpty(){
		return head == null;
	}
	
	public int getMaxSize(){
		return maxSize;
	}
	
	private static class Node<T> {
		private Node<T> next;
		private final T data;
		public Node(T t){
			this.data = t;
		}
	}
	
	
}
