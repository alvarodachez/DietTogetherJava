package com.jacaranda.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class DietRegime implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private List<DietDish> dishes;

	private List<DietDayRegime> days;

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
	 * @return the dishes
	 */

	@OneToMany
	@JoinColumn(name = "dish_id")
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
	 * @return the days
	 */
	@OneToMany
	@JoinColumn(name = "day_id")
	public List<DietDayRegime> getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(List<DietDayRegime> days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "DietRegime [id=" + id + ", dishes=" + dishes + ", days=" + days + "]";
	}

}
