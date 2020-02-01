package com.meli.mutants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meli.mutants.Daos.DnaDAO;
import com.meli.mutants.Daos.StatsDAO;
import com.meli.mutants.dtos.DnaDto;
import com.meli.mutants.dtos.StatsDto;
import com.meli.mutants.models.Dna;
import com.meli.mutants.models.Stats;
import com.meli.mutants.utils.MutantCheckinator;

@RestController
public class MainController {
	//TODO: Validar que sea NxN, n==n
	// validar: A,T,C,G
	
    @Autowired
    private DnaDAO dnaDAO;
	
    @Autowired
    private StatsDAO statsDAO;
	
	@RequestMapping(value = "/mutant", method= RequestMethod.POST)
	public ResponseEntity<Boolean> checkMutant(@RequestBody DnaDto dnaSequence) {
//		DNAValidator dnaValidator = new DNAValidator();
//		if (!dnaValidator.validateDna(dnaSequence)) {
//			return false;
//		}
		
		MutantCheckinator mutantCheckinator = new MutantCheckinator(dnaSequence.dna);

		String joinedSequence = String.join("", dnaSequence.dna);
		Dna dna = new Dna(joinedSequence);
		
		try {
			dnaDAO.save(dna);
		} catch (Exception e) {
			if (!e.getMessage().contains("duplicate key error collection")) {
				e.printStackTrace();
				return new ResponseEntity<>(true, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			//En este punto ya existe esa secuencia de dna en bd y no lo checkeo
			dna = dnaDAO.findBySequence(joinedSequence);
			if(dna.isMutant()) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
			}
		}
		
		
		Stats stats = statsDAO.find().get(); 
		if (stats==null) {
			stats = new Stats();
		}
		
		//TODO: usar obligatoriamente la firma boolean isMutant(String[] dna);		
		boolean isMutant = mutantCheckinator.isMutant();
		stats.storeNewValue(statsDAO,isMutant);
		dna.storeNewValue(dnaDAO,isMutant);
 
		if (isMutant) {
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}
		
	}
	
	@RequestMapping(value = "/stats", method= RequestMethod.GET)
	public StatsDto showStats() {
		Stats stats = statsDAO.find().get(); 
		if (stats==null) {
			stats = new Stats();
		}
		return new StatsDto(stats);
	}
}
