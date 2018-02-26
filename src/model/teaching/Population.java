package model.teaching;

import java.util.LinkedList;
import java.util.List;

import model.neuralnetwork.NeuralNetwork;


public class Population {
	
	private static final int[] architecture = {10,10,4};
	
	private static float mutationFactor = 0.1f;

	private LinkedList<Generation> generations;
	
	
	
	public Population() {}
	
	public static Generation createRandomGeneration(int numberOfEntities, int inputSize) {
		Generation generation = new Generation();
		
		List<Entity> entities = new LinkedList<>();
		
		for(int i = 0; i < numberOfEntities; i++) {
			entities.add(
					new Entity((
							new NeuralNetwork())
							.setInputLayer(new float[inputSize])
							.createLayers(Population.architecture)
							.generateRandomWeights()
							.generateRandomBiases()));
		}
		generation.setEntities(entities);
		
		return generation;
		//addNewGeneration(generation);
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
	
	public Generation breedNextGeneration() {
		Generation actualGeneration = generations.getLast();
		
		actualGeneration.orderByFitness();
		
		List<Entity> entities = actualGeneration.getEntities();
		
		//TODO folytatni.
		
		return null;
	}

	public LinkedList<Generation> getGenerations() {
		return generations;
	}

	public void setGenerations(LinkedList<Generation> generations) {
		this.generations = generations;
	}
	
	
	
}
