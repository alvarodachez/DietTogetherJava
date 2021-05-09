package com.jacaranda.model.dto;

import java.util.List;

import com.jacaranda.model.DietPrivateRegisterMode;
import com.jacaranda.model.DietRegister;

public class DietPrivateActivityClassicDto extends DietPrivateActivityDto {
	
	private DietPrivateRegisterMode registerMode;

	private List<DietRegister> totalRegisters;

	public List<DietRegister> getTotalRegisters() {
		return totalRegisters;
	}

	public void setTotalRegisters(List<DietRegister> totalRegisters) {
		this.totalRegisters = totalRegisters;
	}

	public DietPrivateRegisterMode getRegisterMode() {
		return registerMode;
	}

	public void setRegisterMode(DietPrivateRegisterMode registerMode) {
		this.registerMode = registerMode;
	}
	
	

}
