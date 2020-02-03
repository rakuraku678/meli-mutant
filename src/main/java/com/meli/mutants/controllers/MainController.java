package com.meli.mutants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meli.mutants.daos.DnaDAO;
import com.meli.mutants.daos.StatsDAO;
import com.meli.mutants.dtos.DnaDto;
import com.meli.mutants.models.Dna;
import com.meli.mutants.models.Stats;
import com.meli.mutants.utils.MutantCheckinator;

@RestController
public class MainController {

	@Autowired
	private DnaDAO dnaDAO;

	@Autowired
	private StatsDAO statsDAO;

	@RequestMapping(value = "/mutant", method = RequestMethod.POST)
	public ResponseEntity<Boolean> checkMutant(@RequestBody DnaDto dnaSequence) {

		String joinedSequence = String.join("", dnaSequence.dna);

		Dna dna = dnaDAO.findBySequence(joinedSequence);
		//si existe un dna no lo chequeo solo devuelvo el resultado
		if (dna != null) {
			if (dna.isMutant()) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
			}
		}

		dna = new Dna(joinedSequence);
		
		// En este punto la secuencia de DNA es nueva para nuestra BD por lo que la
		// analizo.
		Stats stats = statsDAO.find().get();
		if (stats == null) {
			stats = new Stats();
		}

		MutantCheckinator mutantCheckinator = new MutantCheckinator(dnaSequence.dna);
		boolean isMutant = mutantCheckinator.isMutant();
		stats.storeNewValue(statsDAO, isMutant);
		dna.storeNewValue(dnaDAO, isMutant);

		if (isMutant) {
			return new ResponseEntity<>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}
	}

}
