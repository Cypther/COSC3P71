
/**This is the Parameter Class
 * 
 * This class sets the parameters for
 * the Genetic Algorithm for TSP
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Parameters {

	/* Class Variables */
	private String fileName;
	private int numberOfRuns = 10;
	private boolean elitism = true;
	private double elitismRate = 1;
	private double crossover = 0.8;
	private double mutation = 0.2;
	private int generations = 100;
	private int population = 50;
	private int tournamentSize = 3;
	private int seed;

	private boolean UniformOrderCrossOver = true;
	private boolean OrderCrossOver = false;

	/* Setters and getters */

	// getters

	public String getFileName() {
		if (this.fileName == null) {
			return "";
		} else {
			return this.fileName;
		}

	}

	public int getNumberofRuns() {
		return this.numberOfRuns;
	}

	public boolean isElitism() {
		return this.elitism;
	}

	public double getCrossover() {
		return this.crossover;
	}

	public double getMutation() {
		return this.mutation;
	}

	public int getGenerations() {
		return this.generations;
	}

	public int getPopulation() {
		return this.population;
	}

	public boolean getUniformOrderCrossOver() {
		return this.UniformOrderCrossOver;
	}

	public boolean getOrderCrossOver() {
		return this.OrderCrossOver;
	}

	public int getTournamentSize() {
		return this.tournamentSize;
	}

	public int getSeed() {
		return this.seed;
	}

	public double getElitismRate() {
		return this.elitismRate;
	}

	// Setters

	public void setFileName(String name) {
		this.fileName = name;
	}

	public void setNumberOfRuns(int runsPerSet) {
		numberOfRuns = runsPerSet;
	}

	public void setElitism(boolean elitism) {
		this.elitism = elitism;
	}

	public void setCrossover(double crossover) {
		this.crossover = crossover;
	}

	public void setMutation(double mutation) {
		this.mutation = mutation;
	}

	public void setGenerations(int generations) {
		this.generations = generations;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public void setUniformOrderCrossOver(boolean UniformOrderCrossOver) {
		this.UniformOrderCrossOver = UniformOrderCrossOver;
	}

	public void setOrderCrossOver(boolean OrderCrossOver) {
		this.OrderCrossOver = OrderCrossOver;
	}

	public void setTournamentSize(int tournamentSize) {
		this.tournamentSize = tournamentSize;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}
	
	public void setElitismRate(double elitismRate) {
		this.elitismRate = elitismRate;
	}

	/*
	 * Saving the file to the location ./output
	 * 
	 */

	public void saveOutPut(String text) {

		// text = dataInformation(text);

		BufferedWriter output = null;
		try {
			File file = new File("./output/GA_Test_Run.txt");
			output = new BufferedWriter(new FileWriter(file));
			output.write(text);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null)
				try {
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		//System.out.println("File Saved: ./output/A_Test_Run.txt");

	}
	

	/*
	 * A method for displaying the formation for the parameters
	 * 
	 */

	public String dataInformation(String text) {

		// The date
		DateFormat f = new SimpleDateFormat("yyyy.MM.dd 'at' HH.mm.ss");
		String date = "" + f.format(Calendar.getInstance().getTime());
		text = "----------------------------------" + "\n" + text;
		text = "Seed: " + this.seed + "\n" + text;
		text = "Tournament Size: " + this.tournamentSize + "\n" + text;
		text = "Size of Population: " + this.population + "\n" + text;
		text = "Number of Generations: " + this.generations + "\n" + text;
		text = "Mutation Rate: " + this.mutation + "\n" + text;
		text = "Crossover Rate: " + this.crossover + "\n" + text;
		text = "Elitism Rate: " + this.elitismRate + "\n" + text;
		text = "Elitism: " + this.elitism + "\n" + text;
		text = "Number of runs: " + this.numberOfRuns + "\n" + text;
		text = "Name: " + this.getFileName() + "\n" + text;
		text = "----------------------------------" + "\n" + text;
		text = "Date: " + date + "\n" + text;
		return text;

	}
	
	/*Displaying the information after the operation*/
	public void printParameters() {
		System.out.println("**************************************");
		System.out.println("Name: " + getFileName());
		System.out.println("Number of runs: " + this.numberOfRuns);
		System.out.println("Elitism: " + this.elitism);
		System.out.println("Elitism Rate: " + this.elitismRate);
		System.out.println("Crossover Rate: " + this.crossover);
		System.out.println("Mutation Rate: " + this.mutation);
		System.out.println("Number of Generations: " + this.generations);
		System.out.println("Size of Population: " + this.population);
		System.out.println("Tournament Size: " + this.tournamentSize);
		System.out.println("Seed: " + this.seed);
		System.out.println("UniformOrderCrossOver: " + this.UniformOrderCrossOver);
		System.out.println("OrderCrossOver: " + this.OrderCrossOver);
		System.out.println("**************************************");
	}

} // ParameterSet
