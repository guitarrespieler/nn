package model.teaching;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Serializer implements Runnable{

	private Gson gson;
	
	private Population population;
	
	public static final String dirName = "population";
	public static final String preName = "/generation_";
	public static final String extrension = ".json";
	
	
	public Serializer(Population pop){
		this.population = pop;
	}
	
	public void serialize(Population pop) throws IOException {
		if(gson == null)
			gson = new Gson();
		
		File dir = new File(dirName);
		
		if(!dir.exists())
			dir.mkdirs();
		
		for (Generation gen : pop.getGenerations()) {
			
			String str = gson.toJson(gen);
			
			File f = new File(dirName + preName + gen.getGenerationNumber() + extrension);
			
			if(!f.exists())
				f.createNewFile();
			
			FileWriter fw = new FileWriter(f);
			
			fw.write(str);
			fw.flush();
			fw.close();		
		}
		
	}
	
	public Population deserialize() throws IOException {
		if(gson == null)
			gson = new Gson();
		
		int i = 0;
		
		LinkedList<Generation> generations = new LinkedList<>();
		
		File dir = new File(dirName);
		if(!dir.exists()) {
			Population pop = new Population();
			pop.setGenerations(generations);
			return pop;
		}
		
		while(true) {
			File f = new File(dirName + preName + i + extrension);
			
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
	
	public Population deserializeLastGeneration() throws FileNotFoundException {
		if(gson == null)
			gson = new Gson();
		
		LinkedList<Generation> generations = new LinkedList<>();
		
		File dir = new File(dirName);
		if(dir.exists()) {
			int i = dir.listFiles().length - 1;
			
			File f = new File(dirName + preName + i + extrension);
				
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

	@Override
	public void run() {
		gson = new Gson();
		
		try {
			serialize(population);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
