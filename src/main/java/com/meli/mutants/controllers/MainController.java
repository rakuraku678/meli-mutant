package com.meli.mutants.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.meli.mutants.controllers.dtos.DnaDto;

@RestController
@RequestMapping("/mutant")
public class MainController {
	//TODO: Validar que sea NxN, n==n
	// validar: A,T,C,G
	
	
	@RequestMapping(value = "", method= RequestMethod.POST)
	public int checkMutant(@RequestBody DnaDto dnaSequence) {
		int result = checkDiagonalHits(dnaSequence.dna);
		return result;
	}
	
	private int checkHorizontalHits(String[] sequence) {
		
		int horizontalHits = 0;
		for (int i = 0; i < sequence.length; i++) {
			int hHits = 0;
			for (int j = 0; j < sequence[i].length()-1; j++) {
				if (sequence[i].charAt(j) == sequence[i].charAt(j+1) ) {
					int positionI = i;
					int positionJ = j+1;
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
		
		Map<String,Integer> temporaryVerticalCounters = Maps.newHashMap();
		
		int verticalHits = 0;
		String clave ="";
		for (int i = 0; i < sequence.length-1; i++) {
			
			for (int j = 0; j < sequence.length-1; j++) {
				
				if (i==0 || j==0) {
					clave = ""+i+j;
				}
				
				System.out.println("++++");
				System.out.println("Evaluando: "+sequence[i].charAt(j));
				System.out.println("Contra: "+sequence[i+1].charAt(j+1));
				System.out.println("++++");
				
				if (sequence[i].charAt(j) == sequence[i+1].charAt(j+1) ) {
					System.out.println("====");
					System.out.println("encontre letra "+sequence[i].charAt(j));
					
					if (temporaryVerticalCounters.containsKey(clave)) {
			        	temporaryVerticalCounters.put(clave, temporaryVerticalCounters.get(clave)+1);
			        } else {
			        	temporaryVerticalCounters.put(clave, 1);
			        }
					System.out.println("Actual hHits en columna "+clave+": "+temporaryVerticalCounters.get(clave));
					
					if (temporaryVerticalCounters.containsKey(clave) && temporaryVerticalCounters.get(clave)>=3) {
						verticalHits++;
					}
					System.out.println("====");
				}
				else {
					temporaryVerticalCounters.put(clave, 0);
				}
			}
		}
		
		return verticalHits;
	}
}
