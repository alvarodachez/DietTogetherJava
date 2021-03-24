package com.jacaranda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietDish;

@Repository
public interface DietDishRepository extends JpaRepository<DietDish, Long>{

}
