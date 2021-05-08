package com.jacaranda.services;

import com.jacaranda.model.DietReport;

import java.util.List;

public interface DietReportServiceI {

    public List<DietReport> getUserReports(String username);

    public List<DietReport> getAllReports();

    public DietReport createReport(String username, DietReport report);
    
    public DietReport assignReport(String username, Long id);
    
    public List<DietReport> getAssignedReports(String username);
    
    public DietReport setAdminAnnotation(String username, Long id, String annotation);
    
    public DietReport setResolvedStatus(String username, Long id);
    
    public DietReport setPendingStatus(String username, Long id);
    
    public DietReport getReport(Long id);

}
