package model.teaching;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serializer{

	private static Gson gson;
	
	public static final String dirName = "population";
	public static final String preName = "/generation_";
	public static final String extension = ".json";
	
	public static void serialize(Population pop) throws IOException {
		initGson();

		
		LinkedList<Generation> generations = pop.getGenerations();
		
		File dir = new File(dirName);
		
		if(!dir.exists())
			dir.mkdirs();
		
		for (Generation gen : generations) {
			serializeThisGeneration(gen);
		}
		
	}

	private static void initGson() {
		if(gson == null) {
//			GsonBuilder builder = new GsonBuilder();
//			builder.serializeSpecialFloatingPointValues();
//			gson = builder.create();
			
			gson = new Gson();
		}
	}
	
	public static void serializeThisGeneration(Generation gen) throws IOException {
		File dir = new File(dirName);
		
		if(!dir.exists())
			dir.mkdirs();
		
		String str = gson.toJson(gen);
		
		File f = new File(dirName + preName + gen.getGenerationNumber() + extension);
		
		if(!f.exists())
			f.createNewFile();
		
		FileWriter fw = new FileWriter(f);
		
		fw.write(str);
		fw.flush();
		fw.close();				
	}
	
	public static Population deserialize() throws IOException {
		initGson();
		
		int i = 0;
		
		LinkedList<Generation> generations = new LinkedList<>();
		
		File dir = new File(dirName);
		if(!dir.exists()) {
			Population pop = new Population();
			pop.setGenerations(generations);
			return pop;
		}
		
		while(true) {
			File f = new File(dirName + preName + i + extension);
			
			if(!f.exists())
				break;
			
			Generation gen = gson.fromJson(new FileReader(f), Generation.class);
			
			if(gen == null)
				break;
			
			generations.add(gen);
			
			i++;
		}
		
		Population pop = new Population();
		pop.setGenerations(generations);
		return pop;
	}
	
	public static Population deserializeLastGeneration() throws FileNotFoundException {
		initGson();
		
		LinkedList<Generation> generations = new LinkedList<>();
		
		File dir = new File(dirName);
		if(dir.exists()) {
			int i = dir.listFiles().length - 1;
			
			File f = new File(dirName + preName + i + extension);
				
			if(f.exists()) {
				
				Generation gen = gson.fromJson(new FileReader(f), Generation.class);
				
				if(gen != null)
					generations.add(gen);
			}
		}
		
		Population pop = new Population();
		pop.setGenerations(generations);
		return pop;
	}
}
