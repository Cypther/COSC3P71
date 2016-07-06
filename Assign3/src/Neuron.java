/**This is the Neuron Class
 * 
 * Each (Neuron) stores an connection arraylist
 * 
 * It uses the class Connection for getting the weights and setting the weights
 * storing the errors and the sum of the imputs
 * 
 * All the methods in the this class are commented for more details 
 * 
 * @author Long Nguyen & Chang Ding
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */

import java.util.*;

public class Neuron {
	
	ArrayList<Connection> Inconnections = new ArrayList<Connection>();
	double inputSum;
	Connection biasConnection;
	private double bias = 1;
	double output;
	double _savedError = 0;

	/* Default Constructor */
	public Neuron() {
		
	}
	
	//the temporary error values getters and setters
	public double getError(){
		return _savedError;
	}
	
	public void setError(double error){
		_savedError = error;
	}

	
	//sigmoid function
	//the output will be 0-1
	double sigmoid(double x) {
		this.inputSum = x;
		double sigmoidCal = 1 / (1 + (Math.exp(-x)));
		return sigmoidCal;
	}

	/**
	 * read a list of neurons
	 * connect them to the THIS neuron as incoming neurons
	 * @param inNeurons
	 */
	public void addIncomingConnections(ArrayList<Neuron> inNeurons) {
		for (int i = 0; i < inNeurons.size(); i++) {
			Connection connection = new Connection(inNeurons.get(i), this);
			Inconnections.add(connection);
		}
	}
	

	/**
	 * Sj = (Wij * Aij) + w0j * bias
	 */
	public void calculateOutput() {
		double sum = 0;
		//loop through all incoming neurons
		for (int i = 0; i < Inconnections.size(); i++) {
			//get a single incoming neuron
			Neuron previousNeuron = Inconnections.get(i).getFromNeuron();
			double weight = Inconnections.get(i).getWeight();
			// output from the incoming neuron
			double a = previousNeuron.getOutput(); 
			//calculation
			sum = sum + (weight * a);
		}
		//include the bias neuron
		//to avoid the all 0 inputs
		sum = sum + (biasConnection.getWeight() * bias);
		//set the output using sigmoid function
		output = sigmoid(sum);
	}
	
	/* Setters and getters */
	// getters
	public double getOutput() {
		return output;
	}
	
	public double getBias() {
		return bias;
	}
	
	///// setters
	/* method to set the value */
	public void addBiasConnection(Neuron n) {
		Connection connection = new Connection(n, this);
		biasConnection = connection;
		Inconnections.add(connection);
	}

	public ArrayList<Connection> getAllIncomingConnections() {
		return Inconnections;
	}


	public void setOutput(double output) {
		this.output = output;
	}

}