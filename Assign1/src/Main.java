/**This is the Main Class
 * 
 * This class test the A* search
 * and the Blind Search
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (October 2015)
 * Compiler Version Java 1.7
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Scanner;

public class Main {
	
	  public void menu() {
		  
		    Scanner in = new Scanner (System.in);
		    System.out.println ( "1) Blind Search \n2) AStar Search \n3) Exit" );
		    
		    switch ( in.nextInt() ) {
		      case 1:
		        System.out.println ( "You picked Blind Search " );
		        BlindSearch();
		        break;
		      case 2:
		        System.out.println ( "You picked AStar Search" );
		        AStar();
		        break;
		      case 3:
		        System.out.println ( "End Program " );
		        System.exit(0);
		        break;
		      default:
		        System.err.println ( "Unrecognized option" );
		        System.exit(0);
		        break;
		    }
	  }

	public static void main(String[] args) {
		
		Main testHarness = new Main();
		testHarness.menu();

	}
	
	public void BlindSearch(){
		
		int boardSize = 0;
		
		 Scanner usrInput = new Scanner(System.in);

			while (boardSize < 1) {
				System.out.print("Enter size of the board:  ");
				try {
					boardSize = Integer.parseInt(usrInput.nextLine());
					if (boardSize > 1 && boardSize < 4) {
						System.out.println("Size should be at least 1 or greater than 3.");
						boardSize = 0;
					}
				} catch (NumberFormatException ne) {
					System.out.println("Invalid input try again.");
					boardSize = -1;
				}
			}

		
		 ChessTree tree = new ChessTree(boardSize);
		 System.out.println("Queens/Squares: " + tree.NumberOfQueens);
		 System.out.println("Attempts: " + Node.NumberOfAttempts);
		 System.out.println("Nodes: " + (Node.NumberOfNodes - 1));
		 System.out.println("Blind Search took: " + tree.totalTime + " milliseconds to find a solution.");
		// System.out.println("Solutions: " + Node.NumberOfSolutions);
		
		
	}
	
	public void AStar(){
		
		int boardSize = 0;
		
		int SeedNumber = 0;
		
		Scanner usrInput = new Scanner(System.in);
		
		while (boardSize < 1) {
			System.out.print("Enter size of the board:  ");
			try {
				boardSize = Integer.parseInt(usrInput.nextLine());
				if (boardSize > 1 && boardSize < 4) {
					System.out.println("Size should be at least 1 or greater than 3.");
					boardSize = 0;
				}
				System.out.print("Enter the Seed to Genrate the Random Queens:  ");
				SeedNumber = Integer.parseInt(usrInput.nextLine());
			} catch (NumberFormatException ne) {
				System.out.println("Invalid input try again.");
				boardSize = -1;
			}
		}
		
		int[][] chessBoard = new int[boardSize][boardSize];
		AStar usingAstarSeach = new AStar();
		
		chessBoard = usingAstarSeach.randomQueen(chessBoard.length,  SeedNumber);
		//board = usingAstarSeach.boardTesting(board.length);
		
		System.out.println();
		System.out.println("The chessBoard with random queens ");
		
		usingAstarSeach.printBoard(chessBoard);
		
		AStarChessBoardNode parent = new AStarChessBoardNode(chessBoard);
		System.out.println();
		System.out.println("Searching with A* ");
		System.out.println();
		
		usingAstarSeach.AStarSearch (parent);
		
		
	}

}
