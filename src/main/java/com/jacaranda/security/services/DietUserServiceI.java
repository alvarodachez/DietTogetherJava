package com.jacaranda.security.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.jacaranda.exceptions.DietUserException;
import com.jacaranda.security.model.dto.DietUserDTO;

public interface DietUserServiceI {

	public UserDetails loadUserByUsername(String username);

	public DietUserDTO createNewUser(DietUserDTO userDTO) throws DietUserException;
	
	public DietUserDTO loginUser(DietUserDTO userDTO);
}
