package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DietRegister implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Double weight;

	private LocalDate weightDate;

	private LocalDate nextDateRegister;

	private Double weightDifference;
	
	private DietRegisterStatus registerStatus;
	
	private String athlete;

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * @return the weightDate
	 */
	public LocalDate getWeightDate() {
		return weightDate;
	}

	/**
	 * @param weightDate the weightDate to set
	 */
	public void setWeightDate(LocalDate weightDate) {
		this.weightDate = weightDate;
	}

	/**
	 * @return the nextDateRegister
	 */
	public LocalDate getNextDateRegister() {
		return nextDateRegister;
	}

	/**
	 * @param nextDateRegister the nextDateRegister to set
	 */
	public void setNextDateRegister(LocalDate nextDateRegister) {
		this.nextDateRegister = nextDateRegister;
	}

	/**
	 * @return the weightDifference
	 */
	public Double getWeightDifference() {
		return weightDifference;
	}

	/**
	 * @param weightDifference the weightDifference to set
	 */
	public void setWeightDifference(Double weightDifference) {
		this.weightDifference = weightDifference;
	}



	/**
	 * @return the registerStatus
	 */
	@Column(name = "register_status")
	@Enumerated(EnumType.STRING)
	public DietRegisterStatus getRegisterStatus() {
		return registerStatus;
	}

	/**
	 * @param registerStatus the registerStatus to set
	 */
	public void setRegisterStatus(DietRegisterStatus registerStatus) {
		this.registerStatus = registerStatus;
	}

	/**
	 * @return the athlete
	 */
	public String getAthlete() {
		return athlete;
	}

	/**
	 * @param athlete the athlete to set
	 */
	public void setAthlete(String athlete) {
		this.athlete = athlete;
	}

	@Override
	public String toString() {
		return "DietRegister [id=" + id + ", weight=" + weight + ", weightDate=" + weightDate + ", nextDateRegister="
				+ nextDateRegister + ", weightDifference=" + weightDifference + ", registerStatus=" + registerStatus
				+ ", athlete=" + athlete + "]";
	}
	
	
	
	

}
