/**This is the Class chessTree
 * That does the Blind Search recursively
 * It does is by Depth-first search
 * Search down the tree, if there's not solution
 * goes back to the last parent and search a 
 * different child node
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (October 2014)
 * Compiler Version Java 1.7
 */

public class ChessTree {
	
	public Node root;
	public boolean solution;
	public boolean flag; 
	int NumberOfQueens;
	
	private long currentTime = 0;
	private long endTime = 0;
	
	public long totalTime = 0;
	
	public boolean queenRandomPos;

    /*Default Constructor*/
	public ChessTree(){
		root = null;
		solution = false;
		queenRandomPos = true;
		root = create(root, 8);
		flag = true;
		
		this.currentTime = System.currentTimeMillis();
		searchTree(root);
		
		this.totalTime = endTime - currentTime;
		
	}
	
	/*Overloaded constructor*/
	public ChessTree(int NumberOfQueens){
		root = null;
		solution = false;
		queenRandomPos = true;
		this.NumberOfQueens = NumberOfQueens;
		
		root = create(root);
		flag = true;
		
		this.currentTime = System.currentTimeMillis();
		searchTree(root);
		this.totalTime = endTime - currentTime;
		
	}
	
	 public Node create(Node node){
    //if node is null create new node
		 if(node == null){
			 node = new Node(0, -1, null);
    	  }
		 //printNode(node);
		 return node;
	 }
	 
	 public Node create(Node node, int NumberOfQueens){
		    //if node is null create new node
				 if(node == null){
					 node = new Node( 0, -1, null);
		    	  }
				 //printNode(node);
				 return node;
			 }
	/**
	 * 
	 * searchTree(); This is the blind Search
	 * recursively search every path until it finds a solution  location
	 * 
	 * */
	 
	 
    public void searchTree(Node node)
    {   
    	if(solution == true){
    		return;
    	}
 
        if (node.getColumn() == this.NumberOfQueens) // last line (=number of queens) reached: solution
        {
        	this.endTime = System.currentTimeMillis();
        	// print solutions
            PrintSolution(node); 
            printBoard(node);
            solution = true;
            return;
        }
        for (int rowNumber = 0; rowNumber < this.NumberOfQueens; rowNumber++) // try all rows in next line
        {
    
        	node.setNumberOfAttempts(); //counting the attempts to find a solution
        	
        	/*
        	 * Setting the queen Above 
        	 *  To try all rows in next line
        	 * */
        	
        	node.setQueenAbove(node); 
   
        	/*
        	 *  Looping and checking for all queens in previous lines
        	 *  if the move is valid 
        	 */
            while(validMove(node,rowNumber)){
            	node.setQueenAbove(node.getQueenAbove().getParent()); 
       	    }
        	
         /*
          * If no threat found, we are back at the top of the 
          * recursion tree means we are at column 0
          */
            if (node.getQueenAbove().getColumn() == 0){ 

            	//if want the starting location to be in a differentt location
            	if(queenRandomPos == true){
            		queenRandomPos = false;
            		Node path = new Node(1, 0, node);
            		 //System.out.println("First recursion call ");
            		searchTree(path);
            	}
            	    //Setting this queen at the parent node and recursive the tree
            		Node path = new Node(node.getColumn() + 1, rowNumber, node);
            		searchTree(path);
           	 
            }

        }
    }
    
    /*
     * This method checks if the moves are valid 
     * 
     * */
    
	public boolean validMove(Node node, int rowNumber){
		//if the queen above is not a negative number
		if(!(node.getQueenAbove().getRow() >= 0)){
			return false;
		}
		// checking if there's a queen in vertical location
		if(!(rowNumber != node.getQueenAbove().getRow())){
			return false;
		}
		//checking if there's a queen in diagonal left
		if(!(rowNumber - node.getQueenAbove().getRow() != node.getColumn() + 1 - node.getQueenAbove().getColumn())){
			int x = rowNumber - node.getQueenAbove().getRow();
			int y = node.getColumn() + 1 - node.getQueenAbove().getColumn();
			return false;
		}
		//checking if there's a queen in diagonal right
		if(!(node.getQueenAbove().getRow() - rowNumber != node.getColumn() + 1 - node.getQueenAbove().getColumn())){
			int x = node.getQueenAbove().getRow() - rowNumber;
			int y = node.getColumn() + 1 - node.getQueenAbove().getColumn();
			
			return false;
		}else{
			return true;
		}

	}
    
	 public void printNode(Node node){ 
		 
		 System.out.println("How many Queens " + this.NumberOfQueens);
		 System.out.println("What is getColumn() " + node.getColumn());
		 System.out.println("How many Attempts " + node.getNumberOfAttempts());
		 System.out.println("How many Nodes " + node.getNumberOfNodes());
		 System.out.println("How many Solutions " + node.getNumberOfSolutions());
		 System.out.println("What is getRow() " + node.getRow());
		 System.out.println();
		 
		 if(node.getQueenAbove() != null){
			 System.out.println("What is QueenAbove " + node.getQueenAbove().getRow());
		 }
		 if(node.getParent() != null){
			 System.out.println("What is Parent " + (char)('a' + node.getParent().getRow()));
		 }
		 System.out.println();
	 }
	
	
	/*
	 * Printing out the solution Path
	 * only what row number the queen is on
	 * */
    private void PrintSolution(Node Node)
    {
    	System.out.println();
    	System.out.print("The row of each queen: ");
        Node.NumberOfSolutions++;
        while (Node.getRow() >= 0)
        {
        	System.out.print((Node.getRow() + ", "));
            Node = Node.getParent();
        }
        System.out.println();
 
    }
    
    
	/*
	 * Printing out the solution of the chessboard
	 * only what row number the queen is on
	 * */
	public void printBoard(Node Node){
		
		while (Node.getRow() >= 0)
        {
		for(int j = 0; j < this.NumberOfQueens; j++){
			if(Node.getRow() == j){
				System.out.print("1" + " ");
			}else{
				System.out.print("0" + " ");
			}
				//System.out.print(this.get(j,k) + " ");
		}
		//System.out.print("Column: " + Node.getColumn());
		Node = Node.getParent();
		System.out.println("");
        }
		System.out.println("");
	}

}