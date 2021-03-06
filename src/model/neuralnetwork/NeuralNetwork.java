package model.neuralnetwork;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NeuralNetwork {
	/**
	 * Here is the input layer
	 */
	@Expose
	@SerializedName("inputLayer")
	private Layer inputLayer;
	
	/**
	 * List of layers
	 */
	@Expose
	@SerializedName("layers")
	private LinkedList<Layer> layers;

	
	private static SecureRandom randomGen = new SecureRandom();
	
	/**
	 * várható érték
	 */
	public static final float averageVal = 0.0f;
	
	/**
	 * szórás
	 */
	public static final float deviationVal = 2.0f;
	
	/**
	 * calculates the output values.
	 */
	public NeuralNetwork calculate(Activation hiddenLayersActivationFunction,
			Activation outputLayersActivationFunction) {
		Layer prevLayer = inputLayer;
		
		int numberOfLayers = layers.size();
		
		for(int i = 0; i < numberOfLayers; i++) {
			Layer actualLayer = layers.get(i);
			
			if(i < numberOfLayers - 1)
				actualLayer.activateLayer(prevLayer, hiddenLayersActivationFunction);
			else
				actualLayer.activateLayer(prevLayer, outputLayersActivationFunction);
			
			prevLayer = actualLayer;
		}
		
		return this;
	}
	
	/**
	 * @param values the input values of the Neural Network
	 */
	public NeuralNetwork setInputLayer(float[] values) {
		inputLayer = new Layer(null, null, values, values.length);
		
		return this;
	}
	
	/**
	 * 
	 * @param layer
	 * @return
	 */
	public NeuralNetwork setInputLayer(Layer layer) {
		inputLayer = new Layer(layer.getInputWeights(), layer.getBiases(), layer.getValues(), layer.numberOfNeurons());
		
		return this;
	}
	
	public Layer getInputLayer() {
		return inputLayer;
	}
	
	/**
	 * Creates layers with the given sizes.
	 * Input layer created separately.
	 * Output layer created here.
	 */
	public NeuralNetwork createLayers(int... numberOfNeuronsPerLayer) {
		if(inputLayer == null)
			throw new NullPointerException("add input layer first.");
		
		layers = new LinkedList<>();
		
		Layer previousLayer = inputLayer; 
		
		for(int i = 0; i < numberOfNeuronsPerLayer.length; i++) {
			int layerSize = numberOfNeuronsPerLayer[i];
			
			Layer actualLayer = new Layer(previousLayer.numberOfNeurons(), layerSize);
			
			layers.add(actualLayer);
			
			previousLayer = actualLayer;			
		}
		return this;		
	}
	
	/**
	 * Gives random weights to the connections
	 */
	public NeuralNetwork generateRandomWeights() {
		
		for(int i = 0; i < layers.size(); i++){
			
			Layer layer = layers.get(i);
			
			Layer previousLayer;
			
			if(i == 0) {
				previousLayer = inputLayer;
			}else {
				previousLayer = layers.get(i - 1);
			}
			
			if(previousLayer == null) {
				continue;
			}
			
			ArrayList<float[]> weightsList = new ArrayList<>();
			
			int previousLayerSize = previousLayer.numberOfNeurons();
			
			for(int k = 0; k < layer.numberOfNeurons(); k++) {
				float[] weights = new float[previousLayerSize];
				
				for(int j = 0; j < weights.length; j++) {
//					weights[j] = generateRandomGaussian(averageVal, deviationVal);
					weights[j] = generateRandomFloat() * 2.0f - 1.0f;
				}
				
				weightsList.add(weights);
			}
			layer.setInputWeights(weightsList);
		}
		
		return this;
	}
	
	public NeuralNetwork generateRandomBiases() {
		for (Layer layer : layers) {
			int layerSize = layer.numberOfNeurons();
			
			float[] biases = new float[layerSize];
			
			for(int i = 0; i < layerSize; i++) {
//				biases[i] = generateRandomGaussian(averageVal, deviationVal);
//				biases[i] = generateRandomFloat() * 2.0f - 1.0f;
				biases[i] = randomGen.nextInt(10) - 5;
			}
			layer.setBiases(biases);
		}
		return this;
	}
	
	public NeuralNetwork addLayer(Layer layer) {
		if(layers == null)
			layers = new LinkedList<>();
		
		layers.add(layer);
		
		return this;
	}
	
	/**
	 * Normál eloszlású random számot generál 
	 * @param averageValue várható értékkel és
	 * @param deviation szórással.
	 */
	public static float generateRandomGaussian(float averageValue,float deviation){
		return (float) (randomGen.nextGaussian() * deviation + averageValue);
	}

	public float[] getOutputValues() {
		return layers.getLast().getValues();
	}
	
	public LinkedList<Layer> getLayers(){
		return layers;
	}

	public static float generateRandomFloat() {
		return randomGen.nextFloat();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(inputLayer.toString());
		
		for (Layer layer : layers) {
			sb.append("\n")
			.append(layer.toString());
		}
		return sb.toString();
	}
}
