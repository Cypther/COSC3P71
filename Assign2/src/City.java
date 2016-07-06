/**This is the City Class
 * 
 * This class represents the each city
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */


public class City {
    int positionX;
    int positionY;
    
    /*Default Constructor*/
    public City(){
        this.positionX = 0;
        this.positionY = 0;
    }
    
    /*Overloaded constructor*/
    public City(int x, int y){
        this.positionX = x;
        this.positionY = y;
    }
    
    /* Gets position X */
    public int getXposition(){
        return this.positionX;
    }
    
    /* Gets position Y */
    public int getYposition(){
        return this.positionY;
    }
    
    /* Gets the distance to given city*/
    public double calculateDistance(City city){
        int distanceX = Math.abs(this.getXposition() - city.getXposition());
        int distanceY = Math.abs(this.getYposition() - city.getYposition());
        
        //Pythagorean Theorem
        double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        return distance;
    }

}