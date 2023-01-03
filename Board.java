import java.util.*;

public class Board<T> 
{
	public Stack<T>[] points = (Stack<T>[])(new Stack[26]);
	
	public int Wpieces = 15, Bpieces = 15, wbStones = 0, bbStones = 0;
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_WHITE = "\u001B[37m"; 
	public static final String BG_ANSI_WHITE = "\u001B[47m";
	public static final String BG_ANSI_BLACK = "\u001B[40m";
	
	public void StartingSpots(T Piece1, T Piece2) // This method will put all the pieces in the proper starting spots
	{
		for(int i = 0; i < 26; i++)
			points[i] = new Stack<T>();
		
		for(int i = 1; i <= 15; i++) // Putting all white pieces into starting positions
			if(i >= 1 && i < 3)
				points[1].push(Piece1);
			else if(i >= 3 && i < 8)
				points[12].push(Piece1);
			else if(i >= 8 && i < 11)
				points[17].push(Piece1);
			else if(i >= 11 && i <= 15)
				points[19].push(Piece1);
		
		for(int i = 1; i <= 15; i++) // Putting all black pieces into starting positions
			if(i >= 1 && i < 6)
				points[6].push(Piece2);
			else if(i >= 6 && i < 9)
				points[8].push(Piece2);
			else if(i >= 9 && i < 14)
				points[13].push(Piece2);
			else if(i >= 14 && i <= 15)
				points[24].push(Piece2);
	}
	
	public void PrintBoard(T Wpiece, T Bpiece)
	{
		int index = 0;
		
		System.out.println();
		System.out.println("***** [ THE BOARD ] *****");
		
		for(int i = 0; i < points.length; i++)
		{
			System.out.print(i+":");
			
			for(int j = 0; j < points[index].size(); j++)
			{
					if(points[index].peek() == Wpiece)
						System.out.print(BG_ANSI_WHITE+ ANSI_BLACK +points[index].peek() + ANSI_RESET);
					else
						System.out.print(BG_ANSI_BLACK+ ANSI_WHITE +points[index].peek() + ANSI_RESET);
			}
			
			System.out.println();
			index++;
		}
	}
	
	public void PrintStats()
	{
		System.out.println();
		System.out.println("*****[ THE STATS ]*****");
		System.out.println("Human player has "+wbStones+" stones in bar");
		System.out.println("Computer player has "+bbStones+" stones in bar");
		System.out.println("Human player has "+points[25].size()+" stones at home point 25");
		System.out.println("Computer player has "+points[0].size()+" stones at home point 0");
	}
	
	public boolean CheckWin() // This method checks whether the computer or the player has won the game and is the condition in the while loop in the driver class
	{
		if(points[0].size() == Bpieces)
		{
			System.out.println("The Computer has won the game!");
			return true;
		}
		
		else if(points[25].size() == Wpieces)
		{
			System.out.println("The Player has won the game!");
			return true;
		}
		
		else
			return false;
	}
	
	public boolean PlayerisOnBar() // Returns true if the player has stones on the bar
	{
		if(wbStones > 0)
			return true;
		else
			return false;
	}
	
	public boolean ComputerisOnBar() // Returns true if the computer has stones on the bar 
	{
		if(bbStones > 0)
			return true;
		else
			return false;
	}
	
	public void ComputerTurn(T Wpiece, T Bpiece)
	{
		int dice1, dice2;
		boolean ValidMove1 = false, ValidMove2 = false;
		Random rand = new Random();
		dice1 = rand.nextInt(6) + 1;
		dice2 = rand.nextInt(6) + 1;
		
		System.out.println();
		System.out.println("*******************************");
		System.out.println("*        COMPUTER MOVE          *");
		System.out.println("*******************************");
		System.out.println();
		System.out.println();
		System.out.println("***** [ THE ROLL ] *****");
		System.out.println("==> DICE #1 player rolled a "+dice1);
		System.out.println("==> DICE #2 player rolled a "+dice2);
		
		if(ComputerisOnBar() && dice1 != dice2)
			System.out.println("The Computer still has "+bbStones+" stones on the bar! Loss of moves");
		
		else if(ComputerisOnBar() && dice1 == dice2)
		{
			bbStones--;
			System.out.println("The Computer rolled doubles! 1 black stone was taken off the bar!");
			System.out.println("The Computer still has "+bbStones+" stones on the bar!");
		}
		
		else
		{
			for(int i = 0; i < points.length; i++)
			{
				if(points[i].isEmpty() == false && points[i].peek() != Wpiece && points[i].peek() == Bpiece) // Goes through the board until it finds a spot with a black piece 
				{
					if(i - dice1 < 0) // Can't move to a spot outside the board
						continue;
						
					else if(points[i - dice1].isEmpty() == false && points[i - dice1].peek() == Wpiece && points[i - dice1].size() > 0)  // Can't move to a spot with multiple white pieces on it
						continue;
						
					else if(points[i - dice1].isEmpty() == false && points[i - dice1].peek() == Bpiece) // Can move to a spot that has black pieces on it
					{
						points[i - dice1].push(points[i].pop());
						System.out.println("The Computer moves a stone from point "+i+" to point "+(i - dice1)+"(DICE#1)");
						ValidMove1 = true;
						break;
					}
						
					else if(points[i - dice1].isEmpty() == false && points[i - dice1].peek() == Wpiece && points[i - dice1].size() == 1) // Happens if the computer can capture a stone
					{
						points[i - dice1].pop();
						points[i - dice1].push(points[i].pop());
						++wbStones;
						--Wpieces;
						System.out.println("The Computer moves a stone from point "+i+" to point "+(i - dice1)+"(DICE#1)");
						System.out.println("A white stone has been bumped to the bar!");
						ValidMove1 = true;
						break;
					}
						
					else if(points[i - dice1].isEmpty())  // Can move to an empty spot
					{
						points[i - dice1].push(points[i].pop());
						System.out.println("The Computer moves a stone from point "+i+" to point "+(i - dice1)+"(DICE#1)");
						ValidMove1 = true;
						break;
					}
				}
			}
			
			if(ValidMove1 == false)
				System.out.println("No valid moves were possible with DICE#1");
			
			for(int i = 0; i < points.length; i++)
			{
				
				if(points[i].isEmpty() == false && points[i].peek() == Bpiece) // Goes through the board until it finds a spot with a black piece
				{
					if(i - dice2 < 0) // Can't move to a spot outside the board
						continue;
						
					else if(points[i - dice2].isEmpty() == false && points[i - dice2].peek() == Wpiece && points[i - dice2].size() > 0) // Can't move to a spot with multiple white pieces on it
						continue;
						
					else if(points[i - dice2].isEmpty() == false && points[i - dice2].peek() == Bpiece) // Can move to a spot that has black pieces on it
					{
						points[i - dice2].push(points[i].pop());
						System.out.println("The Computer moves a stone from point "+i+" to point "+(i - dice2)+"(DICE#2)");
						ValidMove2 = true;
						break;
					}
						
					else if(points[i - dice2].isEmpty() == false && points[i - dice2].peek() == Wpiece && points[i - dice2].size() == 1) // Happens if the computer can capture a stone
					{
						points[i - dice2].pop();
						points[i - dice2].push(points[i].pop());
						++wbStones;
						--Wpieces;
						System.out.println("The Computer moves a stone from point "+i+" to point "+(i - dice2)+"(DICE#2)");
						System.out.println("A white stone has been bumped to the bar!");
						ValidMove2 = true;
						break;
					}
						
					else if(points[i - dice2].isEmpty()) // Can move to an empty spot
					{
						points[i - dice2].push(points[i].pop());
						System.out.println("The Computer moves a stone from point "+i+" to point "+(i - dice2)+"(DICE#2)");
						ValidMove2 = true;
						break;
					}
							
				}
			}
			
			if(ValidMove2 == false)
				System.out.println("No valid moves were possible with DICE#2");
		}
		
		PrintBoard(Wpiece, Bpiece);
		PrintStats();
	}
	
	public void PlayerTurn(T Wpiece, T Bpiece)
	{
		int dice1, dice2, decision1, decision2;
		Random rand = new Random();
		Scanner scan = new Scanner(System.in);
		dice1 = rand.nextInt(6) + 1;
		dice2 = rand.nextInt(6) + 1;
		
		System.out.println();
		System.out.println("*******************************");
		System.out.println("*        PLAYER MOVE          *");
		System.out.println("*******************************");
		System.out.println();
		System.out.println();
		System.out.println("***** [ THE ROLL ] *****");
		System.out.println("==> DICE #1 player rolled a "+dice1);
		System.out.println("==> DICE #2 player rolled a "+dice2);
		
		if(PlayerisOnBar() && dice1 != dice2)
			System.out.println("The Player still has "+wbStones+" stones on the bar! Loss of moves");
		
		else if(PlayerisOnBar() && dice1 == dice2)
		{
			wbStones--;
			System.out.println("You rolled doubles! 1 white stone was taken off the bar!");
			System.out.println("The Player still has "+wbStones+" stones on the bar!");
		}
		
		else
		{
			System.out.println("MOVE a stone "+dice1+" moves. (DICE #1)");
			System.out.println("Move which stone? which point? (1-24): ");
			decision1 = scan.nextInt();
		
			try
			{
				if(points[decision1].isEmpty()) // If the spot picked has no stones, it is an illegal move
				{
					System.out.println("Illegal move. Loss of turn.");
				}
			
				else if(points[decision1].peek() == Bpiece) // If the spot picked has a black stone, it is an illegal move
				{
					System.out.println("Illegal move. Loss of turn.");
				}
		
				else if(points[decision1].peek() == Wpiece && points[decision1+dice1].isEmpty() == false && points[decision1+dice1].peek() == Bpiece && points[decision1+dice1].size() > 1) // Can't move to a spot with more than one black stone
				{
					System.out.println("Illegal move. Loss of turn.");
				}
			
				else if(points[decision1].peek() == Wpiece && points[decision1+dice1].isEmpty() == false && points[decision1+dice1].peek() == Wpiece  && points[decision1+dice1].size() > 0) 
				{
					points[decision1+dice1].push(points[decision1].pop());
				}
		
				else if(points[decision1+dice1].isEmpty() && points[decision1].peek() == Wpiece)
				{
					points[decision1+dice1].push(points[decision1].pop());
				}
		
				else if(points[decision1+dice1].peek() == Bpiece && points[decision1].peek() == Wpiece && points[decision1+dice1].size() == 1) // This is what happens if a black stone is captured
				{
					points[decision1+dice1].pop();
					points[decision1+dice1].push(points[decision1].pop()); 
					++bbStones;
					--Bpieces;
					System.out.println("A black stone has been bumped to the bar!");
				}
			}
		
			catch(Exception e) // Will catch any ArrayIndexOutOfBounds exception in case an invalid spot is picked
			{
				System.out.println("Illegal move. Loss of turn.");
			}
		
			PrintBoard(Wpiece, Bpiece);

			System.out.println("MOVE a stone "+dice2+" moves. (DICE #2)");
			System.out.println("Move which stone? which point? (1-24): ");
			decision2 = scan.nextInt();
		
			try
			{
				if(points[decision2].isEmpty()) // If the spot picked has no stones, it is an illegal move
				{
					System.out.println("Illegal move. Loss of turn.");
				}
			
				else if(points[decision2].peek() == Bpiece) // If the spot picked has a black stone, it is an illegal move
				{
					System.out.println("Illegal move. Loss of turn.");
				}
		
				else if(points[decision2].peek() == Wpiece && points[decision2+dice2].isEmpty() == false && points[decision2+dice2].peek() == Bpiece && points[decision2+dice2].size() > 1) // Can't move to a spot with more than one black stone
				{
					System.out.println("Illegal move. Loss of turn.");
				}
			
				else if(points[decision2].peek() == Wpiece && points[decision2+dice2].isEmpty() == false && points[decision2+dice2].peek() == Wpiece  && points[decision2+dice2].size() > 0)
				{
					points[decision2+dice2].push(points[decision2].pop());
				}
		
				else if(points[decision2+dice2].isEmpty() && points[decision2].peek() == Wpiece)
				{
					points[decision2+dice2].push(points[decision2].pop());
				}
		
				else if(points[decision2+dice2].peek() == Bpiece && points[decision2].peek() == Wpiece && points[decision2+dice2].size() == 1) // This is what happens if a black stone is captured
				{
					points[decision2+dice2].pop();
					points[decision2+dice2].push(points[decision2].pop()); 
					++bbStones;
					--Bpieces;
					System.out.println("A black stone has been bumped to the bar!");
				}
			}
		
			catch(Exception e) // Will catch any ArrayIndexOutOfBounds exception in case an invalid spot is picked
			{
				System.out.println("Illegal move. Loss of turn.");
			}
		}
		
		PrintBoard(Wpiece, Bpiece);
		PrintStats();
	}
	
}
