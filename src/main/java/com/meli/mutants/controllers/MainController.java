package com.meli.mutants.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meli.mutants.controllers.dtos.DnaDto;

@RestController
@RequestMapping("/mutant")
public class MainController {
	//TODO: Validar que sea NxN, n==n
	// validar: A,T,C,G
	
	
	@RequestMapping(value = "", method= RequestMethod.POST)
	public boolean checkMutant(@RequestBody DnaDto dnaSequence) {
		boolean result = containsIsMutantHorizontal(dnaSequence.dna);
		return result;
	}
	
	private boolean isMutantHorizontal(String[] sequence) {
		
		for (int i = 0; i < sequence.length; i++) {
			int hHits = 0;
			for (int j = 0; j < sequence[i].length()-1; j++) {
				if (sequence[i].charAt(j) == sequence[i].charAt(j+1) ) {
					int positionI = i;
					int positionJ = j+1;
					System.out.println("Actual hHits: "+hHits);
					System.out.println("encontre letra "+sequence[i].charAt(j+1));
					System.out.println("en: "+positionI+","+positionJ);
					hHits++;
					if (hHits==3) {//cuando encuentre 3 pares sucesivos iguales es porque hay 4 letras iguales 
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean containsIsMutantHorizontal(String[] sequence) {
		// (A,T,C,G
		for (int i = 0; i < sequence.length; i++) {
			if (sequence[i].contains("AAAA") || 
				sequence[i].contains("TTTT")|| 
				sequence[i].contains("CCCC")|| 
				sequence[i].contains("GGGG")) {
				return true;
			}
		}
		return false;
	}
}
