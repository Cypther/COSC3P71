/**This is the Connection Class
 * 
 * This class keep track of the weight values 
 * by storing the information of the next and previous neuron
 * 
 * Methods for getting the weights and setting the weights
 * 
 * @author Long Nguyen & Chang Ding
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */

public class Connection {
	double weight = 0;
	private Neuron previousNeuron;
	private Neuron nextNeuron;

	//Default Constructor
	public Connection(Neuron fromNeuron, Neuron toNeuron) {
		this.previousNeuron = fromNeuron;
		this.nextNeuron = toNeuron;
	}

	/* Setters and getters */

	// getters
	public double getWeight() {
		return this.weight;
	}

	public Neuron getFromNeuron() {
		return this.previousNeuron;
	}

	public Neuron getToNeuron() {
		return this.nextNeuron;
	}

	///// setters
	//method to set the value

	public void setWeight(double weight) {
		this.weight = weight;
	}
}