package com.jacaranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietMealRegime;

@Repository
public interface DietMealRegimeRepository extends JpaRepository<DietMealRegime, Long> {

}
