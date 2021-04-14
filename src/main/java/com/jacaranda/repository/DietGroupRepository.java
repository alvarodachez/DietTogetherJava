package com.jacaranda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietGroup;

@Repository
public interface DietGroupRepository extends JpaRepository<DietGroup,Long>{

	@Query(value="SELECT * FROM diet_group WHERE enabled = 1", nativeQuery = true)
	public List<DietGroup> findByEnabled();
}
