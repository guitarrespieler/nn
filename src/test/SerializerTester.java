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
	
	@BeforeEach
	void initPop() {
		pop = new Population();
	}
	
	@Test
	void serializeTest() throws IOException {
		pop.addNewGeneration(Population.createRandomGeneration(50, 20));
		
		Serializer ser = new Serializer(pop);
		
		ser.run();
		
		File f = new File(Serializer.dirName + Serializer.preName + 0 + Serializer.extrension);
		
		assertTrue(f.exists());
	}
	
	@Test
	void serializeMoreTest() {
		for(int i = 0; i < 60; i++){
			pop.addNewGeneration(Population.createRandomGeneration(50, 20));
		}
		
		Serializer ser = new Serializer(pop);
		
		ser.run();
		
		for(int i = 0; i < 60; i++) {			
			File f = new File(Serializer.dirName + Serializer.preName + i + Serializer.extrension);
			
			assertTrue(f.exists());
		}

	}
	
	@Test
	void deserializePopulation() throws IOException {
		Serializer ser = new Serializer(null);
		
		Population pop2 = ser.deserialize();
		
		assertNotNull(pop2);
	}
	
	@Test
	void deserializeTest2() throws IOException {
		serializeMoreTest(); //creates a big population
		
		//now read it back
				
		Serializer ser = new Serializer(null);
		
		Population pop2 = ser.deserialize();		
		assertNotNull(pop2);
		
		assertEquals(pop.getGenerations().size(), pop2.getGenerations().size());
	
	}

}
