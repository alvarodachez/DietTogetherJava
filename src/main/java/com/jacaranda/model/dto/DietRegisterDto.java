package com.jacaranda.model.dto;

import java.time.LocalDate;

public class DietRegisterDto {

	LocalDate name;

	Double value;

	public LocalDate getName() {
		return name;
	}

	public void setName(LocalDate name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	

}
