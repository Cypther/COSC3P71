/**This is a generic PriorityQueue Class
 * That does the Blind Search recursively
 * 
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (October 2015)
 * Compiler Version Java 1.7
 */

class PriorityQueue <E>{
	  // array in sorted order, from max at 0 to min at size-1
	  private int maxSize;
	  private AStarChessBoardNode<E>[] QueueArray;
	  private int numberOfitems;
	 
	  public PriorityQueue(int s) {
	    maxSize = s;
	    QueueArray = new AStarChessBoardNode [maxSize]; //casting generic 
	    numberOfitems = 0;
	  }
	 
	  public void insert(E item) {
	    int i;
	    
	    AStarChessBoardNode itemCell = (AStarChessBoardNode)item;
	   
	 
	     // insert at 0	
	    if (numberOfitems == 0){
	    	QueueArray[numberOfitems++] = (AStarChessBoardNode<E>) item; 
	    }else{
	    	// start at end,
	      for (i = numberOfitems - 1; i >= 0; i--){
	    	  // if new item larger,
	    	  if (itemCell.getHeuristicCost() <= QueueArray[i].getHeuristicCost()){
	    		  QueueArray[i + 1] = QueueArray[i]; // shift upward
	    	  }else{
	    		  break;
	    	  }// if smaller,
	    	  // done shifting
	      }
	      QueueArray[i + 1] = itemCell; // insert it
	      numberOfitems++;
	    }
	  }
	  
	  /*Remove the first in the queue*/
	  public E remove(){
		  E ret; 
	      ret = (E) QueueArray[--numberOfitems];
	      return ret;
	  }
	 
	  /*Look at the last */
	  public E peekMin(){
		  E ret; 
	      ret = (E) QueueArray[numberOfitems - 1];
	      return ret;
	  }
	 
	  public boolean isEmpty(){
	    return (numberOfitems == 0);
	  }
	 
	  public boolean isFull(){
	    return (numberOfitems == maxSize);
	  }
	  
	  /*Check if that Object is in the list */
	  public boolean contains(E item){
		  
		  AStarChessBoardNode itemCell = (AStarChessBoardNode)item;
		  for(int i = 0; i < maxSize; i++){
			  if(itemCell.equals(QueueArray[i])){
				  System.out.println("Equals works! ");
				  return true;
			  }
		  }
		  return false;
		  
	  }
	  
  public int getSize(){
	      return this.maxSize;
	  }
	  
  /*Removing the first in the queue*/
	  public E poll(){
		  E ret; 
		  if(numberOfitems!=0){
			  ret = (E) QueueArray[--numberOfitems];
			  return ret;
		  }
		  return null;
	  }
}