package model.teaching;

import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Generation {

	@SerializedName("generationNumber")
	private int generationNumber;
	@SerializedName("Entities")
	private List<Entity> entities;

	
	Generation(){}
	
	public void orderByFitness() {
		Collections.sort(entities, new Entity());
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
	
	
}
