package com.jacaranda.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.jacaranda.security.model.DietUser;

@Entity
public class DietAthlete implements Serializable {

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	/** Id de la entidad en bbdd */
	private Long id;

	/** Nombre de usuario relacionado con DietUser */
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id")
//	private DietUser userId;
	private String name;

	private String surname;

	private LocalDateTime birthDay;

	private DietAthlete athleteFather;

	private DietPhysicalData physicalData;

	private Double totalWeightDifference;

	private Double gamePoints;

	private Double totalPoints;

	private List<DietAthlete> friends;

	private List<DietGroup> groups;

	private DietGroup groupId;

	private DietGroup actualGroup;

	private List<DietPrivateActivity> privateActivities;

	private DietPrivateActivity actualPrivateActivity;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "athleteId", cascade = CascadeType.ALL)
	private DietRegime regime;

	/**
	 * @return the id
	 */
	@Id
	@Column(name = "athlete_id")
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
	 * @return the userId
	 */
//	public DietUser getUserId() {
//		return userId;
//	}
//
//	/**
//	 * @param userId the userId to set
//	 */
//	public void setUserId(DietUser userId) {
//		this.userId = userId;
//	}

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
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the birthDay
	 */
	public LocalDateTime getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(LocalDateTime birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * @return the athleteFather
	 */
	@ManyToOne
	@JoinColumn(name = "athlete_father", referencedColumnName = "athlete_id")
	public DietAthlete getAthleteFather() {
		return athleteFather;
	}

	/**
	 * @param athleteFather the athleteFather to set
	 */
	public void setAthleteFather(DietAthlete athleteFather) {
		this.athleteFather = athleteFather;
	}

	/**
	 * @return the physicalData
	 */
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "athleteId", cascade = CascadeType.ALL)
	public DietPhysicalData getPhysicalData() {
		return physicalData;
	}

	/**
	 * @param physicalData the physicalData to set
	 */
	public void setPhysicalData(DietPhysicalData physicalData) {
		this.physicalData = physicalData;
	}

	/**
	 * @return the totalWeightDifference
	 */
	public Double getTotalWeightDifference() {
		return totalWeightDifference;
	}

	/**
	 * @param totalWeightDifference the totalWeightDifference to set
	 */
	public void setTotalWeightDifference(Double totalWeightDifference) {
		this.totalWeightDifference = totalWeightDifference;
	}

	/**
	 * @return the gamePoints
	 */
	public Double getGamePoints() {
		return gamePoints;
	}

	/**
	 * @param gamePoints the gamePoints to set
	 */
	public void setGamePoints(Double gamePoints) {
		this.gamePoints = gamePoints;
	}

	/**
	 * @return the totalPoints
	 */
	public Double getTotalPoints() {
		return totalPoints;
	}

	/**
	 * @param totalPoints the totalPoints to set
	 */
	public void setTotalPoints(Double totalPoints) {
		this.totalPoints = totalPoints;
	}

	/**
	 * @return the friends
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "athleteFather")
	public List<DietAthlete> getFriends() {
		return friends;
	}

	/**
	 * @param friends the friends to set
	 */
	public void setFriends(List<DietAthlete> friends) {
		this.friends = friends;
	}

	/**
	 * @return the groups
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "athleteId")
	public List<DietGroup> getGroups() {
		return groups;
	}

	/**
	 * @param groups the groups to set
	 */
	public void setGroups(List<DietGroup> groups) {
		this.groups = groups;
	}

	/**
	 * @return the groupId
	 */
	@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	public DietGroup getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(DietGroup groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the actualGroup
	 */
	public DietGroup getActualGroup() {
		return actualGroup;
	}

	/**
	 * @param actualGroup the actualGroup to set
	 */
	public void setActualGroup(DietGroup actualGroup) {
		this.actualGroup = actualGroup;
	}

	/**
	 * @return the privateActivities
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "athleteId")
	public List<DietPrivateActivity> getPrivateActivities() {
		return privateActivities;
	}

	/**
	 * @param privateActivities the privateActivities to set
	 */
	public void setPrivateActivities(List<DietPrivateActivity> privateActivities) {
		this.privateActivities = privateActivities;
	}

	/**
	 * @return the actualPrivateActivity
	 */
	public DietPrivateActivity getActualPrivateActivity() {
		return actualPrivateActivity;
	}

	/**
	 * @param actualPrivateActivity the actualPrivateActivity to set
	 */
	public void setActualPrivateActivity(DietPrivateActivity actualPrivateActivity) {
		this.actualPrivateActivity = actualPrivateActivity;
	}

	/**
	 * @return the regime
	 */
	public DietRegime getRegime() {
		return regime;
	}

	/**
	 * @param regime the regime to set
	 */
	public void setRegime(DietRegime regime) {
		this.regime = regime;
	}

}
