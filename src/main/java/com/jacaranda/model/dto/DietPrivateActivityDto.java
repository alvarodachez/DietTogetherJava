package com.jacaranda.model.dto;

import java.time.LocalDate;

import com.jacaranda.model.DietPrivateActivityMode;

public class DietPrivateActivityDto {

	private Long id;

	private LocalDate expireDate;

	private Double weightObjective;

	private String athlete;

	private LocalDate createDate;

	private DietPrivateActivityMode privateActivityMode;

	private Boolean enabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public Double getWeightObjective() {
		return weightObjective;
	}

	public void setWeightObjective(Double weightObjective) {
		this.weightObjective = weightObjective;
	}

	public String getAthlete() {
		return athlete;
	}

	public void setAthlete(String athlete) {
		this.athlete = athlete;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public DietPrivateActivityMode getPrivateActivityMode() {
		return privateActivityMode;
	}

	public void setPrivateActivityMode(DietPrivateActivityMode privateActivityMode) {
		this.privateActivityMode = privateActivityMode;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
