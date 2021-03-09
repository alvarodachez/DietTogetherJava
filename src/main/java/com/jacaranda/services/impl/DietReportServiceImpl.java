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
}
