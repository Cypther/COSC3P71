/**This is the read file Class
 * 
 * This class holds in the reads in two files
 * the berlin52.tsp and the Parameters and parses the file
 * to be use for the GA
 * 
 * @author Long Nguyen
 * 
 * @version 1.0 (November 2015)
 * Compiler Version Java 1.7
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class readFile {
	
	public readFile(){
		readFile();
	}

		public ArrayList<City> readFile() {
		// reading in the file
		File file = new File("berlin52.tsp");

		if (!file.exists()) {
			System.err.println("file does not exist.");
			System.exit(1);
		}

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		String dataInput = "";
		ArrayList<String> fileData = new ArrayList<String>();

		try {
			fis = new FileInputStream(file);

			// Here BufferedInputStream is added for fast reading.
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			// dis.available() returns 0 if the file does not have more lines.

			while (dis.available() != 0) {

				// this statement reads the line from the file and print it to
				// the console.
				// System.out.println(dis.readLine());
				// dataInput = dataInput + dis.readLine() + " ";
				dataInput = dis.readLine();
				if (dis.available() != 0 && Character.isDigit(dataInput.charAt(0))) {
					fileData.add(dataInput);
					// System.out.println(fileData.get(0).toString());
				}

			}

			// dispose all the resources after using them.
			fis.close();
			bis.close();
			dis.close();

			// try catch for error handling
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<City> data = parseInputFileInput(fileData);
		return data; 
	}
	
	public ArrayList<City> parseInputFileInput(ArrayList<String> dataInput){
		
		
		String[] cityDistance;
		
		City[] cityArray = new City[dataInput.size()];
		ArrayList<City> cityData = new ArrayList<City>();
		
		/*Adding the All the Cities to the ChromosomeData data*/
		for(int i = 0; i < dataInput.size(); i++){	
			//changing string to string Array by spiting it with white spaces
			cityDistance = dataInput.get(i).split("\\s",-1); 
			
			int number1 = (int) Double.parseDouble(cityDistance[1]);
			int number2 = (int) Double.parseDouble(cityDistance[2]);
			
			cityArray[i]= new City (number1,number2); 
			
			cityData.add(cityArray[i]);//
			
		}
		
		return cityData;
	}

	/**
	 * Reads in a parameter file and and sets the data return the parameter file.
	 * 
	 */

	public static ArrayList<Parameters> ReadParameters() {

		File fileName = new File("Parameter.txt");
		ArrayList fileData = new ArrayList<String>();
		
		/* error if the file is not found
		 * error checking 
		 * */
		if (!fileName.exists()) {
			System.err.println("Parameter file is not found!");
			System.exit(1);
		}

		try {
			FileReader readName = new FileReader(fileName);
			BufferedReader readIn = new BufferedReader(readName);

			String line = "";
			
			while ((line = readIn.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty())
					continue; 
				fileData.add(line);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found : " + fileName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Error reading file : " + fileName);
			e.printStackTrace();
			System.exit(1);
		}

		
		ArrayList<Parameters> sets = new ArrayList<Parameters>();
		Parameters parameterSet = new Parameters();
		while (!fileData.isEmpty()) {
			
			String line = fileData.remove(0).toString();
			StringTokenizer tokens = new StringTokenizer(line, " :", false);
			String stringLing = tokens.nextToken();

			/* Switch case for parsing */
			switch (stringLing) {
			case "fileName":
				parameterSet.setFileName(tokens.nextToken());
				continue;
			case "numberPerFun":
				parameterSet.setNumberOfRuns(Integer.parseInt(tokens.nextToken()));
				continue;
			case "elitism":
				parameterSet.setElitism(Boolean.parseBoolean(tokens.nextToken()));
				continue;
			case "elitismRate":
				parameterSet.setElitismRate(Double.parseDouble(tokens.nextToken()));
				continue;
			case "crossOver":
				parameterSet.setCrossover(Double.parseDouble(tokens.nextToken()));
				continue;
			case "mutationRate":
				parameterSet.setMutation(Double.parseDouble(tokens.nextToken()));
				continue;
			case "generations":
				parameterSet.setGenerations(Integer.parseInt(tokens.nextToken()));
				continue;
			case "population":
				parameterSet.setPopulation(Integer.parseInt(tokens.nextToken()));
				continue;
			case "UniformOrderCrossOver":
				parameterSet.setUniformOrderCrossOver(Boolean.parseBoolean(tokens.nextToken()));
				continue;
			case "OrderCrossOver":
				parameterSet.setOrderCrossOver(Boolean.parseBoolean(tokens.nextToken()));
				continue;
			case "tournamentSize":
				parameterSet.setTournamentSize(Integer.parseInt(tokens.nextToken()));
				continue;
			case "seed":
				parameterSet.setSeed(Integer.parseInt(tokens.nextToken()));
				continue;
			case "****":
				sets.add(parameterSet);
				parameterSet = new Parameters();
				continue;
			default: System.out.println("Unknown line");
				continue;
			}
		}

		return sets;
	}

}
