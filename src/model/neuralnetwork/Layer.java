package model.neuralnetwork;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

public class Layer {
	@SerializedName("biases")
	private float[] biases;
	@SerializedName("values")
	private float[] values;
	@SerializedName("inputWeights")
	private ArrayList<float[]> inputWeights;
	
	public Layer(Layer otherLayer, ArrayList<float[]> inputWeights) {
		biases = Arrays.copyOf(otherLayer.biases, otherLayer.biases.length);
		values = Arrays.copyOf(otherLayer.values, otherLayer.values.length);
		if(inputWeights != null)
			this.inputWeights = new ArrayList<>(otherLayer.getInputWeights());
	}
	
	public Layer(ArrayList<float[]> inputWeights, float[] biases, float[] values) {
		this.inputWeights = inputWeights;
		this.biases = biases;
		this.values = values; 
	}
	
	public Layer(int previousLayersSize, int layerSize) {
		
		this.inputWeights = new ArrayList<>();
		for (int i = 0; i < layerSize; i++) {
			inputWeights.add(new float[previousLayersSize]);			
		}
		
		this.biases = new float[layerSize];
		this.values = new float[layerSize];
	}

	/**
	 * activates the neurons of this layer.
	 */
	public void activateLayer(Layer previousLayer, Activation mode) {
		if(previousLayer == null)
			return;
		
		for(int i = 0; i < values.length; i++) {
			float tempValue = 0;
			float[] weights = inputWeights.get(i);
			
			for (int j = 0; j < weights.length; j++){
				float prevValue = previousLayer.getValues()[j];
				
				float weight = weights[j];
				
				tempValue += prevValue * weight;				
			}
			
			tempValue += biases[i];
			
			values[i] = ActivationFunctions.activationFunction(tempValue, mode);			
		}
	}
	
	public int size() {
		return values.length;
	}

	//getters, setters
	public ArrayList<float[]> getInputWeights() {
		return inputWeights;
	}
	public void setInputWeights(ArrayList<float[]> inputWeights) {
		this.inputWeights = inputWeights;
	}
	public float[] getBiases() {
		return biases;
	}
	public void setBiases(float[] biases) {
		this.biases = biases;
	}
	public float[] getValues() {
		return values;
	}
	public void setValues(float[] values) {
		this.values = values;
	}
	
	
}
