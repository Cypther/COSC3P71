/**This is the Node Class for the chessTree

 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (October 2015)
 * Compiler Version Java 1.7
 */

public class Node {
    
	 public static int NumberOfAttempts;
	 public static int NumberOfNodes;
	 public static int NumberOfSolutions;

	 private Node queenAbove;
     private Node parent;
     
     private Node child;
     
     private int column; 
     private int row;
    
    
    /*Default Constructor*/
    public Node(){
    	
    	this.column = -1;
        this.row = -1;
        this.parent = null;
        this.child = null;
        NumberOfNodes++;
    }
    
    /*Overloaded constructor*/
    public Node( int column, int Row, Node Parent){
        
        this.column = column;
        this.row = Row;
        this.parent = Parent;
        this.child = null;
        NumberOfNodes++;
        
    }
    
    /*Setters and getters*/
    
    //getters
    
    public int getNumberOfSolutions(){
        return NumberOfSolutions;
    }
    
    public int getNumberOfNodes(){
        return NumberOfNodes;
    }
    
    public int getNumberOfAttempts(){
        return NumberOfAttempts;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getColumn(){
        return column;
    }
    
    public Node getParent(){
        return parent;
    }
    
    public Node getChild(){
        return child;
    }
    
    public Node getQueenAbove(){
        return queenAbove;
    }
    /////setters 
    /*method to set the value*/
    
    public void setNumberOfAttempts(){
        this.NumberOfAttempts++;
    }
    
    public void setQueenAbove(Node queenAbove){
        this.queenAbove = queenAbove;
    }
    
    public void setParent(Node Parent){
        this.parent = Parent;
    }
    
    public void setChild(Node Child){
        this.child = child;
    }

}