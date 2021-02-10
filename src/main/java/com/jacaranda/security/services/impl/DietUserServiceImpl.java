package com.jacaranda.security.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jacaranda.security.model.DietUser;
import com.jacaranda.security.model.dto.DietUserDTO;
import com.jacaranda.security.model.dto.DietUserDTOConverter;
import com.jacaranda.security.repository.DietUserRepository;
import com.jacaranda.security.services.DietUserServiceI;

@Service("userService")
public class DietUserServiceImpl implements DietUserServiceI {

	@Autowired
	private DietUserRepository repository;

	@Autowired
	private DietUserDTOConverter converter;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}

	public DietUser createNewUser(DietUserDTO userDTO) {
		return repository.save(converter.fromUserDTOToUser(userDTO));
	}
}
