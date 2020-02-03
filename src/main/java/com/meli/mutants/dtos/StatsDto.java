package com.meli.mutants.dtos;

import com.meli.mutants.models.Stats;

public class StatsDto {
	public int count_mutant_dna;
	public int count_human_dna;
	public float ratio;

	public StatsDto(Stats stats) {
		this.count_human_dna = stats.getHumanCount();
		this.count_mutant_dna = stats.getMutantCount();
		if (this.count_human_dna == 0)
			this.ratio = 1;
		else
			this.ratio = (float) this.count_mutant_dna / this.count_human_dna;
	}
	
	public StatsDto() {

	}
	
}
