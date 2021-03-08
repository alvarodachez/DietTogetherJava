package com.jacaranda.repository;

import com.jacaranda.model.DietReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietReportRepository extends JpaRepository<DietReport, Long> {
}
