package com.jacaranda.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class DietImc implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Double imcValue;

	private DietPhysicalData physicalDataId;
	
	
	private DietRegister registerId;

	private DietScale actualScale;

//	private Map<DietScale, Integer> scales = new LinkedHashMap();

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
	 * @return the physicalDataId
	 */
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="physicalData_id")
	public DietPhysicalData getPhysicalDataId() {
		return physicalDataId;
	}

	/**
	 * @param physicalDataId the physicalDataId to set
	 */
	public void setPhysicalDataId(DietPhysicalData physicalDataId) {
		this.physicalDataId = physicalDataId;
	}

	
	/**
	 * @return the registerId
	 */
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="register_id")
	public DietRegister getRegisterId() {
		return registerId;
	}

	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(DietRegister registerId) {
		this.registerId = registerId;
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
//	public Map<DietScale, Integer> getScales() {
//		return scales;
//	}
//
//	/**
//	 * @param scales the scales to set
//	 */
//	public void setScales(Map<DietScale, Integer> scales) {
//		this.scales = scales;
//	}

}
