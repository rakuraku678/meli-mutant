package com.meli.mutants.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meli.mutants.daos.StatsDAO;
import com.meli.mutants.dtos.StatsDto;
import com.meli.mutants.models.Stats;

@RestController
public class StatsController {

	@Autowired
	private StatsDAO statsDAO;

	@RequestMapping(value = "/stats", method = RequestMethod.GET)
	public StatsDto showStats() {
		Stats stats = statsDAO.find().get();
		if (stats == null) {
			stats = new Stats();
		}
		return new StatsDto(stats);
	}
}
