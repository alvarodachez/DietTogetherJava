package com.jacaranda.services;

import java.util.List;

import com.jacaranda.model.dto.DietChartGroupPointsDto;
import com.jacaranda.model.dto.DietChartRegisterDto;

public interface DietChartServiceI {

	public List<DietChartRegisterDto> totalProfileRegisters(String username);
	
	public List<DietChartRegisterDto> totalProfileWeightDifferenceRegisters(String username);
	
	public List<DietChartRegisterDto> totalGroupRegisters(String username);
	
	public List<DietChartGroupPointsDto> pointsGroup(String username);
}
