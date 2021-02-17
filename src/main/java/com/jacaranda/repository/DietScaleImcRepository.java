package com.jacaranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietScaleImc;

@Repository
public interface DietScaleImcRepository extends JpaRepository<DietScaleImc, Long> {

}
