/**
 * This is the Genetic Algorithm Class
 * 
 * This class represents the Genetic Algorithm It has to cross ovver operation
 * Uniform Order CrossOver and Order CrossOver
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (November 2015) Compiler Version Java 1.7
 */

public class GeneticAlgorithm {

	/* Genetic Algorithm parameters */
	private double crossOVerRate;
	private double mutationRate;
	private int tournamentSize;
	private boolean elitism;
	private boolean UniformOrderCrossOver;
	private boolean OrderCrossOver;
	private double elitismRate;

	public GeneticAlgorithm() {
		
	}

	/* changing the change Matation rate if it's stuck in a local max */
	public void changeMatationRate() {
		this.mutationRate += 0.0001;
	}

	public double getMatationRate() {
		return this.mutationRate;
	}

	/* Setting the parameters for the GA */
	public GeneticAlgorithm(Parameters parameterSet) {

		this.crossOVerRate = parameterSet.getCrossover();
		this.mutationRate = parameterSet.getMutation();
		this.tournamentSize = parameterSet.getTournamentSize();
		this.elitism = parameterSet.isElitism();
		this.UniformOrderCrossOver = parameterSet.getUniformOrderCrossOver();
		this.OrderCrossOver = parameterSet.getOrderCrossOver();
		this.elitismRate = parameterSet.getElitismRate();

	}

	// Evolves one generation for a single population
	public Population geneticAlgorithm(Population pop) {

		// int elitismOffSet = 0;
		Population newPopulation = new Population(pop.populationSize());
		int populationSize = pop.populationSize();
		Chromosome[] elitismPopulation;

		/*
		 * Keep our best Chromosome from the last generation* if true minus one
		 * from the population size
		 */
		if (elitism == true) {
			if (elitismRate == 1) {
				/* Taking the one best from the last generation */
				newPopulation.saveChromosome((pop.populationSize() - 1), pop.getBestFitness());
				populationSize--;
			} else {
				/* taking the best percent % form the last generation */
				elitismPopulation = pop.getBestFitnessPercent(elitismRate);
				for (int i = 1; i < elitismPopulation.length + 1; i++) {
					newPopulation.saveChromosome(pop.populationSize() - i, elitismPopulation[i - 1]);
					populationSize--;
				}

			}
		}

		int x = 0;
			while(x < populationSize) {
			// Select parents
			Chromosome parent1 = tournamentSelection(pop);
			Chromosome parent2 = tournamentSelection(pop);

			// Crossover from parents
			Chromosome child = null;
			if (UniformOrderCrossOver == true) {
				if(GARandomSeed.getRandomDouble() < crossOVerRate && x < populationSize){
					child = UniformOrderCrossOver(parent1, parent2);
					newPopulation.saveChromosome(x, child);
					x++;
				}else{
					if(x < populationSize){
						newPopulation.saveChromosome(x, parent1);
						x++;
					}
					if(x < populationSize){
						newPopulation.saveChromosome(x, parent2);
						x++;
					}
				}
			}else 
			if(OrderCrossOver == true) {
				if(GARandomSeed.getRandomDouble() < crossOVerRate && x < populationSize){
					child = orderedCrossover(parent1, parent2);
					newPopulation.saveChromosome(x, child);
					x++;
				}else{
					if(x < populationSize){
						newPopulation.saveChromosome(x, parent1);
						x++;
					}
					if(x < populationSize){
						newPopulation.saveChromosome(x, parent2);
						x++;
					}
				}
			} else {
				System.err.println("No Cross Over selected");
				System.exit(1);
			}
		}//while
		
		for (int i = 0; i < populationSize; i++) {
			      newPopulation.saveChromosome(i, chromoesomeMutation(newPopulation.getChromosome(i)));
		}
		
		return newPopulation;
	}

	/*
	 * Uniform crossover - bits are randomly copied from the first and then from
	 * the second parent Bit 1 is copied from the first parent, Bit zero is
	 * copied from the second parent
	 * 
	 */

	public Chromosome UniformOrderCrossOver(Chromosome parent1, Chromosome parent2) {

		// Create new child
		Chromosome child = new Chromosome();

		int[] bitMask = new int[parent1.sizeOfChromosomes()];
		
		
		/*
		 * Generating the random number between one and zero if the
		 * crossOVerRate is high, more ones are created, if it's low more
		 * zeros are created. if it's even, than, it's random between one
		 * and zero
		 */

		   //This generate random strings of 1 and 0
	       for(int i = 0; i < parent1.sizeOfChromosomes(); i++){
	    	   int randomBit = (int) (GARandomSeed.getRandomDouble() * 2);   
	    	   bitMask[i] = randomBit;   
	       }  

		// Crossing from parent one, if bitMask is one
		for (int i = 0; i < bitMask.length; i++) {
			if (bitMask[i] == 1) {
				child.setCity(i, parent1.getCity(i));
			}
		}
		
		// Crossing the zero from parent two
		for (int i = 0; i < bitMask.length; i++) {
			if (bitMask[i] == 0) {
				// If child doesn't have the city add it
				if (!child.chromosomesContains(parent2.getCity(i))) {
					child.setCity(i, parent2.getCity(i));
				}
				
			}
		}
	
		
		// for parent two, adding the reminding cities that are not duplicates
		for (int i = 0; i < bitMask.length; i++) {
			if (bitMask[i] == 1) {
				// If child doesn't have the city add it
				if (!child.chromosomesContains(parent2.getCity(i))) {
					for (int j = 0; j < bitMask.length; j++) {
						if (child.getCity(j) == null) {
							child.setCity(j, parent2.getCity(i));
							break;
						}
					}
				}
			}
		}
		

		return child;
	}

	/*
	 * Order Crossover: Select a random start and end Position in the
	 * chromosome, copies that sub selected chromosome to the children than,
	 * copies the remind chromosome from parent 2
	 * 
	 */
	public Chromosome orderedCrossover(Chromosome parent1, Chromosome parent2) {
		// Create new child Chromosome
		Chromosome child = new Chromosome();

		/*
		 * generate a random start and end Position if the crossOVerRate is low,
		 * a smaller sub position is created if the crossover rate is high, a
		 * much larger sub position is created for the crossover
		 */
		int startPosition = (int) GARandomSeed.getRandom(parent1.sizeOfChromosomes());
		int endPosition = (int) GARandomSeed.getRandom(parent1.sizeOfChromosomes());

		int temp; //temp variable for swapping

		// Swapping the bigger value of startPosition and endPosition
		if (endPosition < startPosition) {
			temp = endPosition;
			endPosition = startPosition;
			startPosition = temp;
		}

		// adding the sub Chromosome from parent1 to the children
		for (int i = 0; i < child.sizeOfChromosomes(); i++) {

			// Add in between start position and endPosition
			if (i > startPosition && i < endPosition) {
				child.setCity(i, parent1.getCity(i));
			}
		}

		// Adding the rest from the second parent
		for (int i = 0; i < parent2.sizeOfChromosomes(); i++) {

			// checking for duplicates
			if (!child.chromosomesContains(parent2.getCity(i))) {
				// if not duplcates than add the city in a null space
				for (int j = 0; j < child.sizeOfChromosomes(); j++) {
					if (child.getCity(j) == null) {
						child.setCity(j, parent2.getCity(i));
						break;
					}
				}
			}
		}
		return child;
	}

	// Selects a random Chromosome for the crossover
	private Chromosome tournamentSelection(Population pop) {
		Population tournament = new Population(tournamentSize);

		// random select from the population
		for (int i = 0; i < tournamentSize; i++) {
			// int randomSelect = (int) (Math.random() * pop.populationSize());
			int randomSelect = (int) (GARandomSeed.getRandom(pop.populationSize()));
			//System.out.println(pop.getChromosome(randomSelect).getTotalDistance());
			tournament.saveChromosome(i, pop.getChromosome(randomSelect));
		}
		// Get the best in the tournament
		Chromosome winner = tournament.getBestFitness();
		return winner;
	}	
	
	// Mutate the Chromosome using swap mutation
	private Chromosome chromoesomeMutation(Chromosome chromosome) {
            
		Chromosome muationChromosome = new Chromosome();
		
		/*Copying all the cities to muationChromosome */
		for(int i =0; i < muationChromosome.sizeOfChromosomes(); i ++){
			muationChromosome.setCity(i, chromosome.getCity(i));
		}
		
		/*Looping throught the chromosomes,if the random seed is less that mutation then swapped*/
		for (int i = 0; i < muationChromosome.sizeOfChromosomes(); i++) {
			if (GARandomSeed.getRandomDouble() < mutationRate) {
				// get a random city location to be swap
				int swap = (int)GARandomSeed.getRandom(muationChromosome.sizeOfChromosomes());
				// Get the cities at loop i, and random swap city
				City city1 = muationChromosome.getCity(i);
				City city2 = muationChromosome.getCity(swap);

				// Swap the two cities around in the chromosome
				muationChromosome.setCity(swap, city1);
				muationChromosome.setCity(i, city2);
			}
		}
		return muationChromosome;
	}

}