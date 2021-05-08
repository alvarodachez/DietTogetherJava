package com.jacaranda.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jacaranda.model.DietReport;
import com.jacaranda.model.DietReportStatus;
import com.jacaranda.repository.DietAthleteRepository;
import com.jacaranda.repository.DietReportRepository;
import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.services.DietReportServiceI;

@Service("reportService")
public class DietReportServiceImpl implements DietReportServiceI {

	@Autowired
	DietReportRepository reportRepo;

	@Autowired
	DietUserRepository userRepo;
	
	@Autowired
	DietAthleteRepository athleteRepo;

	@Override
	public List<DietReport> getUserReports(String username) {

		/** Obtenemos usuario por nombre de usuario */
		DietUser user = userRepo.findByUsername(username).get();

		return user.getAthleteId().getReports();
	}

	@Override
	public List<DietReport> getAllReports() {
		return reportRepo.findAll();
	}

	@Override
	public DietReport createReport(String username, DietReport report) {
		/** Obtenemos usuario por nombre de usuario */
		DietUser user = userRepo.findByUsername(username).get();

		DietReport reportToCreate = new DietReport();
		reportToCreate.setReportCategory(report.getReportCategory());
		reportToCreate.setDescription(report.getDescription());
		reportToCreate.setCreateReportDate(LocalDate.now());
		reportToCreate.setAthleteHasReported(username);
		reportToCreate.setReportStatus(DietReportStatus.PENDING);
		reportToCreate.setAdminToResolve("No asignado");
		reportRepo.save(reportToCreate);

		user.getAthleteId().getReports().add(reportToCreate);
		
		athleteRepo.save(user.getAthleteId());
		userRepo.save(user);

		return reportToCreate;
	}

	@Override
	public DietReport assignReport(String username, Long id) {
		DietUser user = userRepo.findByUsername(username).get();
		DietReport report = reportRepo.findById(id).get();
		
		report.setAdminToResolve(username);
		report.setReportStatus(DietReportStatus.ASSIGNED);
		
		reportRepo.save(report);
		
		user.getAthleteId().getReportsAssigned().add(report);
		
		athleteRepo.save(user.getAthleteId());
		userRepo.save(user);
		return report;
	}

	@Override
	public List<DietReport> getAssignedReports(String username) {
		DietUser user = userRepo.findByUsername(username).get();
		
		return user.getAthleteId().getReportsAssigned();
	}

	@Override
	public DietReport setAdminAnnotation(String username, Long id, String annotation) {
		
		DietUser user = userRepo.findByUsername(username).get();
		DietReport report = reportRepo.findById(id).get();
		
		report.setAdminAnnotations(annotation);
		
		reportRepo.save(report);
		
		athleteRepo.save(user.getAthleteId());
		
		userRepo.save(user);
		
		return report;
	}

	@Override
	public DietReport setResolvedStatus(String username, Long id) {
		
		DietUser user = userRepo.findByUsername(username).get();
		DietReport report = reportRepo.findById(id).get();
		
		report.setReportStatus(DietReportStatus.RESOLVED);
		
		reportRepo.save(report);
		athleteRepo.save(user.getAthleteId());
		userRepo.save(user);
		
		return report;
	}
	
	@Override
	public DietReport setPendingStatus(String username, Long id) {
		
		DietUser user = userRepo.findByUsername(username).get();
		DietReport report = reportRepo.findById(id).get();
		
		int position = 0;
		
		for(DietReport reportToDelete:user.getAthleteId().getReportsAssigned()) {
			if(reportToDelete.getId() == id) {
				position = user.getAthleteId().getReportsAssigned().indexOf(reportToDelete);
			}
		}
		
		user.getAthleteId().getReportsAssigned().remove(position);
		
		report.setAdminToResolve("No asignado");
		report.setReportStatus(DietReportStatus.PENDING);
		
		reportRepo.save(report);
		
		
		
		
		athleteRepo.save(user.getAthleteId());
		userRepo.save(user);
		
		return report;
	}
	
	

	@Override
	public DietReport getReport(Long id) {
		
		return reportRepo.findById(id).get();
	}
	
	
	
	
	
	
	
	
	
}
