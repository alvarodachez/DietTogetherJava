package com.jacaranda.model.dto;

import java.time.LocalDate;
import java.util.List;

import com.jacaranda.model.DietPrivateRegisterMode;
import com.jacaranda.model.DietRegister;

public class DietPrivateActivityProgressiveDto extends DietPrivateActivityDto {

	private DietPrivateRegisterMode registerMode;

	private List<DietRegister> daylyRegisters;

	private List<DietRegister> totalRegisters;

	private LocalDate actualProgressiveDate;

	private LocalDate nextProgressiveDate;

	public List<DietRegister> getDaylyRegisters() {
		return daylyRegisters;
	}

	public void setDaylyRegisters(List<DietRegister> daylyRegisters) {
		this.daylyRegisters = daylyRegisters;
	}

	public List<DietRegister> getTotalRegisters() {
		return totalRegisters;
	}

	public void setTotalRegisters(List<DietRegister> totalRegisters) {
		this.totalRegisters = totalRegisters;
	}

	public LocalDate getActualProgressiveDate() {
		return actualProgressiveDate;
	}

	public void setActualProgressiveDate(LocalDate actualProgressiveDate) {
		this.actualProgressiveDate = actualProgressiveDate;
	}

	public LocalDate getNextProgressiveDate() {
		return nextProgressiveDate;
	}

	public void setNextProgressiveDate(LocalDate nextProgressiveDate) {
		this.nextProgressiveDate = nextProgressiveDate;
	}

	public DietPrivateRegisterMode getRegisterMode() {
		return registerMode;
	}

	public void setRegisterMode(DietPrivateRegisterMode registerMode) {
		this.registerMode = registerMode;
	}

}
