package com.jacaranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietRegime;

@Repository
public interface DietRegimeRepository extends JpaRepository<DietRegime, Long>{

}
