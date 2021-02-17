package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class DietGroup implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;
	
	private List<DietAthlete> athletes;
	
	private LocalDateTime expireDate;

	private DietChallengeType challengeType;

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
	@OneToMany
	@JoinColumn(name = "athletes_id")
	public List<DietAthlete> getAthletes() {
		return athletes;
	}

	/**
	 * @param athletes the athletes to set
	 */
	public void setAthletes(List<DietAthlete> athletes) {
		this.athletes = athletes;
	}

	/**
	 * @return the expireDate
	 */
	public LocalDateTime getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(LocalDateTime expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the challengeType
	 */
	@Column(name="challenge_type")
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

}
