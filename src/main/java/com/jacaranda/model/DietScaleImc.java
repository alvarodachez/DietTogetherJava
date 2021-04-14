package com.jacaranda.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DietScaleImc implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private DietScale scale;

	private Double weightScale;

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
	 * @return the scale
	 */
	@Enumerated(EnumType.STRING)
	public DietScale getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(DietScale scale) {
		this.scale = scale;
	}

	/**
	 * @return the weightScale
	 */
	public Double getWeightScale() {
		return weightScale;
	}

	/**
	 * @param weightScale the weightScale to set
	 */
	public void setWeightScale(Double weightScale) {
		this.weightScale = weightScale;
	}

	@Override
	public String toString() {
		return "DietScaleImc [id=" + id + ", scale=" + scale + ", weightScale=" + weightScale + "]";
	}

}
