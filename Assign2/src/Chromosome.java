/**This is the Chromosome Class
 * 
 * This class represents the Chromosome
 * and store each city in the chromosme, the fitness is 
 * calculated by the total distance between each city in the
 * chromosme
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */

import java.util.ArrayList;
import java.util.Collections;

public class Chromosome {

	private static int numberOfcities;
	// Holds our chromosomes of the cities
	private ArrayList<City> chromosomes = new ArrayList<City>();
	private double fitness = 0;
	private int distance = 0;

	// Constructs null Chromosome
	public Chromosome() {
		for (int i = 0; i < this.numberOfcities; i++) {
			chromosomes.add(null);
		}
		this.fitness = 0;
		this.distance = 0;
	}

	// Constructs a Chromosome base on the City data
	public Chromosome(ArrayList<City> chromosomes) {
		this.numberOfcities = chromosomes.size();
		for (int i = 0; i < chromosomes.size(); i++) {
			this.chromosomes.add(chromosomes.get(i));
		}
		this.fitness = 0;
		this.distance = 0;
	}

	// Gets a city from the chromosomes
	public City getCity(int chromosomes) {
		return (City)this.chromosomes.get(chromosomes);
	}

	// Sets a city in a certain position within the chromosomes
	public void setCity(int chromosomesPosition, City city) {
		this.chromosomes.set(chromosomesPosition, city);
		// If the chromosomes has been change, reset the fitness and distance
		this.fitness = 0;
		this.distance = 0;
	}

	// Gets the fitness chromosomes 
	public double getFitness() {

		if (this.fitness == 0) {
			this.fitness = (1 / (double) getTotalDistance()) * 100000;
		}
		return this.fitness;
	}
	
	// Creates a random chromosomes by using the shuffle function build in the
		// arraylist
		public void generateChromosome() {
			Collections.shuffle(this.chromosomes);
		}

	// Get number of cities on our tour
	public int sizeOfChromosomes() {
		return this.chromosomes.size();
	}

	// Check if the chromosomes contains a city
	public boolean chromosomesContains(City city) {
		if(this.chromosomes.contains(city)){
			return true;
		}else{
			return false;
		}
	}

	// Gets the total distance of the cities in the Chromosome
	public int getTotalDistance() {
		int chromosomeDistance = 0;
		if (this.distance == 0){
			// Loop through our Chromosome's and calculate the distances between
			// each cities
			for (int i = 0; i < this.sizeOfChromosomes(); i++) {
				City fromCity = getCity(i);
				// Check we're not at the last city, if so set the
				// final city to the start
				// calculated distance between the two cities in the chromosome
				if((i + 1) < this.sizeOfChromosomes()){
					chromosomeDistance += ((int)fromCity.calculateDistance(this.getCity(i + 1)));
				}else{
					chromosomeDistance += ((int)fromCity.calculateDistance(this.getCity(0)));
				}
	
			}
			this.distance = chromosomeDistance;
		}
		return this.distance;
	}

}