/**This is the NeuralNetwork Class
 * 
 * This class keep does all the operation of the neural
 * Network. Each (Neuron)layer is stored in an Arraylist 
 * 
 * It uses the class Connection for getting the weights and setting the weights
 * 
 * All the methods in the this class are commented for more details 
 * 
 * @author Long Nguyen & Chang Ding
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class NeuralNetwork {

	// input layer, hidden layer, output layer.
	private ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
	private ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
	private ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
	// Random order input
	public ArrayList<Integer> shuffleData = new ArrayList<Integer>();
	// the bias neuron
	private Neuron bias = new Neuron();
	private int[] layers;
	
	//training time
	private int _actualTrainingTime = 1000;

	// set the learning rate
	private double learningRate = Main._learningRate;

	// store the output.
	double resultOutputs[] = new double[TrainingData.getSize()];

	/**
	 * Constructor Create all neurons and connections Connections are created in
	 * the neuron class
	 * 
	 * @param input
	 * @param hidden
	 * @param output
	 */
	public NeuralNetwork(int input, int hidden, int output) {
		// for random order input
		for (int i = 0; i < 16; i++) {
			shuffleData.add(i);
		}
		Collections.shuffle(shuffleData);

		// store the layer information
		this.layers = new int[] { input, hidden, output };

		// initialize the network
		for (int i = 0; i < layers.length; i++) {
			switch (i) {
			// create input layer
			case 0:
				for (int j = 0; j < layers[i]; j++) {
					Neuron neuron = new Neuron();
					inputLayer.add(neuron);
				}
				continue;
				// hidden layer
				// build connections
			case 1:
				for (int j = 0; j < layers[i]; j++) {
					Neuron neuron = new Neuron();
					neuron.addIncomingConnections(inputLayer);
					// add bias
					neuron.addBiasConnection(bias);
					hiddenLayer.add(neuron);
				}
				continue;
				// output layer
				// build connections
			case 2:
				for (int j = 0; j < layers[i]; j++) {
					Neuron neuron = new Neuron();
					neuron.addIncomingConnections(hiddenLayer);
					// add bias
					neuron.addBiasConnection(bias);
					outputLayer.add(neuron);
				}
				continue;
				// error
			default:
				System.out.println("Initialization Errors!");
				break;
			}
		}
		initializeRandomWeights();
	}

	// randomNumberBetween [-1 to 1]
	double getRandom() {
		return (RandomSeed.getRandomDouble() * 2 - 1);
	}

	/**
	 * Calculating the output of the network based on the input the forward pass
	 */
	public void activate() {
		// calculate the result for hidden layer
		for (int i = 0; i < hiddenLayer.size(); i++) {
			hiddenLayer.get(i).calculateOutput();
		}
		// calculate the output for output layer
		// in this case, output layer size is 1
		for (int i = 0; i < outputLayer.size(); i++) {
			outputLayer.get(i).calculateOutput();
		}
	}

	// initialize random weights
	public void initializeRandomWeights() {
		// weight for hidden layer
		for (int i = 0; i < hiddenLayer.size(); i++) {
			ArrayList<Connection> connections = hiddenLayer.get(i)
					.getAllIncomingConnections();
			for (int j = 0; j < connections.size(); j++) {
				double newWeight = getRandom();
				connections.get(j).setWeight(newWeight);
			}
		}

		// weight for output layer
		for (int i = 0; i < outputLayer.size(); i++) {
			ArrayList<Connection> connections = outputLayer.get(i)
					.getAllIncomingConnections();
			for (int j = 0; j < connections.size(); j++) {
				double newWeight = getRandom();
				connections.get(j).setWeight(newWeight);
			}
		}
	}

	/**
	 * initialize the value for input layer
	 * 
	 * @param inputs
	 */
	public void setInput(double inputs[]) {
		for (int i = 0; i < inputLayer.size(); i++) {
			inputLayer.get(i).setOutput(inputs[i]);
		}
	}

	/**
	 * return the output value from the ouput layer
	 * 
	 * @return
	 */
	public double getOutput() {
		double outputs = 0;
		for (int i = 0; i < outputLayer.size(); i++)
			outputs = outputLayer.get(i).getOutput();
		return outputs;
	}

	/**
	 * all output propagate back correctOutPut first calculate the partial
	 * derivative of the error with respect to each of the weight leading into
	 * the output neurons bias is also updated here
	 */
	public void backPropagation(double correctOutPut) {
		// get all connections to output layout
		ArrayList<Connection> cToOutput = outputLayer.get(0)
				.getAllIncomingConnections();
		// actual output
		double actualOutput = outputLayer.get(0).getOutput();
		// delta
		double error = correctOutPut - actualOutput;
		// store the error to each hidden neuron
		// the value stored is w*error
		// backward pass
		for (int i = 0; i < cToOutput.size(); i++) {
			cToOutput.get(i).getFromNeuron()
					.setError(cToOutput.get(i).getWeight() * error);
		}

		// update the weight from the input to the hidden layer
		for (int i = 0; i < hiddenLayer.size(); i++) {
			// all connections to the hidden layer
			ArrayList<Connection> cToHidden = hiddenLayer.get(i)
					.getAllIncomingConnections();
			// update the weight
			// the newWeight = oldWeight + Rate*w*error*preOutput*(df(e)/d(e));
			for (int j = 0; j < cToHidden.size(); j++) {
				cToHidden.get(j).setWeight(
						cToHidden.get(j).getWeight()
								+ learningRate
								* cToHidden.get(j).getToNeuron().getError()
								* cToHidden.get(j).getFromNeuron().getOutput()
								* cToHidden.get(j).getToNeuron().getOutput()
								* (1 - cToHidden.get(j).getToNeuron()
										.getOutput()));
			}
		}
		// update the weight from the hidden to the output
		// we only have one neuron in output layer
		// the newWeight = oldWeight + Rate*w*error*preOutput*(df(e)/d(e));
		for (int i = 0; i < cToOutput.size(); i++) {
			cToOutput.get(i).setWeight(
					cToOutput.get(i).getWeight() + learningRate * error
							* cToOutput.get(i).getFromNeuron().getOutput()
							* cToOutput.get(i).getToNeuron().getOutput()
							* (1 - cToOutput.get(i).getToNeuron().getOutput()));
		}

	}

	/**
	 * print out the test result
	 */
	public void printResult() {
		// format
		DecimalFormat df = new DecimalFormat("#");
		for (int i = 0; i < TrainingData.getSize(); i++) {
			// print the pattern
			System.out.print("Inputs: ");
			for (int j = 0; j < layers[0]; j++) {
				System.out.print(df.format(TrainingData.getInputArray()[i][j])
						+ " ");
			}
			// print the expected result
			System.out.print("  Expected: ");
			for (int j = 0; j < layers[2]; j++) {
				System.out.print(df.format(TrainingData.getOutputArray()[i])
						+ " ");
			}

			// print the actual result
			System.out.print("   Output: ");
			for (int j = 0; j < layers[2]; j++) {
				DecimalFormat dff2 = new DecimalFormat("#");
				System.out.print(dff2.format(resultOutputs[i]) + " ");

			}

			// print the output value (sigmoid)
			System.out.print("   Output Value: ");
			for (int j = 0; j < layers[2]; j++) {
				DecimalFormat dff = new DecimalFormat("#.#####");
				System.out.print(dff.format(resultOutputs[i]) + " ");

			}
			System.out.println();
		}
		_actualTrainingTime+=1;
		System.out.println("Training Times: "+_actualTrainingTime);
	}

	/**
	 * Train neural network until minError reached or maxSteps exceeded
	 * 
	 * @param trainingTime
	 * @param minError
	 */
	void trainNetWork(int trainingTime, double minError) {
		double accuracy = 0;
		double output;
		for (int i = 0; i < trainingTime; i++) {
			// default correctTime is 16
			double correctTime = TrainingData.getSize();
			// shuffle the order of input
			Collections.shuffle(shuffleData);
			// start training for each input
			for (int x = 0; x < TrainingData.getSize(); x++) {
				// get expected output
				double designedResult = TrainingData.getOutput(shuffleData
						.get(x));
				// to make the input in random order
				setInput(TrainingData.getInput(shuffleData.get(x)));
				// feed-forward
				activate();
				// get the actual output
				output = getOutput();
				// store the result
				resultOutputs[shuffleData.get(x)] = output;
				// for the incorrect ouput, we need to do the backProp.
				if (designedResult == 0 && output > 0.5) {
					backPropagation(TrainingData.getOutput(shuffleData.get(x)));
					//one input is not correct
					correctTime-=1;
				}
				if (designedResult == 1 && output < 0.5) {
					backPropagation(TrainingData.getOutput(shuffleData.get(x)));
					//one input is not correct
					correctTime-=1;
				}

			}
			//calculate the accuracy
			accuracy = correctTime / TrainingData.getSize();
			//print out the accuracy for single run
			System.out.println(i+"th run, accuracy: "+accuracy+",   ");
			//set actual training time
			_actualTrainingTime = i;
			// if the error is less than the minimum error
			// break the loop
			if (Math.abs(1-accuracy) < minError) {
				break;
			}
		}
	}

	public void printTestError() {
		int totalData = TrainingData.getSize();
		int m = 0;
		for (int i = 0; i < totalData; i++) {
			if (TrainingData.getOutput(i) == 1 && resultOutputs[i] > 0.5)
				m++;
			if (TrainingData.getOutput(i) == 0 && resultOutputs[i] < 0.5)
				m++;
		}
		System.out.println();
		System.out.println("The test accuracy is " + (double) m
				/ (double) totalData);
	}

	// test the network
	// input all the data, test the result
	public void testNetWork() {
		System.out.println();
		System.out
				.println("Neural network to correctly predict the parity of a 4-bit vector");
		//test the data
		for (int x = 0; x < TrainingData.getSize(); x++) {
			//set the input
			setInput(TrainingData.getInput(x));
			// feed-forward
			activate();
			// store the result
			resultOutputs[x] = getOutput();
		}
		printResult();
		printTestError();
	}

}