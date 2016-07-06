/**
 * This is the A* class, that does the searching
 * base on each node that was created,
 * each chessboard will be a node for the 
 * A* search algorithm 
 * @author Long Nguyen
 * 
 * @version 1.0 (October 2015) Compiler Version Java 1.7
 */

import java.util.Random;

public class AStar {
	
	/*Class Variables*/
	private AStarChessBoardNode bestBoard;
	private int boardConfigurations = 0;
	
	private PriorityQueue<AStarChessBoardNode> openList;
	private PriorityQueue<AStarChessBoardNode> closedList;
	
	private long currentTime = 0;
	private long endTime = 0;
	public long totalTime = 0;

	/* Default Constructor */
	public AStar() {
		this.boardConfigurations = 0;
		this.currentTime = 0;
		this.endTime = 0;
		this.bestBoard = null;
	}

	
	/*
	 * The A* search algorithm, looks for the best chess board
	 * base on the best Heuristic Value, it sorts the Heuristic
	 * value using the Priority Queue class.
	 * 
	 * */
	
	public void AStarSearch(AStarChessBoardNode board) {

		this.currentTime = System.currentTimeMillis();

		//initialize the open and close list using PriorityQueue
		openList = new PriorityQueue(board.getBoardSize() * board.getBoardSize() * 1000);
		closedList = new PriorityQueue(board.getBoardSize() * board.getBoardSize() * 1000);

		//Add the first board to the open list
		openList.insert(board);

		//While the openList PriorityQueue is not empty 
		while (!openList.isEmpty()) {

			//taking the best Heuristic off the Priority Queue
			this.bestBoard = openList.poll();
			
			//Displaying data if the board size is 15 or greater
			if(board.getBoardSize() >= 9){
				System.out.println("Processing the best Board: " + bestBoard.printBoardData());
			}
			//Adding the best board to the close list Priority Queue
			closedList.insert(this.bestBoard);

			//counter for how many board were use
			this.boardConfigurations++;

			//if this is the end state break the loop
			if (bestBoard.noQueensAttacking()) {
				this.endTime = System.currentTimeMillis();
				this.totalTime = (endTime - currentTime);
				break;
			} else {
				//initialize the child chessboard base on the Parent chessboard
				AStarChessBoardNode[] childrenOfTheParentChessBoard = bestBoard.createAStarChildNode();
				
				//looping through the Child board
				for(int i = 0; i < childrenOfTheParentChessBoard.length; i++){
					//If the Child board is null, or already in the open and close list skip to the next iteration
					if (childrenOfTheParentChessBoard[i] == null || closedList.contains(childrenOfTheParentChessBoard[i]) || openList.contains(childrenOfTheParentChessBoard[i])) {
						continue;
					}
					//if the Heuristic value is greater or equal to the current Best Board, add to the openlist
					if (childrenOfTheParentChessBoard[i].getHeuristicCost() >= bestBoard.getHeuristicCost()) {
						openList.insert(childrenOfTheParentChessBoard[i]);
					}
				}
			}
		}
		/*If the goal has been found*/
		if (bestBoard.noQueensAttacking()) {
			disPlayInformation(bestBoard);
		}
		/*There's if the queue is empty*/
		else{
			System.out.println("No Solution Found! ");
		}

	}

	/*
	 * Displaying the final information for A* Search
	 * 
	 */
	public void disPlayInformation(AStarChessBoardNode bestBoard) {
		System.out.println("The Solution Found! ");
		bestBoard.displayBoard();
		System.out.println("The best Board: " + bestBoard.printBoardData());
		System.out.println(this.boardConfigurations + " number of board configurations were tested.");
		System.out.println("A* took: " + totalTime + " milliseconds to find a solution.");
	}

	/*
	 * Printing the initial chessBoard
	 * 
	 */
	public void printBoard(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == 0) {
					System.out.print("0 ");
				} else {
					System.out.print(board[i][j] + " ");
				}
			}
			System.out.println();
		}

	}

	/*
	 * test board use for debugging  
	 * */
	
	public int[][] boardTesting(int sizeOfBoard) {

		int[][] testBoard = new int[][] { 
			{ 0, 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 1, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 1, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 0, 1, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 1, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 1, 0, 0, 0, 0 } };
			
		return testBoard;

	}
	
/*
 * Creating a random chess board base on the user seed number
 * 
 * */

	public int[][] randomQueen(int sizeOfBoard, int Seed) {

		int[][] randomQueenBoard = new int[sizeOfBoard][sizeOfBoard];
		int SeedX = Seed;
		int SeedY = Seed;
		int queens = 0;
		while (queens < sizeOfBoard) {

			int randX = randInteger(0, sizeOfBoard - 1, SeedX);
			int randY = randInteger(0, sizeOfBoard - 1, SeedY);

			for (int x = 0; x < sizeOfBoard; x++) {

				for (int y = 0; y < sizeOfBoard; y++) {

					if (randX == x && randY == y) {
						// blocked[j][k] = 1;
						if (randomQueenBoard[x][y] != 1) {
							// System.out.println(x +" "+ y);
							randomQueenBoard[x][y] = 1;
							queens++;
							// return true;
						}else{
							//creating a new seed base on the x and y
							SeedX = (SeedY*y)-y;
							SeedY = (SeedX*x)-x;
							}
							System.out.println(SeedX +" "+ SeedY);
						}

					}
				}

		}
		return randomQueenBoard;

	}
	
/*
 * Generating the a random number base on 
 * the user seed and x and y values
 * 
 * */
	public static int randInteger(int min, int max, int Seed) {

		Random rand = new Random();
		
		rand.setSeed(Seed);
		
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}