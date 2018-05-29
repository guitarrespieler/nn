package model.neuralnetwork;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Layer {
	@Expose
	@SerializedName("size")
	private int size;
	
	@Expose
	@SerializedName("biases")
	private float[] biases;

	private float[] values;	

	@Expose
	@SerializedName("inputWeights")
	private ArrayList<float[]> inputWeights;
	
	public Layer() {
	}
	
	public Layer(ArrayList<float[]> inputWeights, float[] biases, float[] values, int layerSize) {
		this.inputWeights = inputWeights;
		this.biases = biases;
		this.values = values; 
		
		this.size = layerSize;
	}
	
	public Layer(int previousLayersSize, int layerSize) {
		
		this.inputWeights = new ArrayList<>();
		for (int i = 0; i < layerSize; i++) {
			inputWeights.add(new float[previousLayersSize]);			
		}
		
		this.biases = new float[layerSize];
		this.values = new float[layerSize];
		this.size = layerSize;
	}

	/**
	 * activates the neurons of this layer.
	 */
	public void activateLayer(Layer previousLayer, Activation mode) {
		if(previousLayer == null)
			return;
		if(values == null)
			values = new float[numberOfNeurons()];
		
		final float[] prevLayersValues = previousLayer.getValues();
		
		for(int i = 0; i < numberOfNeurons(); i++) {
			float tempValue = 0;
			float[] weights = inputWeights.get(i);
			
			for (int j = 0; j < prevLayersValues.length; j++){
				
				float prevValue = prevLayersValues[j];
				
				float weight = weights[j];
				
				tempValue += prevValue * weight;				
			}
			
			tempValue += biases[i];
			
			values[i] = ActivationFunctions.activationFunction(tempValue, mode);			
		}
	}
	
	public int numberOfNeurons() {
		return size;
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
	
	@Override
	public String toString() {
		if(values != null)
			return Arrays.toString(values);
		return "no data";
	}
	
	
}
