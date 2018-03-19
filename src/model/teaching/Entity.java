package model.teaching;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import model.neuralnetwork.Layer;
import model.neuralnetwork.NeuralNetwork;

public class Entity implements Comparator<Entity>{
	
	@SerializedName("fitness")
	private int fitness;
	
	@SerializedName("neuralnetwork")
	private NeuralNetwork nn;
	
	public Entity(){}
	
	public Entity(NeuralNetwork nn){
		this.nn = nn;
		fitness = 0;
	}
	
	/**
	 * Both of the entities gives 1-1 weights
	 * @param e1 parent entity
	 * @param e2 parent entity
	 * @param mutationFactor 
	 * @return child of the parents
	 */
	public static Entity crossOver(Entity e1, Entity e2, float mutationFactor) {
		NeuralNetwork newNN = new NeuralNetwork();
		
		int inputLayerSize = e1.nn.getInputLayer().numberOfNeurons();
		newNN.setInputLayer(new float[inputLayerSize]);
		
		List<Layer> layers1 = e1.nn.getLayers();
		List<Layer> layers2 = e2.nn.getLayers();
		
		int numberOfLayers = layers1.size();
		
		for(int i = 0; i < numberOfLayers; i++) {
			Layer actualLayer1 = layers1.get(i);
			Layer actualLayer2 = layers2.get(i);
			
			int layerSize = actualLayer1.numberOfNeurons();
			Layer newLayer;
			
			if(i > 0)
				newLayer = new Layer(layers1.get(i - 1).numberOfNeurons(), layerSize);
			else
				newLayer = new Layer(inputLayerSize, layerSize);
			
			ArrayList<float[]> weightsList1 = actualLayer1.getInputWeights();
			ArrayList<float[]> weightsList2 = actualLayer2.getInputWeights();
			ArrayList<float[]> newWeightsList = new ArrayList<>();
			
			for(int j = 0; j < layerSize; j++) {
				float[] weights1 = weightsList1.get(j);
				float[] weights2 = weightsList2.get(j);
				float[] newWeights = new float[weights1.length];		
				
				for (int k = 0; k < weights1.length; k++) {					
					float randomNum = NeuralNetwork.generateRandomFloat();
					
					if(randomNum < mutationFactor && mutationFactor > 0.00001f) { //mutation
//						Generation.numOfmutatedGenes++;
//						newWeights[k] = NeuralNetwork.generateRandomGaussian(NeuralNetwork.averageVal, NeuralNetwork.deviationVal);
						newWeights[k] = NeuralNetwork.generateRandomFloat() * 2.0f - 1.0f;
					}else {
						if(k % 2 == 0) {						
							newWeights[k] = weights1[k];						
						}else {
							newWeights[k] = weights2[k];
						}		
					}		
				}
				newWeightsList.add(newWeights);
			}
			newLayer.setInputWeights(newWeightsList);
			newLayer.setBiases(actualLayer1.getBiases()); //fixen az e1 rétegétől veszi át a bias értékeket
			newNN.addLayer(newLayer);
		}
		
		return new Entity(newNN);	
	}

	/**
	 * Tells the diff between the 2 entities
	 * @param e1
	 * @param e2
	 * @return a float between 0.0f and 1.0f (percentage)
	 */
	public static float getDifference(Entity e1, Entity e2) {
		float numberOfWeights = 0.0f;
		float numberOfDiff = 0.0f;
		
		LinkedList<Layer> layers1 = e1.getNn().getLayers();
		LinkedList<Layer> layers2 = e2.getNn().getLayers();
		
		for(int i = 0; i < layers1.size(); i++) {
			Layer layer1 = layers1.get(i);
			Layer layer2 = layers2.get(i);
			
			ArrayList<float[]> weightsList1 = layer1.getInputWeights();
			ArrayList<float[]> weightsList2 = layer2.getInputWeights();
			
			for(int j = 0; j < layer1.numberOfNeurons(); j++) {
				float[] weights1 = weightsList1.get(j);
				float[] weights2 = weightsList2.get(j);
				
				for(int k = 0; k < weights1.length; k++) {
					numberOfWeights++;
					if(Float.compare(weights1[k], weights2[k]) != 0) {
						numberOfDiff++;
					}
					
				}
			}
		}
		final float retval = numberOfDiff / numberOfWeights;
		return retval;
	}
	
	@Override
	public int compare(Entity o1, Entity o2) {				
		return Integer.compare(o2.fitness, o1.fitness);
	}

	@Override
	public boolean equals(Object obj) {
		Entity other;
		
		try {
			other = (Entity) obj;
		} catch (Exception e) {
			return false;
		}
		return this.fitness == other.fitness;
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
