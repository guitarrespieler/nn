package model.teaching;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.neuralnetwork.NeuralNetwork;


public class Population {
	
	private static Random random = new Random();
	
	private LinkedList<Generation> generations = new LinkedList<>();
	
	public Population() {}
	
	/**
	 * @param numberOfEntities
	 * @param inputSize
	 * @param architecture
	 * @return
	 */
	public static Generation createRandomGeneration(int numberOfEntities, int inputSize, int[] architecture) {
		Generation generation = new Generation();
		
		LinkedList<Entity> entities = new LinkedList<>();
		
		for(int i = 0; i < numberOfEntities; i++) {
			entities.add(
					new Entity((
							new NeuralNetwork())
							.setInputLayer(new float[inputSize])
							.createLayers(architecture)
							.generateRandomWeights()
							.generateRandomBiases()));
		}
		generation.setEntities(entities);
		
		return generation;
	}

	public void addNewGeneration(Generation generation) {
		if(generations == null)
			generations = new LinkedList<>();
		if(generations.isEmpty())
			generation.setGenerationNumber(0);
		else
			generation.setGenerationNumber(generations.getLast().getGenerationNumber() + 1);
		
		generations.add(generation);
	}
	
	/**
	 * 
	 * @param numberOfEntities
	 * @param inputSize
	 * @param selectionRatio
	 * @param mutationFactor
	 * @param elitism
	 * @return
	 */
	public Generation breedNextGeneration(int numberOfEntities, int inputSize, float selectionRatio, float mutationFactor, boolean elitism, int[] architecture) {
		Generation actualGeneration = generations.getLast();
		
		LinkedList<Entity> selectedEntities = selectBestEntities(actualGeneration, selectionRatio);
		
		LinkedList<Entity> newEntities = new LinkedList<>();
		
		//create all possible crossovers
		for(int i = 0; i < selectedEntities.size(); i++) {
			for(int j = i; j < selectedEntities.size(); j++) {
				if(i == j)
					continue;
				newEntities.add(Entity.crossOver(selectedEntities.get(i), selectedEntities.get(j), mutationFactor));
			}			
		}
		
		if(newEntities.size() > numberOfEntities) {
			//reduce the size of the population
			while(newEntities.size() > numberOfEntities) {
				float randomFloat = NeuralNetwork.generateRandomGaussian(0.5f, 0.2f);
				int randomInt = random.nextInt(numberOfEntities);
				
				if(randomFloat < 0.5f)
					newEntities.remove(randomInt);			
			}
		}else {
			while(newEntities.size() < numberOfEntities) {
				newEntities.add(
						new Entity((
								new NeuralNetwork())
								.setInputLayer(new float[inputSize])
								.createLayers(architecture)
								.generateRandomWeights()
								.generateRandomBiases()));
			}
		}
		
		if(elitism) {
			newEntities.removeFirst();
			newEntities.addFirst(selectedEntities.getFirst());
		}
		
		Generation newGen = new Generation();
		
		newGen.setEntities(newEntities);
		
		return newGen;
	}
	
	/**
	 * Selects the best X % of the entities.
	 */
	private LinkedList<Entity> selectBestEntities(Generation actualGeneration, float selectionRatio) {
		actualGeneration.orderByFitness();
		
		List<Entity> entities = actualGeneration.getEntities();
		
		LinkedList<Entity> selected = new LinkedList<>();
		
		float numberOfSelectedEntities = entities.size() * selectionRatio;
		
		
		if(numberOfSelectedEntities > entities.size())
			numberOfSelectedEntities = entities.size();
		
		int rounded = Math.round(numberOfSelectedEntities);
		
		for(int i = 0; i < rounded; i++) {
			selected.add(entities.get(i));
		}
		return selected;
	}

	public int size() {
		if(generations == null)
			return 0;
		return generations.size();
	}

	public LinkedList<Generation> getGenerations() {
		return generations;
	}

	public void setGenerations(LinkedList<Generation> generations) {
		this.generations = generations;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Population for teaching neural network with genetic algorithm.")
		.append("\n")
		.append("The population contains ")
		.append(generations.size())
		.append(" generations.");
		sb.append("\n");
		if(size() == 0)
			return sb.toString();
		
		Generation last = generations.getLast();
		last.orderByFitness();
		sb.append("The biggest fitness point of the last generation is " + last.getMaxFitnessPoint());
		sb.append("\n");		
		
		return sb.toString();
	}
	
}
