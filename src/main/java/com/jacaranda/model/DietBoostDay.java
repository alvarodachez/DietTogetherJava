package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class DietBoostDay implements Serializable{

	/** SERIAL ID*/
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private LocalDate actualDate;
	
	private LocalDate nextDate;
	
	private DietBoostAthlete boostAthlete;
	
	private List<DietBoostAthlete> boostAthletes;

	
	
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
	 * @return the actualDate
	 */
	public LocalDate getActualDate() {
		return actualDate;
	}

	/**
	 * @param actualDate the actualDate to set
	 */
	public void setActualDate(LocalDate actualDate) {
		this.actualDate = actualDate;
	}

	/**
	 * @return the nextDate
	 */
	public LocalDate getNextDate() {
		return nextDate;
	}

	/**
	 * @param nextDate the nextDate to set
	 */
	public void setNextDate(LocalDate nextDate) {
		this.nextDate = nextDate;
	}

	/**
	 * @return the boostAthlete
	 */
	@OneToOne
	@JoinColumn(name = "boost_athlete_id")
	public DietBoostAthlete getBoostAthlete() {
		return boostAthlete;
	}

	/**
	 * @param boostAthlete the boostAthlete to set
	 */
	public void setBoostAthlete(DietBoostAthlete boostAthlete) {
		this.boostAthlete = boostAthlete;
	}

	/**
	 * @return the boostAthletes
	 */
	@OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name = "boost_athletes_id")
	public List<DietBoostAthlete> getBoostAthletes() {
		return boostAthletes;
	}

	/**
	 * @param boostAthletes the boostAthletes to set
	 */
	public void setBoostAthletes(List<DietBoostAthlete> boostAthletes) {
		this.boostAthletes = boostAthletes;
	}

	@Override
	public String toString() {
		return "DietBoostDay [id=" + id + ", actualDate=" + actualDate + ", nextDate=" + nextDate + ", boostAthlete="
				+ boostAthlete + ", boostAthletes=" + boostAthletes + "]";
	}
	
	
}
