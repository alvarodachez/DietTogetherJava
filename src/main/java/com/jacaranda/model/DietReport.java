package com.jacaranda.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class DietReport implements Serializable {

    /** SERIAL ID */
    private static final long serialVersionUID = 1L;

    private Long id;

    private DietReportCategory reportCategory;

    private String description;


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
}
