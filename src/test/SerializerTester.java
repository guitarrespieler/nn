package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.teaching.Population;
import model.teaching.Serializer;

class SerializerTester {

	Population pop;
	int[] architecture = {10,10,6};
	@BeforeEach
	void initPop() {
		pop = new Population();
	}
	
	@Test
	void serializeTest() throws IOException {
		
		pop.addNewGeneration(Population.createRandomGeneration(50, 20, architecture));
		
		Serializer.serialize(pop);
		
		File f = new File(Serializer.dirName + Serializer.preName + 0 + Serializer.extension);
		
		assertTrue(f.exists());
	}
	
	@Test
	void serializeMoreTest() throws IOException {
		for(int i = 0; i < 60; i++){
			pop.addNewGeneration(Population.createRandomGeneration(50, 20, architecture));
		}
		
		Serializer.serialize(pop);
		
		for(int i = 0; i < 60; i++) {			
			File f = new File(Serializer.dirName + Serializer.preName + i + Serializer.extension);
			
			assertTrue(f.exists());
		}

	}
	
	@Test
	void deserializePopulation() throws IOException {
		Population pop2 = Serializer.deserialize();
		
		assertNotNull(pop2);
	}
	
	@Test
	void deserializeTest2() throws IOException {
		serializeMoreTest(); //creates a big population
		
		//now read it back
				
		Population pop2 = Serializer.deserialize();		
		assertNotNull(pop2);
		
		assertEquals(pop.getGenerations().size(), pop2.getGenerations().size());
	
	}
	
	@Test
	void deserializeLastGen() throws IOException {
		serializeMoreTest();
		
		Population pop2 = Serializer.deserializeLastGeneration();
		
		assertEquals(pop2.getGenerations().size(), 1);
		assertEquals(pop2.getGenerations().getFirst().getGenerationNumber(), 59);
	}

}
