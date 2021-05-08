package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class DietGroup implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private List<String> athletes;

	private LocalDate creationDate;

	private LocalDate expireDate;

	private DietChallengeType challengeType;

	private Boolean enabled;
	
	private List<DietRegister> registersToVerify;
	
	private DietBoostDay boostDay;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the athletes
	 */
	@ElementCollection
	public List<String> getAthletes() {
		return athletes;
	}

	/**
	 * @param athletes the athletes to set
	 */
	public void setAthletes(List<String> athletes) {
		this.athletes = athletes;
	}

	/**
	 * @return the creationDate
	 */
	public LocalDate getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
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
	 * @return the challengeType
	 */
	@Column(name = "challenge_type")
	@Enumerated(EnumType.STRING)
	public DietChallengeType getChallengeType() {
		return challengeType;
	}

	/**
	 * @param challengeType the challengeType to set
	 */
	public void setChallengeType(DietChallengeType challengeType) {
		this.challengeType = challengeType;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the registersToVerify
	 */
	@OneToMany
    @JoinColumn(name = "register_id")
	public List<DietRegister> getRegistersToVerify() {
		return registersToVerify;
	}

	/**
	 * @param registersToVerify the registersToVerify to set
	 */
	public void setRegistersToVerify(List<DietRegister> registersToVerify) {
		this.registersToVerify = registersToVerify;
	}

	/**
	 * @return the boostDay
	 */
	@OneToOne
    @JoinColumn(name = "boost_day_id")
	public DietBoostDay getBoostDay() {
		return boostDay;
	}

	/**
	 * @param boostDay the boostDay to set
	 */
	public void setBoostDay(DietBoostDay boostDay) {
		this.boostDay = boostDay;
	}

	@Override
	public String toString() {
		return "DietGroup [id=" + id + ", name=" + name + ", athletes=" + athletes + ", creationDate=" + creationDate
				+ ", expireDate=" + expireDate + ", challengeType=" + challengeType + ", enabled=" + enabled
				+ ", registersToVerify=" + registersToVerify + ", boostDay=" + boostDay + "]";
	}
	
	

}
