package com.jacaranda.services;

import com.jacaranda.model.DietReport;

import java.util.List;

public interface DietReportServiceI {

    public List<DietReport> getUserReports(String username);

    public List<DietReport> getAllReports();

    public DietReport createReport(String username, DietReport report);

}
