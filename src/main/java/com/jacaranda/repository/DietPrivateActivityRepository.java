package com.jacaranda.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietPrivateActivity;

@Repository
public interface DietPrivateActivityRepository extends JpaRepository<DietPrivateActivity, Long> {

	@Query(value="SELECT * FROM diet_private_activity WHERE enabled = 1 AND private_register_mode LIKE 'PROGRESSIVE' AND actual_progressive_date = ?1", nativeQuery = true)
	public List<DietPrivateActivity> findByEnabledProgressiveOnDate(@Param("date") LocalDate date);
}
