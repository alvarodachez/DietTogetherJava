package com.jacaranda.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jacaranda.security.model.DietUser;

@Repository
public interface DietUserRepository extends JpaRepository<DietUser, Long> {

	public Optional<DietUser> findByUsername(String username);
	
	@Query(value="SELECT * FROM diet_user WHERE username LIKE ?1%", nativeQuery = true)
	public List<DietUser> findByInitials(@Param("initials")String initials);
	
	public boolean existsByUsername(String username);
	
	@Query(value="SELECT username FROM diet_user WHERE athlete_id = ?1", nativeQuery = true)
	public String findUsernameByAthleteId(@Param("id")Long id);
}
