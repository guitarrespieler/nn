package model.teaching;

import java.util.LinkedList;
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
	public static Generation createRandomGeneration(TeachingParams params) {
		Generation generation = new Generation();
		
		LinkedList<Entity> entities = new LinkedList<>();
		
		for(int i = 0; i < params.numberOfEntitiesPerGen; i++) {
			entities.add(
					createNewEntity(params));
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
	 * breeds the next generation
	 */
	public Generation breedNextGeneration(TeachingParams params) {
		Generation actualGeneration = generations.getLast();
		
		actualGeneration.orderByFitness();
		
		LinkedList<Entity> actualEntities = actualGeneration.getEntities();
		
		LinkedList<Entity> selectedEntities = selectEntities(actualEntities, params.selectionRatio);
		
		selectedEntities.add(createNewEntity(params));
		
		LinkedList<Entity> newEntities = new LinkedList<>();
		
		//create some crossovers
		for(int i = 0; i < selectedEntities.size(); i++) {
			for(int j = 0; j < selectedEntities.size(); j++) {
				if(i == j)
					continue;
				Entity newEntity = Entity.crossOver(selectedEntities.get(i), selectedEntities.get(j), params.mutationFactor);
				
//				System.out.println("Difference is " + Entity.getDifference(selectedEntities.get(i), newEntity));
//				System.out.println("Difference is " + Entity.getDifference(selectedEntities.get(j), newEntity));
				
				newEntities.add(newEntity);
			}			
		}
		
		int numberOfEntities = params.numberOfEntitiesPerGen;
		
		if(newEntities.size() > numberOfEntities) {		
			removeRandomEntity(newEntities, numberOfEntities);			//reduce the size of the population
		}else {
			while(newEntities.size() < numberOfEntities) {
				newEntities.add(createNewEntity(params));
			}
		}
		
//		if(elitism) {//select the best 3
//			for(int i = 0; i < 3; i++)
//				newEntities.removeLast();
//			
//			for(int i = 0; i < 3; i++) {
//				newEntities.addFirst(selectedEntities.get(i));
//			}
//		}
		
		if(params.elitism) {
			removeRandomEntity(newEntities, numberOfEntities);
			newEntities.addFirst(actualEntities.getFirst());
		}
		
		Generation newGen = new Generation();
		
		newGen.setEntities(newEntities);
		
		return newGen;
	}

	private static Entity createNewEntity(TeachingParams params) {
		return new Entity((new NeuralNetwork())
				.setInputLayer(new float[params.inputSize])
				.createLayers(params.architecture)
				.generateRandomWeights()
				.generateRandomBiases());
	}

	private void removeRandomEntity(LinkedList<Entity> newEntities, int numberOfEntities) {
		while(newEntities.size() > numberOfEntities) {
			int randomInt = random.nextInt(numberOfEntities);
			newEntities.remove(randomInt);
		}
	}
	

	/**
	 * Selects the best X % of the entities, and some random one.
	 */
	private LinkedList<Entity> selectEntities(LinkedList<Entity> actualEntities, float selectionRatio) {
		LinkedList<Entity> selected = new LinkedList<>();
		
		final int actualEntitiesListSize = actualEntities.size();
		
		float numberOfSelectedEntities = actualEntitiesListSize * selectionRatio;
		
		
		if(numberOfSelectedEntities > actualEntitiesListSize)
			numberOfSelectedEntities = actualEntitiesListSize;
		
		int rounded = Math.round(numberOfSelectedEntities);
		
		for(int i = 0; i < rounded; i++) {
			selected.add(actualEntities.get(i));
		}
		
		int numberOfRandomlyChoosed = 1;
		
		while(selected.size() < numberOfRandomlyChoosed + rounded) {
			int randomIndex = random.nextInt(rounded - numberOfRandomlyChoosed) + rounded;
			
			if(randomIndex > actualEntitiesListSize - 1)
				randomIndex = actualEntitiesListSize - 1;
			
			selected.add(actualEntities.get(randomIndex));
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
