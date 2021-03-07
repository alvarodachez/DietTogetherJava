package com.jacaranda.model.dto;

public class DietProgressBarDto {
	
	private Long daysLeft;
	
	private Double totalPercentage;

	/**
	 * @return the daysLeft
	 */
	public Long getDaysLeft() {
		return daysLeft;
	}

	/**
	 * @param daysLeft the daysLeft to set
	 */
	public void setDaysLeft(Long daysLeft) {
		this.daysLeft = daysLeft;
	}

	/**
	 * @return the totalPercentage
	 */
	public Double getTotalPercentage() {
		return totalPercentage;
	}

	/**
	 * @param totalPercentage the totalPercentage to set
	 */
	public void setTotalPercentage(Double totalPercentage) {
		this.totalPercentage = totalPercentage;
	}
	
	

}
