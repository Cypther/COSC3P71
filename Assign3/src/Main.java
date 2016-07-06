/**This is the Main Class
 * 
 * This class keep track of the weight values 
 * by storing the information of the next and previous neuron
 * 
 * The default value for epoch is 10000 and the min error is 0.001
 * 
 * @author Long Nguyen & Chang Ding
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */

import java.util.Scanner;

public class Main {

	private static Scanner in;
	public static double _learningRate;
	int trainingTime;
	public Main() {
		in = new Scanner(System.in);
		int seed;
		seed = 2;
		// read the learning rate from the console
		System.out.println("Please enter your learning rate:");
		while (!in.hasNextDouble()) {
			in.next();
			System.err.println("Invalid Value\n");
			System.out.println("Please enter your learning rate:");
		}
		_learningRate = in.nextDouble();
		//test learning rate
		if (_learningRate >= 0 && _learningRate <= 1) {
			// set the seed
			RandomSeed.initSeed(seed);
			// training time set to be 500
			trainingTime = 1000;
			double minErrorCondition = 0.001;
			// create network, 16 hidden layout
			NeuralNetwork netWork = new NeuralNetwork(4, 16, 1);
			// training the network
			netWork.trainNetWork(trainingTime, minErrorCondition);
			// test the network
			netWork.testNetWork();
		} else {
			//error handling
			System.err.println("Learning Rate must be between 0 and 1\n");
			System.out.println("Program ends.");
		}
		
		System.out.println("Input Layer: 4, Hidden Layer: 16, Output Layer: 1");
		System.out.println("Learning Rate: "+_learningRate);
		
	}

	public static void main(String[] args) {
		new Main();
	}

}
