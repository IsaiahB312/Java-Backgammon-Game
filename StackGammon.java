
public class StackGammon 
{	
	public static void main(String[] args) 
	{	
		Board<Character> gameboard = new Board<Character>();
		
		char wpiece = 'W', bpiece = 'B';
		
		gameboard.StartingSpots(wpiece, bpiece);
		gameboard.PrintBoard(wpiece, bpiece);
		gameboard.PrintStats();
		
		while(gameboard.CheckWin() == false)
		{
			gameboard.PlayerTurn(wpiece, bpiece);
			gameboard.ComputerTurn(wpiece, bpiece);
		}
			
			
		System.out.println("GAME OVER!");
			
	}

}
