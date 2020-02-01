package com.meli.mutants.models;

import org.mongodb.morphia.annotations.Entity;

import com.meli.mutants.Daos.StatsDAO;

@Entity
public class Stats extends BaseModel {

	private int mutantCount;
	private int humanCount;
	
    public Stats() {
		super();
	}
    
	public Integer getMutantCount() {
		return mutantCount;
	}
	public void setMutantCount(Integer mutantCount) {
		this.mutantCount = mutantCount;
	}
	public Integer getHumanCount() {
		return humanCount;
	}
	public void setHumanCount(Integer humanCount) {
		this.humanCount = humanCount;
	}

	public void incrementHumanCount() {
		this.humanCount++;
	}
	
	public void incrementMutantCount() {
		this.mutantCount++;
	}
	
	public void storeNewValue(StatsDAO statsDAO, boolean isMutant) {
		if (isMutant) {
			this.incrementMutantCount();
		}
		else {
			this.incrementHumanCount();
		}
		statsDAO.save(this);
	}
	
}
