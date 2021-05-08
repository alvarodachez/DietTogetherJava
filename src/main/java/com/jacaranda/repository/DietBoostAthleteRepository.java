package com.jacaranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietBoostAthlete;

@Repository
public interface DietBoostAthleteRepository extends JpaRepository<DietBoostAthlete,Long>{

}
