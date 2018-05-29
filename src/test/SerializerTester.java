package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.teaching.Entity;
import model.teaching.Generation;
import model.teaching.Population;
import model.teaching.Serializer;
import model.teaching.TeachingParams;

class SerializerTester {

	Population pop;
	
	TeachingParams params = new TeachingParams();
	
	@BeforeEach
	void initPop() {
		pop = new Population();
	}
	
//	@Test
	void serializeTest() throws IOException {
		
		pop.addNewGeneration(Population.createRandomGeneration(params));
		
		Serializer.serialize(pop);
		
		File f = new File(Serializer.dirName + Serializer.preName + 0 + Serializer.extension);
		
		assertTrue(f.exists());
	}
	
	@Test
	void serializeMoreTest() throws IOException {
		for(int i = 0; i < 60; i++){
			pop.addNewGeneration(Population.createRandomGeneration(params));
		}
		
		Serializer.serialize(pop);
		
		for(int i = 0; i < 60; i++) {			
			File f = new File(Serializer.dirName + Serializer.preName + i + Serializer.extension);
			
			assertTrue(f.exists());
		}

	}
	
//	@Test
	void deserializePopulation() throws IOException {
		Population pop2 = Serializer.deserialize();
		
		assertNotNull(pop2);
	}
	
//	@Test
	void deserializeTest2() throws IOException {
		serializeMoreTest(); //creates a big population
		
		//now read it back
				
		Population pop2 = Serializer.deserialize();		
		assertNotNull(pop2);
		
		assertEquals(pop.getGenerations().size(), pop2.getGenerations().size());
	
	}
	
//	@Test
//	void deserializeLastGen() throws IOException {
//		serializeMoreTest();
//		
//		Population pop2 = Serializer.deserializeLastGeneration();
//		
//		assertEquals(pop2.getGenerations().size(), 1);
//		assertEquals(pop2.getGenerations().getFirst().getGenerationNumber(), 59);
//	}
	
	@Test
	void deserializeTest3() throws IOException {
		serializeMoreTest();
		
		Population pop2 = Serializer.deserialize();
		
		final LinkedList<Generation> gen1 = pop.getGenerations();
		final LinkedList<Generation> gen2 = pop2.getGenerations();
		
		for (int i = 0; i < gen1.size(); i++) {
			final LinkedList<Entity> entities1 = gen1.get(i).getEntities();
			final LinkedList<Entity> entities2 = gen2.get(i).getEntities();
			
			for(int j = 0; j < entities1.size(); j++) {
				final Entity entity1 = entities1.get(j);
				final Entity entity2 = entities2.get(j);
				
				final float difference = Entity.getDifference(entity1, entity2);
				assertTrue(difference < 0.00001f);
			}
		}
		
	}

}
