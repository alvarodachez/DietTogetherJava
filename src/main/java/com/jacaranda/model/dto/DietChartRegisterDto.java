package com.jacaranda.model.dto;

import java.util.List;

public class DietChartRegisterDto {

	String name;
	
	List<DietRegisterDto> series;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DietRegisterDto> getSeries() {
		return series;
	}

	public void setSeries(List<DietRegisterDto> series) {
		this.series = series;
	}

	
	
	

	
	
	
	
}
