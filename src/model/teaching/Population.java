package model.teaching;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import model.neuralnetwork.NeuralNetwork;

public class Population {

	private static Random random = new Random();

	private LinkedList<Generation> generations = new LinkedList<>();

	public Population() {
	}

	/**
	 * @param numberOfEntities
	 * @param inputSize
	 * @param architecture
	 * @return
	 */
	public static Generation createRandomGeneration(TeachingParams params) {
		Generation generation = new Generation();

		LinkedList<Entity> entities = new LinkedList<>();

		for (int i = 0; i < params.numberOfEntitiesPerGen; i++) {
			entities.add(createNewEntity(params));
		}
		generation.setEntities(entities);

		return generation;
	}

	public void addNewGeneration(Generation generation) {
		if (generations == null)
			generations = new LinkedList<>();
		if (generations.isEmpty())
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

		List<Entity> actualEntities = actualGeneration.getEntities();

		List<Entity> selectedEntities = selectEntities(actualEntities, params.selectionRatio);

		if (random.nextFloat() > 0.5)
			selectedEntities.add(createNewEntity(params));

		long startTime = Calendar.getInstance().getTimeInMillis();
		List<Entity> newEntities = createNewEntities(params, selectedEntities);
		long endTime = Calendar.getInstance().getTimeInMillis();

		System.out.println("creating new entities took " + (endTime - startTime) + " ms");

		int numberOfEntities = params.numberOfEntitiesPerGen;

		if (newEntities.size() > numberOfEntities) {
			while (newEntities.size() > numberOfEntities) {
				removeRandomEntity(newEntities); // reduce the size of the population
			}

		} else {
			while (newEntities.size() < numberOfEntities) {
				newEntities.add(createNewEntity(params));
			}
		}

		// if(elitism) {//select the best 3
		// for(int i = 0; i < 3; i++)
		// newEntities.removeLast();
		//
		// for(int i = 0; i < 3; i++) {
		// newEntities.addFirst(selectedEntities.get(i));
		// }
		// }

		if (params.elitism) {// ha igen, a legjobb 3 túléli

			for (int i = 0; i < 3; i++) {
				removeRandomEntity(newEntities);
			}

			for (int i = 0; i < 3; i++) {
				newEntities.add(actualEntities.get(i));
			}
		}

		Generation newGen = new Generation();

		newGen.setEntities(newEntities);

		return newGen;
	}

	private List<Entity> createNewEntities(TeachingParams params, List<Entity> selectedEntities) {
		List<Entity> newEntities = Collections.synchronizedList(new LinkedList<Entity>());

		CountDownLatch latch = new CountDownLatch(selectedEntities.size());

		// create some crossovers
		for (int i = 0; i < params.getNumberOfEntitiesPerGen(); i++) {
			(new Thread(() -> {
				Random random = new Random();

				int entity_1 = 0;
				int entity_2 = 0;

				while (entity_1 == entity_2) {
					entity_1 = random.nextInt(selectedEntities.size() - 1);
					entity_2 = random.nextInt(selectedEntities.size() - 1);
				}
				
				final Entity e1 = selectedEntities.get(entity_1);
				final Entity e2 = selectedEntities.get(entity_2);
				
				Entity newEntity = Entity.crossOver(e1, e2, params.mutationFactor);
				
				newEntities.add(newEntity);
				
				latch.countDown();
			})).start();
		}

//		for (int i = 0; i < selectedEntities.size(); i++) {
//			final int tempVal = i;
//			(new Thread(() -> {
//				int index = tempVal;
//
//				final Entity entity_i = selectedEntities.get(index);
//
//				List<Entity> tempList = new LinkedList<>();
//
//				for (int j = 0; j < selectedEntities.size(); j++) {
//					if (index == j)
//						continue;
//
//					final Entity entity_j = selectedEntities.get(j);
//
//					Entity newEntity = Entity.crossOver(entity_i, entity_j, params.mutationFactor);
//
//					// System.out.println("Difference is " +
//					// Entity.getDifference(selectedEntities.get(i), newEntity));
//					// System.out.println("Difference is " +
//					// Entity.getDifference(selectedEntities.get(j), newEntity));
//
//					tempList.add(newEntity);
//				}
//				newEntities.addAll(tempList);
//
//				latch.countDown();
//
//			})).start();
//		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return newEntities;
	}

	private static Entity createNewEntity(TeachingParams params) {
		return new Entity((new NeuralNetwork()).setInputLayer(new float[params.inputSize])
				.createLayers(params.architecture).generateRandomWeights().generateRandomBiases());
	}

	private void removeRandomEntity(List<Entity> newEntities) {
		int randomInt = random.nextInt(newEntities.size() - 1);
		newEntities.remove(randomInt);
	}

	/**
	 * Selects the best X % of the entities, and some random one.
	 */
	private LinkedList<Entity> selectEntities(List<Entity> actualEntities, float selectionRatio) {
		LinkedList<Entity> selected = new LinkedList<>();

		final int actualEntitiesListSize = actualEntities.size();

		float numberOfSelectedEntities = actualEntitiesListSize * selectionRatio;

		if (numberOfSelectedEntities > actualEntitiesListSize)
			numberOfSelectedEntities = actualEntitiesListSize;

		int rounded = Math.round(numberOfSelectedEntities);

		for (int i = 0; i < rounded; i++) {
			selected.add(actualEntities.get(i));
		}

		int numberOfRandomlyChoosed = 2;

		for (int x = 0; x < numberOfRandomlyChoosed; x++) {
			int randomIndex = random.nextInt(rounded - numberOfRandomlyChoosed) + rounded;

			if (randomIndex > actualEntitiesListSize - 1)
				randomIndex = actualEntitiesListSize - 1;

			selected.add(actualEntities.get(randomIndex));
		}

		return selected;
	}

	public int size() {
		if (generations == null)
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

		sb.append("Population for teaching neural network with genetic algorithm.").append("\n")
				.append("The population contains ").append(generations.size()).append(" generations.");
		sb.append("\n");
		if (size() == 0)
			return sb.toString();

		Generation last = generations.getLast();
		last.orderByFitness();
		sb.append("The biggest fitness point of the last generation is " + last.getMaxFitnessPoint());
		sb.append("\n");

		return sb.toString();
	}

}
