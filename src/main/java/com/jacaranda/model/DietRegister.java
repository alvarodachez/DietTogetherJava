package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class DietRegister implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Double weight;

	private LocalDateTime weightDate;

	private DietImc imc;

	private Double weightDifference;

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
	public LocalDateTime getWeightDate() {
		return weightDate;
	}

	/**
	 * @param weightDate the weightDate to set
	 */
	public void setWeightDate(LocalDateTime weightDate) {
		this.weightDate = weightDate;
	}

	/**
	 * @return the imc
	 */
	@OneToOne
	@JoinColumn(name = "imc_id")
	public DietImc getImc() {
		return imc;
	}

	/**
	 * @param imc the imc to set
	 */
	public void setImc(DietImc imc) {
		this.imc = imc;
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

}
