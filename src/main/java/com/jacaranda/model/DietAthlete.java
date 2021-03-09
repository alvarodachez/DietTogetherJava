package com.jacaranda.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class DietAthlete implements Serializable {

    /**
     * SERIAL ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id de la entidad en bbdd
     */
    private Long id;

    private String name;

    private String surname;

    private LocalDate birthDay;

    private DietPhysicalData physicalData;

    private Double totalWeightDifference;

    private Double gamePoints;

    private Double totalPoints;

    private List<String> friends;

    private List<DietGroup> groups;

    private DietGroup actualGroup;

    private List<DietPrivateActivity> privateActivities;

    private DietPrivateActivity actualPrivateActivity;

    private DietRegime regime;

    private DietMailBox mailBox;

    private List<DietReport> reports;
    
    private List<DietReport> reportsAssigned;

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
    public LocalDate getBirthDay() {
        return birthDay;
    }

    /**
     * @param birthDay the birthDay to set
     */
    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    /**
     * @return the physicalData
     */
    @OneToOne
    @JoinColumn(name = "physical_data_id")
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
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "athleteFather")
    @ElementCollection
    public List<String> getFriends() {
        return friends;
    }

    /**
     * @param friends the friends to set
     */
    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    /**
     * @return the groups
     */
    @OneToMany
    @JoinColumn(name = "group_id")
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
     * @return the actualGroup
     */
    @OneToOne
    @JoinColumn(name = "actual_group_id")
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
    @OneToMany
    @JoinColumn(name = "privateActivity_id")
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
    @OneToOne
    @JoinColumn(name = "actual_privateActivity_id")
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
    @OneToOne
    @JoinColumn(name = "regime_id")
    public DietRegime getRegime() {
        return regime;
    }

    /**
     * @param regime the regime to set
     */
    public void setRegime(DietRegime regime) {
        this.regime = regime;
    }

    /**
     * @return the mailBox
     */
    @OneToOne
    @JoinColumn(name = "mailBox_id")
    public DietMailBox getMailBox() {
        return mailBox;
    }

    /**
     * @param mailBox the mailBox to set
     */
    public void setMailBox(DietMailBox mailBox) {
        this.mailBox = mailBox;
    }

    @OneToMany
    @JoinColumn(name = "athlete_id")
    public List<DietReport> getReports() {
        return reports;
    }

    public void setReports(List<DietReport> reports) {
        this.reports = reports;
    }

	/**
	 * @return the reportsAssigned
	 */
    @OneToMany
    @JoinColumn(name = "admin_id")
	public List<DietReport> getReportsAssigned() {
		return reportsAssigned;
	}

	/**
	 * @param reportsAssigned the reportsAssigned to set
	 */
	public void setReportsAssigned(List<DietReport> reportsAssigned) {
		this.reportsAssigned = reportsAssigned;
	}
    
    
}
