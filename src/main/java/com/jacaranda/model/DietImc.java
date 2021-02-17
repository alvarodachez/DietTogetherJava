package com.jacaranda.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class DietImc implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Double imcValue;

	private DietScale actualScale;

	private List<DietScaleImc> scales;

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
	 * @return the imcValue
	 */
	public Double getImcValue() {
		return imcValue;
	}

	/**
	 * @param imcValue the imcValue to set
	 */
	public void setImcValue(Double imcValue) {
		this.imcValue = imcValue;
	}

	/**
	 * @return the actualScale
	 */
	
	@Enumerated(EnumType.STRING)
	public DietScale getActualScale() {
		return actualScale;
	}

	/**
	 * @param actualScale the actualScale to set
	 */
	public void setActualScale(DietScale actualScale) {
		this.actualScale = actualScale;
	}

	/**
	 * @return the scales
	 */
	@OneToMany
	@JoinColumn(name = "meal_id")
	public List<DietScaleImc> getScales() {
		return scales;
	}

	/**
	 * @param scales the scales to set
	 */
	public void setScales(List<DietScaleImc> scales) {
		this.scales = scales;
	}


	

}
