package com.meli.mutants.models;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import com.meli.mutants.daos.DnaDAO;

@Entity
public class Dna extends BaseModel {

	@Indexed(options = @IndexOptions(unique = true))
	private String sequence;

	private boolean isMutant;

	public Dna() {
		super();
	}

	public Dna(String sequence) {
		super();
		this.sequence = sequence;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public boolean isMutant() {
		return isMutant;
	}

	public void setMutant(boolean isMutant) {
		this.isMutant = isMutant;
	}

	public void storeNewValue(DnaDAO dnaDAO, boolean isM) {
		this.setMutant(isM);
		dnaDAO.save(this);
	}

}
