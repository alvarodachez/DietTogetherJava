package com.jacaranda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietAthlete;

@Repository
public interface DietAthleteRepository extends JpaRepository<DietAthlete, Long>{

	@Query(value = "SELECT * FROM diet_athlete athlete WHERE athlete.actual_group_id = ?1 AND athlete.game_points IN (SELECT MIN(athlete2.game_points) FROM diet_athlete athlete2 where athlete2.actual_group_id = ?1)", nativeQuery = true)
	public List<DietAthlete> findByMinimumPoints(@Param("id") Long id);
}
