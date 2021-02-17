package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DietPrivateActivity implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private LocalDateTime expireDate;

	private Double weightObjective;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the expireDate
	 */
	public LocalDateTime getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(LocalDateTime expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the weightObjective
	 */
	public Double getWeightObjective() {
		return weightObjective;
	}

	/**
	 * @param weightObjective the weightObjective to set
	 */
	public void setWeightObjective(Double weightObjective) {
		this.weightObjective = weightObjective;
	}

}
