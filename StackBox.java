import java.util.*;

public class StackBox<T> 
{
	ArrayList<T> stack = new ArrayList<T>();
	private int top = 0;
	
	public boolean empty()
	{
		return stack.isEmpty();
	}
	
	public T push(T element)
	{
		stack.add(element);
		top++;
		return element;
	}
	
	public T pop() throws EmptyStackException
	{
		if(empty())
			throw new EmptyStackException("The stack is currently empty!");
		return stack.remove(--top);
	}
	
	public T peek() throws EmptyStackException
	{
		if(empty())
			throw new EmptyStackException("The stack is currently empty!");
		return stack.get(top-1);
	}
}
