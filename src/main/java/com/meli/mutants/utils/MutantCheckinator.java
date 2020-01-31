package com.meli.mutants.utils;

import java.util.Map;

import com.google.common.collect.Maps;

public class MutantCheckinator implements MutantCheckinatorInt {


	private String[] dnaSequence;
	private int horizontalHits = 0;
	private int verticalHits = 0;
	private int diagonalHits = 0;
	
	//hacer fors y condiciones de evaluacion segun sea diagonal o vertical, no analizar nada si estas en diagonal y te pasaste de rango
	
	@Override
	public boolean isMutant() {
		setHorizontalHits();
		System.out.println("horizontales: "+horizontalHits);
		if (horizontalHits > 1) return true;
		setVerticalHits();
		System.out.println("verticalHits: "+verticalHits);
		if (horizontalHits + verticalHits > 1) return true;
		setDiagonalHits();
		System.out.println("diagonalHits: "+diagonalHits);
		if (horizontalHits + verticalHits + diagonalHits > 1) return true;
		return false;
	}
	
	
	public String[] getDnaSequence() {
		return dnaSequence;
	}

	public void setDnaSequence(String[] dnaSequence) {
		this.dnaSequence = dnaSequence;
	}

	@Override
	public void setDiagonalHits() {
		Map<String,Integer> temporaryDiagonalCounters = Maps.newHashMap();
		KeyGenerator keyGenerator = new KeyGenerator();
		
		String clave ="";
		for (int i = 0; i < dnaSequence.length-1; i++) {
			
			for (int j = 0; j < dnaSequence.length-1; j++) {
				clave = keyGenerator.generateDiagonalKey(i, j);
				
				if (dnaSequence[i].charAt(j) == dnaSequence[i+1].charAt(j+1) ) {
					if (temporaryDiagonalCounters.containsKey(clave)) {
						temporaryDiagonalCounters.put(clave, temporaryDiagonalCounters.get(clave)+1);
			        } else {
			        	temporaryDiagonalCounters.put(clave, 1);
			        }
					
					if (temporaryDiagonalCounters.containsKey(clave) && temporaryDiagonalCounters.get(clave)>=3) {
						this.diagonalHits++;
					}
				}
				else {
					temporaryDiagonalCounters.put(clave, 0);
				}
			}
		}
	}

	@Override
	public void setVerticalHits() {
		Map<Integer,Integer> temporaryVerticalCounters = Maps.newHashMap();
		
		for (int i = 0; i < dnaSequence.length-1; i++) {
			for (int j = 0; j < dnaSequence.length; j++) {
				if (dnaSequence[i].charAt(j) == dnaSequence[i+1].charAt(j) ) {
					if (temporaryVerticalCounters.containsKey(j)) {
			        	temporaryVerticalCounters.put(j, temporaryVerticalCounters.get(j)+1);
			        } else {
			        	temporaryVerticalCounters.put(j, 1);
			        }
					
					if (temporaryVerticalCounters.containsKey(j) && temporaryVerticalCounters.get(j)>=3) {
						this.verticalHits++;
					}
				}
				else {
					temporaryVerticalCounters.put(j, 0);
				}
			}
		}
		
	}

	@Override
	public void setHorizontalHits() {
		for (int i = 0; i < dnaSequence.length; i++) {
			int hHits = 0;
			for (int j = 0; j < dnaSequence[i].length()-1; j++) {
				if (dnaSequence[i].charAt(j) == dnaSequence[i].charAt(j+1) ) {
					hHits++;
					if (hHits>=3) {//cuando encuentre 3 pares sucesivos iguales o mas, es porque hay 4 letras iguales o 5 o mas
						this.horizontalHits++;
					}
				}
			}
		}
	}

}
