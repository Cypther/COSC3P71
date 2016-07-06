/**This is the GARandom Class
 * 
 * This class is used for keeping a random seed 
 * consistent throughout the Genetic Algorithm 
 * operation.
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */


import java.util.Random;

public final class GARandomSeed {

	private static Random randSeed;
	
	/* Initialize the random seed */
	public static void initSeed(int seed) {
		randSeed = new Random(seed);
	} 
	
	/* random an int between 0 and max */
	public static int getRandom(int max) {
		return randSeed.nextInt(max);
	}
	
	/*  a random value between 0 and 1  */
	public static double getRandomDouble() {
		return randSeed.nextDouble();
	}
} 