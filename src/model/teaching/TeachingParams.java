package model.teaching;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class TeachingParams {
	
	@SerializedName("maxNumberOfGenerations")
	public int maxNumberOfGenerations = 10000;
	@SerializedName("mutationFactor")
	public float mutationFactor = 0.0f;
	@SerializedName("fitnessGoal")
	public int fitnessGoal = 3000;	
	@SerializedName("continueTeaching")
	public boolean continueTeaching = false;
	@SerializedName("numberOfEntitiesPerGen")
	public int numberOfEntitiesPerGen = 50;
	@SerializedName("inputSize")
	public int inputSize = 6;
	@SerializedName("selectionRatio")
	public float selectionRatio = 0.3f;
	public int[] architecture = {6,6,5};
	@SerializedName("elitism")
	public boolean elitism = true;
	@SerializedName("avgFitness")
	public Float avgFitness = null;
	@SerializedName("deltaFitness")
	public Float deltaFitness = null;
		
	transient public int entityIndex = 0;
	
	transient public boolean isTeachingEnded = false;
	
	public transient Pair<Integer, Generation> bestFitness;
	
	private static transient Gson gson;
	
	private static transient final String fileName = "params.json";
	
	public static TeachingParams getTeachingParams() {
		try {
			File f = new File(fileName);
			
			if(!f.exists()) {
				TeachingParams newParams = new TeachingParams();
				
				serialize(newParams);
				
				return newParams;
			}
			return deSerialize();
		}catch (Exception e) {
			return new TeachingParams();
		}
	}
	
	public static void serialize(TeachingParams params) throws IOException {
		if(gson == null) {
			GsonBuilder builder = new GsonBuilder();
			builder.serializeSpecialFloatingPointValues();
			gson = builder.create();
		}
			
		File f = new File(fileName);
		
		if(!f.exists()) {
			FileWriter fw = new FileWriter(f);			
			String str = gson.toJson(params, TeachingParams.class);
			
			fw.write(str);
			fw.flush();
			fw.close();
		}
	}
	
	public static TeachingParams deSerialize() throws IOException {
		if(gson == null) {
			GsonBuilder builder = new GsonBuilder();
			builder.serializeSpecialFloatingPointValues();
			gson = builder.create();
		}
		File f = new File(fileName);
		
		FileReader fr = new FileReader(f);
		
		TeachingParams parameters = gson.fromJson(gson.newJsonReader(fr), TeachingParams.class);
		fr.close();
		
		if(parameters == null)
			return new TeachingParams();
		else
			return parameters;
	}
	
	
	

	public int getMaxNumberOfGenerations() {
		return maxNumberOfGenerations;
	}

	public void setMaxNumberOfGenerations(int maxNumberOfGenerations) {
		this.maxNumberOfGenerations = maxNumberOfGenerations;
	}

	public float getMutationFactor() {
		return mutationFactor;
	}

	public void setMutationFactor(float mutationFactor) {
		this.mutationFactor = mutationFactor;
	}

	public int getEntityIndex() {
		return entityIndex;
	}

	public void setEntityIndex(int entityIndex) {
		this.entityIndex = entityIndex;
	}

	public boolean isContinueTeaching() {
		return continueTeaching;
	}

	public void setContinueTeaching(boolean continueTeaching) {
		this.continueTeaching = continueTeaching;
	}

	public int getNumberOfEntitiesPerGen() {
		return numberOfEntitiesPerGen;
	}

	public void setNumberOfEntitiesPerGen(int numberOfEntitiesPerGen) {
		this.numberOfEntitiesPerGen = numberOfEntitiesPerGen;
	}

	public boolean isTeachingEnded() {
		return isTeachingEnded;
	}

	public void setTeachingEnded(boolean isTeachingEnded) {
		this.isTeachingEnded = isTeachingEnded;
	}

	public Pair<Integer, Generation> getBestFitness() {
		return bestFitness;
	}

	public void setBestFitness(Pair<Integer, Generation> bestFitness) {
		this.bestFitness = bestFitness;
	}

	public int getInputSize() {
		return inputSize;
	}

	public void setInputSize(int inputSize) {
		this.inputSize = inputSize;
	}

	public float getSelectionRatio() {
		return selectionRatio;
	}

	public void setSelectionRatio(float selectionRatio) {
		this.selectionRatio = selectionRatio;
	}

	public int[] getArchitecture() {
		return architecture;
	}

	public void setArchitecture(int[] architecture) {
		this.architecture = architecture;
	}

	public boolean isElitism() {
		return elitism;
	}

	public void setElitism(boolean elitism) {
		this.elitism = elitism;
	}

	public int getFitnessGoal() {
		return fitnessGoal;
	}

	public void setFitnessGoal(int fitnessGoal) {
		this.fitnessGoal = fitnessGoal;
	}

	public float getAvgFitness() {
		return avgFitness;
	}

	public void setAvgFitness(float avgFitness) {
		this.avgFitness = avgFitness;
	}

	public Float getDeltaFitness() {
		return deltaFitness;
	}

	public void setDeltaFitness(Float deltaFitness) {
		this.deltaFitness = deltaFitness;
	}

	public void setAvgFitness(Float avgFitness) {
		this.avgFitness = avgFitness;
	}
	
	}
