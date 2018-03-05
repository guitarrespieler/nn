package model.teaching;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.google.gson.annotations.SerializedName;

import model.neuralnetwork.Layer;
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
	
	/**
	 * Both of the entities gives 1-1 weights
	 * @param e1 parent entity
	 * @param e2 parent entity
	 * @param mutationFactor 
	 * @return child of the parents
	 */
	public static Entity crossOver(Entity e1, Entity e2, float mutationFactor) {
		NeuralNetwork newNN = new NeuralNetwork();
		
		int inputLayerSize = e1.nn.getInputLayer().size();
		newNN.setInputLayer(new float[inputLayerSize]);
		
		List<Layer> layers1 = e1.nn.getLayers();
		List<Layer> layers2 = e2.nn.getLayers();
		
		int numberOfLayers = layers1.size();
		
		for(int i = 0; i < numberOfLayers; i++) {
			Layer actualLayer1 = layers1.get(i);
			Layer actualLayer2 = layers2.get(i);
			
			int layerSize = actualLayer1.size();
			Layer newLayer;
			
			if(i > 0)
				newLayer = new Layer(layers1.get(i - 1).size(), layerSize);
			else
				newLayer = new Layer(inputLayerSize, layerSize);
			
			ArrayList<float[]> newWeightsList = new ArrayList<>();
			
			for(int j = 0; j < layerSize; j++) {
				float[] actualWeights1 = actualLayer1.getInputWeights().get(j);
				float[] actualWeights2 = actualLayer2.getInputWeights().get(j);
				
				int weightsLength = actualWeights1.length;				
				float[] newWeights = new float[weightsLength];
				
				for(int k = 0; k < weightsLength; k++) {
					float randomNum = NeuralNetwork.generateRandomGaussian(0.5f, 0.1f);
					
					if(randomNum < mutationFactor) { //mutation
						newWeights[k] = NeuralNetwork.generateRandomGaussian(NeuralNetwork.averageVal, NeuralNetwork.deviationVal);
					}else {
						if(j % 2 == 0) {						
							newWeights[k] = actualWeights1[k];						
						}else {
							newWeights[k] = actualWeights2[k];
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
