import java.util.*;

public class StackBoxDriver 
{

	public static void main(String[] args) 
	{
		StackBox<Integer> stack = new StackBox<Integer>();
		boolean exit = false;
		
		while(!exit)
		{
			int choice;
			Scanner scan = new Scanner(System.in);
			System.out.println("=[==[==[=[=================================]=]==]==]=");
			System.out.println("=[==[==[=[     Welcome to StackBox         ]=]==]==]=");
			System.out.println("=[==[==[=[=================================]=]==]==]=");
			System.out.println();
			System.out.println("Choose...");
			System.out.println("1. Push an (Integer) to our stack box.");
			System.out.println("2. Pop a (Integer) off our stack box.");
			System.out.println("3. Peek at top of the stack.");
			System.out.println("4. Exit program");
			choice = scan.nextInt();
			
			try
			{
				if(choice == 1)
				{
					int entry;
					Scanner s = new Scanner(System.in);
					System.out.println();
					System.out.println("Enter an Integer you want pushed onto the stack: ");
					entry = s.nextInt();
					System.out.println();
					System.out.println(stack.push(entry)+" was pushed onto the stack.");
					System.out.println();
					exit = false;
				}
				
				else if(choice == 2) 
				{
					System.out.println("The element "+stack.pop()+" was removed from the top the stack.");
					System.out.println();
					exit = false;
				}
				
				else if(choice == 3) 
				{
					System.out.println("Element currently at the top of the stack: "+stack.peek());
					System.out.println();
					exit = false;
				}
					
				else if(choice == 4) 
				{
					exit = true;
				}
			}
			
			catch(EmptyStackException e)
			{
				System.out.println("Stack is empty. Caught EmptyStackException.");
			}
		}
		
		System.out.println("Program terminated.");
	}

}
