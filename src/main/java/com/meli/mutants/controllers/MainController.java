package com.meli.mutants.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meli.mutants.controllers.dtos.DnaDto;
import com.meli.mutants.utils.DNAValidator;
import com.meli.mutants.utils.MutantCheckinator;

@RestController
@RequestMapping("/mutant")
public class MainController {
	//TODO: Validar que sea NxN, n==n
	// validar: A,T,C,G
	
	
	@RequestMapping(value = "", method= RequestMethod.POST)
	public ResponseEntity<Boolean> checkMutant(@RequestBody DnaDto dnaSequence) {
//		DNAValidator dnaValidator = new DNAValidator();
//		if (!dnaValidator.validateDna(dnaSequence)) {
//			return false;
//		}
		
		MutantCheckinator mutantCheckinator = new MutantCheckinator(dnaSequence.dna); 
		
		if(mutantCheckinator.isMutant()) {
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}
		
	}
	
}
