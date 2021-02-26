package com.jacaranda.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class DietMealRegime implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private DietMeal meal;

	private DietDish dish;

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
	 * @return the meal
	 */
	@Column(name="meal")
	@Enumerated(value = EnumType.STRING)
	public DietMeal getMeal() {
		return meal;
	}

	/**
	 * @param meal the meal to set
	 */
	public void setMeal(DietMeal meal) {
		this.meal = meal;
	}

	/**
	 * @return the dish
	 */
	@OneToOne
	@JoinColumn(name="dish_id")
	public DietDish getDish() {
		return dish;
	}

	/**
	 * @param dish the dish to set
	 */
	public void setDish(DietDish dish) {
		this.dish = dish;
	}

}
