package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DietBoostAthlete implements Serializable{
	
	/** SERIAL ID */
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String username;
	
	private Double weightChallenge;
	
	private List<LocalDate> boostDates;

	
	
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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the weightChallenge
	 */
	public Double getWeightChallenge() {
		return weightChallenge;
	}

	/**
	 * @param weightChallenge the weightChallenge to set
	 */
	public void setWeightChallenge(Double weightChallenge) {
		this.weightChallenge = weightChallenge;
	}

	/**
	 * @return the boostDates
	 */
	@ElementCollection
	public List<LocalDate> getBoostDates() {
		return boostDates;
	}

	/**
	 * @param boostDates the boostDates to set
	 */
	public void setBoostDates(List<LocalDate> boostDates) {
		this.boostDates = boostDates;
	}

	@Override
	public String toString() {
		return "DietBoostAthlete [id=" + id + ", username=" + username + ", weightChallenge=" + weightChallenge
				+ ", boostDates=" + boostDates + "]";
	}
	
	
}
