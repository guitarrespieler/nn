package model.teaching;

import java.util.Collections;
import java.util.LinkedList;

import com.google.gson.annotations.SerializedName;

public class Generation {

	@SerializedName("generationNumber")
	private int generationNumber = 0;
	@SerializedName("maxFitnessPoint")
	private int maxFitnessPoint = 0;
	@SerializedName("Entities")
	private LinkedList<Entity> entities;

	Generation(){}
	
	public void orderByFitness() {
		Collections.sort(entities, entities.getFirst());
		maxFitnessPoint = entities.getFirst().getFitness();
	}
	
	public int getGenerationSize() {
		if(entities == null)
			return 0;
		else return entities.size();
	}

	public LinkedList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(LinkedList<Entity> entities) {
		this.entities = entities;
	}

	public int getGenerationNumber() {
		return generationNumber;
	}

	public void setGenerationNumber(int generationNumber) {
		this.generationNumber = generationNumber;
	}

	public int getMaxFitnessPoint() {
		return maxFitnessPoint;
	}

	public void setMaxFitnessPoint(int maxFitnessPoint) {
		this.maxFitnessPoint = maxFitnessPoint;
	}
	
	
}
