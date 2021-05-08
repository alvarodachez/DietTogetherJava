package com.jacaranda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietDish;

@Repository
public interface DietDishRepository extends JpaRepository<DietDish, Long>{


	@Query(value="SELECT * FROM diet_dish WHERE name LIKE ?1% AND dish_id = ?2", nativeQuery = true)
	public List<DietDish> findByInitials(@Param("initials")String initials, Long id);
}
