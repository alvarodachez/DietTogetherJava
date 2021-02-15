package com.jacaranda.services.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.model.DietAthlete;
import com.jacaranda.model.DietImc;
import com.jacaranda.model.DietPhysicalData;
import com.jacaranda.model.dto.DietAthleteDTO;
import com.jacaranda.repository.DietAthleteRepository;
import com.jacaranda.repository.DietImcRepository;
import com.jacaranda.repository.DietPhysicalDataRepository;
import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.services.DietAthleteServiceI;

@Service("athleteService")
public class DietAthleteServiceImpl implements DietAthleteServiceI {

	@Autowired
	DietAthleteRepository athleteRepo;

	@Autowired
	DietImcRepository imcRepo;

	@Autowired
	DietPhysicalDataRepository physicalDataRepo;

	@Autowired
	DietUserRepository userRepo;

	@Override
	public DietAthlete signUpPrincipalData(String username, DietAthleteDTO athleteDto) {

		DietUser user = userRepo.findByUsername(username).get();

		DietAthlete athlete = new DietAthlete();

		DietPhysicalData physicalData = new DietPhysicalData();

		DietImc imc = new DietImc();

		athlete.setName(athleteDto.getName());
		athlete.setSurname(athleteDto.getSurname());
		athlete.setBirthDay(LocalDateTime.now());

		physicalData.setWeight(athleteDto.getWeight());
		physicalData.setHeight(athleteDto.getHeight());
		
		imc.setImcValue(athleteDto.getWeight() / (athleteDto.getHeight() * athleteDto.getHeight()));
		
		imcRepo.save(imc);
		physicalDataRepo.save(physicalData);
		athleteRepo.save(athlete);
		
		physicalData.setImc(imc);
		physicalDataRepo.save(physicalData);
		
		athlete.setPhysicalData(physicalData);
		athleteRepo.save(athlete);
		
		user.setAthleteId(athlete);
		userRepo.save(user);

		return user.getAthleteId();
	}

}
