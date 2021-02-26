package com.jacaranda.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class DietDayRegime implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	public Long id;

	public DietDay day;

	public List<DietMealRegime> meals;

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
	 * @return the day
	 */
	@Column(name="day")
	@Enumerated(value = EnumType.STRING)
	public DietDay getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(DietDay day) {
		this.day = day;
	}

	/**
	 * @return the meals
	 */
	@OneToMany
	@JoinColumn(name = "meal_id")
	public List<DietMealRegime> getMeals() {
		return meals;
	}

	/**
	 * @param meals the meals to set
	 */
	public void setMeals(List<DietMealRegime> meals) {
		this.meals = meals;
	}

}
