package com.jacaranda.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacaranda.model.DietAthlete;
import com.jacaranda.model.dto.DietAthleteDTO;
import com.jacaranda.services.impl.DietAthleteServiceImpl;

@RestController
@RequestMapping("/athlete")
public class DietAthleteController {

	@Autowired
	DietAthleteServiceImpl athleteService;

	@PostMapping("/sign-up-data/{username}")
	public ResponseEntity<DietAthlete> signUpPrincipalData(@PathVariable String username,
			@RequestBody DietAthleteDTO athleteDTO) {

		return ResponseEntity.status(HttpStatus.CREATED).body(athleteService.signUpPrincipalData(username, athleteDTO));

	}
}
