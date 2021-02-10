package com.jacaranda.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class DietRegime implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private DietAthlete athleteId;

	private List<DietDish> dishes;

	//private Map<DietDay, Map<DietMeal, DietDish>> regime = new LinkedHashMap();

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
	 * @return the athleteId
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public DietAthlete getAthleteId() {
		return athleteId;
	}

	/**
	 * @param athleteId the athleteId to set
	 */
	public void setAthleteId(DietAthlete athleteId) {
		this.athleteId = athleteId;
	}

	/**
	 * @return the dishes
	 */

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "regimeId")
	public List<DietDish> getDishes() {
		return dishes;
	}

	/**
	 * @param dishes the dishes to set
	 */
	public void setDishes(List<DietDish> dishes) {
		this.dishes = dishes;
	}

	/**
	 * @return the regime
	 */
//	public Map<DietDay, Map<DietMeal, DietDish>> getRegime() {
//		return regime;
//	}
//
//	/**
//	 * @param regime the regime to set
//	 */
//	public void setRegime(Map<DietDay, Map<DietMeal, DietDish>> regime) {
//		this.regime = regime;
//	}

}
