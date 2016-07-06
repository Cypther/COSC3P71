/**This is the TrainingData Class
 * 
 * This class is use for training the neural network
 * 
 * @author Long Nguyen & Chang Ding
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */

public final class TrainingData {
	
	/**
	 * The correct parity value for the binary data
	 */
	
	private static double[] expectedOutputs = {
			0,
			1,
			1,
			0,		
			1,
			1,
			0,
			0,
			1,
			1,
			0,
			0,
			1,
			1,
			0,
			0
		};
	
	/**
	 * Binary data input from 0 to 15
	 */
	private static double inputs[][] = { 
			
			{0,0,0,0},
	  		{0,0,1,0},
    		{1,1,1,0},
    		{0,0,1,1},
    		{0,1,0,0},
    		{1,0,1,1},
    		{0,1,0,1},
    		{0,1,1,0},
    		{0,1,1,1},
    		{1,0,0,0},
    		{1,0,0,1},
    		{1,0,1,0},
    		{1,1,0,1},
    		{0,0,0,1},
    		{1,1,1,1},
    		{1,1,0,0} 
			
	
	
	};
	
	/*getters*/
	public static int getSize(){
		return inputs.length;
	}
	
	public static double getOutput(int i) {
		return expectedOutputs[i];
	}
	
	public static double[] getOutputArray() {
		return expectedOutputs;
	}
	
	public static double[] getInput(int i) {
		return inputs[i];
	}
	    
	public static double[][] getInputArray() {
		return inputs;
	} 
	
	
	

}
