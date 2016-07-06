/**This is the Population Class
 * 
 * This class holds in the chromosomes, in the an array
 * the array of chromosomes represents the population
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */

import java.util.ArrayList;
import java.util.Vector;
import java.util.Arrays;

public class Population {

	// Holds population of Chromosome
	public Chromosome[] chromosomes;

	// Default Constructor
	public Population(int populationSize, ArrayList<City> cityData) {
		this.chromosomes = new Chromosome[populationSize];
		for (int i = 0; i < this.populationSize(); i++) {
			Chromosome cityChromosome = new Chromosome(cityData);
			cityChromosome.generateChromosome();
			saveChromosome(i, cityChromosome);
		}
	}

	// Construct the population
	public Population(int populationSize) {
		this.chromosomes = new Chromosome[populationSize];
	}

	// Gets a Chromosome from population
	public Chromosome getChromosome(int index) {
		return this.chromosomes[index];
	}
	
	// Saves a Chromosome
	public void saveChromosome(int index, Chromosome chromosomes) {
		this.chromosomes[index] = chromosomes;
	}
	
	// Gets population size
	public int populationSize() {
		return this.chromosomes.length;
	}

	// Gets the best Chromosome in the population
	public Chromosome getBestFitness() {
		Chromosome theBest = this.chromosomes[0];
		// Loop through individuals to find fittest
		for (int i = 1; i < this.populationSize(); i++) {
			if (theBest.getFitness() <= getChromosome(i).getFitness()) {
				theBest = getChromosome(i);
			}
		}
		//System.out.println( this.populationSize());
		return theBest;
	}

	// Gets the best 10% Chromosome in the population
	public Chromosome[] getBestFitnessPercent(double percent) {

		int tenPerCent =  (int) Math.floor((this.populationSize()*percent));
		int counter = 0;

		//System.out.println(chromosomes.length);
		Chromosome[] bestTenPercent = new Chromosome[tenPerCent];
		double[] bestFitnessTemp = new double[chromosomes.length];
		// Loop through individuals to find fittest
		for (int i = 0; i < this.populationSize(); i++) {
			bestFitnessTemp[i] = getChromosome(i).getFitness();
		}
		/*Sort by the most fitness*/
		Arrays.sort(bestFitnessTemp);
		
		/*Looping and getting the best 10% of the population */
		for (int i = bestFitnessTemp.length; i > (bestFitnessTemp.length - tenPerCent); i--) {
			for (int j = 0; j < this.populationSize(); j++) {
				if (getChromosome(j).getFitness() == bestFitnessTemp[i-1] && counter < tenPerCent) {
					bestTenPercent[counter] = getChromosome(j);
					counter++;
				}
			}
		}
		return bestTenPercent;
	}

	/*
	 * calculate the average fitness of the population
	 */
	public double getAverageFitness() {
		double Average = 0;

		for (int i = 0; i < this.chromosomes.length; i++) {
			Average += this.chromosomes[i].getFitness();
		}
		Average = (Average / this.chromosomes.length);

		return Average;
	}

}