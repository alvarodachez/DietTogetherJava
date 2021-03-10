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
public class DietReport implements Serializable {

    /** SERIAL ID */
    private static final long serialVersionUID = 1L;

    private Long id;

    private DietReportCategory reportCategory;

    private String description;
    
    private LocalDate createReportDate;
    
    private String athleteHasReported;
    
    private String adminAnnotations;
    
    private String adminToResolve;
    
    private DietReportStatus reportStatus;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    public DietReportCategory getReportCategory() {
        return reportCategory;
    }

    public void setReportCategory(DietReportCategory reportCategory) {
        this.reportCategory = reportCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	/**
	 * @return the createReportDate
	 */
	public LocalDate getCreateReportDate() {
		return createReportDate;
	}

	/**
	 * @param createReportDate the createReportDate to set
	 */
	public void setCreateReportDate(LocalDate createReportDate) {
		this.createReportDate = createReportDate;
	}

	/**
	 * @return the athleteHasReported
	 */
	public String getAthleteHasReported() {
		return athleteHasReported;
	}

	/**
	 * @param athleteHasReported the athleteHasReported to set
	 */
	public void setAthleteHasReported(String athleteHasReported) {
		this.athleteHasReported = athleteHasReported;
	}

	/**
	 * @return the adminAnnotations
	 */
	public String getAdminAnnotations() {
		return adminAnnotations;
	}

	/**
	 * @param adminAnnotations the adminAnnotations to set
	 */
	public void setAdminAnnotations(String adminAnnotations) {
		this.adminAnnotations = adminAnnotations;
	}

	/**
	 * @return the adminToResolve
	 */
	public String getAdminToResolve() {
		return adminToResolve;
	}

	/**
	 * @param adminToResolve the adminToResolve to set
	 */
	public void setAdminToResolve(String adminToResolve) {
		this.adminToResolve = adminToResolve;
	}

	/**
	 * @return the reportStatus
	 */
	@Column(name = "report_status")
    @Enumerated(EnumType.STRING)
	public DietReportStatus getReportStatus() {
		return reportStatus;
	}

	/**
	 * @param reportStatus the reportStatus to set
	 */
	public void setReportStatus(DietReportStatus reportStatus) {
		this.reportStatus = reportStatus;
	}
	
	
	
	
    
    
}
