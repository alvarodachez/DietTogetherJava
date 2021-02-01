package com.jacaranda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jacaranda.model.DietUser;

@Repository
public interface DietUserRepository extends JpaRepository<DietUser, Long> {

	public Optional<DietUser> findByUsername(String username);
}
