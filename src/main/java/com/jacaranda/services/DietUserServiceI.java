package com.jacaranda.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.jacaranda.model.DietUser;
import com.jacaranda.model.dto.DietUserDTO;

public interface DietUserServiceI {

	public UserDetails loadUserByUsername(String username);

	public DietUser createNewUser(DietUserDTO userDTO);
}
