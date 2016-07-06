/**
 * This is the A* chessBoard class,
 * each chessboard will be a node for the 
 * A* search algorithm 
 * @author Long Nguyen
 * 
 * @version 1.0 (October 2015) Compiler Version Java 1.7
 */

public class AStarChessBoardNode<E> {

	private AStarChessBoardNode parent;
	private int[][] chessBoard;
	private int heuristicCost = 0;
	private Queen[] Queens;
	private int boardSize;

	/* Default Constructor */
	public AStarChessBoardNode() {
		parent = null;
	}

	/* Overloaded constructor with one parameter */
	public AStarChessBoardNode(int[][] board) {

		this.boardSize = board.length;
		this.chessBoard = new int[board.length][board.length];
		this.chessBoard = copyingBoard(board);
		Queen[] checkQueen = new Queen[board.length];
		checkQueen = checkWorstQueen(board);
		this.heuristicCost = calculateHeuristicCost(checkQueen);
	}

	/* Overloaded constructor with two parameter */
	public AStarChessBoardNode(AStarChessBoardNode parent, int[][] board) {
		this.parent = parent;
		this.chessBoard = new int[board.length][board.length];
		this.chessBoard = copyingBoard(board);
		Queen[] checkQueen = new Queen[board.length];
		checkQueen = checkWorstQueen(board);
		this.heuristicCost = calculateHeuristicCost(checkQueen);

	}

	/* The goal State when no Queens are Attacking each other */
	public boolean noQueensAttacking() {
		for (int i = 0; i < this.Queens.length; i++) {
			if (Queens[i] != null && Queens[i].getAttackQueen() != 0) {
				return false;
			}
		}
		/*Display the final board and return true when 
		 * no queens are attacking */
		//displayBoard(this.chessBoard);
		return true;
	}

	
	public String printBoardData() {
		String stringBoard = "";

		for (int i = 0; i < chessBoard.length; i++) {
			for (int j = 0; j < chessBoard.length; j++) {
				if (chessBoard[i][j] == 1) {
					// System.out.print(i);
					stringBoard += "{"+ i +","+  j + "}";
				}
			}
		}
		stringBoard += " Heuristic Cost: " + this.heuristicCost;
		return stringBoard;
	}

	/*
	 * Checking Up, Down, Diagonal, if there's another Queen attacking
	 * this.Queen being pass in
	 * 
	 */

	public int queensThreatening(int x, int y, Queen locationOfQueen) {

		int attackCounter = 0;

		for (int j = 0; j < chessBoard.length; j++) {
			// search horizontal
			if (checkQueen(j, y, locationOfQueen) == 1) {
				attackCounter++;
			}
			// search vertical
			if (checkQueen(x, j, locationOfQueen) == 1) {
				attackCounter++;
			}
		}
		// Check Diagonal
		for (int j = 0; j < chessBoard.length; j++) {
			// System.out.println(j);

			// down left Diagonal
			if (checkQueen(x - j, y - j, locationOfQueen) == 1) {
				attackCounter++;
			}
			// up left Diagonal
			if (checkQueen(x - j, y + j, locationOfQueen) == 1) {
				attackCounter++;
			}
			// down right Diagonal
			if (checkQueen(x + j, y - j, locationOfQueen) == 1) {
				attackCounter++;
			}
			// up right Diagonal
			if (checkQueen(x + j, y + j, locationOfQueen) == 1) {
				attackCounter++;
			}
		}
		return attackCounter;
	}

	/*
	 * Checking if there's a Queen in the location being check and that's it's
	 * not outside the bounds of the array
	 */

	public int checkQueen(int x, int y, Queen locationOfQueen) {

		if (x < 0 || y < 0 || x > chessBoard.length - 1 || y > chessBoard.length - 1) {
			// System.out.println("Something wrong in get");
			return -1;
		}
		// checking if it's not the same queen
		if (locationOfQueen.posX == x && locationOfQueen.posY == y) {
			return -1;
		}
		// return the 1
		return chessBoard[x][y];
	}

	/*
	 * Checking on the board to see what is the Worst Queens with the highest
	 * Attack, Placing all the Queens in an array and returning it
	 * 
	 */

	public Queen[] checkWorstQueen(int[][] randomQueenBaord) {

		int numberQueens = 0;
		int numbersOfAttactks = 0;

		this.Queens = new Queen[randomQueenBaord.length];

		for (int i = 0; i < randomQueenBaord.length; i++) {
			for (int j = 0; j < randomQueenBaord.length; j++) {

				// if there's a queen at this locations
				if (randomQueenBaord[i][j] == 1) {
					/*
					 * create a new Queen and add it to the Queen array
					 */
					Queens[numberQueens] = new Queen(i, j);
					// Check the number of Attack to the queen
					numbersOfAttactks = queensThreatening(i, j, Queens[numberQueens]);
					Queens[numberQueens].setAttackQueen(numbersOfAttactks);
					numberQueens++;
				}
			}
		}

		return this.Queens;
	}

	/*
	 * This method calculate the Heuristic Value of the Chessboard, the values
	 * are arbitrary 7 points for a Queen with no attacks, 3 points for a
	 * Queens with one attack 1 point for a Queen with 2 attacks, 0 for a Queen
	 * with 3 or more attacks
	 * 
	 */

	public int calculateHeuristicCost(Queen[] queenArray) {

		int hValue = chessBoard.length;

		int AttackQueenCounter = 0;

		// queenArray is a global variable
		Queen[] worstQueenAttack = queenArray; // number of queens attacking

		for (int i = 0; i < worstQueenAttack.length; i++) {

			if (worstQueenAttack[i].getAttackQueen() == 0) {
				AttackQueenCounter += 7;
			}
			if (worstQueenAttack[i].getAttackQueen() == 1) {
				AttackQueenCounter += 3;
			}
			if (worstQueenAttack[i].getAttackQueen() == 2) {
				AttackQueenCounter += 1;
			}
			if (worstQueenAttack[i].getAttackQueen() >= 3) {
				AttackQueenCounter += 0;
			}
		}

		return AttackQueenCounter;

	}

	/*
	 * Finding the Worst Queen on the Chess board, base on the highest Attacks
	 */
	public Queen[] findTheWorstQueen(Queen[] queenArray) {

		int highestAttack = Integer.MIN_VALUE;
		int numberQueens = 0;
		Queen[] worstQueens;
		int tempNumberQueens = 0;

		// Find the worst queen
		for (int i = 0; i < queenArray.length; i++) {
			if (queenArray[i] == null) {
				return null;
			}
			// find the highest Attack
			if (queenArray[i].getAttackQueen() >= highestAttack) {
				highestAttack = queenArray[i].getAttackQueen();
			}
		}

		// Count how many queens with the same Attack
		for (int i = 0; i < queenArray.length; i++) {
			if (queenArray[i].getAttackQueen() == highestAttack) {
				numberQueens++;
			}
		}

		worstQueens = new Queen[numberQueens];

		// Creating a new Array with the same queen for that attack
		for (int i = 0; i < queenArray.length; i++) {
			if (queenArray[i].getAttackQueen() == highestAttack) {
				worstQueens[tempNumberQueens++] = queenArray[i];
			}
		}
		return worstQueens;

	}

	/*
	 * The method calculate the total number of children needed base on the size
	 * of the board and the number of the worse queens on the board
	 * (((sizeOfBoard*sizeOfBoard)-SizeOfbaord)*NumberOfTheworseQueens)
	 * 
	 */

	public int calculateNumberOfChildren(int numberOfBadQueens, int sizeOfChessBoard) {

		int totalNumberOfChildrenNeeded = 0;

		totalNumberOfChildrenNeeded = (((sizeOfChessBoard * sizeOfChessBoard) - sizeOfChessBoard) * numberOfBadQueens);

		return totalNumberOfChildrenNeeded;
	}

	/*
	 * The method creates number of children base on the Parent board, check for
	 * the worst Queens on the board and the size of the board. Then it calls
	 * the calculate method, it creates an Arrays of AStarChessBoardNode, and
	 * added different chessboard to it, with different combination of each queen in the 
	 * free position. Each chess board has a heuristicCost
	 * value assign to it.
	 */

	public AStarChessBoardNode[] createAStarChildNode() {

		Queen[] checkQueen = new Queen[chessBoard.length];
		checkQueen = checkWorstQueen(chessBoard);

		Queen[] badQueen = findTheWorstQueen(checkQueen);

		int totalNumberOfChildren = calculateNumberOfChildren(badQueen.length, chessBoard.length);

		int howManyChildrenAdded = 0;

		AStarChessBoardNode[] totalChildren = new AStarChessBoardNode[totalNumberOfChildren];

		int[][] tempBoard = new int[chessBoard.length][chessBoard.length];

		//tempBoard = copyingBoard(chessBoard);

		/* Loop base on number of the worse queens */
		for (int numberOfBadQueens = 0; numberOfBadQueens < badQueen.length; numberOfBadQueens++) {
			Queen theBadQueen = badQueen[numberOfBadQueens];
			
			/* Looping through the 2D array (chessboard) */
			for (int i = 0; i < chessBoard.length; i++) {
				for (int j = 0; j < chessBoard.length; j++) {
					
					/* copying the parent board to tempBoard*/
					tempBoard = copyingBoard(chessBoard);
					
					/* if there's a queen already at this location skip this loop*/
					if (boardContainsQueenAtThisLocation(chessBoard, i, j)) {
						continue;
					}
					/* if this is the location of the bad queen on the board skip this loop*/
					if (theBadQueen.posX == i && theBadQueen.posY == j) {
						continue;	
					}else{
						/* removing the bad queen on the tempboard */
						tempBoard = removeQueen(tempBoard, theBadQueen);
						/* adding the new queen to the tempboard */
						tempBoard[i][j] = 1;
						
						/* Create the new board */
						totalChildren[howManyChildrenAdded++] = new AStarChessBoardNode(this, tempBoard);
					}
				}
			}
		}

		return totalChildren;
	}

	/*
	 * Printing out the ChessBoard with the Queens on it
	 */
	public void displayBoard(int[][] board) {

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
	
	public void displayBoard() {

		for (int i = 0; i < this.chessBoard.length; i++) {
			for (int j = 0; j < this.chessBoard.length; j++) {
				if (chessBoard[i][j] == 0) {
					System.out.print("0 ");
				} else {
					System.out.print(chessBoard[i][j] + " ");
				}
			}
			System.out.println();
		}

	}

	/*
	 * Removing the Queens at the location on the chessboard by setting the
	 * location the board to zero
	 */

	public int[][] removeQueen(int[][] tempBoard, Queen badQueen) {

		for (int i = 0; i < tempBoard.length; i++) {
			for (int j = 0; j < tempBoard.length; j++) {
				if (badQueen.posX == i && badQueen.posY == j) {
					// System.out.println("Removed " + i + " and " + j);
					tempBoard[i][j] = 0;
					// badQueenCounter++;
				}
			}
		}
		return tempBoard;

	}

	/*
	 * This method is for copying the chessboard With the same queen location
	 * the chessbaord
	 */

	public int[][] copyingBoard(int[][] boardSouce) {

		int[][] boardCopy = new int[boardSouce.length][boardSouce.length];
		for (int i = 0; i < boardSouce.length; i++) {
			for (int j = 0; j < boardSouce.length; j++) {
				boardCopy[i][j] = boardSouce[i][j];

			}
		}

		return boardCopy;
	}

	/*
	 * Checking if there's a Queen at this location On the chessboard
	 */

	private boolean boardContainsQueenAtThisLocation(int[][] board, int x, int y) {

		if (board[x][y] == 1) {
			return true;
		} else {
			return false;
		}
	}

	/* getters */
	public AStarChessBoardNode getParent() {
		return parent;
	}

	public int getHeuristicCost() {
		return heuristicCost;
	}

	public int[][] getBoard() {
		return chessBoard;
	}

	public int getBoardSize() {
		return this.boardSize;
	}

}