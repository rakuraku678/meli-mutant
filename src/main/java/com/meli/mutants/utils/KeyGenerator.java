package com.meli.mutants.utils;

public class KeyGenerator {

	public String generateDiagonalKey(int i, int j) {
		String clave = "";
		// Busco unicidad de clave para linea diagonal (Elijo el primer eslabon)
		if (i != 0 && j != 0) {
			if (j > i)
				clave = "" + (i - i) + (j - i);// si esta sobre la diagonal
			else
				clave = "" + (i - j) + (j - j);// si esta debajo de la diagonal
		} else {
			clave = "" + i + j;// ya es primer eslabon
		}
		return clave;
	}
}
