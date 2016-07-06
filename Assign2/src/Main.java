/**This is the Main Class
 * 
 * This class test the Genetic Algorithm 
 * for TSP
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

public class Main {

	public static void main(String[] args) {

		/* Reading in the file */
		readFile read = new readFile();
		/* Reading the Parameters file */
		ArrayList<Parameters> parameterSet = read.ReadParameters();
		ArrayList<City> cityData = new ArrayList<City>();
		cityData = read.readFile();

		GeneticAlgorithmOperation(parameterSet, cityData);

	}
	
	/*
	 * GeneticAlgorithmOperation uses the GeneticAlgorithm class and the parameter
	 * to do the calculation and takes the average between each run
	 * 
	 * */

	public static void GeneticAlgorithmOperation(ArrayList<Parameters> parameterSet, ArrayList<City> cityData) {

		/* Need to clean this up, very messy */
		GeneticAlgorithm TSP;
		String maintext = "";
		String currentRun = "";
		
		/* loops runs by how many parameters are set in the file*/
		for (int i = 0; i < parameterSet.size(); i++) {
			TSP = new GeneticAlgorithm(parameterSet.get(i));
			
			System.out.println("Parameter number: " + i);
			
			double[] averageFitness;
			double[] eliteFitness;
			double[] distance;

			/*creating the array base on how many generations*/
			if (parameterSet.get(i).getGenerations() == 0){
				averageFitness = new double[1];
				eliteFitness = new double[1];
				distance = new double[1];
			} else {
				averageFitness = new double[parameterSet.get(i).getGenerations()];
				eliteFitness = new double[parameterSet.get(i).getGenerations()];
				distance = new double[parameterSet.get(i).getGenerations()];
			}

			currentRun = currentRun + "Parameter: "+ (i+1)+ "\n";
			
			// Number of Runs per set
			for (int j = 0; j < parameterSet.get(i).getNumberofRuns(); j++) {
				System.out.println("Number of runs: " + (j + 1));
				
				double currentRunEliteFitness =0;
				double currentRunAverageFitness = 0;
				double currentRunBestDistance =0;
				
				/*Changing the seed each run*/
				parameterSet.get(i).setSeed(j);
				GARandomSeed.initSeed(parameterSet.get(i).getSeed());

				// creating the populations
				Population population = new Population(parameterSet.get(i).getPopulation(), cityData);

				Population perviousPop = null;
				/* Evolve the population to getGenerations() size generations */
				for (int k = 0; k < parameterSet.get(i).getGenerations(); k++) {

					population = TSP.geneticAlgorithm(population);

					/*Saving the value from the kth generation*/
					if (k != 0 && parameterSet.get(i).getGenerations() % k == 0) {
						//System.out.print("Generations " + k + "  ");
						//perviousPop = population;
					}
					/* Saving the average in the array */
					averageFitness[k] = averageFitness[k] + population.getAverageFitness();
					eliteFitness[k] = eliteFitness[k] + population.getBestFitness().getFitness();
					distance[k] = distance[k] + population.getBestFitness().getTotalDistance();

					/*checking if we are stuck in a local max, by taking the different between this generation and the
					 * perious kth generation */
					if (perviousPop != null
							&& (perviousPop.getBestFitness().getTotalDistance() - population.getBestFitness().getTotalDistance()) == 0) {
						//TSP.changeMatationRate();
						//System.out.print(" Matation Rate is now: " + TSP.getMatationRate());
						//System.out.println();
					}
					
				}
				currentRunAverageFitness = population.getAverageFitness();
				currentRunEliteFitness = population.getBestFitness().getFitness();
				currentRunBestDistance = population.getBestFitness().getTotalDistance();
				parameterSet.get(i).getSeed();
				
				currentRun = currentRun + "Run: "+ (j + 1)+ " Seed: " + parameterSet.get(i).getSeed() +" AverageFitness: " + currentRunAverageFitness + " Best Fitness: " + currentRunEliteFitness + " Distance: " + currentRunBestDistance + "\n";
				//parameterSet.get(i).saveOutPut(currentRun);
				//System.out.println("AverageFitness: " + population.getAverageFitness() + " Best Fitness: " +population.getBestFitness().getFitness() + " Distance: "+ population.getBestFitness().getTotalDistance());
			}
			
			currentRun = currentRun + "\n";
			
			if (parameterSet.get(i).getGenerations() == 0) {
				parameterSet.get(i).setGenerations(1);
			}

			ArrayList<Generation> generation = new ArrayList<Generation>(parameterSet.get(i).getGenerations());

			/* caluclating the average and saving them in the arrayList */
			for (int j = 0; j < parameterSet.get(i).getGenerations(); j++) {
				averageFitness[j] = (averageFitness[j] / parameterSet.get(i).getNumberofRuns());
				eliteFitness[j] = (eliteFitness[j] / parameterSet.get(i).getNumberofRuns());
				distance[j] = (distance[j] / parameterSet.get(i).getNumberofRuns());
				generation.add(new Generation(eliteFitness[j], averageFitness[j], distance[j]));

			}
			
			/*for formation the output to a save file*/
			DecimalFormat df = new DecimalFormat("##.########");
			String tempText = "";
			maintext = maintext + parameterSet.get(i).dataInformation(tempText);
			tempText = "";
			tempText = "Generation: "  + "\tAverage: " + "\tElite: " + "\tDistance: " + "\n" ;
			maintext = maintext + tempText;
			

			//parsing string data for out
			for (int x = 0; x < generation.size(); x++) {
				maintext = maintext + (x + 1) + "\t" +  df.format(generation.get(x).average) + "\t" + df.format(generation.get(x).elite) + "\t" + generation.get(x).distance
				+ "\n";
			}
			//text = parameterSet.get(i).dataInformation(text);
			//parameterSet.get(i).printParameters();
			maintext = currentRun + maintext;
			parameterSet.get(i).saveOutPut(maintext);
			System.out.println("File Saved: ./output/A_Test_Run.txt");

		}

	}

}