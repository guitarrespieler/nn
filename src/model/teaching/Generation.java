package model.teaching;

import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Generation {

	@SerializedName("generationNumber")
	private int generationNumber;
	@SerializedName("Entities")
	private List<Entity> entities;
	@SerializedName("maxFitnessPoint")
	private int maxFitnessPoint = 0;

	
	Generation(){}
	
	public void orderByFitness() {
		Collections.sort(entities, new Entity());
		maxFitnessPoint = entities.get(0).getFitness();
	}
	
	public Entity crossOver(Entity e1, Entity e2) {
		return null;
	}
	
	public int getGenerationSize() {
		if(entities == null)
			return 0;
		else return entities.size();
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
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
