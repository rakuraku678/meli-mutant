package com.meli.mutants.utils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Maps;

public class MutantCheckinator implements MutantCheckinatorInt {

	private String[] dnaSequence;
	private int horizontalHits = 0;
	private int verticalHits = 0;
	private int diagonalHits = 0;
	private KeyGenerator keyGenerator = new KeyGenerator();

	public MutantCheckinator(String[] dna) {
		this.dnaSequence = dna;
	}

	@Override
	public boolean isMutant() {
		Map<Integer, Integer> temporaryVerticalCounters = Maps.newHashMap();// memoria de hits en secuencia vertical
		Map<String, Integer> temporaryDiagonalCounters = Maps.newHashMap();// memoria de hits en secuencia diagonal
		AtomicInteger hHits = new AtomicInteger(0);// memoria de letras en secuencia horizontal

		for (int i = 0; i < dnaSequence.length; i++) {
			hHits.set(0);
			for (int j = 0; j < dnaSequence.length; j++) {

				evaluateHorizontals(i, j, hHits);
				if (horizontalHits > 1)
					return true;

				evaluateVerticals(i, j, temporaryVerticalCounters);
				if (horizontalHits + verticalHits > 1)
					return true;

				evaluateDiagonals(i, j, temporaryDiagonalCounters);
				if (horizontalHits + verticalHits + diagonalHits > 1)
					return true;
			}
		}
		return false;
	}

	private void evaluateVerticals(int i, int j, Map<Integer, Integer> temporaryVerticalCounters) {
		if (i >= dnaSequence.length - 1) {
			return;
		}
		if (dnaSequence[i].charAt(j) == dnaSequence[i + 1].charAt(j)) {
			if (temporaryVerticalCounters.containsKey(j)) {
				temporaryVerticalCounters.put(j, temporaryVerticalCounters.get(j) + 1);
			} else {
				temporaryVerticalCounters.put(j, 1);
			}

			if (temporaryVerticalCounters.containsKey(j) && temporaryVerticalCounters.get(j) >= 3) {
				this.verticalHits++;
			}
		} else {
			temporaryVerticalCounters.put(j, 0);
		}
	}

	private void evaluateHorizontals(int i, int j, AtomicInteger hHits) {
		if (j >= dnaSequence[i].length() - 1) {
			return;
		}
		if (dnaSequence[i].charAt(j) == dnaSequence[i].charAt(j + 1)) {
			hHits.set(hHits.get() + 1);
			if (hHits.get() >= 3) {
				this.horizontalHits++;
			}
		}
		else {
			hHits.set(0);
		}
	}

	private void evaluateDiagonals(int i, int j, Map<String, Integer> temporaryDiagonalCounters) {

		if (i >= dnaSequence.length - 1 || j >= dnaSequence.length - 1) {
			return;
		}
		String clave = keyGenerator.generateDiagonalKey(i, j);

		if (dnaSequence[i].charAt(j) == dnaSequence[i + 1].charAt(j + 1)) {
			if (temporaryDiagonalCounters.containsKey(clave)) {
				temporaryDiagonalCounters.put(clave, temporaryDiagonalCounters.get(clave) + 1);
			} else {
				temporaryDiagonalCounters.put(clave, 1);
			}

			if (temporaryDiagonalCounters.containsKey(clave) && temporaryDiagonalCounters.get(clave) >= 3) {
				this.diagonalHits++;
			}
		} else {
			temporaryDiagonalCounters.put(clave, 0);
		}
	}

	public String[] getDnaSequence() {
		return dnaSequence;
	}

	public void setDnaSequence(String[] dnaSequence) {
		this.dnaSequence = dnaSequence;
	}

}
