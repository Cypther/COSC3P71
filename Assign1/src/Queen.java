/**This is a  Queen Class
 * This is use with the 
 * AStar ChessboardNode class
 * @author Long Nguyen
 * 
 * @version 1.0 (October 2015)
 * Compiler Version Java 1.7
 */
public class Queen{
	    
    private int attackQueen;
    
    int posX;
    int posY;
    
    int Data;
    
    /*Default Constructor*/
	Queen(){
        this.posX = 0;
        this.posY = 0; 
    }
    
    /*Overloaded constructor*/
	Queen(int x, int y){
        this.posX = x;
        this.posY = y; 
    }
	
	
	//getters
	public int getAttackQueen() {
		return attackQueen;
	}

	public void setAttackQueen(int attack) {
		this.attackQueen = attack;
	}
    
    @Override
    public String toString(){
        return "["+this.posX+", "+this.posY+"]";
    }

}
