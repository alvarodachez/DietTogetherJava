package com.jacaranda.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class DietPhysicalData implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Double weight;

	private Double height;
	
	private DietImc imc;

	private List<DietRegister> registers;

	private DietRegister lastRegister;

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
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * @return the height
	 */
	public Double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Double height) {
		this.height = height;
	}

	/**
	 * @return the imc
	 */
	@OneToOne
	@JoinColumn(name="imc_id")
	public DietImc getImc() {
		return imc;
	}

	/**
	 * @param imc the imc to set
	 */
	public void setImc(DietImc imc) {
		this.imc = imc;
	}

	/**
	 * @return the registers
	 */
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "physicalDataId")
	public List<DietRegister> getRegisters() {
		return registers;
	}

	/**
	 * @param registers the registers to set
	 */
	public void setRegisters(List<DietRegister> registers) {
		this.registers = registers;
	}

	/**
	 * @return the lastRegister
	 */
	public DietRegister getLastRegister() {
		return lastRegister;
	}
	
	public void imcCalculation() {
		Double imc = this.weight/(this.height*this.height);
		
		this.imc.setImcValue(imc);
	}

	/**
	 * @param lastRegister the lastRegister to set
	 */
	public void setLastRegister(DietRegister lastRegister) {
		this.lastRegister = lastRegister;
	}

}
