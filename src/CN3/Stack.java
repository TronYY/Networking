package CN3;
public interface Stack<E> {
	public int size();
	public boolean isEmpty();
	public E top();
	public void push(E element) throws FullStackException;
	public E pop() throws EmptyStackException;
	
}
