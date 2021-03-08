package com.jacaranda.services.impl;

import com.jacaranda.model.DietReport;
import com.jacaranda.repository.DietReportRepository;
import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.services.DietReportServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("reportService")
public class DietReportServiceImpl implements DietReportServiceI {

	@Autowired
	DietReportRepository reportRepo;

	@Autowired
	DietUserRepository userRepo;

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
		reportRepo.save(reportToCreate);

		user.getAthleteId().getReports().add(reportToCreate);
		userRepo.save(user);

		return reportToCreate;
	}
}
