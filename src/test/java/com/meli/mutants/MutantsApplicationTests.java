package com.meli.mutants;

import java.net.URI;
import java.net.URISyntaxException;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.meli.mutants.daos.DnaDAO;
import com.meli.mutants.daos.StatsDAO;
import com.meli.mutants.dtos.DnaDto;
import com.meli.mutants.dtos.StatsDto;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MutantsApplicationTests {

	@LocalServerPort
	int randomServerPort;

	@Autowired
	private DnaDAO dnaDAO;

	@Autowired
	private StatsDAO statsDAO;

	@Test
	void contextLoads() throws URISyntaxException, ParseException {

		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		DnaDto dnaDto = new DnaDto(dna);

		String[] dna2 = { "CTGCAA", "CAGTGC", "TTATGT", "AGAAGG", "CGCCTA", "TCACTG" };
		DnaDto dnaDto2 = new DnaDto(dna2);

		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/mutant";
		URI uri = new URI(baseUrl);

		final String baseUrl2 = "http://localhost:" + randomServerPort + "/stats";
		URI uri2 = new URI(baseUrl2);

		RequestEntity<DnaDto> requestEntity = RequestEntity.post(uri).contentType(MediaType.APPLICATION_JSON)
				.body(dnaDto);
		ResponseEntity<String> result = restTemplate.exchange(requestEntity, String.class);
		Assert.assertEquals(200, result.getStatusCodeValue());

		requestEntity = RequestEntity.post(uri).contentType(MediaType.APPLICATION_JSON).body(dnaDto2);
		boolean failed;
		try {
			ResponseEntity<String> result2 = restTemplate.exchange(requestEntity, String.class);
			failed = true;
		} catch (Exception e) {
			failed = false;
		}

		Assert.assertEquals(failed, false);

		ResponseEntity<StatsDto> result3 = restTemplate.getForEntity(uri2, StatsDto.class);
		Assert.assertEquals(result3.getBody().count_human_dna, 1);
		Assert.assertEquals(result3.getBody().count_mutant_dna, 1);

		dnaDAO.deleteAll();
		statsDAO.deleteAll();
	}
}
