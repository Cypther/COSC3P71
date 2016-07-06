/**This is the Generation Class
 * 
 * This class is used to holds the information from 
 * each generation of a population.
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */

public class Generation {
	/* The best fitness of a population */
	public double elite;
	/* The average fitness of a population */
	public double average;
	/* The best distance of a population */
	public double distance;
	
	/* Setting the Constructor */
	public Generation(double elite, double average, double distance) {
		this.elite = elite;
		this.average = average;
		this.distance = distance;
	}
}
