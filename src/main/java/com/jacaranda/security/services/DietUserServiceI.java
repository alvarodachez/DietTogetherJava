package com.jacaranda.security.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.model.dto.DietUserDTO;

public interface DietUserServiceI {

	public UserDetails loadUserByUsername(String username);

	public DietUserDTO createNewUser(DietUserDTO userDTO);
	
	public DietUserDTO loginUser(DietUserDTO userDTO);
}
