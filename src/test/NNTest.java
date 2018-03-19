package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.neuralnetwork.NeuralNetwork;
import model.teaching.Entity;

class NNTest {

	int[] architecture = {8,6};
	int inputSize = 12;
	
	Entity e1;
	Entity e2;
	
	@BeforeEach
	void initEntities() {
		e1 = new Entity((new NeuralNetwork())
				.setInputLayer(new float[inputSize])
				.createLayers(architecture)
				.generateRandomWeights()
				.generateRandomBiases());
		
		e2 = new Entity((new NeuralNetwork())
				.setInputLayer(new float[inputSize])
				.createLayers(architecture)
				.generateRandomWeights()
				.generateRandomBiases());
	}
	
	@Test
	void test() {
		NeuralNetwork nn = new NeuralNetwork();
		
		nn.setInputLayer(new float[inputSize])
		.createLayers(architecture)
		.generateRandomWeights()
		.generateRandomBiases();
		
		
		assertEquals(inputSize, nn.getInputLayer().numberOfNeurons());
		
		for(int i = 0; i < architecture.length; i++) {
			assertEquals(architecture[i], nn.getLayers().get(i).numberOfNeurons());
		}
	}
	
	@Test
	void crossoverTest() {
		final float difference = Entity.getDifference(e1, e2);
		final int compare = Float.compare(1.0f, difference);
		assertTrue(compare == 0); //szal nem egyformák
		
		Entity e3 = Entity.crossOver(e1, e2, 0.0f);
		
		float diff1 = Entity.getDifference(e1, e3);
		float diff2 = Entity.getDifference(e2, e3);
		assertTrue(diff1 > 0.4f);
		assertTrue(diff1 < 0.6f);		
		assertTrue(diff2 > 0.4f);
		assertTrue(diff2 < 0.6f);
	}
	
	@Test
	void mutationTest() {
		Entity e4 = Entity.crossOver(e1, e2, 0.3f);
		
		final float difference1 = Entity.getDifference(e1, e4);
		final float difference2 = Entity.getDifference(e2, e4);
		
		assertTrue(difference1 > 0.6);		
		assertTrue(difference2 > 0.6);
	}
}
