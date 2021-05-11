package com.jacaranda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietRegister;

@Repository
public interface DietRegisterRepository extends JpaRepository<DietRegister,Long>{

	@Query(value="select * from diet_register where register_private_id = ?1 order by weight_date asc", nativeQuery = true)
	public List<DietRegister> findByPrivateActivity(@Param("privateId") String privateId);
	
}
