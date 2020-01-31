package com.meli.mutants.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.meli.mutants.controllers.dtos.DnaDto;
import com.meli.mutants.utils.DNAValidator;
import com.meli.mutants.utils.MutantCheckinator;

@RestController
@RequestMapping("/mutant")
public class MainController {
	//TODO: Validar que sea NxN, n==n
	// validar: A,T,C,G
	
	DNAValidator dnaValidator = new DNAValidator();
	
	MutantCheckinator mutantCheckinator = new MutantCheckinator(); 
	
	@RequestMapping(value = "", method= RequestMethod.POST)
	public boolean checkMutant(@RequestBody DnaDto dnaSequence) {
		
		if (!dnaValidator.validateDna(dnaSequence)) {
			return false;
		}
		
		mutantCheckinator.setDnaSequence(dnaSequence.dna);
		
		return mutantCheckinator.isMutant();
		
	}
	
	@RequestMapping(value = "/versiondeprecada", method= RequestMethod.POST)
	public int checkMutantDeprecated(@RequestBody DnaDto dnaSequence) {
		int result = checkDiagonalHits(dnaSequence.dna);
		return result;
	}
	
	private int checkHorizontalHits(String[] sequence) {
		
		int horizontalHits = 0;
		for (int i = 0; i < sequence.length; i++) {
			int hHits = 0;
			for (int j = 0; j < sequence[i].length()-1; j++) {
				if (sequence[i].charAt(j) == sequence[i].charAt(j+1) ) {
					hHits++;
					if (hHits>=3) {//cuando encuentre 3 pares sucesivos iguales o mas, es porque hay 4 letras iguales o 5 o mas
						horizontalHits++;
					}
				}
			}
		}
		return horizontalHits;
	}
	
	private int checkVerticalHits(String[] sequence) {
		
		Map<Integer,Integer> temporaryVerticalCounters = Maps.newHashMap();
		
		int verticalHits = 0;
		for (int i = 0; i < sequence.length-1; i++) {
			for (int j = 0; j < sequence.length; j++) {
				if (sequence[i].charAt(j) == sequence[i+1].charAt(j) ) {
					if (temporaryVerticalCounters.containsKey(j)) {
			        	temporaryVerticalCounters.put(j, temporaryVerticalCounters.get(j)+1);
			        } else {
			        	temporaryVerticalCounters.put(j, 1);
			        }
					
					if (temporaryVerticalCounters.containsKey(j) && temporaryVerticalCounters.get(j)>=3) {
						verticalHits++;
					}
				}
				else {
					temporaryVerticalCounters.put(j, 0);
				}
			}
		}
		
		return verticalHits;
	}
	
	
	private int checkDiagonalHits(String[] sequence) {
		
		Map<String,Integer> temporaryDiagonalCounters = Maps.newHashMap();
		
		int verticalHits = 0;
		String clave ="";
		for (int i = 0; i < sequence.length-1; i++) {
			
			for (int j = 0; j < sequence.length-1; j++) {
				//Busco unicidad de clave para linea diagonal (Elijo el primer eslabon de la diagonal)
				if (i!=0 && j!=0) {
					if (j>i)
						clave = ""+(i-i)+(j-i);//si esta sobre la diagonal
					else
						clave = ""+(i-j)+(j-j);//si esta debajo de la diagonal
				}
				else {
					clave = ""+i+j;//ya es primer eslabon
				}
				
				if (sequence[i].charAt(j) == sequence[i+1].charAt(j+1) ) {
					if (temporaryDiagonalCounters.containsKey(clave)) {
						temporaryDiagonalCounters.put(clave, temporaryDiagonalCounters.get(clave)+1);
			        } else {
			        	temporaryDiagonalCounters.put(clave, 1);
			        }
					
					if (temporaryDiagonalCounters.containsKey(clave) && temporaryDiagonalCounters.get(clave)>=3) {
						verticalHits++;
					}
				}
				else {
					temporaryDiagonalCounters.put(clave, 0);
				}
			}
		}
		
		return verticalHits;
	}
}
