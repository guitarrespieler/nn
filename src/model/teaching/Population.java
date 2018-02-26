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
		
		sb.append("Population for teaching neural network with genetic algorithm.");
		sb.append("\n");
		sb.append("The population contains " + generations.size() + " generations.");
		sb.append("\n");
		Generation last = generations.getLast();
		last.orderByFitness();
		sb.append("The biggest fitness point of the last generation is " + last.getMaxFitnessPoint());
		sb.append("\n");		
		
		return sb.toString();
	}
	
}
