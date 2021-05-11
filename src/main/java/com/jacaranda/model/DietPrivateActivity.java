package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDate;
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
public class DietPrivateActivity implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private LocalDate expireDate;

	private Double weightObjective;

	private String athlete;

	private LocalDate createDate;

	private DietPrivateActivityMode privateActivityMode;

	private DietPrivateRegisterMode privateRegisterMode;

	private Boolean enabled;

	private List<DietRegister> registers;

	private LocalDate actualProgressiveDate;

	private LocalDate nextProgressiveDate;

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
	 * @return the expireDate
	 */
	public LocalDate getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the weightObjective
	 */
	public Double getWeightObjective() {
		return weightObjective;
	}

	/**
	 * @param weightObjective the weightObjective to set
	 */
	public void setWeightObjective(Double weightObjective) {
		this.weightObjective = weightObjective;
	}

	public String getAthlete() {
		return athlete;
	}

	public void setAthlete(String athlete) {
		this.athlete = athlete;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	@Column(name = "private_activity_mode")
	@Enumerated(EnumType.STRING)
	public DietPrivateActivityMode getPrivateActivityMode() {
		return privateActivityMode;
	}

	public void setPrivateActivityMode(DietPrivateActivityMode privateActivityMode) {
		this.privateActivityMode = privateActivityMode;
	}

	@Column(name = "private_register_mode")
	@Enumerated(EnumType.STRING)
	public DietPrivateRegisterMode getPrivateRegisterMode() {
		return privateRegisterMode;
	}

	public void setPrivateRegisterMode(DietPrivateRegisterMode privateRegisterMode) {
		this.privateRegisterMode = privateRegisterMode;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@OneToMany
	@JoinColumn(name = "register_private_id")
	public List<DietRegister> getRegisters() {
		return registers;
	}

	public void setRegisters(List<DietRegister> registers) {
		this.registers = registers;
	}

	public LocalDate getActualProgressiveDate() {
		return actualProgressiveDate;
	}

	public void setActualProgressiveDate(LocalDate actualProgressiveDate) {
		this.actualProgressiveDate = actualProgressiveDate;
	}

	public LocalDate getNextProgressiveDate() {
		return nextProgressiveDate;
	}

	public void setNextProgressiveDate(LocalDate nextProgressiveDate) {
		this.nextProgressiveDate = nextProgressiveDate;
	}

}
