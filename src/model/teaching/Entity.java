package model.teaching;

import java.util.Comparator;

import com.google.gson.annotations.SerializedName;

import model.neuralnetwork.NeuralNetwork;

public class Entity implements Comparator<Entity>{
	
	@SerializedName("fitness")
	private int fitness;
	
	@SerializedName("neuralnetwork")
	private NeuralNetwork nn;
	
	
	
	Entity(){}
	
	Entity(NeuralNetwork nn){
		this.nn = nn;
		fitness = 0;
	}

	@Override
	public int compare(Entity o1, Entity o2) {
		return Integer.compare(o1.fitness, o2.fitness);
	}

	public NeuralNetwork getNn() {
		return nn;
	}

	public void setNn(NeuralNetwork nn) {
		this.nn = nn;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
}
