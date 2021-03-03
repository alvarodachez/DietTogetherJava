package com.jacaranda.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.common.DietImcConstants;
import com.jacaranda.model.DietRegister;
import com.jacaranda.model.DietScale;
import com.jacaranda.model.DietScaleImc;
import com.jacaranda.repository.DietAthleteRepository;
import com.jacaranda.repository.DietPhysicalDataRepository;
import com.jacaranda.repository.DietRegisterRepository;
import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.services.DietRegisterServiceI;

@Service("registerService")
public class DietRegisterServiceImpl implements DietRegisterServiceI {

	@Autowired
	DietUserRepository userRepo;

	@Autowired
	DietRegisterRepository registerRepo;

	@Autowired
	DietAthleteRepository athleteRepo;
	
	@Autowired
	DietPhysicalDataRepository physicalDataRepo;

	@Override
	public DietRegister createRegister(String username, DietRegister register) {
		DietUser user = userRepo.findByUsername(username).get();
		
		DietRegister registerToCreate = new DietRegister();
		
		registerToCreate.setWeight(register.getWeight());
		registerToCreate.setWeightDate(LocalDate.now());

		if(user.getAthleteId().getPhysicalData().getLastRegister() == null) {
			
			registerToCreate.setWeightDifference(user.getAthleteId().getPhysicalData().getWeight() - registerToCreate.getWeight());
			
			registerRepo.save(registerToCreate);
			
			user.getAthleteId().getPhysicalData().setLastRegister(registerToCreate);
			
			user.getAthleteId().getPhysicalData().setWeight(registerToCreate.getWeight());
			
			user.getAthleteId().getPhysicalData().getImc().setImcValue(registerToCreate.getWeight()/Math.pow(user.getAthleteId().getPhysicalData().getHeight(), 2));
			
			user.getAthleteId().getPhysicalData().getImc().setActualScale(this.scaleCalculation(registerToCreate.getWeight(),user.getAthleteId().getPhysicalData().getImc().getScales()));
			
			
			
			
			
			physicalDataRepo.save(user.getAthleteId().getPhysicalData());
			
			athleteRepo.save(user.getAthleteId());
			
			userRepo.save(user);
			
		}else {
			LocalDate dateLimit = user.getAthleteId().getPhysicalData().getLastRegister().getWeightDate().plusWeeks(1L);
			
			if(registerToCreate.getWeightDate().isAfter(dateLimit)) {
				registerToCreate.setWeightDifference(user.getAthleteId().getPhysicalData().getLastRegister().getWeight() - registerToCreate.getWeight());
				
				registerRepo.save(registerToCreate);
				
				user.getAthleteId().getPhysicalData().getRegisters().add(user.getAthleteId().getPhysicalData().getLastRegister());
				
				user.getAthleteId().getPhysicalData().setLastRegister(registerToCreate);
				
				physicalDataRepo.save(user.getAthleteId().getPhysicalData());
				
				athleteRepo.save(user.getAthleteId());
				
				userRepo.save(user);
				
				
			}
		}
		return user.getAthleteId().getPhysicalData().getLastRegister();
	}
	
	private Double gamePointCalculation(DietScale actualScale) {
		
		Double res = 0.0;
		if(actualScale == DietScale.NORMALWEIGHT) {
			res = DietImcConstants.NORMOPESO_POINTS;
		}else if(actualScale == DietScale.OVERWEIGHT_ONE) {
			res = DietImcConstants.SOBREPESO1_POINTS;
		}else if(actualScale == DietScale.OVERWEIGHT_TWO) {
			res = DietImcConstants.SOBREPESO2_POINTS;
		}else if(actualScale == DietScale.OBESITY_ONE) {
			res = DietImcConstants.OBESIDAD1_POINTS;
		}else if(actualScale == DietScale.OBESITY_TWO) {
			res = DietImcConstants.OBESIDAD2_POINTS;
		}else if(actualScale == DietScale.OBESITY_THREE) {
			res = DietImcConstants.OBESIDAD3_POINTS;
		}else {
			res = DietImcConstants.OBESIDAD4_POINTS;
		}
		
		return res;
	}
	
	private DietScale scaleCalculation(Double weight, List<DietScaleImc> scalesImc) {

		/** Variable a devolver */
		DietScale actualScale = DietScale.OBESITY_FOUR;

		/** Diferentes escalas del imc */
		Double scale1, scale2, scale3, scale4, scale5, scale6, scale7;

		/** Asignamos datos de cada escala del atleta */
		scale1 = scalesImc.get(0).getWeightScale();
		scale2 = scalesImc.get(1).getWeightScale();
		scale3 = scalesImc.get(2).getWeightScale();
		scale4 = scalesImc.get(3).getWeightScale();
		scale5 = scalesImc.get(4).getWeightScale();
		scale6 = scalesImc.get(5).getWeightScale();
		scale7 = scalesImc.get(6).getWeightScale();

		/**
		 * Calculo de la escala segun en que intervalo se encuentra el atleta con su
		 * peso
		 */
		if ((scale1 <= weight) && (weight < scale2)) {
			actualScale = DietScale.NORMALWEIGHT;
		} else if ((scale2 <= weight) && (weight < scale3)) {
			actualScale = DietScale.OVERWEIGHT_ONE;
		} else if ((scale3 <= weight) && (weight < scale4)) {
			actualScale = DietScale.OVERWEIGHT_TWO;
		} else if ((scale4 <= weight) && (weight < scale5)) {
			actualScale = DietScale.OBESITY_ONE;
		} else if ((scale5 <= weight) && (weight < scale6)) {
			actualScale = DietScale.OBESITY_TWO;
		} else if ((scale6 <= weight) && (weight < scale7)) {
			actualScale = DietScale.OBESITY_THREE;
		}

		return actualScale;
	}

}
