package com.jacaranda.services;

import com.jacaranda.model.DietAthlete;
import com.jacaranda.model.dto.DietAthleteDTO;

public interface DietAthleteServiceI {

	public DietAthlete signUpPrincipalData(String username, DietAthleteDTO athleteDto);
}
