package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DietGroupRequest implements Serializable{

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	private Long id;

	private DietRequestStatus requestStatus;

	private LocalDate requestDate;

	private String claimantAthlete;

	private String requestedAthlete;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "groupRequest_id")
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
	 * @return the requestStatus
	 */
	@Enumerated(EnumType.STRING)
	public DietRequestStatus getRequestStatus() {
		return requestStatus;
	}

	/**
	 * @param requestStatus the requestStatus to set
	 */
	public void setRequestStatus(DietRequestStatus requestStatus) {
		this.requestStatus = requestStatus;
	}

	/**
	 * @return the requestDate
	 */
	public LocalDate getRequestDate() {
		return requestDate;
	}

	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	/**
	 * @return the claimantAthlete
	 */
	public String getClaimantAthlete() {
		return claimantAthlete;
	}

	/**
	 * @param claimantAthlete the claimantAthlete to set
	 */
	public void setClaimantAthlete(String claimantAthlete) {
		this.claimantAthlete = claimantAthlete;
	}

	/**
	 * @return the requestedAthlete
	 */
	public String getRequestedAthlete() {
		return requestedAthlete;
	}

	/**
	 * @param requestedAthlete the requestedAthlete to set
	 */
	public void setRequestedAthlete(String requestedAthlete) {
		this.requestedAthlete = requestedAthlete;
	}

	@Override
	public String toString() {
		return "DietGroupRequest [id=" + id + ", requestStatus=" + requestStatus + ", requestDate=" + requestDate
				+ ", claimantAthlete=" + claimantAthlete + ", requestedAthlete=" + requestedAthlete + "]";
	}
}
