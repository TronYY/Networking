package CN3;
public class NodeStack<E> implements Stack<E>{
	protected Node<E> top;
	protected int size;
	public NodeStack(){
		top=null;
		size=0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return top==null;
	}
	public E top() throws EmptyStackException{
		if (isEmpty()) throw new EmptyStackException("Stack is empty.");
		return top.getElement();
	}
	public void push(E element) throws FullStackException {
		Node<E> v=new Node<E>(element,top);
		top=v;
		size++;	
	}
	public E pop() throws EmptyStackException {
		if (isEmpty()) throw new EmptyStackException("Stack is empty!");
		E topEle;
		topEle=top.getElement();
		top=top.getNext();
		size--;
		return topEle;
	}
}

	
